package pl.sokolak.teamtally.frontend.login;

import com.vaadin.flow.component.html.Image;

class LogoGenerator {
    static Image createLogo() {
        var logo = new Image("assets/logo.png", "Team Tally");
        logo.addClassName("logo-medium");
        return logo;
    }
}
