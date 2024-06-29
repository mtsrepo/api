package com.mts.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
@ComponentScan
public class JwtUtil {
	
	
	private String secret = "abchrdfhgt$fsdhs3gnknjbc#gfdfksjb";

	//7. validate token user name and request user also expDate
	public boolean validateToken(String token, String username) {
		try {
			String usernameInToken = getUsername(token);
			return (usernameInToken.equals(username) && !isTokenExpired(token));
		}catch(Exception e) {
//			e.getMessage();
//			System.out.println("JWT strings must contain exactly 2 period characters. Found: 0 " + e.getMessage());
			return false;
		}
		
	}

	//6. Check Current and Exp Date
	private boolean isTokenExpired(String token) {
		final Date expiration = getExpDate(token);
		return expiration.before(new Date());
	}

	//5. Generate Token with Empty Claims
	public String generateToken(String  username) {
		
		Map<String, Object> claims = new HashMap<>();
		return generateToken(claims, username);
	}

	//4.Read username
	public String getUsername(String token) {
		return getClaims(token).getSubject();
	}
	
	//3. read ExpDate
	public Date getExpDate(String token) {
		return getClaims(token).getExpiration();
	}
	
	//2. Read Claim
	private Claims getClaims(String token) {
		return Jwts.parser()
				.setSigningKey(this.secret)
				.parseClaimsJws(token)
				.getBody();
	}
	
	//1. generate token	
	private String generateToken(Map<String, Object> claims, String subject) {

		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuer("shibam007")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(900000000)))
				.signWith(SignatureAlgorithm.HS256, this.secret)
				.compact();
	}
	
//	public Integer validateToken(String token) {
//		try {
//			Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
//			
//		}catch(Exception e) {
//			e.printStackTrace();
//			return 0;
//		}
//		return 0;
//	}




}
