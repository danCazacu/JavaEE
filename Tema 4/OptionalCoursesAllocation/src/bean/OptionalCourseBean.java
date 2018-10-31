package bean;
import dao.DatabaseOperations;
import org.jetbrains.annotations.NotNull;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ManagedBean(name = "optionalCourseBean")
@RequestScoped
public class OptionalCourseBean extends CourseBean {



    @PostConstruct
    public void init() {

    }

    @Override
    public ArrayList coursesList() {
        return DatabaseOperations.getAllOptionalCourses();
    }

    public List<SelectItem> getOptionalPackagesCodes() {
        return DatabaseOperations.getAllPackagesCodes();
    }


    public static String insertOptionalCourse(OptionalCourseBean newOptionalCourse){

        return DatabaseOperations.insertOptionalCoursesInDB(newOptionalCourse);
    }
    @NotNull
    public static String editOptionalCourse(int id){

        return DatabaseOperations.editOptionalCourse(id);
    }

    public String updateOptionalCourse(OptionalCourseBean updateOptionalCourse) {

        return DatabaseOperations.updateOptionalCourseDetailsInDB(updateOptionalCourse);
    }

}
