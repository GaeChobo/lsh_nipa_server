package com.meta.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Service;

import com.meta.dto.UserDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class SecurityServiceImpl implements SecurityService {

	String key = "84720989750123457466639208678901_5562";
	
	@Override
	public String CreateToken(List<UserDTO> dto) {
		
		Map<String, Object> headers = new HashMap<>();
		headers.put("typ", "JWT");
		headers.put("alg", "HS256");
		
		Map<String, Object> payloads = new HashMap<>();
		long expiredTime = 1000 * 60 * 60 * 24 * 20;
		
		Date now = new Date(expiredTime);
		
		now.setTime(now.getTime() + expiredTime);
		
		payloads.put("user_id", dto.get(0).user_id);
		payloads.put("company_name",dto.get(0).company_name);
		payloads.put("creation_datetime", dto.get(0).creation_datetime);
		payloads.put("expiration_datetime", dto.get(0).expiration_datetime);
		
		String jwt = Jwts.builder()
				.setExpiration(now)
				.setHeader(headers)
				.setClaims(payloads)
				.signWith(SignatureAlgorithm.HS256, key.getBytes())
				.compact();
		
		return jwt;
	}
	
    @Override
    public String getSubject(String token) {
            Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(key)).parseClaimsJws(token).getBody();
            return claims.getSubject();
    }
	
    // 유효성 검증
    public boolean validationToken(String jwt) {

            try
            {
                    if (jwt != null)
                    {
                            String content = getSubject(jwt);

                            if (content != null)
                                    return true;
                            else
                                    return false;
                    } else
                    {
                            return false;
                    }

            } catch (Exception e)
            {
                    // TODO: handle exception
                    return false;
            }
    }
	
    // 만료 체크
    public boolean expireToken(String jwt) {

            try
            {
                    Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt);

                    return !claims.getBody().getExpiration().before(new Date());

            } catch (Exception e)
            {
                    // TODO: handle exception
                    return false;
            }

    }
}
