package entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Embeddable
public class StudentPreferencesCompositeKey implements Serializable {

    @Column(name = "student_id")
    private Integer studentId;

    @Column(name = "optional_course_id")
    private Integer optionalCourseId;

    @Column(name = "position")
    private int position;

    public StudentPreferencesCompositeKey() {
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getOptionalCourseId() {
        return optionalCourseId;
    }

    public void setOptionalCourseId(Integer optionalCourseId) {
        this.optionalCourseId = optionalCourseId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
