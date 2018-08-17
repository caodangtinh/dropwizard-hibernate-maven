package com.tinhcao.dropwizard.db.dao;

import com.tinhcao.dropwizard.db.entity.Person;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class PersonDAO extends AbstractDAO<Person> {
    /**
     * Creates a new DAO with a given session provider.
     *
     * @param sessionFactory a session provider
     */
    public PersonDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Person> findAll() {
        return list(namedQuery(Person.FIND_ALL));
    }

    public Person findById(long id) {
        return get(id);
    }

    public Person createPerson(Person person) {
        return persist(person);
    }

    public void delete(long id) {
        currentSession().delete(get(id));
    }

    public void update(Person person) {
        currentSession().saveOrUpdate(person);
    }
}
