package pl.sokolak.teamtally.backend.code;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CodeConfiguration {

    @Bean
    public CodeService codeService(CodeRepository repository) {
        return new CodeService(repository, codeMapper());
    }

    public CodeMapper codeMapper() {
        return new CodeMapper();
    }
}
