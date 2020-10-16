import db.configuration.HibernateCfg;
import db.entities.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Helpers;

import java.util.List;

//addhbrnt
public class HiberneteTsk01 {

    final Logger logger = LoggerFactory.getLogger(Helpers.class);
    private static Transaction trans = null;
    private static Session session = null;
    @Test
    public void checkDBConnect() {
        try {
            HibernateCfg.getSessionFactory().openSession();
            logger.info("Connected DB");
        }catch(Exception ex) {
            ex.printStackTrace();
            logger.info("Connection is failed...");
        }
    }

//    @Test
//    public void createTable() throws SQLException, ClassNotFoundException {
//        Task1.createSomeTable();
//    }

    @Test
    public void insertDataInTable(){
        try {
           Helpers.saveUsers();
           if(session != null){
               logger.info("Session created, INSERT data in table");
               session.close();
           }
           else {
               logger.info("Session is not created");
           }

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    @Test
    public void selectAndSaveUsersAsJson(){
        try {
            List<User> userList = Helpers.selectUsers();
            if (userList!=null){
                logger.info("List of Users Created in created Session... ");
            }
            else {
                logger.info("List of users is null");
            }

            Boolean assertionResult = userList.get(0).equals(userList.get(1));
            if(assertionResult != true){
                logger.info("The selected Users are not equal");
            }
            else {
                logger.info("The selected Users is equal");
            }
                try {
                    logger.info("Start creating json file");
                    Helpers.saveSelectedUsersAsJsonFile();

                } finally {
                    logger.info("Task is finished JSon data {} saved as answer.json ", Helpers.jsonData());
                    Helpers.deleteTable();
                    logger.info("TheEnd");
                }

        }catch (Exception ex){
            ex.printStackTrace();
            logger.info("You are FckUp, look whats happens wrong.... ");
        }
    }


}