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

public class JWTAuthFilter extends UsernamePasswordAuthenticationFilter {

    public static final int TOKEN_EXPIRES = 1000000000;
    public static final String SECRET_KET = "classroom";

    @Autowired
    private ModelMapper mapper;

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
        String token = JWT.create()
                .withSubject(userData.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRES))
                .sign(Algorithm.HMAC512(SECRET_KET));

        response.getWriter().write(token);
        response.getWriter().flush();
    }
}
