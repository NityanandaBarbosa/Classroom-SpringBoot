package ifce.tjw.spring.services;

import ifce.tjw.spring.dto.ActivityCreateDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ifce.tjw.spring.dto.ActivityCreatedDTO;
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

    @Autowired
    private ModelMapper mapper;

    public ActivityService(ActivityRepository repository, UserRepository userRepository,
            DisciplineRepository disciplineRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.disciplineRepository = disciplineRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public ActivityCreatedDTO createActivity(ActivityCreateDTO dto, Long userId, Long disciplineId) {
        Activity activity = new Activity();
        activity.setTittle(dto.getTittle());
        activity.setDescription(dto.getDescription());
        Discipline discipline = disciplineRepository.getReferenceById(disciplineId);
        User creator = userRepository.getReferenceById(userId);
        if(discipline == null || creator == null){
            return null;
        }
        if(discipline.getUser().contains(creator) || discipline.getOwner() == creator){
            activity.setCreator(creator);
            activity.setDiscipline(discipline);
            discipline.getActivities().add(activity);
            repository.save(activity);
            disciplineRepository.save(discipline);
            return mapper.map(activity, ActivityCreatedDTO.class);
        }
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public ActivityCreatedDTO patchActivity(ActivityCreateDTO dto, Long userId, Long activityId) {
        Activity activity = repository.getReferenceById(activityId);
        User creator = userRepository.getReferenceById(userId);
        if(activity == null || creator == null){
            return null;
        }
        if(activity.getDiscipline().getUser().contains(creator) || activity.getDiscipline().getOwner() == creator){
            String tittle = dto.getTittle();
            String description = dto.getDescription();
            if(tittle != null && tittle != ""){
                activity.setTittle(dto.getTittle());
            }
            if(description != null && description != ""){
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
        if(activity == null || creator == null){
            return null;
        }
        Discipline discipline = activity.getDiscipline();
        if(discipline.getUser().contains(creator) || discipline.getOwner() == creator){
            discipline.getActivities().remove(activity);
            repository.delete(activity);
            disciplineRepository.save(discipline);
            return mapper.map(activity, ActivityCreatedDTO.class);
        }
        return null;
    }

    public List<ActivityCreatedDTO> getAllActivities(Long userId, Long disciplineId) {
        List<ActivityCreatedDTO> dtoList = new ArrayList<>();
        Discipline discipline = disciplineRepository.getReferenceById(disciplineId);
        User creator = userRepository.getReferenceById(userId);
        if(discipline == null || creator == null){
            return null;
        }
        if(discipline.getUser().contains(creator) || discipline.getOwner() == creator){
            discipline.getActivities().forEach((activity -> dtoList.add(mapper.map(activity, ActivityCreatedDTO.class))));
            return dtoList;
        }
        return null;
    }

    public ActivityCreatedDTO getActivity(Long userId, Long activityId) {
        Activity activity = repository.getReferenceById(activityId);
        User creator = userRepository.getReferenceById(userId);
        if(activity == null || creator == null){
            return null;
        }
        if(activity.getDiscipline().getUser().contains(creator) || activity.getDiscipline().getOwner() == creator){ ;
            return mapper.map(activity, ActivityCreatedDTO.class);
        }
        return null;
    }
}
