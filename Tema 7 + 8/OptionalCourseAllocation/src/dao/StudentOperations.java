package dao;

import entity.LecturerEntity;
import entity.OptionalCourseEntity;
import entity.StudentEntity;
import entity.StudentPreferencesEntity;
import org.eclipse.persistence.annotations.CascadeOnDelete;
import util.Constants;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


@ManagedBean(name = "studentOperations")
@RequestScoped
public class StudentOperations extends DatabaseOperations<StudentEntity> {

    private static String updateKey;

    public String getCOmpleteOrIncomplete(StudentEntity student){

        //get optional courses no of year each year;
        Query queryGetNoOfOptCourses = getEntityManager().createQuery("SELECT o from " + OptionalCourseEntity.class.getSimpleName() + " o WHERE o.course.year = '" + student.getYear() + "'");
        int noOfOptCourses = queryGetNoOfOptCourses.getResultList().size();

        Query getCountOfStudIdIsFound = getEntityManager().createQuery("SELECT  o from " + StudentPreferencesEntity.class.getSimpleName() + " o WHERE o.primaryKey.studentId = '" + student.getId()+ "'" );
        int countVotes = getCountOfStudIdIsFound.getResultList().size();

        if(noOfOptCourses == countVotes){

            return "Complete preference list";
        }

        return "Incomplete preference list";
    }

    @Override
    public String getForEdit(String primaryKey) {

        StudentEntity editRecord = null;

        if(primaryKey != null && primaryKey.trim().length() > 0) {
            try {


                editRecord = (StudentEntity) getEntityManager().createNamedQuery("findByName")
                        .setParameter("name", primaryKey)
                        .getSingleResult();


                if(editRecord != null) {

                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(Constants.Student.SessionKeys.EDIT_RECORD_KEY, editRecord);
                    updateKey = primaryKey;
                }
            } catch (Exception sqlException) {
                sqlException.printStackTrace();
                //TODO showError(sqlException);
                //TODO error page??? or a message in viewPage
                return "editStudent";
            }
        }else{

            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(Constants.Student.SessionKeys.EDIT_RECORD_KEY);
            updateKey = null;
        }
        return "editStudent";
    }

    @Override
    public String update(StudentEntity updateRecord) {

        if(updateKey == null || updateKey.trim().length() < 0){

            return insert(updateRecord);
        }
        try {

            if(!getEntityManager().getTransaction().isActive()){

                getEntityManager().getTransaction().begin();
            }

            StudentEntity oldRecord = (StudentEntity) getEntityManager().createNamedQuery("findByName")
                    .setParameter("name", updateKey)
                    .getSingleResult();

            oldRecord.setName(updateRecord.getName());
            oldRecord.setEmail(updateRecord.getEmail());
            oldRecord.setYear(updateRecord.getYear());

            getEntityManager().getTransaction().commit();

        } catch (Exception sqlException) {

            //TODO showError(sqlException);
            return "error";
        }

        return "success";
    }
}
