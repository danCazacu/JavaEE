package bean.database;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "optionalCourseBean")
@RequestScoped
public class OptionalCourseBean extends CourseBean {

    @PostConstruct
    public void init() {

    }

}
