package dao.operation;

import bean.OptionalPackageBean;
import util.Constants;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

@ManagedBean(name = "optionalPackageOperations")
@RequestScoped
public class OptionalPackageOperations extends DatabaseOperations<OptionalPackageBean> {
    private static String updateKey;

    public ArrayList<String> getAllPackagesCodes() {

        ArrayList<OptionalPackageBean> optionalPackagesBeans = getAll();
        ArrayList<String> lstOptionalPackagesCodes = new ArrayList<>();
       for(OptionalPackageBean optionalPackageBean: optionalPackagesBeans){

           lstOptionalPackagesCodes.add(optionalPackageBean.getPackageId());
       }

        return lstOptionalPackagesCodes;
    }

    @Override
    public ArrayList<OptionalPackageBean> getAll() {
        ArrayList<OptionalPackageBean> lstOptionalPackages = new ArrayList<>();
        try {

            if(connection != null) {

                stmt = connection.createStatement();
                resultSet = stmt.executeQuery("select * from optional_packages");
                while (resultSet.next()) {

                    OptionalPackageBean optionalPackageObj = new OptionalPackageBean();
                    optionalPackageObj.setPackageId(resultSet.getString(Constants.OptionalPackage.Table.COLUMN_CODE));
                    optionalPackageObj.setYear(resultSet.getInt(Constants.OptionalPackage.Table.COLUMN_YEAR));
                    optionalPackageObj.setSemester(resultSet.getInt(Constants.OptionalPackage.Table.COLUMN_SEMESTER));
                    optionalPackageObj.setDisciplineNumber(resultSet.getInt(Constants.OptionalPackage.Table.COLUMN_DISCIPLINE_NUMBER));

                    lstOptionalPackages.add(optionalPackageObj);
                }
            }
        } catch (Exception sqlSelectException) {
            sqlSelectException.printStackTrace();
            //showError(sqlSelectException);
        }

        return lstOptionalPackages;
    }

    @Override
    public String insert(OptionalPackageBean newRecord) {
        int inserted = 0;
        String navigationResult = "";

        try{
            if(newRecord != null) {

                pstmt = connection.prepareStatement("insert into optional_packages(code, year, semester, disciplineno) values(?, ?, ? , ?)");
                pstmt.setString(1, newRecord.getPackageId());
                pstmt.setInt(2, newRecord.getYear());
                pstmt.setInt(3, newRecord.getSemester());
                pstmt.setInt(4, newRecord.getDisciplineNumber());
            }

            inserted = pstmt.executeUpdate();
        } catch (SQLException insertSQLException) {
            //TODO showError(e);
            insertSQLException.printStackTrace();
            navigationResult = Constants.OptionalPackage.Routing.EDIT;
        }

        if(inserted != 0){

            navigationResult = Constants.OptionalPackage.Routing.VIEW;
        }else{

            navigationResult = Constants.OptionalPackage.Routing.EDIT;
        }

        return navigationResult;
    }

    @Override
    public void delete(String deleteRecord) {
        try {

            pstmt = connection.prepareStatement("delete from optional_packages where code = ?" );
            pstmt.setString(1, deleteRecord);
            pstmt.executeUpdate();
        } catch(Exception sqlException){
            sqlException.printStackTrace();
            //TODO showError(sqlException);
        }
    }

    @Override
    public String getForEdit(String primaryKey) {
        OptionalPackageBean editRecord = null;
        /* Setting The Particular Lecturer Details In Session */
        Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        //TODO editOptionalPackgeCode = optPkgCode;
        if(primaryKey!=null && primaryKey.trim().length()>0) {
            try {

                pstmt = connection.prepareStatement("select * from optional_packages where code = ?");
                pstmt.setString(1, primaryKey);
                resultSet = pstmt.executeQuery();
                if (resultSet != null) {
                    resultSet.next();
                    editRecord = new OptionalPackageBean();
                    editRecord.setPackageId(resultSet.getString(Constants.OptionalPackage.Table.COLUMN_CODE));
                    editRecord.setYear(resultSet.getInt(Constants.OptionalPackage.Table.COLUMN_YEAR));
                    editRecord.setSemester(resultSet.getInt(Constants.OptionalPackage.Table.COLUMN_SEMESTER));
                    editRecord.setDisciplineNumber(resultSet.getInt(Constants.OptionalPackage.Table.COLUMN_DISCIPLINE_NUMBER));
                }
                sessionMapObj.put(Constants.OptionalPackage.SessionKeys.EDIT_RECORD_KEY, editRecord);
                updateKey = primaryKey;
            } catch (Exception sqlException) {

                sqlException.printStackTrace();
                //TODO showError(sqlException);
                return Constants.OptionalPackage.Routing.EDIT;
            }
        }else{
            sessionMapObj.remove(Constants.OptionalPackage.SessionKeys.EDIT_RECORD_KEY);
            updateKey = null;
        }
        return Constants.OptionalPackage.Routing.EDIT;
    }

    @Override
    public String update(OptionalPackageBean updateRecord) {
        if(updateKey == null || updateKey.trim().length()<0)
        {
            return insert(updateRecord);
        }
        try {
            pstmt = connection.prepareStatement("update optional_packages set code=?, year=?, semester=?, disciplineno=? where code=?");
            pstmt.setString(1, updateRecord.getPackageId());
            pstmt.setInt(2, updateRecord.getYear());
            pstmt.setInt(3, updateRecord.getSemester());
            pstmt.setInt(4, updateRecord.getDisciplineNumber());
            pstmt.setString(5, updateKey);
            pstmt.executeUpdate();

        } catch (Exception sqlException) {

            //TODO showError(sqlException);
            return Constants.OptionalPackage.Routing.EDIT;
        }

        return Constants.OptionalPackage.Routing.VIEW;
    }

    @Override
    public String cancel() {

        return Constants.OptionalPackage.Routing.VIEW;
    }
}
