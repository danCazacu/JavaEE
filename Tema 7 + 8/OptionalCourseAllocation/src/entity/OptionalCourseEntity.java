package entity;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name = "findOptionalCourse",
        query = "SELECT o FROM OptionalCourseEntity o WHERE o.course.code = :code")
@Entity
@Table(name = "optional_courses")
@ManagedBean(name = "optionalCourseEntity")
public class OptionalCourseEntity implements Serializable {


    @Id
    @OneToOne(orphanRemoval=true, cascade = CascadeType.PERSIST)
    @PrimaryKeyJoinColumn
    private CourseEntity course;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "optionalpackages_id", referencedColumnName = "id")
    private OptionalPackageEntity optionalPackage;

    public CourseEntity getCourse() {
        return course;
    }

    public void setCourse(CourseEntity course) {
        this.course = course;

    }

    public void setOptionalPackage(OptionalPackageEntity optionalPackage) {
        this.optionalPackage = optionalPackage;
    }


    public OptionalPackageEntity getOptionalPackage() {
        return optionalPackage;
    }

    @PostConstruct
    public void init() {
        //optionalPackage = new OptionalPackageEntity();
        course = new CourseEntity();
    }
}
