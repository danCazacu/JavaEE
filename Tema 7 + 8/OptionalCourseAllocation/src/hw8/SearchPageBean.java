package hw8;

import entity.CourseEntity;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class SearchPageBean {

    private CourseEntity course;
    private String optionalPackageCode;

    public CourseEntity getCourse() {
        return course;
    }

    public void setCourse(CourseEntity course) {
        this.course = course;
    }

    public String getOptionalPackageCode() {
        return optionalPackageCode;
    }

    public void setOptionalPackageCode(String optionalPackageCode) {
        this.optionalPackageCode = optionalPackageCode;
    }
}
