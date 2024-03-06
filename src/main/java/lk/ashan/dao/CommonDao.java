package lk.ashan.dao;

import java.sql.*;

public class CommonDao {

    public static ResultSet get(String qry) {

        ResultSet rslt = null;

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/euc", "root", "1234");
            Statement stm = conn.createStatement();
            rslt = stm.executeQuery(qry);
        } catch (Exception e) {
            System.out.println("Can't Connect as" + e.getMessage());
        }

        return rslt;
    }


    public static String modify(String qry){

        String msg = "0";

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/euc", "root", "1234");
            Statement stm = conn.createStatement();
            int rows = stm.executeUpdate(qry);
            if(rows!=0) msg="1";
        }catch (SQLException e){
            System.out.println("Can't Connect as" + e.getMessage());
            msg="Database Error";
        }
        return  msg;

    }


}
