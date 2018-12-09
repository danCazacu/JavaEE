package dao;

import entity.CourseEntity;
import entity.LecturerEntity;
import entity.OptionalCourseEntity;
import org.eclipse.persistence.annotations.CascadeOnDelete;
import util.Constants;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
import java.util.List;

@NamedQuery(name = "findByCode",
        query = "SELECT  c FROM CourseEntity c WHERE c.code = :code")
@ManagedBean(name = "courseOperations")
@RequestScoped
public class CourseOperations extends DatabaseOperations<CourseEntity> {

    private static String updateKey;

    @Override
    public List<CourseEntity> getAll() {

        List<CourseEntity> compulsoryCourses = getEntityManager().createQuery("SELECT  t from " + CourseEntity.class.getSimpleName() + " t WHERE NOT EXISTS (SELECT o from " + OptionalCourseEntity.class.getSimpleName() + " o WHERE t.id=o.course.id)").getResultList();
        return compulsoryCourses;
    }

    public List<CourseEntity> getAllWithOptional() {

        return (List<CourseEntity>) getEntityManager().createQuery("Select t from " +  CourseEntity.class.getSimpleName() + " t").getResultList();

    }

    public static CourseEntity findByName(String name) {

        Query query1 = getEntityManager().createQuery("SELECT  t from " + CourseEntity.class.getSimpleName() + " t WHERE t.name = '" + name + "'");
        CourseEntity result = (CourseEntity) query1.getSingleResult();

        return result;
    }

    @Override
    public String getForEdit(String primaryKey) {

        CourseEntity editRecord = null;

        if(primaryKey != null && primaryKey.trim().length() > 0) {
            try {


                editRecord = (CourseEntity) getEntityManager().createNamedQuery("findCourse")
                        .setParameter("code", primaryKey)
                        .getSingleResult();


                if(editRecord != null) {

                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(Constants.Course.SessionKeys.EDIT_RECORD_KEY, editRecord);
                    updateKey = primaryKey;
                }
            } catch (Exception sqlException) {
                sqlException.printStackTrace();
                //TODO showError(sqlException);
                //TODO error page??? or a message in viewPage
                return "editCompulsoryCourse";
            }
        }else{

            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(Constants.Course.SessionKeys.EDIT_RECORD_KEY);
            updateKey = null;
        }
        return "editCompulsoryCourse";
    }

    @Override
    public String update(CourseEntity updateRecord) {

        if(updateKey == null || updateKey.trim().length() < 0){

            return insert(updateRecord);
        }
        try {

            if(!getEntityManager().getTransaction().isActive()){

                getEntityManager().getTransaction().begin();
            }

            CourseEntity oldRecord = (CourseEntity) getEntityManager().createNamedQuery("findCourse")
                    .setParameter("code", updateKey)
                    .getSingleResult();

            oldRecord.setName(updateRecord.getName());
            oldRecord.setShortName(updateRecord.getShortName());
            oldRecord.setCode(updateRecord.getCode());
            oldRecord.setUrl(updateRecord.getUrl());
            oldRecord.setCredits(updateRecord.getCredits());
            oldRecord.setSemester(updateRecord.getSemester());
            oldRecord.setYear(updateRecord.getYear());
            oldRecord.setLecturers(updateRecord.getLecturers());

            getEntityManager().flush();
            getEntityManager().getTransaction().commit();

        } catch (Exception sqlException) {

            //TODO showError(sqlException);
            return "error";
        }

        return "success";
    }

}
