package pl.syberry.themoodbackend.model;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Functions {

    public SessionFactory createSession() {
        SessionFactory session = new Configuration().configure()
                .addAnnotatedClass(Book.class)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Admin.class)
                .addAnnotatedClass(Restaurant.class)
                .buildSessionFactory();
        return session;
    }
}
