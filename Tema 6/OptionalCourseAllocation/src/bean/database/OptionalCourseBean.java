package bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "optionalCourseBean")
@RequestScoped
public class OptionalCourseBean extends CourseBean {

    @PostConstruct
    public void init() {

    }

}
