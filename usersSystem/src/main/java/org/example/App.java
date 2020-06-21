package org.example;

import org.example.Software_Eng_Project5.entities.*;
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

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.util.List;

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
        configuration.addAnnotatedClass(Profession.class);
        configuration.addAnnotatedClass(Course.class);
        configuration.addAnnotatedClass(Question.class);
        configuration.addAnnotatedClass(Answer.class);
        configuration.addAnnotatedClass(Test.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("mainWindow"));
        stage.setScene(scene);
        stage.show();
        setProfessions();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void addUser2DB(String userName, String password, String userType, List<Profession> teacherProfessions){
        try {
            SessionFactory sessionFactory = getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();

            User user = new User(userName, password, userType);
            session.save(user);

            if (userType.equals("Teacher")){
                Teacher teacher = new Teacher(userName, teacherProfessions);
                session.save(teacher);
                for(Profession profession: teacherProfessions){
                    profession = session.get(Profession.class, profession.getCode());
                    profession.getTeacherList().add(teacher);
                    session.update(profession);
                }
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

    public static void setProfessions(){
        try {
            SessionFactory sessionFactory = getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();


            List<Profession> professionList = getAll(Profession.class);
            mainWindowController.setProfessionsList(professionList);

            session.flush();
            session.getTransaction().commit();

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

    public static <T> List<T> getAll(Class<T> object) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(object);
        Root<T> rootEntry = criteriaQuery.from(object);
        CriteriaQuery<T> allCriteriaQuery = criteriaQuery.select(rootEntry);
        TypedQuery<T> allQuery = session.createQuery(allCriteriaQuery);

        return allQuery.getResultList();
    }

    public static void main(String[] args) {
        launch();
    }

}
