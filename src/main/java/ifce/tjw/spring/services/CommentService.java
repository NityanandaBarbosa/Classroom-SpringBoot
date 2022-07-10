package ifce.tjw.spring.services;

import ifce.tjw.spring.dto.CommentCreatedDTO;
import ifce.tjw.spring.dto.CommentDTO;
import ifce.tjw.spring.entity.Activity;
import ifce.tjw.spring.entity.Comment;
import ifce.tjw.spring.entity.Discipline;
import ifce.tjw.spring.entity.User;
import ifce.tjw.spring.repositories.ActivityRepository;
import ifce.tjw.spring.repositories.CommentRepository;
import ifce.tjw.spring.repositories.UserRepository;
import ifce.tjw.spring.utils.UserId;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository repository;
    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public CommentService(CommentRepository repository, ActivityRepository activityRepository, UserRepository userRepository, ModelMapper mapper) {
        this.repository = repository;
        this.activityRepository = activityRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public CommentCreatedDTO createComment(CommentDTO dto, Long activityId) {
        Long userId = UserId.getAuthenticatedUserId(userRepository);
        Comment comment = new Comment();
        Activity activity = activityRepository.getReferenceById(activityId);
        Discipline discipline = activity.getDiscipline();
        User sender = userRepository.getReferenceById(userId);
        if(activity == null || sender == null){
            return null;
        }
        if(discipline.getStudents().contains(sender) || discipline.getOwner() == sender){
            comment.setMessage(dto.getMessage());
            comment.setActivity(activity);
            comment.setSender(sender);
            repository.save(comment);
            return mapper.map(comment, CommentCreatedDTO.class);
        }
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public CommentCreatedDTO patchComment(CommentDTO dto, Long userId, Long commentId) {
        Comment comment = repository.getReferenceById(commentId);
        User sender = userRepository.getReferenceById(userId);
        if(comment == null || sender == null){
            return null;
        }
        if(comment.getSender() == sender){
            comment.setMessage(dto.getMessage());
            repository.save(comment);
            return mapper.map(comment, CommentCreatedDTO.class);
        }
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public CommentCreatedDTO deleteComment(Long userId, Long commentId) {
        Comment comment = repository.getReferenceById(commentId);
        User sender = userRepository.getReferenceById(userId);
        if(comment == null || sender == null){
            return null;
        }
        if(comment.getSender() == sender){
            repository.delete(comment);
            return mapper.map(comment, CommentCreatedDTO.class);
        }
        return null;
    }

    public List<CommentCreatedDTO> getActivityComments(Long userId, Long activityId) {
        List<CommentCreatedDTO> dtoList = new ArrayList<>();
        Discipline discipline = activityRepository.getReferenceById(activityId).getDiscipline();
        List<Comment> commentList = repository.getAllByActivityId(activityId);
        User sender = userRepository.getReferenceById(userId);
        if(commentList == null || sender == null){
            return null;
        }
        if(discipline.getStudents().contains(sender) || discipline.getOwner() == sender){
            commentList.forEach(comment -> dtoList.add(mapper.map(comment, CommentCreatedDTO.class)));
            return dtoList;
        }
        return null;
    }
}
