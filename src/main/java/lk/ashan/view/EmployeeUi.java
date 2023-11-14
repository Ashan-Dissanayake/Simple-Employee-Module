package lk.ashan.view;

import lk.ashan.controller.EmployeeController;
import lk.ashan.entity.Employee;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import java.util.Vector;
import java.util.List;

public class EmployeeUi extends JFrame {

    JTable tblEmployee;

    public EmployeeUi() {

        setTitle("Employee Detail Management");
        setSize(500, 250);
        setLocation(500, 600);

        Vector columns = new Vector();
        columns.add("Name");
        columns.add("NIC");
        columns.add("Gender");

        Vector data = new Vector();

        tblEmployee = new JTable();
        DefaultTableModel tblModel = new DefaultTableModel(data, columns);
        tblEmployee.setModel(tblModel);

        JScrollPane jspTable = new JScrollPane();
        jspTable.setPreferredSize(new Dimension(450, 180));
        jspTable.setViewportView(tblEmployee);

        Container con = getContentPane();
        FlowLayout lay = new FlowLayout();
        con.setLayout(lay);

        con.add(jspTable);

        initialize();

    }

    public void initialize() {
        loadView();
    }

    public void loadView() {
        List<Employee> emplist = EmployeeController.get();
        fillTable(emplist);

    }

    public void fillTable(List<Employee> emplist) {

        DefaultTableModel model = (DefaultTableModel) tblEmployee.getModel();

        for (Employee emp : emplist) {
            Vector row = new Vector<>();
            row.add(emp.getName());
            row.add(emp.getNic());
            row.add(emp.getGender().getName());

            model.addRow(row);
        }

    }

}
