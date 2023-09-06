package com.team.gallexiv.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;
import static io.jsonwebtoken.security.Keys.secretKeyFor;

@Data
@Component
@ConfigurationProperties(prefix = "gallexiv.jwt")
public class JwtUtils {

	private long expire;
	private String header;
	private String secretKey;

	// 生成jwt
	public String generateToken(String username) {

		Date nowDate = new Date();
		Date expireDate = new Date(nowDate.getTime() + 1000 * expire);
        SecretKey userKey = hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

		return Jwts.builder()
				.setHeaderParam("typ", "JWT")
				.setSubject(username)
				.setIssuedAt(nowDate)
				.setExpiration(expireDate)// 7天過期
				.signWith(userKey)
				.compact();
	}

	// 解析jwt
	public Claims getClaimByToken(String jwt) throws ExpiredJwtException {
        SecretKey userKey = hmacShaKeyFor(this.secretKey.getBytes(StandardCharsets.UTF_8));
		return Jwts.parserBuilder()
				.setSigningKey(userKey)
				.build()
				.parseClaimsJws(jwt)
				.getBody();
	}

	// jwt是否過期
	public boolean isTokenExpired(Claims claims) {
		return claims.getExpiration().before(new Date());
	}

}
