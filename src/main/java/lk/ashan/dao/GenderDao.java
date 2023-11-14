package lk.ashan.dao;

import lk.ashan.entity.Gender;

import java.sql.SQLException;
import java.sql.ResultSet;

public class GenderDao {

    public static Gender getById(int id) {

        Gender gender = new Gender();

        try {

            String qry = "SELECT * FROM gender where id=" + id;
            ResultSet rslt = CommonDao.get(qry);

            rslt.next();
            gender.setId(rslt.getInt("id"));
            gender.setName(rslt.getString("name"));

        } catch (SQLException e) {
            System.out.println("Can't Connect as" + e.getMessage());
        }

        return gender;

    }

}
