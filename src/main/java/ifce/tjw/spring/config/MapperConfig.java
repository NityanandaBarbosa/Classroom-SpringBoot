package ifce.tjw.spring.config;

import ifce.tjw.spring.dto.*;
import ifce.tjw.spring.entity.Activity;
import ifce.tjw.spring.entity.Attachment;
import ifce.tjw.spring.entity.Comment;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ifce.tjw.spring.entity.Discipline;

@Configuration
public class MapperConfig {
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		modelMapper.createTypeMap(Discipline.class, DisciplineDTO.class)
				.<String>addMapping(src -> src.getOwner().getName(), (dest, value) -> dest.setOwnerName(value));

		modelMapper.createTypeMap(Discipline.class, DisciplineGetDTO.class)
				.<String>addMapping(src -> src.getOwner().getName(), (dest, value) -> dest.setOwnerName(value));

		modelMapper.createTypeMap(Activity.class, ActivityCreatedDTO.class)
				.<String>addMapping(src -> src.getCreator().getName(), (dest, value) -> dest.setCreatorName(value));

		modelMapper.createTypeMap(Activity.class, ActivityCompleteDTO.class)
				.<String>addMapping(src -> src.getCreator().getName(), (dest, value) -> dest.setCreatorName(value));

		modelMapper.createTypeMap(Comment.class, CommentCreatedDTO.class)
				.<String>addMapping(src -> src.getSender().getName(), (dest, value) -> dest.setSender(value));

		modelMapper.createTypeMap(Attachment.class, AttachmentCreatedDTO.class)
				.<String>addMapping(src -> src.getUser().getName(), (dest, value) -> dest.setSender(value))
				.<String>addMapping(src -> src.getActivity().getTittle(), (dest, value) -> dest.setActivity(value));

		return modelMapper;
	}
}
