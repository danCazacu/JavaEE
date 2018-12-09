package dao;

import entity.*;
import org.eclipse.persistence.annotations.CascadeOnDelete;
import util.Constants;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ManagedBean(name = "optionalPackageOperations")
@RequestScoped
public class OptionalPackageOperations extends DatabaseOperations<OptionalPackageEntity> {

    private String updateKey;

    public List<String> getAllPackagesCodes() {

        return (List<String>) getEntityManager().createQuery("Select t.code from " + OptionalPackageEntity.class.getSimpleName() + " t").getResultList();

    }

    public static OptionalPackageEntity findByCode(String code) {

        Query query1 = getEntityManager().createQuery("SELECT  t from " + OptionalPackageEntity.class.getSimpleName() + " t WHERE t.code = '" + code + "'");
        OptionalPackageEntity result = (OptionalPackageEntity) query1.getSingleResult();

        return result;
    }

    @Override
    public String getForEdit(String primaryKey) {

        OptionalPackageEntity editRecord = null;

        if(primaryKey != null && primaryKey.trim().length() > 0) {
            try {


                editRecord = (OptionalPackageEntity) getEntityManager().createNamedQuery("findOptionalPackage")
                        .setParameter("code", primaryKey)
                        .getSingleResult();


                if(editRecord != null) {

                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(Constants.OptionalPackage.SessionKeys.EDIT_RECORD_KEY, editRecord);
                    updateKey = primaryKey;
                }
            } catch (Exception sqlException) {
                sqlException.printStackTrace();
                //TODO showError(sqlException);
                //TODO error page??? or a message in viewPage
                return "editOptionalPackage";
            }
        }else{

            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(Constants.OptionalPackage.SessionKeys.EDIT_RECORD_KEY);
            updateKey = null;
        }
        return "editOptionalPackage";
    }

    @Override
    public String update(OptionalPackageEntity updateRecord) {

        if(updateKey == null || updateKey.trim().length() < 0){

            return insert(updateRecord);
        }
        try {

            if(!getEntityManager().getTransaction().isActive()){

                getEntityManager().getTransaction().begin();
            }

            OptionalPackageEntity oldRecord = (OptionalPackageEntity) getEntityManager().createNamedQuery("findOptionalPackage")
                    .setParameter("code", updateKey)
                    .getSingleResult();

            oldRecord.setCode(updateRecord.getCode());
            oldRecord.setYear(updateRecord.getYear());
            oldRecord.setSemester(updateRecord.getSemester());
            oldRecord.setCourses(updateRecord.getCourses());

            oldRecord.setCourses(updateRecord.getCourses());

            getEntityManager().flush();
            getEntityManager().getTransaction().commit();

        } catch (Exception sqlException) {

            //TODO showError(sqlException);
            return "error";
        }

        return "success";
    }
}
