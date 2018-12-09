package entity;


import util.JPAUtil;

import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.concurrent.TimeUnit;

@ManagedBean(name = "test")
public class Test {

  //  public static void main(String[] args) {

    static int counterOld = 30;



    public void doEntityInterogation(){


        EntityManager em = JPAUtil.getEntityManager();

        try {

            //PrintWriter writer = new PrintWriter("fileBeforeCache.txt", "UTF-8");

            String path = System.getProperty("user.home");
            File textFile = new File(path, "beforeCaching.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(textFile));

            long startTime = System.nanoTime();
            add1000Courses(em);
            long seconds = System.nanoTime() - startTime;

            out.write("add100courses without caching: " + seconds + " nanoseconds");
            out.newLine();

            startTime = System.nanoTime();
            update1000Courses(em);
            seconds = System.nanoTime() - startTime;

            out.write("update100Courses without caching: " + seconds + " nanoseconds");
            out.newLine();

            startTime = System.nanoTime();
            delete1000Courses(em);
            seconds =System.nanoTime() - startTime;

            out.write("delete100Courses without caching: " + seconds + " nanoseconds");
            out.newLine();


            out.close();

        } catch (Exception e) {

            e.printStackTrace();
        } finally {

            em.close();
        }
    }


    public void doInsertInStudentPref(EntityManager em){

        StudentEntity student1 = em.find(StudentEntity.class, 76 );

        OptionalCourseEntity optCourse1 = em.find(OptionalCourseEntity.class, 126 );
        OptionalCourseEntity  optCourse2 = em.find(OptionalCourseEntity.class,  127);
        OptionalCourseEntity  optCourse3 = em.find(OptionalCourseEntity.class, 128);
        OptionalCourseEntity  optCourse4 = em.find(OptionalCourseEntity.class, 129);
        OptionalCourseEntity  optCourse5 = em.find(OptionalCourseEntity.class, 130 );
        //OptionalCourseEntity optCourse6 = em.find(OptionalCourseEntity.class, 126 );


        StudentPreferencesEntity pref1 = new StudentPreferencesEntity();
        StudentPreferencesCompositeKey pref_pk1 = new StudentPreferencesCompositeKey();

        pref_pk1.setStudentId(student1.getId());
        pref_pk1.setOptionalCourseId(optCourse1.getCourse().getId());
        pref_pk1.setPosition(1);

        pref1.setPrimaryKey(pref_pk1);

        em.persist(pref1);

        /*StudentPreferencesEntity pref2 = new StudentPreferencesEntity();
        StudentPreferencesCompositeKey pref_pk2 = new StudentPreferencesCompositeKey();
        pref_pk2.setStudentId(student1.getId());
        pref_pk2.setOptionalCourseId(optCourse2.getCourse().getId());
        pref_pk2.setPosition(1);

        pref2.setPrimaryKey(pref_pk2);

        em.persist(pref2);*/

        StudentPreferencesEntity pref3 = new StudentPreferencesEntity();
        StudentPreferencesCompositeKey pref_pk3 = new StudentPreferencesCompositeKey();
        pref_pk3.setStudentId(student1.getId());
        pref_pk3.setOptionalCourseId(optCourse3.getCourse().getId());
        pref_pk3.setPosition(2);
        pref3.setPrimaryKey(pref_pk3);

        em.persist(pref3);


        StudentPreferencesEntity pref4 = new StudentPreferencesEntity();
        StudentPreferencesCompositeKey pref_pk4 = new StudentPreferencesCompositeKey();

        pref_pk4.setStudentId(student1.getId());
        pref_pk4.setOptionalCourseId(optCourse4.getCourse().getId());
        pref_pk4.setPosition(3);

        pref4.setPrimaryKey(pref_pk4);

        em.persist(pref4);

        StudentPreferencesEntity pref5 = new StudentPreferencesEntity();
        StudentPreferencesCompositeKey pref_pk5 = new StudentPreferencesCompositeKey();

        pref_pk5.setStudentId(student1.getId());
        pref_pk5.setOptionalCourseId(optCourse5.getCourse().getId());
        pref_pk5.setPosition(1);

        pref5.setPrimaryKey(pref_pk5);

        em.persist(pref5);

        /*StudentPreferencesEntity pref6 = new StudentPreferencesEntity();
        StudentPreferencesCompositeKey pref_pk6 = new StudentPreferencesCompositeKey();

        pref_pk6.setStudentId(student1.getId());
        pref_pk6.setOptionalCourseId(optCourse6.getCourse().getId());
        pref_pk6.setPosition(1);

        pref6.setPrimaryKey(pref_pk6);

        em.persist(pref6);*/

    }

    public void addLecturer (EntityManager em){



        LecturerEntity lecturer = new LecturerEntity();

        lecturer.setName("Dan Caz" + counterOld++);
        lecturer.setEmail("danc@test.com");
        lecturer.setUrl("http://dan-cazacu.com");

        em.persist(lecturer);

    }

    public void addStudent (EntityManager em){


        StudentEntity student = new StudentEntity();

        student.setName("Dan Caz" + counterOld++);
        student.setEmail("danc@test.com");
        student.setYear(3);

        em.persist(student);

    }


    public List<LecturerEntity> getAllLecturers (EntityManager em){

        List<LecturerEntity> listLecturers = em.createQuery(
                "SELECT p FROM LecturerEntity p", LecturerEntity.class).getResultList();

        return listLecturers;
    }

    public void showAllLecturers (List<LecturerEntity> listLecturers){


        if (listLecturers == null) {
            System.out.println("No lecturers found . ");
        } else {
            for (LecturerEntity lecturer : listLecturers) {
                System.out.print("Lecturer name= " + lecturer.getName()
                        + ", email" + lecturer.getEmail()
                        + ", url =" + lecturer.getUrl());
            }
        }
    }

    public void addOptionalPackage(EntityManager em){


        OptionalPackageEntity optionalPackage = new OptionalPackageEntity();
        optionalPackage.setCode("CO" + counterOld++);
        optionalPackage.setSemester(3);
        optionalPackage.setYear(2);

        em.persist(optionalPackage);
    }

    public void delete1000Courses(EntityManager em){


        Metamodel m = JPAUtil.getEntityManager().getMetamodel();
        EntityType<CourseEntity> OptionalCourse_ = m.entity(CourseEntity.class);

        CriteriaBuilder builder = JPAUtil.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<CourseEntity> query = builder.createQuery(CourseEntity.class);

        List<CourseEntity> lstQueryResult = em.createQuery(query).setMaxResults(10000).getResultList();

        for(int i = 0 ; i < lstQueryResult.size(); i++) {

            CourseEntity oldRecord = lstQueryResult.get(i);
            try {
                if (!em.getTransaction().isActive()) {

                    em.getTransaction().begin();
                }

                em.remove(oldRecord);

                em.flush();
                em.getTransaction().commit();

            } catch (Exception e) {

                e.printStackTrace();
            }
        }

    }

    public void update1000Courses(EntityManager em){

        Metamodel m = JPAUtil.getEntityManager().getMetamodel();
        EntityType<CourseEntity> Course_ = m.entity(CourseEntity.class);

        CriteriaBuilder builder = JPAUtil.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<CourseEntity> query = builder.createQuery(CourseEntity.class);

        List<CourseEntity> lstQueryResult = em.createQuery(query).setMaxResults(10000).getResultList();

        int counter = 0;
        for(int i = 0 ; i < lstQueryResult.size(); i++) {

            CourseEntity oldRecord = lstQueryResult.get(i);

            try {
                if (!em.getTransaction().isActive()) {

                    em.getTransaction().begin();
                }


                oldRecord.setName("Test" + counter);
                oldRecord.setShortName("Test" + counter);
                oldRecord.setCode("COTest" + counter);
                oldRecord.setUrl(oldRecord.getUrl() + "m");
                oldRecord.setCredits(oldRecord.getCredits() + 1);

                em.flush();
                em.getTransaction().commit();

                counter ++;
            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }

    public void add1000Courses(EntityManager em){

        int counter = 1;
        for(int i = 0; i < 10000; i++){

            addCourse(em,counter);
            counter++;
        }

    }

    public void addCourse(EntityManager em, int counter){

        em.getTransaction().begin();

        CourseEntity courseEntity = new CourseEntity();

        courseEntity.setCode("CC" + counter);
        courseEntity.setName("Java Technologies " + counter);
        courseEntity.setShortName("Java " + counter);
        courseEntity.setSemester(3);
        courseEntity.setYear(2);
        courseEntity.setCredits(7);
        courseEntity.setUrl("https://profs.info.uaic.ro/~acf/tj/");

        em.persist(courseEntity);
        em.getTransaction().commit();
    }

    public void manyToManyLecturersCourses(EntityManager em){



        LecturerEntity lecturer1 = new LecturerEntity();
        lecturer1.setName("Dan Cazacu " + counterOld);
        lecturer1.setEmail("danc@test.com");
        lecturer1.setUrl("http://dan-cazacu.com");

        LecturerEntity lecturer2 = new LecturerEntity();
        lecturer2.setName("Andreea Prodan " + counterOld);
        lecturer2.setEmail("andreea@test.com");
        lecturer2.setUrl("http://andreea-prodan.com");

        CourseEntity course1 = new CourseEntity();
        course1.setCode("CC" + counterOld);
        course1.setName("Java Technologies " + counterOld);
        course1.setShortName("Java " + counterOld);
        course1.setSemester(3);
        course1.setYear(2);
        course1.setCredits(7);
        course1.setUrl("https://profs.info.uaic.ro/~acf/tj/");

        counterOld++;

        CourseEntity course2 = new CourseEntity();
        course2.setCode("CC" + counterOld);
        course2.setName("Java Technologies " + counterOld);
        course2.setShortName("Java " + counterOld);
        course2.setSemester(3);
        course2.setYear(2);
        course2.setCredits(7);
        course2.setUrl("https://profs.info.uaic.ro/~acf/tj/");

        lecturer1.addCourse(course1);
        lecturer2.addCourse(course2);

        lecturer2.addCourse(course1);

        em.persist(lecturer1);
        em.persist(lecturer2);
    }

    public void add3OptionalCourses(EntityManager em){




        OptionalPackageEntity optionalPackage = new OptionalPackageEntity();
        optionalPackage.setYear(1);
        optionalPackage.setSemester(2);
        optionalPackage.setCode("CO" + counterOld);


        counterOld++;
        OptionalPackageEntity optionalPackage2 = new OptionalPackageEntity();
        optionalPackage2.setYear(1);
        optionalPackage2.setSemester(2);
        optionalPackage2.setCode("CO" + counterOld);

        counterOld++;
        CourseEntity course1 = new CourseEntity();
        course1.setCode("CO" + counterOld);
        course1.setName("Java Technologies " + counterOld);
        course1.setShortName("Java " + counterOld);
        course1.setSemester(3);
        course1.setYear(2);
        course1.setCredits(7);
        course1.setUrl("https://profs.info.uaic.ro/~acf/tj/");

        counterOld++;
        CourseEntity course2 = new CourseEntity();
        course2.setCode("CO" + counterOld);
        course2.setName("Java Technologies " + counterOld);
        course2.setShortName("Java " + counterOld);
        course2.setSemester(3);
        course2.setYear(2);
        course2.setCredits(7);
        course2.setUrl("https://profs.info.uaic.ro/~acf/tj/");

        counterOld++;
        CourseEntity course3 = new CourseEntity();
        course3.setCode("CO" + counterOld);
        course3.setName("Java Technologies " + counterOld);
        course3.setShortName("Java " + counterOld);
        course3.setSemester(3);
        course3.setYear(2);
        course3.setCredits(7);
        course3.setUrl("https://profs.info.uaic.ro/~acf/tj/");

        OptionalCourseEntity optionalCourse = new OptionalCourseEntity();
        optionalCourse.setCourse(course1);

        OptionalCourseEntity optionalCourse2 = new OptionalCourseEntity();
        optionalCourse2.setCourse(course2);

        OptionalCourseEntity optionalCourse3 = new OptionalCourseEntity();
        optionalCourse3.setCourse(course3);

        optionalPackage.addCourse(optionalCourse);
        optionalPackage.addCourse(optionalCourse2);

        optionalPackage2.addCourse(optionalCourse3);

        em.persist(optionalPackage);
        em.persist(optionalPackage2);

    }

}
