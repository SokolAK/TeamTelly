package pl.sokolak.teamtally.frontend.admin_section.user;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.sokolak.teamtally.abstracts.Data;
import pl.sokolak.teamtally.backend.user.UserDto;
import pl.sokolak.teamtally.backend.user.UserService;
import pl.sokolak.teamtally.backend.user.role.RoleService;
import pl.sokolak.teamtally.frontend.MainView;
import pl.sokolak.teamtally.frontend.common.AbstractViewWithSideForm;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RolesAllowed("ADMIN")
@Route(value = "users", layout = MainView.class)
@PageTitle("Users")
public class UserView extends AbstractViewWithSideForm<UserDto> {

    public UserView(UserService userService, RoleService roleService) {
        this.service = userService;
        this.form = new UserForm(roleService.findAll());
        addClassName("user-view");
        init();
    }

    @Override
    protected void configureGrid() {
        grid = new Grid<>(UserDto.class);
        grid.addClassNames("user-grid");
        grid.setColumns();
        grid.addColumn(UserRenderer.create()).setAutoWidth(true);
        grid.asSingleSelect().addValueChangeListener(event -> editData(event.getValue()));
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
    protected boolean shouldReloadAppLayoutOnSaveOrUpdate() {
        return true;
    }
}
