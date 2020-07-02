package ru.netology.test;


import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import ru.netology.models.DataHelperAPI;
import ru.netology.models.JSONparts;
import ru.netology.models.UserAPI;

import java.sql.DriverManager;
import java.sql.SQLException;

public class TransferMoneyAPITest {

    @Test
    void shouldAuthorizationTransferMoneyAPI() throws SQLException {
        UserAPI user = DataHelperAPI.getUserAPI();
        JSONparts.jsonPartLogin(user);
        String token = JSONparts.jsonPartCode(DataHelperAPI.getVerificationCode(user));
        JSONparts.jsonPartListCard(token);
        JSONparts.jsonPartTransfer(DataHelperAPI.getTransferAPI(), token);
    }

    @AfterAll
    public static void delData() throws SQLException {
        val delSQLtransfer = "DELETE FROM card_transactions;";
        val delSQLcard = "DELETE FROM cards;";
        val delSQLcode = "DELETE FROM auth_codes;";
        val delSQLuser = "DELETE FROM users;";
        val runner = new QueryRunner();
        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            runner.update(conn, delSQLtransfer);
            runner.update(conn, delSQLcard);
            runner.update(conn, delSQLcode);
            runner.update(conn, delSQLuser);
        }
    }
}
