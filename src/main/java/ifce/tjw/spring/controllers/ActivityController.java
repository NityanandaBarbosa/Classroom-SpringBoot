package ifce.tjw.spring.controllers;

import java.util.Map;

import ifce.tjw.spring.dto.ActivityCreateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<ActivityCreatedDTO> createDiscipline(@RequestBody ActivityCreateDTO dto,
            @PathVariable Long id,
            @RequestHeader(name = "Authorization") String token) {
        Map<String, String> payload = UserInfoToken.getUserInfoFromToken(token);
        Long userId = Long.parseLong(payload.get("id"));
        try {
            ActivityCreatedDTO discipline = service.createActivity(dto, userId, id);

            if (discipline != null) {
                return new ResponseEntity<>(discipline, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
