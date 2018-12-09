package dao;

import entity.CourseEntity;
import entity.OptionalCourseEntity;
import entity.StudentPreferencesEntity;
import util.Constants;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.Query;

@ManagedBean(name = "optionalCourseOperations")
@RequestScoped
public class OptionalCourseOperations  extends DatabaseOperations<OptionalCourseEntity> {

    private static String updateKey;

    @Override
    public String insert(OptionalCourseEntity optionalCourseEntity) {

        try {
            if (!getEntityManager().getTransaction().isActive()) {
                getEntityManager().getTransaction().begin();
            }

            CourseEntity course = new CourseEntity();
            course.setName(optionalCourseEntity.getCourse().getName());
            course.setShortName(optionalCourseEntity.getCourse().getShortName());
            course.setCode(optionalCourseEntity.getCourse().getCode());
            course.setUrl(optionalCourseEntity.getCourse().getUrl());
            course.setYear(optionalCourseEntity.getCourse().getYear());
            course.setSemester(optionalCourseEntity.getCourse().getSemester());
            course.setCredits(optionalCourseEntity.getCourse().getCredits());
            course.setLecturers(optionalCourseEntity.getCourse().getLecturers());

            optionalCourseEntity.setCourse(course);

            //optionalCourseEntity.setOptionalPackage(findById(optionalCourseEntity.getOptionalPackage().getId()));

            getEntityManager().persist(optionalCourseEntity);

            getEntityManager().getTransaction().commit();

            return "success";
        }catch(Exception e){

            e.printStackTrace();
            return "error";
        }
    }

    public static double getPrefered(OptionalCourseEntity optionalCourse){

        Query getNoOfStudThatPreffered = getEntityManager().createQuery("SELECT s from " + StudentPreferencesEntity.class.getSimpleName()
                + " s WHERE s.primaryKey.optionalCourseId='" + optionalCourse.getCourse().getId() + "' AND s.primaryKey.position = '1'");

        int countStudentsTharPreferred = getNoOfStudThatPreffered.getResultList().size();


        Query getNoOfStudentsThatVoted = getEntityManager().createQuery("SELECT  o from " + StudentPreferencesEntity.class.getSimpleName() + " o WHERE o.primaryKey.optionalCourseId = '" + optionalCourse.getCourse().getId()+ "'" );
        int countStudentsInSameYear = getNoOfStudentsThatVoted.getResultList().size();


        if(countStudentsTharPreferred == 0){

            return 0;
        }
         return (countStudentsTharPreferred * 1.0 /countStudentsInSameYear) * 100;

    }

    @Override
    public String getForEdit(String primaryKey) {

        OptionalCourseEntity editRecord = null;

        if(primaryKey != null && primaryKey.trim().length() > 0) {
            try {


                editRecord = (OptionalCourseEntity) getEntityManager().createNamedQuery("findOptionalCourse")
                        .setParameter("code", primaryKey)
                        .getSingleResult();


                if(editRecord != null) {

                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(Constants.OptionalCourse.SessionKeys.EDIT_RECORD_KEY, editRecord);
                    updateKey = primaryKey;
                }
            } catch (Exception sqlException) {
                sqlException.printStackTrace();
                //TODO showError(sqlException);
                //TODO error page??? or a message in viewPage
                return "editOptionalCourse";
            }
        }else{

            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(Constants.OptionalCourse.SessionKeys.EDIT_RECORD_KEY);
            updateKey = null;
        }
        return "editOptionalCourse";
    }

    @Override
    public String update(OptionalCourseEntity updateRecord) {

        if(updateKey == null || updateKey.trim().length() < 0){

            return insert(updateRecord);
        }
        try {

            if(!getEntityManager().getTransaction().isActive()){

                getEntityManager().getTransaction().begin();
            }

            OptionalCourseEntity oldRecord = (OptionalCourseEntity) getEntityManager().createNamedQuery("findOptionalCourse")
                    .setParameter("code", updateKey)
                    .getSingleResult();

           /* CourseEntity courseOldRecord = (CourseEntity) getEntityManager().createNamedQuery("findCourse")
                        .setParameter("code", updateKey)
                        .getSingleResult();
*/
            oldRecord.getCourse().setName(updateRecord.getCourse().getName());
            oldRecord.getCourse().setShortName(updateRecord.getCourse().getShortName());
            oldRecord.getCourse().setCode(updateRecord.getCourse().getCode());
            oldRecord.getCourse().setUrl(updateRecord.getCourse().getUrl());
            oldRecord.getCourse().setCredits(updateRecord.getCourse().getCredits());
            oldRecord.getCourse().setSemester(updateRecord.getCourse().getSemester());
            oldRecord.getCourse().setYear(updateRecord.getCourse().getYear());
            oldRecord.getCourse().setLecturers(updateRecord.getCourse().getLecturers());
            oldRecord.setOptionalPackage(updateRecord.getOptionalPackage());

            getEntityManager().flush();
            getEntityManager().getTransaction().commit();

        } catch (Exception sqlException) {

            //TODO showError(sqlException);
            return "error";
        }

        return "success";
    }

}
