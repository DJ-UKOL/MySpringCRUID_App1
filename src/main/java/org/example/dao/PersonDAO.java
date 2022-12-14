package org.example.dao;

import org.example.models.Person;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class PersonDAO {

/*    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }*/

    private final EntityManager entityManager;

    @Autowired
    public PersonDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)
    public List<Person> index() {
        //return jdbcTemplate.query("SELECT * FROM Person", new PersonMapper()); // Свой маппер
        // BeanPropertyRowMapper - конвертирует строки из Person.class
        //return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
        //Session session = sessionFactory.getCurrentSession();
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("select p from Person p", Person.class)
                .getResultList();
    }

    public Optional<Person> show(String email) {
        /*return jdbcTemplate.query("SELECT * FROM Person WHERE email=?",
                new Object[] {email},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();*/
        return Optional.empty();
    }

    @Transactional(readOnly = true)
    public Person show(int id) {
        /*return jdbcTemplate.query("SELECT * FROM Person WHERE id=?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);*/
        //Session session = sessionFactory.getCurrentSession();
        Session session = entityManager.unwrap(Session.class);
        return session.get(Person.class, id);
    }

    @Transactional
    public void save(Person person) {
        /*jdbcTemplate.update("INSERT INTO Person(name, age, email, address) VALUES(?, ?, ?, ?)",
                person.getName(),
                person.getAge(),
                person.getEmail(),
                person.getAddress());*/
        //Session session = sessionFactory.getCurrentSession();
        Session session = entityManager.unwrap(Session.class);
        session.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        /*jdbcTemplate.update("UPDATE Person SET name=?, age=?, email=?, address=? WHERE id=?", updatedPerson.getName(),
                updatedPerson.getAge(), updatedPerson.getEmail(), updatedPerson.getAddress(), id);*/
        //Session session = sessionFactory.getCurrentSession();
        Session session = entityManager.unwrap(Session.class);
        Person personToBeUpdate = session.get(Person.class, id);

        personToBeUpdate.setName(updatedPerson.getName());
        personToBeUpdate.setAge(updatedPerson.getAge());
        personToBeUpdate.setEmail(updatedPerson.getEmail());
    }

    @Transactional
    public void delete(int id) {
        //jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
        //Session session = sessionFactory.getCurrentSession();
        Session session = entityManager.unwrap(Session.class);
        session.delete(session.get(Person.class, id));
    }

    @Transactional(readOnly = true)
    public void testNPlus1() {
        Session session = entityManager.unwrap(Session.class);

/*        // 1 запрос к БД
        List<Person> people = session.createQuery("select p from Person p", Person.class)
                .getResultList();

        // N запросов к БД
        for (Person person : people) {
            System.out.println("Person " + person.getName() + " has " + person.getItems());
        }*/

        Set<Person> people = new HashSet<Person>(session.createQuery("select p from Person p LEFT JOIN FETCH p.items")
                .getResultList());
        for (Person person:people) {
            System.out.println("Person " + person.getName() + " has " + person.getItems());
        }
    }

/*    //////////////////////////////////////////////////////////////////////////////////
    ////////////// Тестируем производительность пакетной вставки /////////////////////
    //////////////////////////////////////////////////////////////////////////////////

    public void testMultipleUpdate() {
        List<Person> people = create1000People();

        long before = System.currentTimeMillis();

        for(Person person : people) {
            jdbcTemplate.update("INSERT INTO Person VALUES(?, ?, ?, ?)",
                    person.getId(),
                    person.getName(),
                    person.getAge(),
                    person.getEmail());
        }

        long after = System.currentTimeMillis();

        System.out.println("Time:" + (after-before));
    }

    public void testBatchUpdate() {
        List<Person> people = create1000People();

        long before = System.currentTimeMillis();

        jdbcTemplate.batchUpdate("INSERT INTO Person VALUES(?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, people.get(i).getId());
                        ps.setString(2, people.get(i).getName());
                        ps.setInt(3, people.get(i).getAge());
                        ps.setString(4, people.get(i).getEmail());
                    }

                    @Override
                    public int getBatchSize() {
                        return people.size();
                    }
                });

        long after = System.currentTimeMillis();

        System.out.println("Time:" + (after-before));

    }

    private List<Person> create1000People() {
        List<Person> people = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            people.add(new Person(i, "Name" + i, 30, "name" + i + "@mail.ru", "some address"));
        }
        return people;
    }*/
}