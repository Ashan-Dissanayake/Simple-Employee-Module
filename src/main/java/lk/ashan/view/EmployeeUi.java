package lk.ashan.view;

import lk.ashan.controller.EmployeeController;
import lk.ashan.controller.GenderController;
import lk.ashan.entity.Employee;
import lk.ashan.entity.Gender;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Hashtable;
import java.util.Vector;
import java.util.List;

public class EmployeeUi extends JFrame {

    JTable tblEmployee;
    Vector columns;

    JComboBox<Object> cmbSearchGender;
    JTextField txtSearchName;

    JComboBox cmbGender;
    JTextField txtName;
    JTextField txtNic;

    Color initial;
    Color valid;
    Color invalid;
    Color update;

    JButton btnAdd;
    JButton btnClear;
    JButton btnUpdate;
    JButton btnDelete;

    List<Employee> emplist;
    List<Gender> genlist;

    Employee oldEmployee;
    Employee employee;

    public EmployeeUi() {

        initial = Color.WHITE;
        valid = new Color(200, 255, 200);
        invalid = Color.PINK;
        update = Color.YELLOW;

        setTitle("Employee Detail Management");
        setSize(500, 550);
        setLocation(550, 300);

        JLabel lblName = new JLabel("Name");
        JLabel lblNic = new JLabel("NIC");
        JLabel lblGender = new JLabel("Gender");

        txtName = new JTextField(10);
        txtNic = new JTextField(10);

        cmbGender = new JComboBox<>();

        btnAdd = new JButton("Add");
        btnClear = new JButton("Clear");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");

        JLabel lblSearchName = new JLabel("Search by Name");
        txtSearchName = new JTextField(10);
        JLabel lblSearchGender = new JLabel("Search by Gender");
        cmbSearchGender = new JComboBox();
        JButton btnSearch = new JButton("Search");
        JButton btnClearSearch = new JButton("Clear Search");

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

        con.add(lblName);
        con.add(txtName);
        con.add(lblNic);
        con.add(txtNic);
        con.add(lblGender);
        con.add(cmbGender);

        con.add(btnAdd);
        con.add(btnClear);
        con.add(btnUpdate);
        con.add(btnDelete);

        con.add(lblSearchName);
        con.add(txtSearchName);
        con.add(lblSearchGender);
        con.add(cmbSearchGender);
        con.add(btnSearch);
        con.add(btnClearSearch);

        con.add(jspTable);

        btnSearch.addActionListener(this::btnSearchAp);

        btnClearSearch.addActionListener(this::btnClearSearchAp);

        btnAdd.addActionListener(this::btnAddAp);

        btnClear.addActionListener(this::btnClearAp);

        tblEmployee.getSelectionModel().addListSelectionListener(this::tblEmployeeVc);

        btnUpdate.addActionListener(this::btnUpdateAp);

        btnDelete.addActionListener(this::btnDeleteAp);

        txtName.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                txtNameKR(e);
            }
        });

        txtNic.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                txtNicKR(e);
            }
        });

        cmbGender.addActionListener(this::cmbGenderAp);

        initialize();

    }

    public void initialize() {
        loadView();
        loadForm();
    }

    public void loadView() {

        emplist = EmployeeController.get(null);
        fillTable(emplist);

        List<Gender> genlist = GenderController.get();
        Vector<Object> genders = new Vector<Object>();
        genders.add("Select a Gender");

        genders.addAll(genlist);

        DefaultComboBoxModel<Object> genModel = new DefaultComboBoxModel<Object>(genders);
        cmbSearchGender.setModel(genModel);

    }

    public void fillTable(List<Employee> emplist) {

        Vector<Object> data = new Vector<>();

        for (Employee emp : emplist) {

            Vector<Object> row = new Vector<Object>();

            row.add(emp.getName());
            row.add(emp.getNic());
            row.add(emp.getGender().getName());

            data.add(row);
        }

        DefaultTableModel tblModel = new DefaultTableModel(data, columns);
        tblEmployee.setModel(tblModel);

    }

    public void btnSearchAp(ActionEvent e) {

        Gender gender = null;

        String name = txtSearchName.getText();

        Hashtable<String, Object> ht = new Hashtable<>();
        ht.put("name", name);

        Object sitem = cmbSearchGender.getSelectedItem();

        if (!sitem.equals("Select a Gender"))
            gender = (Gender) sitem;

        if (gender != null)
            ht.put("gender", gender);

        List<Employee> employees = EmployeeController.get(ht);

        fillTable(employees);

    }

    public void btnClearSearchAp(ActionEvent e) {

        int op = JOptionPane.showConfirmDialog(null, "Are You Sure to Clear Search Deatils ?");

        if (op != 1) {
            txtSearchName.setText("");
            cmbSearchGender.setSelectedIndex(0);

            List<Employee> employees = EmployeeController.get(null);
            fillTable(employees);
        }

    }

    public void loadForm() {

        employee = new Employee();

        genlist = GenderController.get();
        Vector<Object> genders = new Vector<Object>();
        genders.add("Select a Gender");

        genders.addAll(genlist);

        DefaultComboBoxModel<Object> genModel = new DefaultComboBoxModel<Object>(genders);
        cmbGender.setModel(genModel);

        txtName.setText("");
        txtNic.setText("");

        oldEmployee = null;

        setStyle(initial);
        enableButtons(true, true, false, false);

    }

    public void setStyle(Color clr) {
        txtName.setBackground(clr);
        txtNic.setBackground(clr);
        cmbGender.setBackground(clr);
    }

    public void enableButtons(boolean add, boolean clr, boolean upd, boolean del) {
        btnAdd.setEnabled(add);
        btnClear.setEnabled(clr);
        btnUpdate.setEnabled(upd);
        btnDelete.setEnabled(del);
    }

    public void txtNameKR(KeyEvent e) {

        // System.out.println("Test-OK");

        String name = txtName.getText();
        if (name.matches("^[A-Z][a-z]{2,}$")) {
            employee.setName(name);
            txtName.setBackground(valid);
            if (oldEmployee != null) {
                if (!employee.getName().equals(oldEmployee.getName())) {
                    txtName.setBackground(update);
                }
            }
        } else {
            employee.setName(null);
            txtName.setBackground(invalid);
        }

    }

    public void txtNicKR(KeyEvent e) {

        // System.out.println("Test-OK");

        String nic = txtNic.getText();
        if (nic.matches("^[0-9]{9}V$")) {
            employee.setNic(nic);
            txtNic.setBackground(valid);
            if (oldEmployee != null) {
                if (!employee.getNic().equals(oldEmployee.getNic())) {
                    txtNic.setBackground(update);
                }
            }
        } else {
            employee.setNic(null);
            txtNic.setBackground(invalid);
        }
    }

    public void cmbGenderAp(ActionEvent e) {

        // System.out.println("Test-Ok");

        int sindex = cmbGender.getSelectedIndex();
        if (sindex != 0) {
            employee.setGender((Gender) cmbGender.getSelectedItem());
            cmbGender.setBackground(valid);
            if (oldEmployee != null) {
                if (!employee.getGender().equals(oldEmployee.getGender())) {
                    cmbGender.setBackground(update);
                }
            }
        } else {
            employee.setGender(null);
            cmbGender.setBackground(invalid);
        }
    }

    public String getErrors() {

        String errors = "";

        if (employee.getName() == null) {
            errors = errors + "Invalid Name";
        }

        if (employee.getNic() == null) {
            errors = errors + "\nInvalid NIC";
        }

        if (employee.getGender() == null) {
            errors = errors + "\nInvalid Gender";
        }

        return errors;
    }

    public String getUpdates() {

        String updates = "";

        if (!employee.getName().equals(oldEmployee.getName())) {
            updates = updates + "Name is Updated - " + employee.getName();
        }

        if (!employee.getNic().equals(oldEmployee.getNic())) {
            updates = updates + "\nNIC is Updated - " + employee.getNic();
        }

        if (!employee.getGender().equals(oldEmployee.getGender())) {
            updates = updates + "\nGender is Updated - " + employee.getGender().getName();
        }

        return updates;

    }

    public void btnAddAp(ActionEvent e) {

        // System.out.println("Test-OK");

        String errors = getErrors();

        if (errors.isEmpty()) {

            String cnfmsg = "Are you Sure to Save Follwing Employee ?\n";
            cnfmsg = cnfmsg + "Name is: " + employee.getName();
            cnfmsg = cnfmsg + "\nNIC is: " + employee.getNic();
            cnfmsg = cnfmsg + "\nGender is:" + employee.getGender().getName();

            int op = JOptionPane.showConfirmDialog(null, cnfmsg);

            if (op == 0) {
                String sts = EmployeeController.post(employee);
                if (sts.equals("1")) {
                    JOptionPane.showMessageDialog(null, "Successfully Saved");
                    loadView();
                    loadForm();
                } else {
                    JOptionPane.showMessageDialog(null, "Faild to Save as" + sts);
                }
            }

        } else {
            JOptionPane.showMessageDialog(null, "You have Following Errors\n" + errors);
        }

    }

    public void btnClearAp(ActionEvent e) {

        int op = JOptionPane.showConfirmDialog(null, "Are You Sure to Clear this Form");

        if (op == 0) {
            loadForm();
        }

    }

    public void tblEmployeeVc(ListSelectionEvent e) {

        int row = tblEmployee.getSelectedRow();

        if (row > -1) {
            Employee employee = emplist.get(row);
            fillForm(employee);
        }

    }

    public void fillForm(Employee emp) {

        oldEmployee = emp;

        employee = new Employee();
        employee.setName(emp.getName());
        employee.setNic(emp.getNic());
        employee.setGender(emp.getGender());

        txtName.setText(emp.getName());
        txtNic.setText(emp.getNic());

        for (Gender gen : genlist) {
            if (gen.equals(emp.getGender())) {
                cmbGender.setSelectedItem(gen);
                break;
            }
        }

        enableButtons(false, true, true, true);
        setStyle(valid);

    }

    public void btnUpdateAp(ActionEvent e) {

        String errors = getErrors();
        employee.setId(oldEmployee.getId());

        if (errors.isEmpty()) {

            String updates = getUpdates();

            if (!updates.isEmpty()) {

                int op = JOptionPane.showConfirmDialog(null, "Are you Sure Update Following Details" + updates);

                if (op == 0) {
                    String sts = EmployeeController.put(employee);
                    if (sts.equals("1")) {
                        JOptionPane.showMessageDialog(null, "Successfully Updated");
                        loadView();
                        loadForm();
                    } else {
                        JOptionPane.showMessageDialog(null, "Faild to Update as" + sts);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Nothing to Update");
            }

        } else {
            JOptionPane.showMessageDialog(null, "You have Following Errors\n" + errors);
        }

    }

    public void btnDeleteAp(ActionEvent e) {

        int op = JOptionPane.showConfirmDialog(null, "Are Sure to Delete Following Employee\n" + oldEmployee.getName());

        if (op == 0) {
            String sts = EmployeeController.delete(oldEmployee);
            if (sts.equals("1")) {
                JOptionPane.showMessageDialog(null, "Successfully Deleted..!");
                loadForm();
                loadView();
            } else {
                JOptionPane.showMessageDialog(null, "Faild to Delete as" + sts);
            }
        }

    }

}
