package daos;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public abstract class BaseDao<T> {
    @Inject
    EntityManager em;

    Class<T> type;

    public BaseDao() {
    }

    public BaseDao(Class<T> _class) {
        this.type = _class;
    }


    public T get(Long id) {
        return em.find(type, id);
    }

    public List<T> getAll() {
        return em.createQuery("from " + type.getSimpleName()).getResultList();
    }

    public void update(T obj) {
        em.merge(obj);
    }

    public void delete(Long id) {
        em.remove(get(id));
    }

    public void create(T obj) {
        em.persist(obj);
    }
}
