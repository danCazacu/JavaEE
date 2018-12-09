package entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "student_preferences")
public class StudentPreferencesEntity implements Serializable {

    @EmbeddedId
    StudentPreferencesCompositeKey primaryKey;

    /*@ManyToOne
    @MapsId("studentId")
    private StudentEntity student;

    @ManyToOne
    @MapsId("optionalCourseId")
    private OptionalCourseEntity optionalCourse;

    private int position;*/

    public StudentPreferencesEntity() {
    }

    public StudentPreferencesCompositeKey getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(StudentPreferencesCompositeKey primaryKey) {
        this.primaryKey = primaryKey;
    }

/*    public StudentEntity getStudent() {
        return student;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }

    public OptionalCourseEntity getOptionalCourse() {
        return optionalCourse;
    }

    public void setOptionalCourse(OptionalCourseEntity optionalCourse) {
        this.optionalCourse = optionalCourse;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }*/
}
