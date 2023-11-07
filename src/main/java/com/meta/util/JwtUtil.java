package com.meta.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import com.meta.dto.UserDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Configuration
public class JwtUtil {
	
	String key = "84720989750123457466639208678901_5562";
	
	public String createToken(List<UserDTO> result) {

		
		
		Map<String, Object> headers = new HashMap<>();
		headers.put("typ", "JWT");
		headers.put("alg", "HS256");
		
		Map<String, Object> payloads = new HashMap<>();
		long expiredTime = 1000 * 60 * 60 * 24 * 20;
		
		Date now = new Date(expiredTime);
		
		now.setTime(now.getTime() + expiredTime);
		
		payloads.put("user_id", result.get(0).user_id);
		payloads.put("company_name",result.get(0).company_name);
		payloads.put("creation_datetime", result.get(0).creation_datetime);
		payloads.put("expiration_datetime", result.get(0).expiration_datetime);
		
		String jwt = Jwts.builder()
				.setExpiration(now)
				.setHeader(headers)
				.setClaims(payloads)
				.signWith(SignatureAlgorithm.HS256, key.getBytes())
				.compact();
		
		return jwt;
		
	}
	
    // Jwt 토큰에서 회원 구별 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 파싱 : "X-AUTH-TOKEN: jwt토큰"
    public String resolveToken(HttpServletRequest req) {
        return req.getHeader("X-AUTH-TOKEN");
    }
    
    
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }
    
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }   
        
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    

	
}