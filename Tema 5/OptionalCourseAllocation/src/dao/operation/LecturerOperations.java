package dao.operation;

import bean.LecturerBean;
import util.Constants;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

@ManagedBean(name = "lecturerOperations")
@RequestScoped
public class LecturerOperations extends DatabaseOperations<LecturerBean>{
    private static String updateKey;

    @Override
    public ArrayList<LecturerBean> getAll() {
        ArrayList<LecturerBean> allLecturers = new ArrayList<>();
        try {

            if(connection !=null) {
                stmt = connection.createStatement();
                resultSet = stmt.executeQuery("select * from lecturer");
                while (resultSet.next()) {

                    LecturerBean lecturerObj = new LecturerBean();
                    lecturerObj.setName(resultSet.getString(Constants.Lecturer.Table.COLUMN_NAME));
                    lecturerObj.setEmail(resultSet.getString(Constants.Lecturer.Table.COLUMN_EMAIL));
                    lecturerObj.setGender(resultSet.getString(Constants.Lecturer.Table.COLUMN_GENDER));
                    lecturerObj.setUrl(resultSet.getString(Constants.Lecturer.Table.COLUMN_URL));

                    allLecturers.add(lecturerObj);
                }
            }
        } catch (Exception sqlSelectException) {
            sqlSelectException.printStackTrace();
            //TODO showError(sqlSelectException);
        }

        return allLecturers;
    }

    /**
     *
     * @param newRecord
     * @return string as navigation result - where to route next
     */

    @Override
    public String insert(LecturerBean newRecord) {

        int inserted = 0;
        String navigationResult = "";

        try{
            if(newRecord != null) {

                pstmt = connection.prepareStatement("insert into lecturer(name, email, gender, url) values(?, ?, ? , ?)");
                pstmt.setString(1, newRecord.getName());
                pstmt.setString(2, newRecord.getEmail());
                pstmt.setString(3, newRecord.getGender());
                pstmt.setString(4, newRecord.getUrl());
            }

            inserted = pstmt.executeUpdate();
        } catch (SQLException e) {
            //TODO showError(e);
            navigationResult = Constants.Lecturer.Routing.EDIT;
        }

        if(inserted != 0){

            navigationResult = Constants.Lecturer.Routing.VIEW;
        }else{

            navigationResult = Constants.Lecturer.Routing.EDIT;
        }

        return navigationResult;
    }

    @Override
    public void delete(String deleteRecord) {
        try {

            pstmt = connection.prepareStatement("delete from lecturer where name = ?" );
            pstmt.setString(1, deleteRecord);
            pstmt.executeUpdate();
        } catch(Exception sqlException){
            sqlException.printStackTrace();
            //TODO showError(sqlException);
        }
    }

    @Override
    public String getForEdit(String primaryKey) {
        LecturerBean editRecord = null;
        /* Setting The Particular Lecturer Details In Session */
        Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

        //TODO watch out for this it has been commented out and you need to find a soultion to be passed as parameter to update String editLecturerName = primaryKey;
        if(primaryKey!=null && primaryKey.trim().length()>0) {
            try {

                pstmt = connection.prepareStatement("select * from lecturer where name = ?");
                pstmt.setString(1, primaryKey);
                resultSet = pstmt.executeQuery();
                if (resultSet != null) {
                    resultSet.next();
                    editRecord = new LecturerBean();
                    editRecord.setName(resultSet.getString(Constants.Lecturer.Table.COLUMN_NAME));
                    editRecord.setEmail(resultSet.getString(Constants.Lecturer.Table.COLUMN_EMAIL));
                    editRecord.setGender(resultSet.getString(Constants.Lecturer.Table.COLUMN_GENDER));
                    editRecord.setUrl(resultSet.getString(Constants.Lecturer.Table.COLUMN_URL));
                }
                sessionMapObj.put(Constants.Lecturer.SessionKeys.EDIT_RECORD_KEY, editRecord);
                updateKey = primaryKey;
            } catch (Exception sqlException) {
                sqlException.printStackTrace();
                //TODO showError(sqlException);
                return Constants.Lecturer.Routing.EDIT;
            }
        }else{
            sessionMapObj.remove(Constants.Lecturer.SessionKeys.EDIT_RECORD_KEY);
            updateKey = null;
        }
        return Constants.Lecturer.Routing.EDIT;
    }

    @Override
    public String update(LecturerBean updateRecord) {

        if(updateKey == null || updateKey.trim().length()<0)
        {
            return insert(updateRecord);
        }
        try {

            pstmt = connection.prepareStatement("update lecturer set name=?, email=?, gender=?, url=? where name=?");
            pstmt.setString(1, updateRecord.getName());
            pstmt.setString(2, updateRecord.getEmail());
            pstmt.setString(3, updateRecord.getGender());
            pstmt.setString(4, updateRecord.getUrl());
            pstmt.setString(5, updateKey);
            pstmt.executeUpdate();

        } catch (Exception sqlException) {

            //TODO showError(sqlException);
            return Constants.Lecturer.Routing.EDIT;
        }

        return Constants.Lecturer.Routing.VIEW;
    }

    @Override
    public String cancel(){

        return Constants.Lecturer.Routing.VIEW;
    }
}
