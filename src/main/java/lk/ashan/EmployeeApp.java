package lk.ashan;

import lk.ashan.view.EmployeeUi;

import javax.swing.*;

public class EmployeeApp {

    public static void main(String[] args) {

        EmployeeUi employeeui  = new EmployeeUi();
        employeeui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        employeeui.setVisible(true);

    }
}
