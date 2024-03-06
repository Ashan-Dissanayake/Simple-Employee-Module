package lk.ashan.controller;

import lk.ashan.dao.EmployeeDao;
import lk.ashan.entity.Employee;
import lk.ashan.entity.Gender;

import java.util.Hashtable;
import java.util.List;

public class EmployeeController {

    public static List<Employee> get(Hashtable<String, Object> ht) {

        List<Employee> employees;

        if (ht == null) {
            employees = EmployeeDao.getAll();
        } else {

            String name = (String) ht.get("name");
            Gender gender = (Gender) ht.get("gender");

            if (gender == null)
                employees = EmployeeDao.getAllByName(name);
            else if (name == null)
                employees = EmployeeDao.getAllByGender(gender);
            else
                employees = EmployeeDao.getAllByNameAndGender(name, gender);
        }

        return employees;

    }

    public static String post(Employee employee) {

        String msg = "";
        String error = "";

        Employee emp = EmployeeDao.getByNic(employee.getNic());

        if (emp != null)
            error = error + "\nNIC Exists";

        if (error.isEmpty()) {
            String dberror = EmployeeDao.save(employee);
            if (dberror.equals("1"))
                msg = "1";
            else
                msg = "DB erroror as : " + dberror;
        } else {
            msg = "Data errorors : \n" + error;
        }

        return msg;
    }

    public static String put(Employee employee) {

        String msg = "";
        String error = "";

        Employee emp = EmployeeDao.getByNic(employee.getNic());

        if (emp != null && emp.getId() != employee.getId())
            error = error + "\nNIC Exists";

        if (error.isEmpty()) {
            String dberror = EmployeeDao.update(employee);
            if (dberror.equals("1"))
                msg = "1";
            else
                msg = "DB erroror as : " + dberror;
        } else {
            msg = "Data errorors : \n" + error;
        }

        return msg;
    }

    public static String delete(Employee employee) {

        String msg = "";
        String errors = "";

        String sts = EmployeeDao.delete(employee);
        if (sts.equals("1")) {
            msg = "1";
        } else {
            errors = sts;
            msg = errors;
        }

        return msg;

    }

}
