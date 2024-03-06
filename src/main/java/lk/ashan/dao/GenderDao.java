package lk.ashan.dao;

import lk.ashan.entity.Gender;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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

    public static List<Gender> getAll() {

        List<Gender> genders = new ArrayList<Gender>();

        try {

            String qry = "SELECT * FROM gender";
            ResultSet rslt = CommonDao.get(qry);

            while (rslt.next()) {

                Gender gender = new Gender();
                gender.setId(rslt.getInt("id"));
                gender.setName(rslt.getString("name"));

                genders.add(gender);

            }

        } catch (SQLException e) {
            System.out.println("Can't Connect as" + e.getMessage());
        }

        return genders;

    }


}
