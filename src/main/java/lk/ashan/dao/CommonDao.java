package lk.ashan.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CommonDao {

    public static ResultSet get(String qry) {

        ResultSet rslt = null;

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/eucc", "root", "1234");
            Statement stm = conn.createStatement();
            rslt = stm.executeQuery(qry);

        } catch (Exception e) {
            System.out.println("Can't Connect as" + e.getMessage());
        }

        return rslt;
    }

}
