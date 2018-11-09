package dao.operation;

import bean.database.CourseBean;
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

    public ArrayList<String> getAllCompulsoryCoursesNames(){
        ArrayList<CourseBean> compulsoryCourseBeans = getAll();
        ArrayList<String> names = new ArrayList<>();
        for (CourseBean compulsoryCourseBean: compulsoryCourseBeans) {
            names.add(compulsoryCourseBean.getName());
        }
        return names;
    }

    @Override
    public ArrayList<CourseBean> getAll() {

        ArrayList<CourseBean> lstCompulsoryCourses = new ArrayList<>();
        try {

            stmt = connection.createStatement();
            resultSet = stmt.executeQuery("select * from course");

            while (resultSet.next()) {

                if(resultSet.getString("optpackages_code") == null) {

                    CourseBean compulsoryCourse = new CourseBean();

                    compulsoryCourse.setId(resultSet.getInt(Constants.Course.Table.COLUMN_ID));
                    compulsoryCourse.setCode(resultSet.getString(Constants.Course.Table.COLUMN_CODE));
                    compulsoryCourse.setShortName(resultSet.getString(Constants.Course.Table.COLUMN_SHORT_NAME));
                    compulsoryCourse.setName(resultSet.getString(Constants.Course.Table.COLUMN_NAME));
                    compulsoryCourse.setYearOfStudy(resultSet.getInt(Constants.Course.Table.COLUMN_YEAR));
                    compulsoryCourse.setSemester(resultSet.getInt(Constants.Course.Table.COLUMN_SEMESTER));
                    compulsoryCourse.setCredits(resultSet.getInt(Constants.Course.Table.COLUMN_CREDITS));
                    compulsoryCourse.setUrl(resultSet.getString(Constants.Course.Table.COLUMN_URL));

                    if(resultSet.getString("lecturer_name") != null){

                        compulsoryCourse.setLecturer(resultSet.getString(Constants.Course.Table.COLUMN_FK_LECTURER_NAME));
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
        } catch (SQLException insertSQLException) {
            insertSQLException.printStackTrace();
            //TODO showError(e);
            navigationResult = Constants.Course.RoutingCompulsory.EDIT;
        }

        if(inserted != 0){

            navigationResult =  Constants.Course.RoutingCompulsory.VIEW;
        }else{

            navigationResult =  Constants.Course.RoutingCompulsory.EDIT;
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
        //TODO SAME AS IN LECTURER editCompulsoryCourseId = id;
        if(primaryKey!=null && primaryKey.trim().length()>0) {
            try {

                pstmt = connection.prepareStatement("select * from course where id = ?");
                pstmt.setInt(1, Integer.parseInt(primaryKey));
                resultSet = pstmt.executeQuery();
                if (resultSet != null) {

                    resultSet.next();
                    editRecord = new CourseBean();
                    editRecord.setCode(resultSet.getString(Constants.Course.Table.COLUMN_CODE));
                    editRecord.setShortName(resultSet.getString(Constants.Course.Table.COLUMN_SHORT_NAME));
                    editRecord.setName(resultSet.getString(Constants.Course.Table.COLUMN_NAME));
                    editRecord.setYearOfStudy(resultSet.getInt(Constants.Course.Table.COLUMN_YEAR));
                    editRecord.setSemester(resultSet.getInt(Constants.Course.Table.COLUMN_SEMESTER));
                    editRecord.setCredits(resultSet.getInt(Constants.Course.Table.COLUMN_CREDITS));
                    editRecord.setUrl(resultSet.getString(Constants.Course.Table.COLUMN_URL));
                    editRecord.setLecturer(resultSet.getString(Constants.Course.Table.COLUMN_FK_LECTURER_NAME));
                    editRecord.setOptPackage(null);
                }
                sessionMapObj.put(Constants.Course.SessionKeysCompulsory.EDIT_RECORD_KEY, editRecord);
                updateKey = primaryKey;
            } catch (Exception sqlException) {

                sqlException.printStackTrace();
                //TODO showError(sqlException);
                return Constants.Course.RoutingCompulsory.EDIT;
            }
        }else{
            sessionMapObj.remove(Constants.Course.SessionKeysCompulsory.EDIT_RECORD_KEY);
            updateKey = null;
        }
        return Constants.Course.RoutingCompulsory.EDIT;
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

            sqlException.printStackTrace();
            //TODO showError(sqlException);
            return Constants.Course.RoutingCompulsory.EDIT;
        }

        return Constants.Course.RoutingCompulsory.VIEW;
    }

    @Override
    public String cancel() {
        return Constants.Course.RoutingCompulsory.VIEW;
    }
}
