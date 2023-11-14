package lk.ashan.dao;

import lk.ashan.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {

    private static List<Employee> get(String qry) {

        List<Employee> employees = new ArrayList<>();

        try {

            ResultSet rslt = CommonDao.get(qry);

            while (rslt.next()) {
                Employee employee = new Employee();
                employee.setId(rslt.getInt("id"));
                employee.setName(rslt.getString("name"));
                employee.setNic(rslt.getString("nic"));

                employee.setGender(GenderDao.getById(rslt.getInt("gender_id")));

                employees.add(employee);
            }

        } catch (SQLException e) {
            System.out.println("Can't Connect as" + e.getMessage());
        }

        return employees;

    }

    public static List<Employee> getAll() {

        String qry = "SELECT * FROM employee";
        List<Employee> employees = get(qry);

        return employees;
    }

}
