package entity;

import javax.faces.bean.ManagedBean;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NamedQuery(name = "findOptionalPackage",
        query = "SELECT o FROM OptionalPackageEntity o WHERE o.code = :code")
@Entity
@Table(name = "optional_packages")
@ManagedBean
public class OptionalPackageEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String code;
    int year = 1;
    int semester = 1;

    @OneToMany(mappedBy = "optionalPackage", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval=true)
    Set<OptionalCourseEntity> courses = new HashSet<>();

    public OptionalPackageEntity(){}

    public OptionalPackageEntity(OptionalPackageEntity optionalPackageEntity) {
        this.code = optionalPackageEntity.code;
        this.year = optionalPackageEntity.year;
        this.semester = optionalPackageEntity.semester;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Set<OptionalCourseEntity> getCourses() {
        return courses;
    }

    public void setCourses(Set<OptionalCourseEntity> courses) {
        this.courses = courses;
    }


    public void addCourse(OptionalCourseEntity course) {
        courses.add(course);
        course.setOptionalPackage(this);
    }

    public void removeCourse(OptionalCourseEntity course) {
        courses.remove(course);
    }


}
