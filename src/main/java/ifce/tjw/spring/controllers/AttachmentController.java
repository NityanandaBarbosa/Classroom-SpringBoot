package ifce.tjw.spring.controllers;

import ifce.tjw.spring.dto.AttachmentCreatedDTO;
import ifce.tjw.spring.entity.Attachment;
import ifce.tjw.spring.services.AttachmentService;
import ifce.tjw.spring.utils.UserInfoToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/attachment")
public class AttachmentController {
    private final AttachmentService service;

    public AttachmentController(AttachmentService service) {
        this.service = service;
    }

    @PostMapping(value = "/file/{activityId}")
    public ResponseEntity<AttachmentCreatedDTO> uploadVideo(@RequestParam MultipartFile file,
            @RequestHeader(name = "Authorization") String token, @PathVariable Long activityId) {
        Map<String, String> payload = UserInfoToken.getUserInfoFromToken(token);
        Long userId = Long.parseLong(payload.get("id"));
        try {
            AttachmentCreatedDTO dto = service.uploadVideo(file, userId, activityId);
            if (dto != null) {
                return new ResponseEntity<>(dto, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping(value = "/file/{attachmentId}")
    public ResponseEntity<AttachmentCreatedDTO> deleteAttachment(@RequestHeader(name = "Authorization") String token,
            @PathVariable Long attachmentId) {
        Map<String, String> payload = UserInfoToken.getUserInfoFromToken(token);
        Long userId = Long.parseLong(payload.get("id"));
        try {
            AttachmentCreatedDTO dto = service.deleteAttachment(userId, attachmentId);
            if (dto != null) {
                return new ResponseEntity<>(dto, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "/file/{attachmentId}")
    public ResponseEntity<Object> getAttachment(@RequestHeader(name = "Authorization") String token,
            @PathVariable Long attachmentId, HttpServletResponse response) {
        Map<String, String> payload = UserInfoToken.getUserInfoFromToken(token);
        Long userId = Long.parseLong(payload.get("id"));
        try {
            service.getAttachment(userId, attachmentId, response);
        } catch (Exception e) {
            System.out.println("ERROR AQUI MANE : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }
}
