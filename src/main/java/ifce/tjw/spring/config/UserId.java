package ifce.tjw.spring.config;

import ifce.tjw.spring.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserId {
    public static Long getAuthenticatedUserId(UserRepository repository){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        Long id = repository.findUserByEmail(userEmail).getId();
        return id;
    }
}
