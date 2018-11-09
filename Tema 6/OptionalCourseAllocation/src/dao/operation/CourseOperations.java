package dao.operation;

import bean.CourseBean;
import util.Constants;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

@ManagedBean(name = "courseOperations")
@RequestScoped
public class CourseOperations extends DatabaseOperations<CourseBean> {
    private static String updateKey;
    @Override
    public ArrayList<CourseBean> getAll() {
        ArrayList<CourseBean> lstCompulsoryCourses = new ArrayList<>();
        try {

            stmt = connection.createStatement();
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
            sqlSelectException.printStackTrace();
            //TODO showError(sqlSelectException);

        }

        return lstCompulsoryCourses;
    }

    @Override
    public String insert(CourseBean newRecord) {
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
                pstmt.setString(8, null);
                pstmt.setString(9, newRecord.getLecturer());
            }

            inserted = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            //TODO showError(e);
            navigationResult = "addCompulsoryCourse";
        }

        if(inserted != 0){

            navigationResult = "viewCompulsoryCourses";
        }else{

            navigationResult = "addCompulsoryCourse";
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
        //TODO SAME AS IN LECTURER editCompulsoryCourseId = id;
        if(primaryKey!=null && primaryKey.trim().length()>0) {
            try {

                pstmt = connection.prepareStatement("select * from course where id = ?");
                pstmt.setInt(1, Integer.parseInt(primaryKey));
                resultSet = pstmt.executeQuery();
                if (resultSet != null) {

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
                sessionMapObj.put("courseBean", editRecord);
                updateKey = primaryKey;
            } catch (Exception sqlException) {

                //showError(sqlException);
                return "editCompulsoryCourse";
            }
        }else{
            sessionMapObj.remove("courseBean");
            updateKey = null;
        }
        return "editCompulsoryCourse";
    }

    @Override
    public String update(CourseBean updateRecord) {
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
            pstmt.setString(8, null);
            pstmt.setString(9, updateRecord.getLecturer());
            pstmt.setInt(10, Integer.valueOf(updateKey));

            pstmt.executeUpdate();
        } catch (Exception sqlException) {

            //showError(sqlException);
            return Constants.Course.Routing.EDIT;
        }

        return Constants.Course.Routing.VIEW;
    }

    @Override
    public String cancel() {
        return Constants.Course.Routing.VIEW;
    }
}
