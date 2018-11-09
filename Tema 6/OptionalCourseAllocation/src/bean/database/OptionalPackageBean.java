package bean.database;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.List;

@ManagedBean(name = "optionalPackageBean")
@RequestScoped
public class OptionalPackageBean {
    private String packageId;
    int year;
    int semester;
    int disciplineNumber;
    private List<OptionalCourseBean> courses;

    public OptionalPackageBean()  {
        disciplineNumber = 1;
        year = 1;
        semester = 1;
    }

    @PostConstruct
    public void init() {

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

    public int getDisciplineNumber() {
        return disciplineNumber;
    }

    public void setDisciplineNumber(int disciplineNumber) {
        this.disciplineNumber = disciplineNumber;
    }

}
