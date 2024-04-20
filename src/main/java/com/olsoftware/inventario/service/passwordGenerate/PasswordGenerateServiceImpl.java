package com.olsoftware.inventario.service.passwordGenerate;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;

@Service
public class PasswordGenerateServiceImpl implements PasswordGenerateService {

	public String generate() {
		SecretKey secretKey = Jwts.SIG.HS256.key().build();
        byte[] keyBytes = secretKey.getEncoded();

        return Encoders.BASE64.encode(keyBytes);
	}
}
