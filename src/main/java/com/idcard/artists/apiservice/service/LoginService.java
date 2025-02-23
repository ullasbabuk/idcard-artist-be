package com.idcard.artists.apiservice.service;

import lombok.RequiredArgsConstructor;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

  @Value("${login-service.user}")
  private String currentUser;

  @Value("${login-service.password}")
  private String currentPassword;

  private final TokenService tokenService;

  public String login(String userId, String password) throws InvalidCredentialsException {
    if ((userId != null) &&
            (userId.length()) > 0 &&
            (userId.equalsIgnoreCase(currentUser))) {
      if ((password != null) &&
              (password.length() > 0) &&
              (password.equalsIgnoreCase(currentPassword))) {
        return tokenService.generateToken();
      }
    } else {
      throw new InvalidCredentialsException();
    }
    return "";
  }
}
