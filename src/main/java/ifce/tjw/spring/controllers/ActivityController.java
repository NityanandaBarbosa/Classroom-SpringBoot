package ifce.tjw.spring.controllers;

import java.util.List;
import java.util.Map;

import ifce.tjw.spring.dto.ActivityCreateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ifce.tjw.spring.dto.ActivityCreatedDTO;
import ifce.tjw.spring.services.ActivityService;
import ifce.tjw.spring.utils.UserInfoToken;

@RestController
@RequestMapping("/activity")
public class ActivityController {
    private final ActivityService service;

    public ActivityController(ActivityService service) {
        this.service = service;
    }

    @PostMapping(value = "/{id}")
    public ResponseEntity<ActivityCreatedDTO> createActivity(@RequestBody ActivityCreateDTO dto,
            @PathVariable Long id,
            @RequestHeader(name = "Authorization") String token) {
        Map<String, String> payload = UserInfoToken.getUserInfoFromToken(token);
        Long userId = Long.parseLong(payload.get("id"));
        try {
            ActivityCreatedDTO createdDTO = service.createActivity(dto, userId, id);

            if (createdDTO != null) {
                return new ResponseEntity<>(createdDTO, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<List<ActivityCreatedDTO>> getAllActivities(@PathVariable Long id,
                                                                     @RequestHeader(name = "Authorization") String token) {
        Map<String, String> payload = UserInfoToken.getUserInfoFromToken(token);
        Long userId = Long.parseLong(payload.get("id"));
        try {
            List<ActivityCreatedDTO> dtoList = service.getAllActivities(userId, id);

            if (dtoList != null) {
                return new ResponseEntity<>(dtoList, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "/one/{activityId}")
    public ResponseEntity<ActivityCreatedDTO> getActivity(@PathVariable Long activityId,
                                                                     @RequestHeader(name = "Authorization") String token) {
        Map<String, String> payload = UserInfoToken.getUserInfoFromToken(token);
        Long userId = Long.parseLong(payload.get("id"));
        try {
            ActivityCreatedDTO dto = service.getActivity(userId, activityId);
            if (dto != null) {
                return new ResponseEntity<>(dto, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<ActivityCreatedDTO> patchActivity(@RequestBody ActivityCreateDTO dto, @PathVariable Long id,
                                                                     @RequestHeader(name = "Authorization") String token) {
        Map<String, String> payload = UserInfoToken.getUserInfoFromToken(token);
        Long userId = Long.parseLong(payload.get("id"));
        try {
            ActivityCreatedDTO patchedDTO = service.patchActivity(dto, userId,id);

            if (patchedDTO != null) {
                return new ResponseEntity<>(patchedDTO, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
