package dao;

import util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class DatabaseOperations<T extends Serializable> {

    private static EntityManager entityManager = null;

    private Class<T> persistentClass;

    public DatabaseOperations() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }


    public static EntityManager getEntityManager() {

        if(entityManager == null){

            entityManager = JPAUtil.getEntityManager();
        }
        return entityManager;
    }

    public abstract String getForEdit(String primaryKey);

    public abstract String update(T updateRecord);

    public  List<T> getAll(){

        return (List<T>) getEntityManager().createQuery("Select t from " +  persistentClass.getSimpleName() + " t").getResultList();

    }

    public void delete(Integer id){

        T objectToBeDeleted = getEntityManager().find(persistentClass, id);
        try {

            if (objectToBeDeleted != null) {

                 if(!getEntityManager().getTransaction().isActive()) {
                    getEntityManager().getTransaction().begin();
                }

                getEntityManager().remove(objectToBeDeleted);

                getEntityManager().getTransaction().commit();
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public T findById(Integer id){

        return entityManager.find(persistentClass, id);
    }

    public String insert(T t){

        try {
            if (!getEntityManager().getTransaction().isActive()) {
                getEntityManager().getTransaction().begin();
            }

            getEntityManager().persist(t);

            //getEntityManager().flush();
            getEntityManager().getTransaction().commit();

            return "success";
        }catch(Exception e){

            e.printStackTrace();
            return "error";
        }
    }

    public String cancel(){

        return "success";
    }
}
