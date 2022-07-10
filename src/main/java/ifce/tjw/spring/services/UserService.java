package ifce.tjw.spring.services;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ifce.tjw.spring.dto.UserCreateDTO;
import ifce.tjw.spring.dto.UserDTO;
import ifce.tjw.spring.entity.User;
import ifce.tjw.spring.repositories.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ModelMapper mapper;

	private BCryptPasswordEncoder crypt = new BCryptPasswordEncoder();
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public UserDTO createUser(UserCreateDTO dto) {
		User user = new User();
		user.setEmail(dto.getEmail());
		user.setNome(dto.getNome());
		user.setPassword(crypt.encode(dto.getPassword()));
		return mapper.map(userRepo.save(user), UserDTO.class);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public UserDTO delete(Long id) {
		Optional<User> user = userRepo.findById(id);
		userRepo.deleteById(id);
		return mapper.map(user, UserDTO.class);
		
	}

	public UserDTO login(String email) {
		Optional<User> optUser = Optional.ofNullable(userRepo.findUserByEmail(email));
		User user = optUser.get();
		return mapper.map(user, UserDTO.class);
	}

	public Long getAuthUserID(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userEmail = authentication.getName();
		Long id = userRepo.findUserByEmail(userEmail).getId();
		return id;
	}
}
