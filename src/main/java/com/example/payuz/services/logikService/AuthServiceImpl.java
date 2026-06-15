package com.example.payuz.services.logikService;

import com.example.payuz.dto.requests.LoginRequest;
import com.example.payuz.dto.requests.SignupRequest;
import com.example.payuz.dto.requests.VerifySmsRequest;
import com.example.payuz.dto.responses.AuthResponse;
import com.example.payuz.dto.responses.UserResponseDto;
import com.example.payuz.entity.SmsCode;
import com.example.payuz.entity.UserBase;
import com.example.payuz.enums.user.UserRole;
import com.example.payuz.enums.user.UserStatus;
import com.example.payuz.exceptions.AlreadyExistsException;
import com.example.payuz.exceptions.BadRequestException;
import com.example.payuz.exceptions.NotFoundException;
import com.example.payuz.payload.ApiResponse;
import com.example.payuz.repositories.SmsRepository;
import com.example.payuz.repositories.UserRepository;
import com.example.payuz.services.AuthService;
import com.example.payuz.token.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Random;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final SmsRepository smsRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final Random random = new Random();


    @Override
    public ApiResponse register(SignupRequest signupRequest) {

        if (userRepository.existsByPhoneNumber(signupRequest.getPhoneNumber())) {
            throw new AlreadyExistsException("Bu telefon raqam oldin ro'yxatdan o'tgan");
        }

        UserBase user = new UserBase();
        user.setFirstName(signupRequest.getFirstName());
        user.setLastName(signupRequest.getLastName());
        user.setPhoneNumber(signupRequest.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setRole(UserRole.USER);
        user.setStatus(UserStatus.PENDING);
        user.setPhoneVerified(false);
        userRepository.save(user);

        SmsCode smsCode = SmsCode.builder()
                .phoneNumber(user.getPhoneNumber())
                .code(generateCode())
                .expireTime(Timestamp.from(Instant.now().plusSeconds(300)))
                .used(false)
                .build();

        smsRepository.save(smsCode);

        return new ApiResponse("Ro'yxatdan o'tish muvaffaqiyatli. SMS kod: " + smsCode.getCode(), true, user);
    }

    @Override
    public ApiResponse verifySms(VerifySmsRequest verifySmsRequest) {

        SmsCode smsCode = smsRepository.findLatestActiveCode(verifySmsRequest.getPhoneNumber())
                .orElseThrow(() -> new NotFoundException("Faol SMS kod topilmadi"));

        if (smsCode.getExpireTime().before(Timestamp.from(Instant.now()))) {
            throw new BadRequestException("SMS kod muddati tugagan");
        }

        if (!smsCode.getCode().equals(verifySmsRequest.getCode())) {
            throw new BadRequestException("SMS kod noto'g'ri");
        }

        UserBase user = userRepository.findByPhoneNumber(verifySmsRequest.getPhoneNumber())
                .orElseThrow(() -> new NotFoundException("Foydalanuvchi topilmadi"));

        smsCode.setUsed(true);
        user.setPhoneVerified(true);
        user.setStatus(UserStatus.ACTIVE);

        smsRepository.save(smsCode);
        userRepository.save(user);

        return new ApiResponse("Telefon raqam tasdiqlandi", true, verifySmsRequest.getPhoneNumber());
    }

    @Override
    public ApiResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getPhoneNumber(),
                        loginRequest.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserBase user = userRepository.findByPhoneNumber(userDetails.getUsername())
            .orElseThrow(() -> new NotFoundException("Foydalanuvchi topilmadi"));

        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        AuthResponse authResponse = AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userResponseDto(
                        UserResponseDto.builder()
                                .id(user.getId())
                                .firstName(user.getFirstName())
                                .lastName(user.getLastName())
                                .phoneNumber(user.getPhoneNumber())
                                .phoneVerified(user.isPhoneVerified())
                                .role(user.getRole())
                                .status(user.getStatus())
                                .build()
                )
                .build();

        return new ApiResponse("Muvaffaqiyatli login", true, authResponse);
    }

    private String generateCode() {
        return String.valueOf(100000 + random.nextInt(900000));
    }
}
