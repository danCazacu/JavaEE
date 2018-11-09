package bean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class AjaxPollBean implements Serializable {

    private int number;

    public int getNumber() {
        return number;
    }

    public void increment() {
        number++;

    }
}
