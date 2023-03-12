package com.ruoyi.system.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class JwtService {
     @Value("${token.secret}")
     String secret;
    public String getJwt(Map<String , Object> data)
    {
         return Jwts.builder().setClaims(data).signWith(SignatureAlgorithm.HS512, secret).compact();
    }
    public Claims isValid(String token)
    {
          return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

}
