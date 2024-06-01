package pl.sokolak.teamtally;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Theme("dashboard-blue")
@PWA(name = "TeamTally", shortName = "TeamTally")
public class TeamTallyApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(TeamTallyApplication.class, args);
    }

}
