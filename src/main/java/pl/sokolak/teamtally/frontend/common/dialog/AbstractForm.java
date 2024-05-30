package pl.sokolak.teamtally.frontend.common.dialog;


import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractForm<T> extends FormLayout {

//    private final Class<T> beanType;
    protected final BeanValidationBinder<T> binder;

    public AbstractForm(Class<T> beanType) {
//        this.beanType = beanType;
        this.binder = new BeanValidationBinder<>(beanType);
    }

    public void setData(T bean) {
        binder.setBean(bean);
    }

    public T getData() {
        return binder.getBean();
    }
}
