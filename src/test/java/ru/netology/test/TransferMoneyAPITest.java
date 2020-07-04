package ru.netology.test;


import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import ru.netology.models.DataHelperAPI;
import ru.netology.utils.JSONparts;
import ru.netology.models.UserAPI;
import ru.netology.utils.SQLpart;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferMoneyAPITest {

    @Test
    void shouldAuthorizationTransferMoneyAPI() throws SQLException {
        UserAPI user = DataHelperAPI.getUserAPI();
        JSONparts.jsonPartLogin(user);
        String token = JSONparts.jsonPartCode(DataHelperAPI.getVerificationCode(user));
        JSONparts.jsonPartListCard(token);
        JSONparts.jsonPartTransfer(DataHelperAPI.getTransferAPI(), token);
    }

    @Test
    void shouldCheckBalanceAfterTransfer() throws SQLException {
        UserAPI user = DataHelperAPI.getUserAPI();
        JSONparts.jsonPartLogin(user);
        String token = JSONparts.jsonPartCode(DataHelperAPI.getVerificationCode(user));
        JSONparts.jsonPartListCard(token);
        int startBalance = DataHelperAPI.getCurrentBalance(DataHelperAPI.getTransferAPI().getFrom());
        JSONparts.jsonPartTransfer(DataHelperAPI.getTransferAPI(), token);
        int expected = startBalance-Integer.parseInt(DataHelperAPI.getTransferAPI().getAmount());
        int actual = DataHelperAPI.getCurrentBalance(DataHelperAPI.getTransferAPI().getFrom());
        assertEquals(expected, actual);
    }

    @AfterAll
    public static void delData() throws SQLException {
        SQLpart.sqlDELETE();
    }

}
