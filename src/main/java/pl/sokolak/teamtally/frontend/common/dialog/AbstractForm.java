package pl.sokolak.teamtally.frontend.common.dialog;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import lombok.AllArgsConstructor;
import pl.sokolak.teamtally.backend.util.CustomValidator;
import pl.sokolak.teamtally.frontend.common.NotificationService;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public abstract class AbstractForm<T> extends FormLayout {

    protected final BeanValidationBinder<T> binder;
    private List<CustomValidator<T>> validators = new ArrayList<>();

    public AbstractForm(Class<T> beanType) {
        this.binder = new BeanValidationBinder<>(beanType);
    }

    public void setData(T bean) {
        binder.setBean(bean);
    }

    public T getData() {
        return binder.getBean();
    }

    public boolean validate() {
        binder.validate();
        return binder.isValid() && checkCustomValidators();
    }

    private boolean checkCustomValidators() {
        T data = binder.getBean();
        return validators.stream()
                .filter(validator -> !validator.getCondition().test(data))
                .peek(validator -> NotificationService.showWarning(validator.getMessage().apply(data)))
                .findAny()
                .isEmpty();
    }

    public void addValidator(CustomValidator<T> validator) {
        validators.add(validator);
    }
}
