package bean;
import dao.DatabaseOperations;
import org.jetbrains.annotations.NotNull;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "courseBean")
@RequestScoped
public class CourseBean implements Serializable {

    private int id;
    private String name;
    private String shortName;
    private String code;
    private int yearOfStudy;
    private int semester;
    private int credits;
    private String url;
    private String lecturer;
    private String optPackage;

    public ArrayList compulsoryCoursesListFroMDB ;
    public  List<SelectItem> lecturersNameList;

    @PostConstruct
    public void init(){

        compulsoryCoursesListFroMDB = DatabaseOperations.getAllCompulsoryCourses();
        lecturersNameList =  new ArrayList<SelectItem>(DatabaseOperations.getAllLecturersName());
    }

    public CourseBean(){

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getOptPackage() {
        return optPackage;
    }

    public void setOptPackage(String optPackage) {
        this.optPackage = optPackage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public List<SelectItem> getLecturersNameList() {
        return lecturersNameList;
    }

    public List<String> completeYearOfStudy(String query) {
        List<String> results = new ArrayList<String>();
        for(int i = 1; i < 4; i++) {
            results.add(query + i);
        }

        return results;
    }

    public List<String> completeSemester(String query) {
        List<String> results = new ArrayList<String>();
        for(int i = 1; i < 7; i++) {
            results.add(query + i);
        }

        return results;
    }

    public ArrayList coursesList() {
        return compulsoryCoursesListFroMDB;
    }

    public static void deleteCourse(int id){

        DatabaseOperations.deleteCompulsoryCourseFromDB(id);
    }

    public static String insertCompulsoryCourse(CourseBean newCompulsoryCourse){

        return DatabaseOperations.insertCompulsoryCoursesInDB(newCompulsoryCourse);
    }
    @NotNull
    public static String editCompulsoryCourse(int id){

        return DatabaseOperations.editCompulsoryCourse(id);
    }

    public String updateCompulsoryCourse(CourseBean updateCompulsoryCourse) {

        return DatabaseOperations.updateCompulsoryCourseDetailsInDB(updateCompulsoryCourse);
    }

}
