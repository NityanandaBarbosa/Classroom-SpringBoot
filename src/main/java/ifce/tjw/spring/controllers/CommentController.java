package ifce.tjw.spring.controllers;

import ifce.tjw.spring.dto.CommentCreatedDTO;
import ifce.tjw.spring.dto.CommentDTO;
import ifce.tjw.spring.services.CommentService;
import ifce.tjw.spring.utils.UserInfoToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @PostMapping(value = "/{activityId}")
    public ResponseEntity<CommentCreatedDTO> createComment(@RequestBody CommentDTO dto,
            @PathVariable Long activityId,
            @RequestHeader(name = "Authorization") String token) {
        Map<String, String> payload = UserInfoToken.getUserInfoFromToken(token);
        Long userId = Long.parseLong(payload.get("id"));
        try {
            CommentCreatedDTO createdDTO = service.createComment(dto, userId, activityId);
            if (createdDTO != null) {
                return new ResponseEntity<>(createdDTO, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PatchMapping(value = "/{commentId}")
    public ResponseEntity<CommentCreatedDTO> patchComment(@RequestBody CommentDTO dto,
            @PathVariable Long commentId,
            @RequestHeader(name = "Authorization") String token) {
        Map<String, String> payload = UserInfoToken.getUserInfoFromToken(token);
        Long userId = Long.parseLong(payload.get("id"));
        try {
            CommentCreatedDTO createdDTO = service.patchComment(dto, userId, commentId);
            if (createdDTO != null) {
                return new ResponseEntity<>(createdDTO, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping(value = "/{commentId}")
    public ResponseEntity<CommentCreatedDTO> deleteComment(@PathVariable Long commentId,
            @RequestHeader(name = "Authorization") String token) {
        Map<String, String> payload = UserInfoToken.getUserInfoFromToken(token);
        Long userId = Long.parseLong(payload.get("id"));
        try {
            CommentCreatedDTO createdDTO = service.deleteComment(userId, commentId);
            if (createdDTO != null) {
                return new ResponseEntity<>(createdDTO, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "/activity/{activity_id}")
    public ResponseEntity<List<CommentCreatedDTO>> getActivityComments(@PathVariable Long activity_id,
            @RequestHeader(name = "Authorization") String token) {
        Map<String, String> payload = UserInfoToken.getUserInfoFromToken(token);
        Long userId = Long.parseLong(payload.get("id"));
        try {
            List<CommentCreatedDTO> listDto = service.getActivityComments(userId, activity_id);
            if (listDto != null) {
                return new ResponseEntity<>(listDto, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
