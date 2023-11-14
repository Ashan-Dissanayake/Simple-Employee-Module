package lk.ashan.view;

import lk.ashan.controller.EmployeeController;
import lk.ashan.controller.GenderController;
import lk.ashan.entity.Employee;
import lk.ashan.entity.Gender;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.util.Hashtable;
import java.util.Vector;
import java.util.List;

public class EmployeeUi extends JFrame {

    JTable tblEmployee;
    JComboBox<Object> cmbSearchGender;
    JTextField txtSearchName;
    Vector columns;

    public EmployeeUi() {

        setTitle("Employee Detail Management");
        setSize(500, 350);
        setLocation(500, 600);

        JLabel lblSearchName = new JLabel("Search By Name");
        txtSearchName = new JTextField(10);
        JLabel lblSearchGender = new JLabel("Search By Gender");
        cmbSearchGender = new JComboBox();
        JButton btnSearch = new JButton("Search");
        JButton btnSearchClear = new JButton("Clear Search");

        columns = new Vector();
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

        con.add(lblSearchName);
        con.add(txtSearchName);
        con.add(lblSearchGender);
        con.add(cmbSearchGender);
        con.add(btnSearch);
        con.add(btnSearchClear);

        con.add(jspTable);

        btnSearch.addActionListener(this::btnSearchAp);
        btnSearchClear.addActionListener(this::btnSearchClearAp);

        initialize();

    }

    public void initialize() {

        loadView();
    }

    public void loadView() {

        List<Employee> emplist = EmployeeController.get(null);
        fillTable(emplist);

        List<Gender> gendlist = GenderController.get();
        Vector<Object> genders = new Vector();
        genders.add("Select a Gender");

        genders.addAll(gendlist);

        DefaultComboBoxModel<Object> genModel = new DefaultComboBoxModel<Object>(genders);
        cmbSearchGender.setModel(genModel);

    }

    public void fillTable(List<Employee> emplist) {

        Vector data = new Vector();

        for (Employee emp : emplist) {
            Vector row = new Vector<>();
            row.add(emp.getName());
            row.add(emp.getNic());
            row.add(emp.getGender().getName());

            data.add(row);
        }

        DefaultTableModel tblModel = new DefaultTableModel(data,columns);
        tblEmployee.setModel(tblModel);

    }

    public  void  btnSearchAp(ActionEvent e){

        Gender gender = null;

        String name = txtSearchName.getText();
        Object sitem = cmbSearchGender.getSelectedItem();

        if (!sitem.equals("Select a Gender")) gender = (Gender) sitem;

        Hashtable<String,Object> ht = new Hashtable();
        ht.put("name",name);

        if(gender!=null) ht.put("gender",gender);

        List<Employee> employees = EmployeeController.get(ht);
        fillTable(employees);

    }

    public void btnSearchClearAp(ActionEvent e) {

        int opt = JOptionPane.showConfirmDialog(null, "Are you sure to Clear the Search");

        if (opt != 1) {

            txtSearchName.setText("");
            cmbSearchGender.setSelectedIndex(0);

            List<Employee> employees = EmployeeController.get(null);
            fillTable(employees);

        }

    }

}
