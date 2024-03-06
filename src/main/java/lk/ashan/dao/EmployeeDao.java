package lk.ashan.dao;

import lk.ashan.entity.Employee;
import lk.ashan.entity.Gender;

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
        return get(qry);
    }

    public static List<Employee> getAllByName(String name) {
        String qry = "select * from  employee where name like '" + name + "%'";
        return get(qry);
    }

    public static List<Employee> getAllByGender(Gender gender) {
        String qry = "SELECT * FROM employee where gender_id=" + gender.getId();
        return get(qry);
    }

    public static List<Employee> getAllByNameAndGender(String name, Gender gender) {
        String qry = "SELECT * FROM employee where name like '" + name + "%' and gender_id=" + gender.getId();
        return get(qry);
    }

    public static Employee getByNic(String nic) {

        Employee employee = null;

        String qry = "select * from employee where nic='"+nic+"'";

        try{

            ResultSet rslt = CommonDao.get(qry);

            if(rslt!=null && rslt.next()){
                employee = new Employee();
                employee.setId(rslt.getInt("id"));
                employee.setName(rslt.getString("name"));
                employee.setNic(rslt.getString("nic"));

                employee.setGender( GenderDao.getById( rslt.getInt("gender_id")  )  );
            }
        } catch(SQLException e){
            System.out.println("Can't Get Results as : " + e.getMessage());
        }

        return employee;
    }

    public static String save(Employee employee) {

        String msg = "Can't Connect Data Base";

        String qry = "INSERT INTO employee (name,nic,gender_id)VALUES('"
                + employee.getName() + "','"
                + employee.getNic() + "',"
                + employee.getGender().getId()
                + ")";

        msg = CommonDao.modify(qry);

        return msg;

    }

    public static String update(Employee employee) {

        String msg = "Can't Connect Data Base";

        String qry = "UPDATE employee SET name='"
                + employee.getName() + "',"
                + "nic='" + employee.getNic() + "',"
                + "gender_id=" + employee.getGender().getId() +
                " WHERE id=" + employee.getId();

        msg = CommonDao.modify(qry);

        return msg;

    }

    public static String delete(Employee employee) {

        String msg = "";

        String qry = "DELETE FROM employee WHERE id=" + employee.getId();
        msg = CommonDao.modify(qry);

        return msg;

    }


}
