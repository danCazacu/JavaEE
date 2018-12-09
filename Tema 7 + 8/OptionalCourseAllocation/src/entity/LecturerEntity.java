package entity;

import javax.faces.bean.ManagedBean;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue(value = "L")
@ManagedBean(name = "lecturerEntity")
public class LecturerEntity extends PersonEntity {

    @Column
    private String url ;
    public LecturerEntity(){ }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @JoinTable(name = "lecturers_courses",
                joinColumns = {
                    @JoinColumn(name = "lecturer_id", referencedColumnName = "id")},
                inverseJoinColumns = {
                    @JoinColumn(name = "course_id", referencedColumnName = "id")})
    @ManyToMany( fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private Set<CourseEntity> courses = new HashSet<>();

    public void addCourse(CourseEntity course) {
        courses.add(course);
        course.getLecturers().add(this);
    }

    public void removeCourse(CourseEntity course) {
        courses.remove(course);
        course.getLecturers().remove(this);
    }


    public Set<CourseEntity> getCourses() {
        return courses;
    }

    public String getCoursesAsString(){

        String strCourses = "";
        if(courses != null) {
            for (CourseEntity course : courses) {

                strCourses += course.getName() + ", ";
            }

            if (!strCourses.equals("")) {

                return strCourses.substring(0, strCourses.length() - 2);
            }
        }

        return strCourses;
    }

    public void setCourses(Set<CourseEntity> courses) {

        this.courses = courses;
    }
}
