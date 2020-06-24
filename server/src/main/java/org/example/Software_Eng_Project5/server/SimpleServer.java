package org.example.Software_Eng_Project5.server;

import org.example.Software_Eng_Project5.entities.*;
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
		configuration.addAnnotatedClass(User.class);
		configuration.addAnnotatedClass(Teacher.class);
		configuration.addAnnotatedClass(Student.class);
		configuration.addAnnotatedClass(Profession.class);
		configuration.addAnnotatedClass(Course.class);
		configuration.addAnnotatedClass(Question.class);
		configuration.addAnnotatedClass(Answer.class);
		configuration.addAnnotatedClass(Exam.class);

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
			String command = message.getCommand();
			Message returnMessage = null;

			switch (command){
				case "LogIn":
					returnMessage = logIn(message.getIndexString(), (String) message.getSingleObject());
					break;

				case "LogOut":
					logOut(message.getIndexString());
					break;

				case "Bring":
					if (message.isList()) {
						returnMessage = bringList(message);
					}
					break;
				case "Insert":
					returnMessage = insertObject(message);
					break;

				case "Update":
					returnMessage = updateObject(message);
					break;

			}
				try {
					if (returnMessage != null)
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

	private Message updateObject(Message message)
	{
		Message retMessage = new Message();
		Class<?> classType = message.getClassType();

		if(classType == Question.class){
			Object object = message.getSingleObject();
			session.update(object);
			List<Answer> answerList = ((Question)object).getAnswers();
			for (Answer answer: answerList)
				session.update(answer);

			retMessage.setType("Updated Question");
		}
		else if(classType == Exam.class)
		{
			insertObject(message);
			retMessage.setType("Updated Exam");
		}

		retMessage.setCommand("Teacher Event");

		return retMessage;

	}

	@SuppressWarnings("unchecked")
	private Message insertObject(Message message) {
		Message retMessage = new Message();
		Class<?> classType = message.getClassType();
		Object object = message.getSingleObject();

		// create Question
		if (classType.equals(Question.class)){
			List<String> textList = (List<String>) message.getObjList();
			List<Answer> answerList = new ArrayList<>();
			Answer answer;

			for (int i = 1; i <= 4; i++)
			{
				answer = new Answer(textList.get(i));
				answerList.add(answer);
				session.save(answer);
			}

			Profession profession = session.get(Profession.class, ((Profession) message.getSingleObject()).getCode());
			List<Course> courseList = (List<Course>) message.getObjList2();
			Teacher writer = session.get(Teacher.class, textList.get(5));

			Question question = new Question(textList.get(0), message.getIndexInt(),
					writer, calculateQuestionCode(profession), profession, courseList, answerList);

			session.save(question);

			System.out.println("Question #" + question.getCode() + " saved");
			retMessage.setItemsType("Question");
			retMessage.setType("Created Question");
		}
		// create Exam
		else if (classType.equals(Exam.class))
		{
			List<Question> questions = (List<Question>) message.getObjList();
			List<String> textList = (List<String>) message.getObjList2();
			Profession profession = session.get(Profession.class, ((Profession) message.getSingleObject()).getCode());
			Course course = (Course) message.getSingleObject2();
			Teacher writer = session.get(Teacher.class, textList.get(2));

			Exam exam = new Exam(calculateExamCode(profession, course), message.getTestTime(), message.isManual(), writer, profession, course);
			exam.setQuestionList(questions);
			exam.setTextForStudent(textList.get(0));
			exam.setTextForTeacher(textList.get(1));

			session.save(exam);

			System.out.println("Exam #" + exam.getCode() + " saved");
			retMessage.setItemsType("Exam");
			retMessage.setType("Created Exam");
		}

		retMessage.setCommand("Teacher Event");

		return retMessage;
	}

	private Message bringList(Message message) {
		Message retMessage = new Message();
		Class<?> classType = message.getClassType();
		String indexString = message.getIndexString();
		List<Question> questionList = null;

		Object object = session.get(classType, indexString);
		if (classType.equals(Course.class))
			questionList = new ArrayList<>(((Course)object).getQuestionList());
		else if(classType.equals(Profession.class))
			questionList = new ArrayList<>(((Profession)object).getQuestionList());
		else if(classType.equals(Teacher.class))
			questionList = new ArrayList<>(((Teacher) object).getQuestionList());

		if (message.getItemsType().equals("Question")) {
			retMessage.setObjList(questionList);
			retMessage.setCommand("Teacher Event");
			retMessage.setItemsType("Question");
			retMessage.setList(true);
			retMessage.setType("Received");
		}

		return retMessage;
	}

	private Message logIn(String userName, String password) {
		Message retMessage = new Message();
		retMessage.setCommand("User Event");
		User user = session.get(User.class, userName);

		if(user != null && user.getPassword().equals(password)){
				if(!user.isConnected()) {
					System.out.println("The " + user.getUserType() + ": " + user.getUserName() + " is connected");
					user.setConnected(true);
					session.update(user);
					retMessage.setType(user.getUserType());
					retMessage.setIndexString(user.getUserName());

					if (user.getUserType().equals("Teacher")){
						Teacher teacher = session.get(Teacher.class, userName);
						List<Profession> professionList = new ArrayList<>(teacher.getProfessionList());
						retMessage.setObjList(professionList);
					}
				}
				else{
					retMessage.setType("Already connected");
				}
		}
		else {
			retMessage.setType("No match found");
		}
		return retMessage;
	}

	private void logOut(String userName){
		User user = session.get(User.class, userName);
		user.setConnected(false);
		session.update(user);
		System.out.println("The " + user.getUserType() + ": " + user.getUserName() + " disconnected");
	}

	private String calculateQuestionCode(Profession profession){
		List<Question> questionList = profession.getQuestionList();
		int listSize = questionList.size();
		if( listSize == 0)
			return (profession.getCode() + "000");
		else {
			int lastCode = Integer.parseInt(questionList.get(listSize - 1).getCode());
			String newCode = Integer.toString(lastCode + 1);
			if (newCode.length() == 4)
				return ("0" + newCode);
			else
				return newCode;
		}
	}

	private String calculateExamCode(Profession profession, Course course){
		List<Exam> examList = profession.getExamList();
		int listSize = examList.size();
		if( listSize == 0)
			return (profession.getCode() + "" + course.getCode() + "000");
		else {
			int lastCode = Integer.parseInt(examList.get(listSize - 1).getCode());
			String newCode = Integer.toString(lastCode + 1);
			if (newCode.length() == 5)
				return ("0" + newCode);
			else
				return newCode;
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
}
