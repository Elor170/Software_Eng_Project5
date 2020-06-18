package org.example;

import org.example.Software_Eng_Project5.entities.Student;
import org.example.Software_Eng_Project5.entities.Teacher;
import org.example.Software_Eng_Project5.entities.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Session session;

    private static SessionFactory getSessionFactory() throws HibernateException {
        Configuration configuration = new Configuration();

        // Add ALL of your entities here. You can also try adding a whole package.
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Teacher.class);
        configuration.addAnnotatedClass(Student.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"));
        stage.setScene(scene);

        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void addUser2DB(String userName, String password, String userType){
        try {
            SessionFactory sessionFactory = getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();

            User user = new User(userName, password, userType);
            session.save(user);

            if (userType.equals("Teacher")){
                Teacher teacher = new Teacher(userName);
                session.save(teacher);
            }

            if (userType.equals("Student")){
                Student student = new Student(userName);
                session.save(student);
            }

            session.flush();
            session.getTransaction().commit();

            System.out.println("Saved");

        } catch (Exception exception) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            System.err.println("An error occurred, changes have been rolled back.");
            exception.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
                session.getSessionFactory().close();
            }
        }

    }

    public static void main(String[] args) {
        launch();
    }

}
