package util;

import model.User;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.jpa.HibernatePersistenceProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Util {
    private static EntityManagerFactory em;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (em == null) {
            try {
                InputStream config = Util.class
                        .getClassLoader()
                        .getResourceAsStream("config.properties");
                if (config == null) {
                    throw new RuntimeException("config.properties not found");
                }
                Properties properties = new Properties();
                properties.load(config);

                properties.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
                properties.setProperty("hibernate.connection.url", "jdbc:mysql://127.0.0.1:3306/test_schema?useSSL=false&serverTimezone=UTC");
                properties.setProperty("hibernate.connection.username", "root");
                properties.setProperty("hibernate.connection.password", "slaveofgod99"); // change password
                properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
                properties.setProperty("hibernate.hbm2ddl.auto", "update");
                properties.setProperty("hibernate.show_sql", "true");
                /*Map<String, Object> settings = new HashMap<>();
                settings.put("javax.persistence.jdbc.driver", properties.getProperty("db.driver"));
                settings.put("javax.persistence.jdbc.url", properties.getProperty("db.url"));
                settings.put("javax.persistence.jdbc.user", properties.getProperty("db.user"));
                settings.put("javax.persistence.jdbc.password", properties.getProperty("db.password"));

                // Hibernate config
                settings.put("hibernate.dialect", properties.getProperty("db.dialect"));
                settings.put("hibernate.show_sql", properties.getProperty("db.show_sql"));
                settings.put("hibernate.hbm2ddl.auto", properties.getProperty("db.hbm2ddl"));*/

                Configuration configuration = new Configuration();
                configuration.setProperties(properties);
                configuration.addAnnotatedClass(User.class);

                SessionFactory sessionFactory = configuration.buildSessionFactory();
                em = sessionFactory.unwrap(EntityManagerFactory.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return em;
    }
}
