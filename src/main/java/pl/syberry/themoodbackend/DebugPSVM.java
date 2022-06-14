package pl.syberry.themoodbackend;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import pl.syberry.themoodbackend.model.Book;
import pl.syberry.themoodbackend.model.User;
import pl.syberry.themoodbackend.model.Admin;

public class DebugPSVM {
    public static void main(String[] args) {
        try (
                SessionFactory factory = new Configuration().configure()
                        .addAnnotatedClass(Book.class)
                        .addAnnotatedClass(User.class)
                        .addAnnotatedClass(Admin.class)
                        .buildSessionFactory();
                Session session = factory.getCurrentSession()
        ) {
            session.beginTransaction();

            User student = session.get(User.class, 1);
            //student.addMood(new Mood("FUNNY", "GREEN", "VERY", new Date())); // WE CAN ADD MOOD ONLY HAVING A STUDENT OBJECT

            for (Book mood : student.getBooks()) { // OR GET MOOD ONLY FROM STUDENT OBJECT
                System.out.println(mood);
            }

            session.getTransaction().commit();
        }
    }
}
