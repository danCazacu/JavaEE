package dao.operation;
import bean.database.CourseBean;
import bean.database.OptionalCourseBean;
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

                if(resultSet.getString(Constants.Course.Table.COLUMN_FK_OPTIONAL_PACKAGES_CODE) != null) {

                    OptionalCourseBean optionalCourse = new OptionalCourseBean();

                    optionalCourse.setOptPackage(resultSet.getString(Constants.Course.Table.COLUMN_FK_OPTIONAL_PACKAGES_CODE));
                    optionalCourse.setId(resultSet.getInt(Constants.Course.Table.COLUMN_ID));
                    optionalCourse.setCode(resultSet.getString(Constants.Course.Table.COLUMN_CODE));
                    optionalCourse.setShortName(resultSet.getString(Constants.Course.Table.COLUMN_SHORT_NAME));
                    optionalCourse.setName(resultSet.getString(Constants.Course.Table.COLUMN_NAME));
                    optionalCourse.setYearOfStudy(resultSet.getInt(Constants.Course.Table.COLUMN_YEAR));
                    optionalCourse.setSemester(resultSet.getInt(Constants.Course.Table.COLUMN_SEMESTER));
                    optionalCourse.setCredits(resultSet.getInt(Constants.Course.Table.COLUMN_CREDITS));
                    optionalCourse.setUrl(resultSet.getString(Constants.Course.Table.COLUMN_URL));

                    if(resultSet.getString(Constants.Course.Table.COLUMN_FK_LECTURER_NAME) != null){

                        optionalCourse.setLecturer(resultSet.getString(Constants.Course.Table.COLUMN_FK_LECTURER_NAME));
                    }else{

                        optionalCourse.setLecturer("");
                    }

                    lstOptionalCourses.add(optionalCourse);
                }
            }
        } catch (Exception sqlSelectException) {

            sqlSelectException.printStackTrace();
            //TODO showError(sqlSelectException);

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
        } catch (SQLException insertSQLException) {
            insertSQLException.printStackTrace();
            //TODO showError(e);
            navigationResult = Constants.Course.RoutingOptional.EDIT;
        }

        if(inserted != 0){

            navigationResult = Constants.Course.RoutingOptional.VIEW;
        }else{

            navigationResult = Constants.Course.RoutingOptional.EDIT;
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
            sqlException.printStackTrace();
            //TODO showError(sqlException);
        }
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
                    editRecord.setCode(resultSet.getString(Constants.Course.Table.COLUMN_CODE));
                    editRecord.setShortName(resultSet.getString(Constants.Course.Table.COLUMN_SHORT_NAME));
                    editRecord.setName(resultSet.getString(Constants.Course.Table.COLUMN_NAME));
                    editRecord.setYearOfStudy(resultSet.getInt(Constants.Course.Table.COLUMN_YEAR));
                    editRecord.setSemester(resultSet.getInt(Constants.Course.Table.COLUMN_SEMESTER));
                    editRecord.setCredits(resultSet.getInt(Constants.Course.Table.COLUMN_CREDITS));
                    editRecord.setUrl(resultSet.getString(Constants.Course.Table.COLUMN_URL));
                    editRecord.setLecturer(resultSet.getString(Constants.Course.Table.COLUMN_FK_LECTURER_NAME));
                    editRecord.setOptPackage(resultSet.getString(Constants.Course.Table.COLUMN_FK_OPTIONAL_PACKAGES_CODE));
                }
                sessionMapObj.put(Constants.Course.SessionKeysOptional.EDIT_RECORD_KEY, editRecord);
                updateKey = primaryKey;
            } catch (Exception sqlException) {

                sqlException.printStackTrace();
                //TODO showError(sqlException);
                return Constants.Course.RoutingOptional.EDIT;
            }
        }else{
            sessionMapObj.remove(Constants.Course.SessionKeysOptional.EDIT_RECORD_KEY);
            updateKey = null;
        }

        return Constants.Course.RoutingOptional.EDIT;
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
            return Constants.Course.RoutingOptional.EDIT;
        }

        return Constants.Course.RoutingOptional.VIEW;
    }

    @Override
    public String cancel() {
        return Constants.Course.RoutingOptional.VIEW;
    }
}
