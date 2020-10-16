import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mysql.jdbc.Statement;
import db.configuration.HibernateCfg;
import db.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

public class Task1 {
    private static final Logger logger = LoggerFactory.getLogger(Task1.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");


    @Test
    public void testConnectBD() throws SQLException {
      Connection connection = null;
      Statement statement;
      ResultSet rs;
      SessionFactory sessionFactory;
      String URL = "jdbc:mysql://192.168.14.73:3306/challange?serverTimezone=Europe/Moscow";
      String JDBC_DRIVER = "com.mysql.jdbc.Driver";
      String USERNAME = "root";
      String PASSWORD = "root";
        try {
           connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            logger.info("Success");
        }
        catch(Exception ex){
            logger.info("fails...");
        }
        finally {
            try {connection.close();
            } catch(SQLException se) {
                /*connected is close now, good luck*/
            }
        }
    }

    @Test
    public static void createSomeTable() throws ClassNotFoundException, SQLException {
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

            String SQL = "CREATE TABLE akravchenko" +
                    "(id INTEGER(20) not NULL,"     +
                    " name VARCHAR(50), "           +
                    " surname VARCHAR(50), "        +
                    " age INTEGER(10), "            +
                    " birthdate DATE, "             +
                    " PRIMARY KEY (id))";

            statement.executeUpdate(SQL);
            logger.info("Table successfully created");
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

    @Test //
    public void insertDataInTable() throws ClassNotFoundException, SQLException{
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
            statement = connection.createStatement();

            statement.executeUpdate("INSERT INTO akravchenko (id, name, surname, age, birthdate) VALUES (1,'Havoc','Petrov', 18,  '2018-10-14')");
            statement.executeUpdate("INSERT INTO akravchenko (id, name, surname, age, birthdate) VALUES (2,'Alfa','Zvenigorod', 33,  '1995-02-07')");
            logger.info("Table successfully updated");

        }finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

  //   select data from table add data to pojo hibernate



    @Test
    public void getUsers() throws IOException {
        Session session = HibernateCfg.getSessionFactory().openSession();
        List<User> userList = (session.createQuery("from User", User.class).list());
        String userFirst = userList.get(0).toString();
        String userSecond = userList.get(1).toString();
        if (userFirst != userSecond){
            logger.info("userFirst != userSecond");
        }else {
            logger.info("Error");
        }
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter(); //перевод объекта в строку
        String json = ow.writeValueAsString(userList);
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
        session.close();

        logger.info(String.valueOf(userFirst.equals(userSecond)));
    }





}




