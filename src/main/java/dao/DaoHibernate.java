package dao;

import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.Util;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

public class DaoHibernate implements UserDao {

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        EntityManager session = Util.getEntityManagerFactory().createEntityManager();
            session.getTransaction().begin();
            session.createNativeQuery(
                    "CREATE TABLE IF NOT EXISTS user (" +
                            "id CHAR(36) NOT NULL, " +
                            "name VARCHAR(50), " +
                            "lastName VARCHAR(50), " +
                            "age TINYINT, " +
                            "PRIMARY KEY (id))"
            ).executeUpdate();
            session.getTransaction().commit();
    }

    @Override
    public void dropUsersTable() {

    }


    @Override
    public void saveUser(String name, String lastName, byte age) {
        EntityManager session = Util.getEntityManagerFactory().createEntityManager();
        try {
            session.getTransaction().begin();
            session.persist(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }



    @Override
    public void removeUserById(long id) {

    }

    @Override
    public List<User> getAllUsers() {
        EntityManager session = Util.getEntityManagerFactory().createEntityManager();
            return session.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public void cleanUsersTable() {

    }
}
