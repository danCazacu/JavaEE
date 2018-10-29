package bean;

import dao.DatabaseOperations;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "optionalPackageBean")
public class OptionalPackageBean {
    private String packageId;
    int year;
    int semester;
    int disciplineNo;
    private List<OptionalCourseBean> courses;

    public ArrayList optionalPackagesListFroMDB ;

    public OptionalPackageBean()  {}

    public OptionalPackageBean(String packageId, List<OptionalCourseBean> courses) {
        this.packageId = packageId;
        this.courses = courses;
    }

    @PostConstruct
    public void init() {

        optionalPackagesListFroMDB = DatabaseOperations.getAllOptionalPackages();
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public List<OptionalCourseBean> getCourses() {
        return courses;
    }

    public void setCourses(List<OptionalCourseBean> courses) {
        this.courses = courses;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getDisciplineNo() {
        return disciplineNo;
    }

    public void setDisciplineNo(int disciplineNo) {
        this.disciplineNo = disciplineNo;
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

    public ArrayList optionalPackagesList() {

        return optionalPackagesListFroMDB;
    }

    public static String insertOptionalPackage(OptionalPackageBean newOptionalPackage){

        return DatabaseOperations.insertOptionalPackageInDB(newOptionalPackage);
    }

    public static void deleteOptionalPackage(String optPkgCode){

        DatabaseOperations.deleteOptionalPackageFromDB(optPkgCode);
    }

    @NotNull
    public static String editOptionalPackage(int id){

        return DatabaseOperations.editOptionalCourse(id);
    }

    public String updateOptionalPackage(OptionalPackageBean updateOptionalPackage) {

        return DatabaseOperations.updateOptionalPackageDetailsInDB(updateOptionalPackage);
    }


}
