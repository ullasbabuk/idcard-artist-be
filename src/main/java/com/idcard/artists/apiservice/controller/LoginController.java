package com.idcard.artists.apiservice.controller;

import com.idcard.artists.apiservice.data.dto.LoginDto;
import com.idcard.artists.apiservice.service.LoginService;
import com.idcard.artists.apiservice.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/login")
@RequiredArgsConstructor
@CrossOrigin
public class LoginController {

  private final LoginService loginService;
  private final TokenService tokenService;

  @PostMapping(
          value = "",
          consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE)
  public LoginDto login(
          @RequestPart(name = "userid") String userid,
          @RequestPart(name = "password") String password
  ) throws InvalidCredentialsException {
    String token = loginService.login(userid, password);
    return LoginDto.builder()
            .userName(userid)
            .token(token)
            .message(token.length() >0 ? "Login Success" : "Login Failed")
            .build();
  }

  @PostMapping(
          value = "/verify",
          consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE)
  public LoginDto verify(
          @RequestPart(name = "userid") String userid,
          @RequestPart(name = "token") String token
  ) {
    return LoginDto.builder()
            .userName(userid)
            .token(token)
            .message(tokenService.verifyToken(token) ? "Valid Token" : "Invalid Token")
            .build();
  }
}
