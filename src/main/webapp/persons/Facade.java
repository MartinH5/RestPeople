package persons;

import entities.Person;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 *
 * @author Martin
 */
public class Facade implements IFacade {

    private EntityManagerFactory emf;

    @Override
    public void addEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Person addPerson(Person p) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
        return p;
    }

    @Override
    public Person deletePerson(int id) throws NoResultException {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> result = em.createNamedQuery("Person.findByPersonid", Person.class);
        Person toBeDeleted = result.setParameter("personid", id).getSingleResult();
        em.getTransaction().begin();
        em.remove(toBeDeleted);
        em.getTransaction().commit();
        return toBeDeleted;
    }

    @Override
    public Person getPerson(int id) throws NoResultException {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> result = em.createNamedQuery("Person.findByPersonid", Person.class);
        Person personFound = result.setParameter("personid", id).getSingleResult();
        return personFound;
    }

    @Override
    public List getPeople() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> result = em.createNamedQuery("Person.findAll", Person.class);
        List<Person> people = result.getResultList();
        return people;
    }

    @Override
    public Person editPerson(Person p) throws NoResultException {
        System.out.println(p.getPersonid() + p.getFname() + p.getLname() + p.getPnumber());
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> result = em.createNamedQuery("Person.findByPersonid", Person.class);
        Person entityToEdit = result.setParameter("personid", p.getPersonid()).getSingleResult();
        em.getTransaction().begin();
        entityToEdit.setFname(p.getFname());
        entityToEdit.setLname(p.getLname());
        entityToEdit.setPnumber(p.getPnumber());
        em.getTransaction().commit();
        return entityToEdit;
    }



}