package dao;

import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.Util;

import java.util.List;
import java.util.UUID;

public class DaoHibernate implements UserDao {

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try(Session session = Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.createSQLQuery(
                    "CREATE TABLE IF NOT EXISTS user (" +
                            "id CHAR(36) NOT NULL, " +
                            "name VARCHAR(50), " +
                            "lastName VARCHAR(50), " +
                            "age TINYINT, " +
                            "PRIMARY KEY (id))"
            ).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {

    }


    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        }catch (Exception e){
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }



    @Override
    public void removeUserById(long id) {

    }

    @Override
    public List<User> getAllUsers() {
        try(Session session = Util.getSessionFactory().openSession()){
            return session.createQuery("FROM User", User.class).list();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void cleanUsersTable() {

    }
}
