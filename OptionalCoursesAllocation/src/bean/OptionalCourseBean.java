package bean;
import dao.DatabaseOperations;
import org.jetbrains.annotations.NotNull;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ManagedBean(name = "optionalCourseBean")
public class OptionalCourseBean extends CourseBean {



    public ArrayList optionalCoursesListFroMDB ;
    public  List<SelectItem> optionalPackagesCodes;

    @PostConstruct
    public void init() {

        optionalCoursesListFroMDB = DatabaseOperations.getAllOptionalCourses();
        optionalPackagesCodes = new ArrayList<SelectItem>(DatabaseOperations.getAllPackagesCodes());
    }

    @Override
    public ArrayList coursesList() {
        return optionalCoursesListFroMDB;
    }

    public List<SelectItem> getOptionalPackagesCodes() {
        return optionalPackagesCodes;
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
