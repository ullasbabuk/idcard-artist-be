package com.idcard.artists.apiservice.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginDto {
  String userName;
  String token;
  String message;
}
