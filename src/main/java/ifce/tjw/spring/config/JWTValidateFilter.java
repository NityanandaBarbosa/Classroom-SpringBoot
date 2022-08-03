package ifce.tjw.spring.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

public class JWTValidateFilter extends BasicAuthenticationFilter {

    public static final String HEADER_AUTH = "Authorization";
    public static final String HEADER_AUTH_PREFIX = "Bearer ";

    public JWTValidateFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authAttribute = request.getHeader(HEADER_AUTH);
        try{
            if(authAttribute == null){
                chain.doFilter(request, response);
                return;
            }

            if(!authAttribute.startsWith(HEADER_AUTH_PREFIX)){
                chain.doFilter(request, response);
                return;
            }
            String token = authAttribute.replace(HEADER_AUTH_PREFIX, "");
            UsernamePasswordAuthenticationToken authenticationToken = getAuthToken(token);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            chain.doFilter(request, response);
        }catch (Exception e){
            response.setStatus(498);
        }
    }

    private UsernamePasswordAuthenticationToken getAuthToken(String token){
        String email = JWT.require(Algorithm.HMAC512(JWTAuthFilter.SECRET_KET))
                .build()
                .verify(token)
                .getSubject();

        if(email == null){
            return null;
        }

        return new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());
    }
}
