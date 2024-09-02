package com.dsvdev.repository;

import com.dsvdev.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class UserHibernateRepository implements UserRepository {
    @Override
    public void createUsersTable() {
        Configuration configuration = new Configuration().addAnnotatedClass(User.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        String query = "CREATE TABLE IF NOT EXISTS users" +
                "(id bigint GENERATED ALWAYS AS IDENTITY NOT NULL," +
                " name varchar NOT NULL, " +
                " lastname varchar NOT NULL, " +
                " age smallint NOT NULL, " +
                " CONSTRAINT users_pk PRIMARY KEY (id))";

        session.createNativeQuery(query).executeUpdate();

        session.getTransaction().commit();
        sessionFactory.close();
    }

    @Override
    public void dropUsersTable() {
        Configuration configuration = new Configuration().addAnnotatedClass(User.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();

        session.getTransaction().commit();
        sessionFactory.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Configuration configuration = new Configuration().addAnnotatedClass(User.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.save(new User(name, lastName, age));

        session.getTransaction().commit();
        sessionFactory.close();
        System.out.println("User с именем — " + name + " добавлен в базу данных");
    }

    @Override
    public void removeUserById(long id) {
        Configuration configuration = new Configuration().addAnnotatedClass(User.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.delete(session.get(User.class, id));

        session.getTransaction().commit();
        sessionFactory.close();
    }

    @Override
    public List<User> getAllUsers() {
        Configuration configuration = new Configuration().addAnnotatedClass(User.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        List<User> users = session.createQuery("from User").list();

        session.getTransaction().commit();
        sessionFactory.close();
        for (User user : users) {
            System.out.println(user.toString());
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Configuration configuration = new Configuration().addAnnotatedClass(User.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.createNativeQuery("TRUNCATE users").executeUpdate();


        session.getTransaction().commit();
        sessionFactory.close();
    }
}
