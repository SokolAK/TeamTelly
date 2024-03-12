package pl.sokolak.teamtally.frontend.admin.user;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.context.annotation.Scope;
import pl.sokolak.teamtally.backend.Data;
import pl.sokolak.teamtally.backend.user.UserDto;
import pl.sokolak.teamtally.backend.user.UserService;
import pl.sokolak.teamtally.backend.user.role.RoleService;
import pl.sokolak.teamtally.frontend.MainView;
import pl.sokolak.teamtally.frontend.common.AbstractView;
import pl.sokolak.teamtally.frontend.common.event.SaveEvent;

import java.util.function.Function;

import static pl.sokolak.teamtally.frontend.localization.Translator.t;

@SpringComponent
@Scope("prototype")
@RolesAllowed("ADMIN")
@Route(value = "users", layout = MainView.class)
@PageTitle("Users")
public class UserView extends AbstractView<UserDto> {

    private final RoleService roleService;

    public UserView(UserService userSer, RoleService roleService) {
        this.service = userSer;
        this.roleService = roleService;
        addClassName("user-view");
        configureForm();
        configureGrid();
        configureView();
        updateList();
    }

    @Override
    protected void configureForm() {
        form = new UserForm(roleService.findAll());
        form.setWidth("25em");
        form.addSaveListener(this::saveData);
        form.addDeleteListener(this::deleteData);
        form.addCloseListener(e -> closeEditor());
    }

    @Override
    protected void configureGrid() {
        grid = new Grid<>(UserDto.class);
        grid.addClassNames("user-grid");
        grid.setColumns();
        grid.addColumn("username").setHeader(t("view.user.user.username"));
        ;
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
    protected void saveOrUpdateData(SaveEvent event) {
        UserDto userDto = getUserDto(event);
        if(userDto.getPassword().isEmpty()) {
            updateData(userDto);
        } else {
            service.save(userDto);
        }
    }

    private UserDto getUserDto(SaveEvent event){
        return ((UserDto) event.getData());
    }

    private void updateData(UserDto userDto) {
        ((UserService) service).updateWithoutPassword(userDto);
    }
}
