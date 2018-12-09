package entity;

import javax.faces.bean.ManagedBean;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@NamedQuery(name = "findCourse",
        query = "SELECT c FROM CourseEntity c WHERE c.code = :code")
@Entity
@Table(name = "courses")
@ManagedBean(name = "courseEntity")
@Cacheable(true)
public class CourseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "short_name")
    private String shortName;
    public String name;
    private String code;
    private Integer year = 1;
    private Integer semester = 1;
    private Integer credits = 1;
    private String url;



    @ManyToMany(fetch = FetchType.LAZY,  cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    Set<LecturerEntity> lecturers = new HashSet<>();


    @OneToOne(mappedBy = "course", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private OptionalCourseEntity optionalCourse;


    public CourseEntity(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String short_name) {
        this.shortName = short_name;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<LecturerEntity> getLecturers() {

        return lecturers;
    }

    public void setLecturers(Set<LecturerEntity> lecturers) {

        for(LecturerEntity lecturer : lecturers){

            this.lecturers.add(lecturer);
        }
    }

    public void addLecturer(LecturerEntity lecturer) {
        lecturers.add(lecturer);
        lecturer.getCourses().add(this);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLecturersAsString(){

        String strLecturers = "";
        for(LecturerEntity lecturer : lecturers){

            strLecturers += lecturer.getName() + ", ";
        }

        if(!strLecturers.equals("")) {

            return strLecturers.substring(0, strLecturers.length() - 1);
        }

        return strLecturers;
    }
}
