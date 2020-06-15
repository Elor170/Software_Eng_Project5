package org.example.Software_Eng_Project5.server;

import org.example.Software_Eng_Project5.entities.Message;
import org.example.Software_Eng_Project5.entities.Teacher;
import org.example.Software_Eng_Project5.server.ocsf.AbstractServer;
import org.example.Software_Eng_Project5.server.ocsf.ConnectionToClient;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SimpleServer extends AbstractServer {

	private static Session session;

	public SimpleServer(int port) {
		super(port);
	}

	private static SessionFactory getSessionFactory() throws HibernateException {
		Configuration configuration = new Configuration();

		// Add ALL of your entities here. You can also try adding a whole package.
		configuration.addAnnotatedClass(Teacher.class);
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();

		return configuration.buildSessionFactory(serviceRegistry);
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		try {
			SessionFactory sessionFactory = getSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();

			Message message = (Message) msg;
			Message returnMessage = new Message();

			if (message.getCommand().startsWith("Bring")) {
				if(message.getCommand().endsWith("One"))
					returnMessage = getObject(message);
			}


				try {
					client.sendToClient(returnMessage);
					System.out.format("Sent message to client %s\n", Objects.requireNonNull(client.getInetAddress()).getHostAddress());
				} catch (IOException e) {
					e.printStackTrace();
				}


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


	public Message getObject(Message message){
		Message retMessage = new Message();
		String indexString = message.getIndexString();
		String objType = message.getType();

		if (objType.equals("User")) {
			retMessage.setCommand("User Event");

			List<Teacher> teacherList = getAll(Teacher.class);
			for (Teacher teacher : teacherList){
				if (teacher.getUserName().equals(indexString)){
					retMessage.setType("Teacher");
					retMessage.setSingleObject(teacher);
					return retMessage;
				}
			}
		}
		return retMessage;
	}

//	public Object getObjectList(Message message){
//
//	}

	public static <T> List<T> getAll(Class<T> object) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = builder.createQuery(object);
		Root<T> rootEntry = criteriaQuery.from(object);
		CriteriaQuery<T> allCriteriaQuery = criteriaQuery.select(rootEntry);
		TypedQuery<T> allQuery = session.createQuery(allCriteriaQuery);

		return allQuery.getResultList();
	}

}
