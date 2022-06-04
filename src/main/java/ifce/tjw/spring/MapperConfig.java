package ifce.tjw.spring;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ifce.tjw.spring.dto.DisciplineDTO;
import ifce.tjw.spring.entity.Discipline;

@Configuration
public class MapperConfig {
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		
		modelMapper.createTypeMap(Discipline.class, DisciplineDTO.class)
			.<String>addMapping(src -> src.getOwner().getNome(), (dest, value) -> dest.setOwnerName(value));
		
		return modelMapper;
	}
}
