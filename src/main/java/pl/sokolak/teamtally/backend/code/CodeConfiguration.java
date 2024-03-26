package pl.sokolak.teamtally.backend.code;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.sokolak.teamtally.backend.mapper.MapperImpl;

@Configuration
public class CodeConfiguration {

    @Bean
    public CodeService codeService(CodeRepository repository) {
        return new CodeService(repository, new MapperImpl());
    }
}
