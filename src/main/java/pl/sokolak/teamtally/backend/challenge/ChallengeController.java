package pl.sokolak.teamtally.backend.challenge;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ChallengeController {

    private ChallengeRepository challengeRepository;

    @GetMapping("/challenge")
    public ResponseEntity<List<Challenge>> getChallenges() {
        return ok(challengeRepository.findAll());
    }
}
