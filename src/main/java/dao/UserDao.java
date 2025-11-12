package dao;

public interface UserDao {
    void createUsersTable();

    void dropUsersTable();

    void saveUser(String name, String lastName, byte age);


    void removeUserById(long id);

    java.util.List<model.User> getAllUsers();

    void cleanUsersTable();
}
