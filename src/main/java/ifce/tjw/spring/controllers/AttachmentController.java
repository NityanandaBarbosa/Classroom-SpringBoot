package ifce.tjw.spring.controllers;


import ifce.tjw.spring.dto.AttachmentCreatedDTO;
import ifce.tjw.spring.services.AttachmentService;
import ifce.tjw.spring.utils.UserInfoToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping
        ("/attachment")
public class AttachmentController {
    private final AttachmentService service;

    public AttachmentController(AttachmentService service) {
        this.service = service;
    }

    @PostMapping(value = "/file/{activityId}")
    public ResponseEntity<Object> uploadVideo(@RequestParam MultipartFile file,
                                                            @RequestHeader(name = "Authorization") String token, @PathVariable Long activityId) {
        Map<String, String> payload = UserInfoToken.getUserInfoFromToken(token);
        Long userId = Long.parseLong(payload.get("id"));
        try {
            System.out.println("CONTROLLER2 " + file.getOriginalFilename());
            service.uploadVideo(file, userId, activityId);
            System.out.println("CONTROLLER3 " + file.getOriginalFilename());
            return new ResponseEntity<>("Uploaded", HttpStatus.CREATED);
//            if (dto != null) {
//                System.out.println("CONTROLLER4 " + file.getOriginalFilename());
//                return new ResponseEntity<>(dto, HttpStatus.CREATED);
//            }
        } catch (Exception e) {
            System.out.println("ERROR");
            return new ResponseEntity<>("Exception : "+ e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
//        return new ResponseEntity<>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
