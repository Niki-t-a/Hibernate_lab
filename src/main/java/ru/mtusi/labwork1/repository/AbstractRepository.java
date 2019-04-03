package ru.mtusi.labwork1.repository;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class AbstractRepository<T> {
    public EntityManager em =
            Persistence.createEntityManagerFactory("MYAPP").createEntityManager();

    private Class<T> persistentClass;

    public AbstractRepository(Class<T> persistentClass){
        this.persistentClass = persistentClass;
    }

    public T save(T entity){
        em.getTransaction().begin();
        T created = em.merge(entity);
        em.getTransaction().commit();
        return created;
    }

    public void delete(long id){
        em.getTransaction().begin();
        em.remove(get(id));
        em.getTransaction().commit();
    }

    public T get(long id){
        return em.find(persistentClass, id);
    }

    public List<T> getAll(){
        TypedQuery<T> namedQuery = em.createNamedQuery(persistentClass.getSimpleName() + ".getAll", persistentClass);
        return namedQuery.getResultList();
    }
}