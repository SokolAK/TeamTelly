package pl.sokolak.teamtally.backend.code;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.sokolak.teamtally.backend.MapperImpl;

@Configuration
public class CodeConfiguration {

    @Bean
    public CodeService codeService(CodeRepository repository) {
        return new CodeService(repository, new MapperImpl());
    }

    public CodeMapper codeMapper() {
        return new CodeMapper();
    }
}
