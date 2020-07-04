package ru.netology.models;

import lombok.Data;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

@Data
public class DataHelperAPI {

    private DataHelperAPI() {
    }

    public static UserAPI getUserAPI(){
        return new UserAPI("vasya","qwerty123");
    }

    public static UserCode getVerificationCode (UserAPI user) throws SQLException {
        val runner = new QueryRunner();
        String login=user.getLogin();
        val conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
        val dataSQLId="SELECT id FROM users WHERE login =?";
        String id =runner.query(conn, dataSQLId, new ScalarHandler<>(),login);;
       // val dataSQL = "SELECT code FROM auth_codes WHERE user_id = ?";
        val dataSQLcode="SELECT code FROM auth_codes ac WHERE created =(SELECT MAX(created) FROM auth_codes WHERE user_id =?)";
        String code = runner.query(conn, dataSQLcode, new ScalarHandler<>(),id);
        return new UserCode (login,code);
   }

   public static int getCurrentBalance (String cardNumber) throws SQLException {
       val runner = new QueryRunner();
       val conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
       val dataSQLId="SELECT balance_in_kopecks FROM cards WHERE number =?";
       int balance =runner.query(conn, dataSQLId, new ScalarHandler<>(),cardNumber);;
       return balance;
    }

    public static Transfer getTransferAPI(){
        String fromCard="5559 0000 0000 0002";
        String toCard="5559 0000 0000 0008";
        String amount="2000";
        return new Transfer(fromCard,toCard,amount);
    }
}
