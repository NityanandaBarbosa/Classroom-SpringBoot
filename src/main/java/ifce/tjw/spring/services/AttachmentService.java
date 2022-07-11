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

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class AttachmentService {
    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final AttachmentRepository repository;

    @Value("${file.upload.dir}")
    private String filePath;

    public AttachmentService(ActivityRepository repository, UserRepository userRepository, ModelMapper mapper,
            AttachmentRepository repository1) {
        this.activityRepository = repository;
        this.userRepository = userRepository;
        this.repository = repository1;
        this.mapper = mapper;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public AttachmentCreatedDTO uploadVideo(MultipartFile file, Long userId, Long activityId) {
        Attachment attachment = new Attachment();
        Activity activity = activityRepository.getReferenceById(activityId);
        Discipline discipline = activity.getDiscipline();
        User user = userRepository.getReferenceById(userId);
        if (activity == null || user == null) {
            throw new RuntimeException("Error to uploadFile");
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
                attachment.setFileType(filePath + dbFileName);
                attachment.setFileNameDB(dbFileName);
                repository.save(attachment);
                return mapper.map(attachment, AttachmentCreatedDTO.class);
            } catch (IOException e) {
                throw new RuntimeException("Error to uploadFile");
            }
        }
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public AttachmentCreatedDTO deleteAttachment(Long userId, Long attachmentId) {
        Attachment attachment = repository.getReferenceById(attachmentId);
        Activity activity = attachment.getActivity();
        Discipline discipline = activity.getDiscipline();
        User user = userRepository.getReferenceById(userId);
        if (attachmentId == null || user == null) {
            throw new RuntimeException("401");
        }
        if (discipline.getStudents().contains(user) || discipline.getOwner() == user) {
            try {
                Files.delete(Paths.get(filePath + attachment.getFileNameDB()));
                System.out.println("DELETOU");
                repository.delete(attachment);
                return mapper.map(attachment, AttachmentCreatedDTO.class);
            } catch (Exception e) {
                System.out.println("ERRORRRRRRR " + e);
                throw new RuntimeException("Error to uploadFile");
            }
        }
        return null;
    }

    public Attachment getAttachment(Long userId, Long attachmentId, HttpServletResponse response) {
        Attachment attachment = repository.getReferenceById(attachmentId);
        Activity activity = attachment.getActivity();
        Discipline discipline = activity.getDiscipline();
        User user = userRepository.getReferenceById(userId);
        if (attachmentId == null || user == null) {
            throw new RuntimeException("401");
        }
        if (discipline.getStudents().contains(user) || discipline.getOwner() == user) {
            try {
                response.setHeader("Content-Disposition", "attachment; filename=" + attachment.getFileName());
                response.setHeader("Content-Transfer-Encoding", "binary");
                BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
                FileInputStream fileInputStream = new FileInputStream(filePath + attachment.getFileNameDB());
                int len;
                byte[] buf = new byte[1024];
                while ((len = fileInputStream.read(buf)) > 0) {
                    outputStream.write(buf, 0, len);
                }
                outputStream.close();
                fileInputStream.close();
                System.out.println("HERE");
                response.flushBuffer();
            } catch (Exception e) {
                System.out.println(e);
                throw new RuntimeException("Error to streamFile");
            }
        }
        throw new RuntimeException("Error to streamFile");
    }

    public void generateFile(String dbFileName, MultipartFile file) throws IOException {
        Path dirPath = Paths.get(filePath);
        Files.createDirectories(dirPath);
        File newFile = new File(filePath + dbFileName);
        newFile.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(newFile);
        fileOutputStream.write(file.getBytes());
        fileOutputStream.close();
    }
}
