package dao.operation;

import bean.OptionalPackageBean;

import javax.faces.context.FacesContext;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class OptionalPackageOperations extends DatabaseOperations<OptionalPackageBean> {
    @Override
    public ArrayList<OptionalPackageBean> getAll() {
        ArrayList<OptionalPackageBean> lstOptionalPackages = new ArrayList<>();
        try {
//
//            if(connection == null){
//
//                OptionalPackageBean optPkgObj = new OptionalPackageBean();
//                optPkgObj.setPackageId("conn null");
//
//                lstOptionalPackages.add(optPkgObj);
//            }

            stmt = connection.createStatement();
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
                optPkgObj.setDisciplineNumber(resultSet.getInt("disciplineNo"));
                lstOptionalPackages.add(optPkgObj);
            }
        } catch (Exception sqlSelectException) {
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
                pstmt.setInt(2, newRecord.getSemester());
                pstmt.setInt(3, newRecord.getYear());
                pstmt.setInt(4, newRecord.getDisciplineNumber());
            }

            inserted = pstmt.executeUpdate();
        } catch (SQLException e) {
            //showError(e);
            navigationResult = "addOptionalPackage";
        }

        if(inserted != 0){

            navigationResult = "viewOptionalPackages";
        }else{

            navigationResult = "addOptionalPackage";
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
            //showError(sqlException);
        }

        //reload();
    }

    @Override
    public String getForEdit(String primaryKey) {
        OptionalPackageBean editRecord = null;
        /* Setting The Particular Lecturer Details In Session */
        Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        //TODO editOptionalPackgeCode = optPkgCode;

        try {

            pstmt = connection.prepareStatement("select * from optional_packages where code = ?");
            pstmt.setString(1, primaryKey);
            resultSet = pstmt.executeQuery();
            if(resultSet != null) {
                resultSet.next();
                editRecord = new OptionalPackageBean();
                editRecord.setPackageId(resultSet.getString("code"));
                editRecord.setYear(resultSet.getInt("year"));
                editRecord.setSemester(resultSet.getInt("semester"));
                editRecord.setDisciplineNumber(resultSet.getInt("disciplineno"));
            }
            sessionMapObj.put("editOptionalPackageObj", editRecord);

        } catch(Exception sqlException) {

            //showError(sqlException);
            return "editOptionalCourse";
        }

        return "editOptionalPackage";
    }

    @Override
    public String update(OptionalPackageBean updateRecord, String primaryKey) {
        try {
            pstmt = connection.prepareStatement("update optional_packages set code=?, year=?, semester=?, disciplineno=? where code=?");
            pstmt.setString(1, updateRecord.getPackageId());
            pstmt.setInt(2, updateRecord.getYear());
            pstmt.setInt(3, updateRecord.getSemester());
            pstmt.setInt(4, updateRecord.getDisciplineNumber());
            pstmt.setString(5, primaryKey);
            pstmt.executeUpdate();

        } catch (Exception sqlException) {

            //showError(sqlException);
            return "editOptionalPackage";
        }

        return "viewOptionalPackages";
    }
}
