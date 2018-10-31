package dao;

import bean.CourseBean;
import bean.LecturerBean;
import bean.OptionalCourseBean;
import bean.OptionalPackageBean;
import org.jetbrains.annotations.NotNull;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@ManagedBean(name="database", eager = true)
@ApplicationScoped
public class DatabaseOperations implements Serializable {
    private static final long serialVersionUID = 1L;

    public static Statement stmt;
    public static Connection conn;
    public static ResultSet resultSet;
    public static PreparedStatement pstmt;
    static String editLecturerName = "";
    static String editOptionalPackgeCode = "";
    static int editCompulsoryCourseId = -1;
    static int editOptionalCourseId = -1;



    @PostConstruct
    public void init() {

        conn = getConnection();
    }

    public static Connection getConnection() {

        String url = "jdbc:postgresql://localhost:5432/JavaEE";
        String user = "postgres";
        String password = "copilotu";

        try {

            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connection completed.");
        } catch (SQLException ex) {
            showError(ex);

        } catch (ClassNotFoundException e) {
            showError(e);
        }

        return conn;
    }

    public static ArrayList<LecturerBean> getAllLecturers() {

        ArrayList<LecturerBean> allLecturers = new ArrayList<>();
        try {

            if(conn !=null) {
                stmt = conn.createStatement();
                resultSet = stmt.executeQuery("select * from lecturer");
                while (resultSet.next()) {

                    LecturerBean lecturerObj = new LecturerBean();
                    lecturerObj.setName(resultSet.getString("name"));
                    lecturerObj.setEmail(resultSet.getString("email"));
                    lecturerObj.setGender(resultSet.getString("gender"));
                    lecturerObj.setUrl(resultSet.getString("url"));

                    allLecturers.add(lecturerObj);
                }
            }
        } catch (Exception sqlSelectException) {

            showError(sqlSelectException);
        }

        return allLecturers;
    }


    public static String insertLecturerInDB(LecturerBean newLecturer){

        int inserted = 0;
        String navigationResult = "";

        try{
            if(newLecturer != null) {

                pstmt = conn.prepareStatement("insert into lecturer(name, email, gender, url) values(?, ?, ? , ?)");
                pstmt.setString(1, newLecturer.getName());
                pstmt.setString(2, newLecturer.getEmail());
                pstmt.setString(3, newLecturer.getGender());
                pstmt.setString(4, newLecturer.getUrl());
            }

            inserted = pstmt.executeUpdate();
        } catch (SQLException e) {
            showError(e);
            navigationResult = "addLecturer";
        }

        if(inserted != 0){

            navigationResult = "viewLecturers";
        }else{

            navigationResult = "addLecturer";
        }

        return navigationResult;
    }

    public static void deleteLecturerFromDB(String lecturerName){

        try {

            pstmt = getConnection().prepareStatement("delete from lecturer where name = ?" );
            pstmt.setString(1, lecturerName);
            pstmt.executeUpdate();
        } catch(Exception sqlException){
            showError(sqlException);
        }

        reload();
    }

    @NotNull
    public static String editLecturerInDB(String lecturerName) {

        LecturerBean editRecord = null;
        /* Setting The Particular Lecturer Details In Session */
        Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        editLecturerName = lecturerName;

        try {

            pstmt = conn.prepareStatement("select * from lecturer where name = ?");
            pstmt.setString(1, lecturerName);
            resultSet = pstmt.executeQuery();
            if(resultSet != null) {
                resultSet.next();
                editRecord = new LecturerBean();
                editRecord.setName(resultSet.getString("name"));
                editRecord.setEmail(resultSet.getString("email"));
                editRecord.setGender(resultSet.getString("gender"));
                editRecord.setUrl(resultSet.getString("url"));
            }
            sessionMapObj.put("editRecordObj", editRecord);

        } catch(Exception sqlException) {

            showError(sqlException);
            return "editLecturer";
        }

        return "editLecturer";
    }

    @NotNull
    public static String updateLecturerDetailsInDB(LecturerBean updateLecturer) {

        try {
            pstmt = conn.prepareStatement("update lecturer set name=?, email=?, gender=?, url=? where name=?");
            pstmt.setString(1,updateLecturer.getName());
            pstmt.setString(2,updateLecturer.getEmail());
            pstmt.setString(3,updateLecturer.getGender());
            pstmt.setString(4,updateLecturer.getUrl());
            pstmt.setString(5,editLecturerName);
            pstmt.executeUpdate();

        } catch(Exception sqlException) {

            showError(sqlException);
            return "editLecturers";
        }

        return "viewLecturers";
    }

    /**
     * reload the current page (used in delete)
     */
    public static void reload(){

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * print message at the beginning of the page
     * @param e exception caught
     */
    public static void showError(@NotNull Exception e){

        StringWriter error = new StringWriter();
        e.printStackTrace(new PrintWriter(error));

        FacesContext facesContext = FacesContext.getCurrentInstance();
        Flash flash = facesContext.getExternalContext().getFlash();
        flash.setKeepMessages(true);

        if(error.toString().contains("duplicate key value violates unique constraint")) {

            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Something went wrong...The name already exist", ""));
        }else if(e.getMessage()!=null && e.getMessage().trim().length()>0){
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Something went wrong..." + e.getMessage(),"" ));
        }else{

            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Something went wrong..." + error.toString(),"" ));
        }
    }

    /**
     * methods for OptionalPackage
     */
    public static ArrayList<OptionalPackageBean> getAllOptionalPackages() {

        ArrayList<OptionalPackageBean> lstOptionalPackages = new ArrayList<>();
        try {

            if(conn == null){

                OptionalPackageBean optPkgObj = new OptionalPackageBean();
                optPkgObj.setPackageId("conn null");

                lstOptionalPackages.add(optPkgObj);
            }

            stmt = conn.createStatement();
            resultSet = stmt.executeQuery("select * from optional_packages");

            if(resultSet == null){

                OptionalPackageBean optPkgObj = new OptionalPackageBean();
                optPkgObj.setPackageId("resultset null");

                lstOptionalPackages.add(optPkgObj);
            }
            while (resultSet.next()) {

                OptionalPackageBean optPkgObj = new OptionalPackageBean();
                optPkgObj.setPackageId(resultSet.getString("code"));
                optPkgObj.setYear(resultSet.getInt("year"));
                optPkgObj.setSemester(resultSet.getInt("semester"));
                optPkgObj.setDisciplineNo(resultSet.getInt("disciplineNo"));
                lstOptionalPackages.add(optPkgObj);
            }
        } catch (Exception sqlSelectException) {

            showError(sqlSelectException);

        }

        return lstOptionalPackages;
    }

    public static String insertOptionalPackageInDB(OptionalPackageBean newOptionalPackage) {

        int inserted = 0;
        String navigationResult = "";

        try{
            if(newOptionalPackage != null) {

                pstmt = conn.prepareStatement("insert into optional_packages(code, year, semester, disciplineno) values(?, ?, ? , ?)");
                pstmt.setString(1, newOptionalPackage.getPackageId());
                pstmt.setInt(2, newOptionalPackage.getSemester());
                pstmt.setInt(3, newOptionalPackage.getYear());
                pstmt.setInt(4, newOptionalPackage.getDisciplineNo());
            }

            inserted = pstmt.executeUpdate();
        } catch (SQLException e) {
            showError(e);
            navigationResult = "addOptionalPackage";
        }

        if(inserted != 0){

            navigationResult = "viewOptionalPackages";
        }else{

            navigationResult = "addOptionalPackage";
        }

        return navigationResult;
    }

    public static void deleteOptionalPackageFromDB(String optPkgCode) {

        try {

            pstmt = getConnection().prepareStatement("delete from optional_packages where code = ?" );
            pstmt.setString(1, optPkgCode);
            pstmt.executeUpdate();
        } catch(Exception sqlException){
            showError(sqlException);
        }

        reload();
    }

    @NotNull
    public static String editOptionalPackageInDB(String optPkgCode) {

        OptionalPackageBean editRecord = null;
        /* Setting The Particular Lecturer Details In Session */
        Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        editOptionalPackgeCode = optPkgCode;

        try {

            pstmt = conn.prepareStatement("select * from optional_packages where code = ?");
            pstmt.setString(1, optPkgCode);
            resultSet = pstmt.executeQuery();
            if(resultSet != null) {
                resultSet.next();
                editRecord = new OptionalPackageBean();
                editRecord.setPackageId(resultSet.getString("code"));
                editRecord.setYear(resultSet.getInt("year"));
                editRecord.setSemester(resultSet.getInt("semester"));
                editRecord.setDisciplineNo(resultSet.getInt("disciplineno"));
            }
            sessionMapObj.put("editOptionalPackageObj", editRecord);

        } catch(Exception sqlException) {

            showError(sqlException);
            return "editOptionalCourse";
        }

        return "editOptionalPackage";
    }

    @NotNull
    public static String updateOptionalPackageDetailsInDB(OptionalPackageBean updateOptionalPackage) {

        try {
            pstmt = conn.prepareStatement("update optional_packages set code=?, year=?, semester=?, disciplineno=? where code=?");
            pstmt.setString(1, updateOptionalPackage.getPackageId());
            pstmt.setInt(2, updateOptionalPackage.getYear());
            pstmt.setInt(3, updateOptionalPackage.getSemester());
            pstmt.setInt(4, updateOptionalPackage.getDisciplineNo());
            pstmt.setString(5, editOptionalPackgeCode);
            pstmt.executeUpdate();

        } catch (Exception sqlException) {

            showError(sqlException);
            return "editOptionalPackage";
        }

        return "viewOptionalPackages";
    }

    /**
     * methods for Compulsory Courses
     */

    public static ArrayList getAllCompulsoryCourses() {

        ArrayList<CourseBean> lstCompulsoryCourses = new ArrayList<>();
        try {

            stmt = conn.createStatement();
            resultSet = stmt.executeQuery("select * from course");

            while (resultSet.next()) {

                if(resultSet.getString("optpackages_code") == null) {

                    CourseBean compulsoryCourse = new CourseBean();


                    compulsoryCourse.setId(resultSet.getInt("id"));
                    compulsoryCourse.setCode(resultSet.getString("code"));
                    compulsoryCourse.setShortName(resultSet.getString("short_name"));
                    compulsoryCourse.setName(resultSet.getString("name"));
                    compulsoryCourse.setYearOfStudy(resultSet.getInt("year"));
                    compulsoryCourse.setSemester(resultSet.getInt("semester"));
                    compulsoryCourse.setCredits(resultSet.getInt("credits"));
                    compulsoryCourse.setUrl(resultSet.getString("url"));

                    if(resultSet.getString("lecturer_name") != null){

                        compulsoryCourse.setLecturer(resultSet.getString("lecturer_name"));
                    }else{

                        compulsoryCourse.setLecturer("");
                    }

                    lstCompulsoryCourses.add(compulsoryCourse);
                }
            }
        } catch (Exception sqlSelectException) {

            showError(sqlSelectException);

        }

        return lstCompulsoryCourses;

    }

    public static void deleteCompulsoryCourseFromDB(int id) {

        try {

            pstmt = getConnection().prepareStatement("delete from course where id = ?" );
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch(Exception sqlException){
            showError(sqlException);
        }

        reload();
    }

    public static ArrayList getAllLecturersName() {

        ArrayList lstLecturersName = new ArrayList<>();
        try {

            stmt = conn.createStatement();
            resultSet = stmt.executeQuery("select * from lecturer");
            while (resultSet.next()) {

                String name = resultSet.getString("name");
                lstLecturersName.add(name);
            }

        } catch (Exception sqlSelectException) {

            showError(sqlSelectException);
        }

        return lstLecturersName;

    }

    public static String insertCompulsoryCoursesInDB(CourseBean newCompulsoryCourse) {

        int inserted = 0;
        String navigationResult = "";

        try{
            if(newCompulsoryCourse != null) {

                pstmt = conn.prepareStatement("insert into course(code, short_name, name, year, semester, credits, url, optpackages_code, lecturer_name) values(?, ?, ? , ?, ? , ? , ?, ? , ?)");
                pstmt.setString(1, newCompulsoryCourse.getCode());
                pstmt.setString(2, newCompulsoryCourse.getShortName());
                pstmt.setString(3, newCompulsoryCourse.getName());
                pstmt.setInt(4, newCompulsoryCourse.getYearOfStudy());
                pstmt.setInt(5, newCompulsoryCourse.getSemester());
                pstmt.setInt(6, newCompulsoryCourse.getCredits());
                pstmt.setString(7, newCompulsoryCourse.getUrl());
                pstmt.setString(8, null);
                pstmt.setString(9, newCompulsoryCourse.getLecturer());
            }

            inserted = pstmt.executeUpdate();
        } catch (SQLException e) {
            showError(e);
            navigationResult = "addCompulsoryCourse";
        }

        if(inserted != 0){

            navigationResult = "viewCompulsoryCourses";
        }else{

            navigationResult = "addCompulsoryCourse";
        }

        return navigationResult;
    }

    @NotNull
    public static String editCompulsoryCourse(int id) {

        CourseBean editRecord = null;
        /* Setting The Particular Lecturer Details In Session */
        Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        editCompulsoryCourseId = id;

        try {

            pstmt = conn.prepareStatement("select * from course where id = ?");
            pstmt.setInt(1, id);
            resultSet = pstmt.executeQuery();
            if(resultSet != null) {

                resultSet.next();
                editRecord = new CourseBean();
                editRecord.setCode(resultSet.getString("code"));
                editRecord.setShortName(resultSet.getString("short_name"));
                editRecord.setName(resultSet.getString("name"));
                editRecord.setYearOfStudy(resultSet.getInt("year"));
                editRecord.setSemester(resultSet.getInt("semester"));
                editRecord.setCredits(resultSet.getInt("credits"));
                editRecord.setUrl(resultSet.getString("url"));
                editRecord.setLecturer(resultSet.getString("lecturer_name"));
                editRecord.setOptPackage(null);
            }
            sessionMapObj.put("editCompulsoryCourseObj", editRecord);

        } catch(Exception sqlException) {

            showError(sqlException);
            return "editCompulsoryCourse";
        }

        return "editCompulsoryCourse";
    }


    @NotNull
    public static String updateCompulsoryCourseDetailsInDB(CourseBean updateCompulsoryCourse) {

        try {

            pstmt = conn.prepareStatement("update course set code=?, short_name=?, name=?, year=?, semester=?, credits=?, url=?, optpackages_code=?, lecturer_name=? where id=?");
            pstmt.setString(1, updateCompulsoryCourse.getCode());
            pstmt.setString(2, updateCompulsoryCourse.getShortName());
            pstmt.setString(3, updateCompulsoryCourse.getName());
            pstmt.setInt(4, updateCompulsoryCourse.getYearOfStudy());
            pstmt.setInt(5, updateCompulsoryCourse.getSemester());
            pstmt.setInt(6, updateCompulsoryCourse.getCredits());
            pstmt.setString(7, updateCompulsoryCourse.getUrl());
            pstmt.setString(8, null);
            pstmt.setString(9, updateCompulsoryCourse.getLecturer());
            pstmt.setInt(10, editCompulsoryCourseId);

            pstmt.executeUpdate();
        } catch (Exception sqlException) {

            showError(sqlException);
            return "editCompulsoryCourse";
        }

        return "viewCompulsoryCourses";
    }

    /**
     * methods for Optional Courses (delete is the same)
     */
    public static ArrayList getAllOptionalCourses() {

        ArrayList<CourseBean> lstOptionalCourses = new ArrayList<>();
        try {

            stmt = conn.createStatement();
            resultSet = stmt.executeQuery("select * from course");

            while (resultSet.next()) {

                if(resultSet.getString("optpackages_code") != null) {

                    OptionalCourseBean optionalCourse = new OptionalCourseBean();

                    optionalCourse.setOptPackage(resultSet.getString("optpackages_code"));
                    optionalCourse.setId(resultSet.getInt("id"));
                    optionalCourse.setCode(resultSet.getString("code"));
                    optionalCourse.setShortName(resultSet.getString("short_name"));
                    optionalCourse.setName(resultSet.getString("name"));
                    optionalCourse.setYearOfStudy(resultSet.getInt("year"));
                    optionalCourse.setSemester(resultSet.getInt("semester"));
                    optionalCourse.setCredits(resultSet.getInt("credits"));
                    optionalCourse.setUrl(resultSet.getString("url"));

                    if(resultSet.getString("lecturer_name") != null){

                        optionalCourse.setLecturer(resultSet.getString("lecturer_name"));
                    }else{

                        optionalCourse.setLecturer("");
                    }

                    lstOptionalCourses.add(optionalCourse);
                }
            }
        } catch (Exception sqlSelectException) {

            showError(sqlSelectException);

        }

        return lstOptionalCourses;

    }

    public static ArrayList getAllPackagesCodes() {

        ArrayList lstPkgCodes = new ArrayList<>();
        try {

            stmt = conn.createStatement();
            resultSet = stmt.executeQuery("select * from optional_packages");
            while (resultSet.next()) {

                String code = resultSet.getString("code");
                lstPkgCodes.add(code);
            }

        } catch (Exception sqlSelectException) {

            showError(sqlSelectException);
        }

        return lstPkgCodes;
    }

    public static String insertOptionalCoursesInDB(OptionalCourseBean newOptionalCourse) {

        int inserted = 0;
        String navigationResult = "";

        try{
            if(newOptionalCourse != null) {

                pstmt = conn.prepareStatement("insert into course(code, short_name, name, year, semester, credits, url, optpackages_code, lecturer_name) values(?, ?, ? , ?, ? , ? , ?, ? , ?)");
                pstmt.setString(1, newOptionalCourse.getCode());
                pstmt.setString(2, newOptionalCourse.getShortName());
                pstmt.setString(3, newOptionalCourse.getName());
                pstmt.setInt(4, newOptionalCourse.getYearOfStudy());
                pstmt.setInt(5, newOptionalCourse.getSemester());
                pstmt.setInt(6, newOptionalCourse.getCredits());
                pstmt.setString(7, newOptionalCourse.getUrl());
                pstmt.setString(8, newOptionalCourse.getOptPackage());
                pstmt.setString(9, newOptionalCourse.getLecturer());
            }

            inserted = pstmt.executeUpdate();
        } catch (SQLException e) {
            showError(e);
            navigationResult = "addOptionalCourse";
        }

        if(inserted != 0){

            navigationResult = "viewOptionalCourses";
        }else{

            navigationResult = "addOptionalCourse";
        }

        return navigationResult;
    }

    @NotNull
    public static String editOptionalCourse(int id) {

        CourseBean editRecord = null;
        /* Setting The Particular Lecturer Details In Session */
        Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        editOptionalCourseId = id;

        try {

            pstmt = conn.prepareStatement("select * from course where id = ?");
            pstmt.setInt(1, id);
            resultSet = pstmt.executeQuery();
            if(resultSet != null) {

                resultSet.next();
                editRecord = new OptionalCourseBean();
                editRecord.setCode(resultSet.getString("code"));
                editRecord.setShortName(resultSet.getString("short_name"));
                editRecord.setName(resultSet.getString("name"));
                editRecord.setYearOfStudy(resultSet.getInt("year"));
                editRecord.setSemester(resultSet.getInt("semester"));
                editRecord.setCredits(resultSet.getInt("credits"));
                editRecord.setUrl(resultSet.getString("url"));
                editRecord.setLecturer(resultSet.getString("lecturer_name"));
                editRecord.setOptPackage(resultSet.getString("optpackages_code"));
            }
            sessionMapObj.put("editOptionalCourseObj", editRecord);

        } catch(Exception sqlException) {

            showError(sqlException);
            return "editOptionalCourse";
        }

        return "editOptionalCourse";
    }

    @NotNull
    public static String updateOptionalCourseDetailsInDB(OptionalCourseBean updateOptionalCourse) {

        try {

            pstmt = conn.prepareStatement("update course set code=?, short_name=?, name=?, year=?, semester=?, credits=?, url=?, optpackages_code=?, lecturer_name=? where id=?");
            pstmt.setString(1, updateOptionalCourse.getCode());
            pstmt.setString(2, updateOptionalCourse.getShortName());
            pstmt.setString(3, updateOptionalCourse.getName());
            pstmt.setInt(4, updateOptionalCourse.getYearOfStudy());
            pstmt.setInt(5, updateOptionalCourse.getSemester());
            pstmt.setInt(6, updateOptionalCourse.getCredits());
            pstmt.setString(7, updateOptionalCourse.getUrl());
            pstmt.setString(8, updateOptionalCourse.getOptPackage());
            pstmt.setString(9, updateOptionalCourse.getLecturer());
            pstmt.setInt(10, editOptionalCourseId);

            pstmt.executeUpdate();
        } catch (Exception sqlException) {

            showError(sqlException);
            return "editOptionalCourse";
        }

        return "viewOptionalCourses";
    }

}
