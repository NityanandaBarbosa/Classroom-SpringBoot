package ifce.tjw.spring.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import ifce.tjw.spring.UserData;
import ifce.tjw.spring.dto.UserCreateDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTAuthFilter extends UsernamePasswordAuthenticationFilter {

    public static final int TOKEN_EXPIRES = 1000000000;
    public static final String SECRET_KET = "classroom";

    public final AuthenticationManager authenticationManager;

    public JWTAuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserCreateDTO userCreateDTO = new ObjectMapper().readValue(request.getInputStream(), UserCreateDTO.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                         userCreateDTO.getEmail(),
                         userCreateDTO.getPassword(),
                         new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException("Fail to auth", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserData userData = (UserData) authResult.getPrincipal();
        Map<String, Object> user = new HashMap<>();
        user.put("name", userData.getName());
        user.put("id", userData.getUserId());
        Date date = new Date(System.currentTimeMillis() + TOKEN_EXPIRES);
        String token = JWT.create()
                .withSubject(userData.getUsername())
                .withPayload(user)
                .withExpiresAt(date)
                .sign(Algorithm.HMAC512(SECRET_KET));
        Map<String, String> tokenInfo = new HashMap<>();
        response.setContentType("application/json");
        tokenInfo.put("access_token", token);
        tokenInfo.put("expires_at", String.valueOf(date));
        new ObjectMapper().writeValue(response.getOutputStream(), tokenInfo);
    }
}
