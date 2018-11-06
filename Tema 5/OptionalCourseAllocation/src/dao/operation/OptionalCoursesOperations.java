package dao.operation;

import bean.CourseBean;
import bean.OptionalCourseBean;
import util.Constants;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;


@ManagedBean(name = "optionalCourseOperations")
@RequestScoped
public class OptionalCoursesOperations extends DatabaseOperations<OptionalCourseBean> {
    private static String updateKey;
    @Override
    public ArrayList<OptionalCourseBean> getAll() {
        ArrayList<OptionalCourseBean> lstOptionalCourses = new ArrayList<>();
        try {

            stmt = connection.createStatement();
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

            //showError(sqlSelectException);

        }

        return lstOptionalCourses;

    }

    @Override
    public String insert(OptionalCourseBean newRecord) {
        int inserted = 0;
        String navigationResult = "";

        try{
            if(newRecord != null) {

                pstmt = connection.prepareStatement("insert into course(code, short_name, name, year, semester, credits, url, optpackages_code, lecturer_name) values(?, ?, ? , ?, ? , ? , ?, ? , ?)");
                pstmt.setString(1, newRecord.getCode());
                pstmt.setString(2, newRecord.getShortName());
                pstmt.setString(3, newRecord.getName());
                pstmt.setInt(4, newRecord.getYearOfStudy());
                pstmt.setInt(5, newRecord.getSemester());
                pstmt.setInt(6, newRecord.getCredits());
                pstmt.setString(7, newRecord.getUrl());
                pstmt.setString(8, newRecord.getOptPackage());
                pstmt.setString(9, newRecord.getLecturer());
            }

            inserted = pstmt.executeUpdate();
        } catch (SQLException e) {
            //showError(e);
            navigationResult = "addOptionalCourse";
        }

        if(inserted != 0){

            navigationResult = "viewOptionalCourses";
        }else{

            navigationResult = "addOptionalCourse";
        }

        return navigationResult;
    }

    @Override
    public void delete(String deleteRecord) {
        try {

            pstmt = connection.prepareStatement("delete from course where id = ?" );
            pstmt.setInt(1, Integer.parseInt(deleteRecord));
            pstmt.executeUpdate();
        } catch(Exception sqlException){
            //showError(sqlException);
        }

        //reload();
    }

    @Override
    public String getForEdit(String primaryKey) {
        CourseBean editRecord = null;
        /* Setting The Particular Lecturer Details In Session */
        Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        //TODO editOptionalCourseId = id;
        if(primaryKey!=null && primaryKey.trim().length()>0) {
            try {

                pstmt = connection.prepareStatement("select * from course where id = ?");
                pstmt.setInt(1, Integer.valueOf(primaryKey));
                resultSet = pstmt.executeQuery();
                if (resultSet != null) {

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
                updateKey = primaryKey;
            } catch (Exception sqlException) {

                //showError(sqlException);
                return "editOptionalCourse";
            }
        }else{
            sessionMapObj.remove(Constants.Lecturer.SessionKeys.EDIT_RECORD_KEY);
            updateKey = null;
        }

        return "editOptionalCourse";
    }

    @Override
    public String update(OptionalCourseBean updateRecord) {
        if(updateKey == null || updateKey.trim().length()<0)
        {
            return insert(updateRecord);
        }
        try {

            pstmt = connection.prepareStatement("update course set code=?, short_name=?, name=?, year=?, semester=?, credits=?, url=?, optpackages_code=?, lecturer_name=? where id=?");
            pstmt.setString(1, updateRecord.getCode());
            pstmt.setString(2, updateRecord.getShortName());
            pstmt.setString(3, updateRecord.getName());
            pstmt.setInt(4, updateRecord.getYearOfStudy());
            pstmt.setInt(5, updateRecord.getSemester());
            pstmt.setInt(6, updateRecord.getCredits());
            pstmt.setString(7, updateRecord.getUrl());
            pstmt.setString(8, updateRecord.getOptPackage());
            pstmt.setString(9, updateRecord.getLecturer());
            pstmt.setInt(10, Integer.valueOf(updateKey));

            pstmt.executeUpdate();
        } catch (Exception sqlException) {

            //showError(sqlException);
            return "editOptionalCourse";
        }

        return "viewOptionalCourses";
    }

    @Override
    public String cancel() {
        return null;
    }
}
