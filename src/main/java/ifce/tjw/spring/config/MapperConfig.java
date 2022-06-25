package ifce.tjw.spring.config;

import ifce.tjw.spring.dto.ActivityCreatedDTO;
import ifce.tjw.spring.dto.DisciplineGetDTO;
import ifce.tjw.spring.entity.Activity;
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

		modelMapper.createTypeMap(Discipline.class, DisciplineGetDTO.class)
				.<String>addMapping(src -> src.getOwner().getNome(), (dest, value) -> dest.setOwnerName(value));

		modelMapper.createTypeMap(Activity.class, ActivityCreatedDTO.class)
				.<String>addMapping(src -> src.getCreator().getNome(), (dest, value) -> dest.setCreatorName(value));

		return modelMapper;
	}
}
