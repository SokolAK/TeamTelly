package pl.sokolak.teamtally.backend.calculator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CalculatorConfiguration {
    @Bean
    PointsCalculator pointsCalculator() {
        return new PointsCalculator();
    }
}
