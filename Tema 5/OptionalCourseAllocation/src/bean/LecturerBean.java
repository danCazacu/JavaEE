package bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


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

    private List<String> selectedColumns = new ArrayList<>();
    private Map<String, String> columnMap = new LinkedHashMap<>();

    @PostConstruct
    public void init() {

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

    @PostConstruct
    private void postConstruct() {
        initColumnProperties();
    }

    private void initColumnProperties() {
        addColumn("name", "Name");
        addColumn("email", "Email");
        addColumn("gender", "Gender");
        addColumn("url", "Lecturer url");
        selectedColumns.addAll(columnMap.keySet());
    }

    private void addColumn(String propertyName, String displayName) {
        columnMap.put(propertyName, displayName);
    }


    public List<String> getSelectedColumns() {
        return selectedColumns;
    }

    public void setSelectedColumns(List<String> selectedColumns) {
        this.selectedColumns = selectedColumns;
    }

    public Map<String, String> getColumnMap() {
        return columnMap;
    }
}
