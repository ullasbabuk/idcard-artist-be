package com.idcard.artists.apiservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.FORBIDDEN, reason="Invalid Token")
public class InvalidTokenException extends RuntimeException {
}
