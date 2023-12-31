package lk.ashan.controller;

import lk.ashan.dao.EmployeeDao;
import lk.ashan.entity.Employee;
import lk.ashan.entity.Gender;

import java.util.Hashtable;
import java.util.List;

public class EmployeeController {

    public static List<Employee> get(Hashtable< String, Object> ht) {

        List<Employee> employees;

        if(ht == null) {
            employees = EmployeeDao.getAll();
        }
        else {
            String name = (String)ht.get("name");
            Gender gender = (Gender) ht.get("gender");

            if(gender==null)employees = EmployeeDao.getAllByName(name);
            else if (name==null) employees = EmployeeDao.getAllByGender(gender);
            else employees = EmployeeDao.getAllByNameAndGender(name,gender);

        }

        return employees;
    }

}
