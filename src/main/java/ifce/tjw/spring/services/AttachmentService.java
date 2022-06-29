package ifce.tjw.spring.services;

import ifce.tjw.spring.dto.AttachmentCreatedDTO;
import ifce.tjw.spring.entity.Activity;
import ifce.tjw.spring.entity.Attachment;
import ifce.tjw.spring.entity.Discipline;
import ifce.tjw.spring.entity.User;
import ifce.tjw.spring.repositories.ActivityRepository;
import ifce.tjw.spring.repositories.AttachmentRepository;
import ifce.tjw.spring.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class AttachmentService {
    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final AttachmentRepository repository;

    @Value("${file.upload.dir}")
    private String filePath;

    public AttachmentService(ActivityRepository repository, UserRepository userRepository, ModelMapper mapper, AttachmentRepository repository1) {
        this.activityRepository = repository;
        this.userRepository = userRepository;
        this.repository = repository1;
        this.mapper = mapper;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void uploadVideo(MultipartFile file, Long userId, Long activityId) {
        Attachment attachment = new Attachment();
        Activity activity = activityRepository.getReferenceById(activityId);
        Discipline discipline = activity.getDiscipline();
        User user = userRepository.getReferenceById(userId);
        if (activity == null || user == null) {
            System.out.println("VAZIO");
            throw new RuntimeException("Error to uploadFile");
//            return null;
        }
        if (discipline.getStudents().contains(user) || discipline.getOwner() == user) {
            System.out.println("VALID");
            attachment.setFileName(file.getOriginalFilename());
            attachment.setActivity(activity);
            attachment.setUser(user);
            try {
                UUID uuidName = UUID.randomUUID();
                String dbFileName = uuidName + file.getOriginalFilename();
                this.generateFile(dbFileName, file);
                attachment.setFileType(filePath+dbFileName);
                attachment.setFileNameDB(dbFileName);
                repository.save(attachment);
//                return mapper.map(attachment, AttachmentCreatedDTO.class);
            } catch (IOException e) {
                System.out.println("ERROR " + e);
                throw new RuntimeException("Error to uploadFile");
            }
        }
//        return null;
    }

    public void generateFile(String dbFileName,MultipartFile file) throws IOException {
        Path dirPath = Paths.get(filePath);
        Files.createDirectories(dirPath);
        File newFile = new File(filePath + dbFileName);
        newFile.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(newFile);
        fileOutputStream.write(file.getBytes());
        fileOutputStream.close();
    }
}
