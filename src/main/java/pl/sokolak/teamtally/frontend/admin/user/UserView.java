package pl.sokolak.teamtally.frontend.admin.user;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.sokolak.teamtally.backend.Data;
import pl.sokolak.teamtally.backend.user.UserDto;
import pl.sokolak.teamtally.backend.user.UserService;
import pl.sokolak.teamtally.backend.user.role.RoleService;
import pl.sokolak.teamtally.frontend.MainView;
import pl.sokolak.teamtally.frontend.common.AbstractViewWithForm;
import pl.sokolak.teamtally.frontend.common.event.SaveEvent;

import static pl.sokolak.teamtally.frontend.localization.Translator.t;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RolesAllowed("ADMIN")
@Route(value = "users", layout = MainView.class)
@PageTitle("Users")
public class UserView extends AbstractViewWithForm<UserDto> {

    public UserView(UserService userSer, RoleService roleService) {
        this.service = userSer;
        this.form = new UserForm(roleService.findAll());
        addClassName("user-view");
        init();
    }

    @Override
    protected void configureGrid() {
        grid = new Grid<>(UserDto.class);
        grid.addClassNames("user-grid");
        grid.setColumns();
        grid.addColumn("username").setHeader(t("view.user.user.username"));
        grid.addColumn("firstName").setHeader(t("view.user.user.firstName"));
        grid.addColumn("lastName").setHeader(t("view.user.user.lastName"));
        grid.addColumn("email").setHeader(t("view.user.user.email"));
        grid.addColumn(u -> u.getUserRole() != null ? u.getUserRole().getName() : "NONE").setHeader(t("view.user.user.role"));
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                editData(event.getValue()));
    }

    @Override
    protected Data convertToFormData(Data data) {
        return ((UserDto) data).withoutPassword();
    }

    @Override
    protected UserDto emptyData() {
        return UserDto.builder().build();
    }

    @Override
    protected boolean shouldUpdate(SaveEvent event) {
        return ((UserDto) event.getData()).getPassword().isEmpty();
    }

    @Override
    protected void updateData(SaveEvent event) {
        UserDto userDto = (UserDto) event.getData();
        ((UserService) service).updateWithoutPassword(userDto);
    }

    @Override
    protected boolean shouldReloadAppLayoutOnSaveOrUpdate() {
        return true;
    }
}
