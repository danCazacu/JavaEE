package bean;

import dao.DatabaseOperations;
import org.jetbrains.annotations.NotNull;
import org.omg.CORBA.DATA_CONVERSION;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;


@ManagedBean(name = "lecturerBean")
@RequestScoped
public class LecturerBean implements Serializable {

    private String name;
    private String email;
    private String gender;
    private String url;

    private String[] selectedCompulsoryCourses;
    private String[] selectedOptionalCourses;

    private List<String> compulsoryCourses;
    private List<String> optionalCourses;

    public ArrayList<LecturerBean> lecturersListFromDB ;

    @PostConstruct
    public void init() {

        lecturersListFromDB = DatabaseOperations.getAllLecturers();

        compulsoryCourses = new ArrayList<String>();
        compulsoryCourses.add("TAIP");
        compulsoryCourses.add("JavaEE");
        compulsoryCourses.add("FMSE");

        optionalCourses = new ArrayList<String>();
        optionalCourses.add("SOD");
        optionalCourses.add("RN");
        optionalCourses.add("RE");


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getSelectedCompulsoryCourses() {
        return selectedCompulsoryCourses;
    }

    public void setSelectedCompulsoryCourses(String[] selectedCompulsoryCourses) {
        this.selectedCompulsoryCourses = selectedCompulsoryCourses;
    }

    public String[] getSelectedOptionalCourses() {
        return selectedOptionalCourses;
    }

    public void setSelectedOptionalCourses(String[] selectedOptionalCourses) {
        this.selectedOptionalCourses = selectedOptionalCourses;
    }

    public List<String> getCompulsoryCourses() {
        return compulsoryCourses;
    }

    public void setCompulsoryCourses(List<String> compulsoryCourses) {
        this.compulsoryCourses = compulsoryCourses;
    }

    public List<String> getOptionalCourses() {
        return optionalCourses;
    }

    public void setOptionalCourses(List<String> optionalCourses) {
        this.optionalCourses = optionalCourses;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> completeGenders(String query) {

        query="";
        ArrayList<String> genders = new ArrayList<String>();
        genders.add(query + "Male");
        genders.add(query + "Female");

        return genders;
    }

    public static String insertLecturer(LecturerBean newLecturerObj){

        return DatabaseOperations.insertLecturerInDB(newLecturerObj);
    }

    public static void deleteLecturer(String lecturerName){

       DatabaseOperations.deleteLecturerFromDB(lecturerName);
    }

    @NotNull
    public static String editLecturer(String lecturerName){

        return DatabaseOperations.editLecturerInDB(lecturerName);
    }

    public String updateLecturer(LecturerBean updateLecturer) {

        return DatabaseOperations.updateLecturerDetailsInDB(updateLecturer);
    }

    public ArrayList<LecturerBean> lecturersList() {
        return lecturersListFromDB;
    }

}
