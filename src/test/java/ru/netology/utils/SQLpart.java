package ru.netology.utils;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLpart {

    private SQLpart() {
    }

    public static void sqlDELETE() throws SQLException {
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
