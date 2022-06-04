package ifce.tjw.spring.config;

import ifce.tjw.spring.dto.DisciplineDTO;
import ifce.tjw.spring.entity.Discipline;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class Cript {
    @Bean
    public BCryptPasswordEncoder encryptPassword() {
        BCryptPasswordEncoder encryptPassword = new BCryptPasswordEncoder();
        return encryptPassword;
    }
}
