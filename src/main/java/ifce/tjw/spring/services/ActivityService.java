package ifce.tjw.spring.services;

import ifce.tjw.spring.utils.UserId;
import ifce.tjw.spring.dto.*;
import ifce.tjw.spring.entity.Comment;
import ifce.tjw.spring.repositories.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ifce.tjw.spring.entity.Activity;
import ifce.tjw.spring.entity.Discipline;
import ifce.tjw.spring.entity.User;
import ifce.tjw.spring.repositories.ActivityRepository;
import ifce.tjw.spring.repositories.DisciplineRepository;
import ifce.tjw.spring.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityService {
    private final ActivityRepository repository;
    private final UserRepository userRepository;
    private final DisciplineRepository disciplineRepository;
    private final CommentRepository commentRepository;
    private final ModelMapper mapper;

    public ActivityService(ActivityRepository repository, UserRepository userRepository,
            DisciplineRepository disciplineRepository, CommentRepository commentRepository, ModelMapper mapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.disciplineRepository = disciplineRepository;
        this.commentRepository = commentRepository;
        this.mapper = mapper;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public ActivityCreatedDTO createActivity(ActivityCreateDTO dto, Long disciplineId) {
        Long userId = UserId.getAuthenticatedUserId(userRepository);
        Activity activity = new Activity();
        activity.setTittle(dto.getTittle());
        activity.setDescription(dto.getDescription());
        Discipline discipline = disciplineRepository.getReferenceById(disciplineId);
        User creator = userRepository.getReferenceById(userId);
        if (discipline == null || creator == null) {
            return null;
        }
        if (discipline.getStudents().contains(creator) || discipline.getOwner() == creator) {
            activity.setCreator(creator);
            activity.setDiscipline(discipline);
            repository.save(activity);
            return mapper.map(activity, ActivityCreatedDTO.class);
        }
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public ActivityCreatedDTO patchActivity(ActivityCreateDTO dto, Long userId, Long activityId) {
        Activity activity = repository.getReferenceById(activityId);
        User creator = userRepository.getReferenceById(userId);
        if (activity == null || creator == null) {
            return null;
        }
        if (activity.getCreator() == creator || activity.getDiscipline().getOwner() == creator) {
            String tittle = dto.getTittle();
            String description = dto.getDescription();
            if (tittle != null && tittle != "") {
                activity.setTittle(dto.getTittle());
            }
            if (description != null && description != "") {
                activity.setDescription(dto.getDescription());
            }
            repository.save(activity);
            return mapper.map(activity, ActivityCreatedDTO.class);
        }
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public ActivityCreatedDTO deleteActivity(Long userId, Long activityId) {
        Activity activity = repository.getReferenceById(activityId);
        User creator = userRepository.getReferenceById(userId);
        if (activity == null || creator == null) {
            return null;
        }
        Discipline discipline = activity.getDiscipline();
        if (discipline.getStudents().contains(creator) || discipline.getOwner() == creator) {
            repository.delete(activity);
            return mapper.map(activity, ActivityCreatedDTO.class);
        }
        return null;
    }

    public DisciplineWithActivities getAllActivities(Long disciplineId) {
        Long userId = UserId.getAuthenticatedUserId(userRepository);
        DisciplineWithActivities disciplineWithActivities = new DisciplineWithActivities();
        List<ActivityCreatedDTO> dtoList = new ArrayList<>();
        Discipline discipline = disciplineRepository.getReferenceById(disciplineId);
        User creator = userRepository.getReferenceById(userId);
        if (discipline == null || creator == null) {
            return null;
        }
        if (discipline.getStudents().contains(creator) || discipline.getOwner() == creator) {
            List<Activity> activityList = repository.getAllByDisciplineId(disciplineId);
            activityList.forEach((activity -> dtoList.add(mapper.map(activity, ActivityCreatedDTO.class))));
            disciplineWithActivities.setId(discipline.getId());
            disciplineWithActivities.setName(discipline.getName());
            disciplineWithActivities.setOwnerName(discipline.getOwner().getNome());
            disciplineWithActivities.setList(dtoList);
            return disciplineWithActivities;
        }
        return null;
    }

    public ActivityCompleteDTO getActivity(Long activityId) {
        Long userId = UserId.getAuthenticatedUserId(userRepository);
        Activity activity = repository.getReferenceById(activityId);
        User creator = userRepository.getReferenceById(userId);
        List<Comment> comments = commentRepository.getAllByActivityId(activityId);
        List<CommentCreatedDTO> commentCreatedDTOList = new ArrayList<>();
        if (activity == null || creator == null) {
            return null;
        }
        if (activity.getDiscipline().getStudents().contains(creator)
                || activity.getDiscipline().getOwner() == creator) {
            DisciplineCreateDTO disciplineCreateDTO = mapper.map(activity.getDiscipline(), DisciplineCreateDTO.class);
            comments.forEach((comment -> commentCreatedDTOList.add(mapper.map(comment, CommentCreatedDTO.class))));
            ActivityCompleteDTO dto = mapper.map(activity, ActivityCompleteDTO.class);
            dto.setComments(commentCreatedDTOList);
            dto.setDiscipline(disciplineCreateDTO);
            return dto;
        }
        return null;
    }
}
