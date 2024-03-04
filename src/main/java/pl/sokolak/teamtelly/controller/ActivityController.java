package pl.sokolak.teamtelly.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sokolak.teamtelly.domain.Activity;
import pl.sokolak.teamtelly.repo.ActivityRepository;


import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ActivityController {

    private ActivityRepository activityRepository;

    @GetMapping("/activity")
    public ResponseEntity<List<Activity>> getActivities() {
        return ok(activityRepository.findAll());
    }
}
