package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import db.configuration.HibernateCfg;
import db.entities.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
//hbnt
public class Helpers {

    private static final Logger logger = LoggerFactory.getLogger(Helpers.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");

    public static Date calendar(int y, int m, int d){
        Date bDateFirst = new GregorianCalendar(y, m, d).getTime();
        return bDateFirst;
    }


    public static void saveUsers() {
        User user1 = new User();         // Создаём <i>transient-object</i>
        user1.setName("Havoc");
        user1.setSurname("Petrov");
        user1.setAge(25);
        user1.setBirthdate(dateFormat.format(calendar(1995,11,21)));

        User user2 = new User();
        user2.setName("Charly");
        user2.setSurname("Kilo");
        user2.setAge(12);
        user2.setBirthdate(dateFormat.format(calendar(1980,02,11)));

        Session session = HibernateCfg.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user1);
        session.save(user2);
        transaction.commit();

        logger.info("user1 ===== {}", user1.toString());
        logger.info("user2 ===== {}", user2.toString());

    }

    public static List<User> selectUsers(){
        Session session = HibernateCfg.getSessionFactory().openSession();
        List<User> userList = (session.createQuery("from User", User.class).list());
        logger.info("Select user1 ===== {}", userList.get(0).toString());
        logger.info("Select user2 ===== {}", userList.get(1).toString());
        session.close();
        logger.info("Session is close");
        return userList;
    }

    public static String jsonData() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter(); //перевод объекта в строку
        String json = ow.writeValueAsString(selectUsers());
        return json;
    }

    public static void saveSelectedUsersAsJsonFile() throws IOException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter(); //перевод объекта в строку
        String json = ow.writeValueAsString(selectUsers());

        try{
            File answer = new File("answer.json");
            if(answer.createNewFile()){
                logger.info("Create Json {}",answer.getName());
            } else {
                logger.info("So close");
            }
        } catch (IOException e) {
            logger.info("Error");
            e.printStackTrace();
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter("answer.json",false));
        writer.write(json);
        writer.close();
    }
    public static void deleteTable() throws ClassNotFoundException, SQLException {
        Connection connection = null;
        java.sql.Statement statement = null;
        String URL = "jdbc:mysql://192.168.14.73:3306/challange?serverTimezone=Europe/Moscow";
        String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        String USERNAME = "root";
        String PASSWORD = "root";

        try {
            logger.info("Registering JDBC driver...");
            Class.forName(JDBC_DRIVER);

            logger.info("Creating connection to database...");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            logger.info("Creating table in selected database...");
            statement = connection.createStatement();

            String SQL = "DROP TABLE akravchenko" ;
            statement.executeUpdate(SQL);
            logger.info("Table successfully DELETED......");
        }
        finally {
            if(statement!=null){
                statement.close();
            }
            if(connection!=null){
                connection.close();
            }
        }
    }

}
