package dao;

import entity.LecturerEntity;
import util.Constants;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.Query;

@ManagedBean(name = "lecturerOperations")
@RequestScoped
public class LecturerOperations extends DatabaseOperations<LecturerEntity> {

    private static String updateKey;

    public static LecturerEntity findByName(String name) {

        Query query1 = getEntityManager().createQuery("SELECT  t from " + LecturerEntity.class.getSimpleName() + " t WHERE t.name = '" + name + "'");
        LecturerEntity result = (LecturerEntity) query1.getSingleResult();

        return result;
    }

    @Override
    public String getForEdit(String primaryKey) {

        LecturerEntity editRecord = null;

        if(primaryKey != null && primaryKey.trim().length() > 0) {
            try {


                editRecord = (LecturerEntity) getEntityManager().createNamedQuery("findByName")
                        .setParameter("name", primaryKey)
                        .getSingleResult();


                if(editRecord != null) {

                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(Constants.Lecturer.SessionKeys.EDIT_RECORD_KEY, editRecord);
                    updateKey = primaryKey;
                }
            } catch (Exception sqlException) {
                sqlException.printStackTrace();
                //TODO showError(sqlException);
                //TODO error page??? or a message in viewPage
                return "editLecturer";
            }
        }else{

            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(Constants.Lecturer.SessionKeys.EDIT_RECORD_KEY);
            updateKey = null;
        }
        return "editLecturer";
    }

    @Override
    public String update(LecturerEntity updateRecord) {

        if(updateKey == null || updateKey.trim().length() < 0){

            return insert(updateRecord);
        }
        try {

            if(!getEntityManager().getTransaction().isActive()){

                getEntityManager().getTransaction().begin();
            }

            LecturerEntity oldRecord = (LecturerEntity) getEntityManager().createNamedQuery("findByName")
                    .setParameter("name", updateKey)
                    .getSingleResult();

            oldRecord.setName(updateRecord.getName());
            oldRecord.setEmail(updateRecord.getEmail());
            oldRecord.setUrl(updateRecord.getUrl());
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
