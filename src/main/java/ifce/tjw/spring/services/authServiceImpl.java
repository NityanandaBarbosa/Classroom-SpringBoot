package ifce.tjw.spring.services;

import ifce.tjw.spring.UserData;
import ifce.tjw.spring.entity.User;
import ifce.tjw.spring.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class authServiceImpl implements UserDetailsService {

    private final UserRepository repo;

    public authServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optUser = Optional.ofNullable(repo.findUserByEmail(username));
        if(optUser == null){
            throw new UsernameNotFoundException("User [ " + username + " ] not found");
        }
        return new UserData(optUser);
    }
}
