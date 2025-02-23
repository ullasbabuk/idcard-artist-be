package com.idcard.artists.apiservice.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

@Service
@RequiredArgsConstructor
public class TokenService {
  private static String secret = "idcard-artist-secret";
  private static String key = "Bjbg0H0cKcS5Bp6it4rLlmoEZIFuaRzh";

  public String generateToken() {
    try
    {
      Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
      Cipher cipher = null;
      cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.ENCRYPT_MODE, aesKey);
      byte[] encrypted = cipher.doFinal((secret+ "-" +String.valueOf(System.currentTimeMillis())).getBytes());
      byte[] encodedBytes = Base64.encodeBase64(encrypted);
      return new String(encodedBytes);
    } catch(Exception e) {
      e.printStackTrace();
    }
    return "";
  }

  public boolean verifyToken(String token) {
    try
    {
      byte[] decodedBytes = Base64.decodeBase64(token);
      Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
      Cipher cipher = null;
      cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.DECRYPT_MODE, aesKey);
      String decrypted = new String(cipher.doFinal(decodedBytes));
      return decrypted.contains(secret);
    } catch(Exception e) {
      e.printStackTrace();
    }
    return false;
  }
}
