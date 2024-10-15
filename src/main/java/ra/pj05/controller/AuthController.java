package ra.pj05.controller;


import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.pj05.constants.EHttpStatus;
import ra.pj05.model.dto.request.FormLogin;
import ra.pj05.model.dto.request.FormRegister;
import ra.pj05.model.dto.response.ResponseWrapper;
import ra.pj05.model.entity.Users;
import ra.pj05.service.IAuthService;
import ra.pj05.service.IUserService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> handleLogin(@Valid @RequestBody FormLogin formLogin) {
        return new ResponseEntity<>(
                ResponseWrapper.builder()
                        .eHttpStatus(EHttpStatus.SUCCESS)
                        .statusCode(HttpStatus.OK.value())
                        .data(authService.handleLogin(formLogin))
                        .build(),
                HttpStatus.OK
        );
    }

    @PostMapping("/register")
    public ResponseEntity<?> handleRegister(@Valid @RequestBody FormRegister formRegister) {
        authService.handleRegister(formRegister);
        return new ResponseEntity<>( ResponseWrapper.builder()
                .eHttpStatus(EHttpStatus.SUCCESS)
                .statusCode(HttpStatus.CREATED.value())
                .data("Register successfully")
                .build(),
                HttpStatus.CREATED);
    }



}
