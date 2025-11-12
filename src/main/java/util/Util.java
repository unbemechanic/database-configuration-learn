package util;

import model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.io.InputStream;
import java.util.Properties;

public class Util {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                InputStream config = Util.class
                        .getClassLoader()
                        .getResourceAsStream("config.properties");
                if (config == null) {
                    throw new RuntimeException("config.properties not found");
                }
                Properties properties = new Properties();
                properties.load(config);
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put(Environment.DRIVER, properties.getProperty("db.driver"));
                settings.put(Environment.URL, properties.getProperty("db.url"));
                settings.put(Environment.USER, properties.getProperty("db.user"));
                settings.put(Environment.PASS, properties.getProperty("db.password"));
                settings.put(Environment.DIALECT, properties.getProperty("db.dialect"));
                settings.put(Environment.SHOW_SQL, properties.getProperty("db.show_sql"));
                settings.put(Environment.HBM2DDL_AUTO, properties.getProperty("db.hbm2ddl"));

                // Applies the settings to the hibernate configuration
                configuration.setProperties(settings);
                // Registers the annotated User class so hibernate creates the corresponding table and maps fields to columns
                configuration.addAnnotatedClass(User.class);

                StandardServiceRegistryBuilder registeryBuilder = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(registeryBuilder.build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
