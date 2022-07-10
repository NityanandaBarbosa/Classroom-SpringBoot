package ifce.tjw.spring.controllers;

import java.util.List;
import java.util.Map;

import ifce.tjw.spring.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import ifce.tjw.spring.services.ActivityService;
import ifce.tjw.spring.utils.UserInfoToken;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/activity")
public class ActivityController {
    private final ActivityService service;

    public ActivityController(ActivityService service) {
        this.service = service;
    }

    @PostMapping(value = "/{id}")
    public String createActivity(ActivityCreateDTO dto, @PathVariable Long id) {
        try {
            ActivityCreatedDTO createdDTO = service.createActivity(dto, id);
            return "redirect:/activity/"+id;

        } catch (Exception e) {
            return "redirect:/activity/"+id;
        }
    }

    @GetMapping(value = "/create-activity/{id}")
    public ModelAndView createActivity(@PathVariable Long id) {
        System.out.println("AQUIIIIIIII");
        ModelAndView mv = new ModelAndView("create-activity.html");
        System.out.println(id.intValue());
        mv.addObject("id", id.intValue());
        return mv;
    }

    @GetMapping(value = "/{id}")
    public ModelAndView getAllActivities(@PathVariable Long id) {
        DisciplineWithActivities dto = service.getAllActivities(id);
        System.out.println(dto);
        ModelAndView mv = new ModelAndView("discipline.html");
        mv.addObject("discipline", dto);
        return mv;
    }

    @GetMapping(value = "/one/{activityId}")
    public ResponseEntity<ActivityCompleteDTO> getActivity(@PathVariable Long activityId,
                                                                     @RequestHeader(name = "Authorization") String token) {
        Map<String, String> payload = UserInfoToken.getUserInfoFromToken(token);
        Long userId = Long.parseLong(payload.get("id"));
        try {
            ActivityCompleteDTO dto = service.getActivity(userId, activityId);
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

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ActivityCreatedDTO> deleteActivity(@PathVariable Long id,
                                                                     @RequestHeader(name = "Authorization") String token) {
        Map<String, String> payload = UserInfoToken.getUserInfoFromToken(token);
        Long userId = Long.parseLong(payload.get("id"));
        try {
            ActivityCreatedDTO dto = service.deleteActivity(userId, id);
            if (dto != null) {
                return new ResponseEntity<>(dto, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
