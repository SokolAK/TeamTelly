package pl.sokolak.teamtally.frontend.admin.user;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.context.annotation.Scope;
import pl.sokolak.teamtally.backend.user.UserDto;
import pl.sokolak.teamtally.backend.user.UserService;
import pl.sokolak.teamtally.frontend.MainView;

@SpringComponent
@Scope("prototype")
@RolesAllowed("ADMIN")
@Route(value = "users", layout = MainView.class)
@PageTitle("Users")
public class UserView extends VerticalLayout {

    private final Grid<UserDto> grid = new Grid<>(UserDto.class);
    private final TextField filterText = new TextField();
    private final UserService service;
    private UserForm form;

    public UserView(UserService service) {
        this.service = service;
        addClassName("user-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateList();
        closeEditor();
    }

    private HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(5, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new UserForm();
        form.setWidth("25em");
        form.addSaveListener(this::saveUser);
        form.addDeleteListener(this::deleteUser);
        form.addCloseListener(e -> closeEditor());
    }

    private void saveUser(UserForm.SaveEvent event) {
        if(event.getUser().getPassword().isEmpty()) {
            service.updateWithoutPassword(event.getUser());
        } else {
            service.save(event.getUser());
        }
        updateList();
        closeEditor();
    }

    private void deleteUser(UserForm.DeleteEvent event) {
        service.delete(event.getUser());
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassNames("user-grid");
        grid.setColumns();
        grid.addColumn("username");
        grid.addColumn("firstName");
        grid.addColumn("lastName");
        grid.addColumn("email");
        grid.addColumn(u -> u.getUserRole() != null ? u.getUserRole().getName() : "NONE").setHeader("Role");
        grid.addColumn("password");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                editUser(event.getValue()));
    }

    private Component getToolbar() {
        Button addUserButton = new Button("Add user");
        addUserButton.addClickListener(click -> addUser());

        var toolbar = new HorizontalLayout(filterText, addUserButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void editUser(UserDto user) {
        if (user == null) {
            closeEditor();
        } else {
            form.setUser(user.withoutPassword());
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setUser(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addUser() {
        grid.asSingleSelect().clear();
        editUser(UserDto.builder().build());
    }

    private void updateList() {
        grid.setItems(service.findAll());
    }
}