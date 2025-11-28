import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;

public class AdminDashboard {
    private JComboBox customerComboBox;
    private JButton enterUserButton;
    private JButton createTellerAccountButton;
    private JButton createBankBranchButton;
    private JButton createUserAccountButton;
    private JButton logOutButton;
    private JPanel adminDashboardPanel;
    private JTable userAccountTable;
    private JScrollPane userAccountsScrollPane;
    private JTable tellerAccountTable;
    private JScrollPane tellerAccountsScrollPane;
    private JLabel adminNameField;
    private JLabel headerNameField;
    private JScrollPane branchScrollPane;
    private JTable branchTable;
    private JButton enterTellerButton;
    private JButton enterBranchButton;
    private JComboBox tellerComboBox;
    private JComboBox branchComboBox;
    private JTextField tellerTextField;
    private JTextField branchTextField;
    private JTextField customerTextField;
    private JButton profileButton;

    private DefaultTableModel userAccountModel;
    private DefaultTableModel tellerAccountModel;
    private DefaultTableModel branchModel;

    private final int AMOUNTOFTABLEROWS = 4;
    private DriverScreen driverScreen;

    final int[] lastSelectedRow = {-1};
    final int[] lastSelectedRowTeller = {-1};
    final int[] lastSelectedRowBranch = {-1};


    public AdminDashboard(DriverScreen driverScreen) {
        this.driverScreen = driverScreen;
        userAccountsScrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        tellerAccountsScrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        branchScrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));


        createBankBranchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                driverScreen.updateCreateBankBranchPage();
                Container parent = adminDashboardPanel.getParent();
                CardLayout layout = (CardLayout) parent.getLayout();
                layout.show(parent, "createBankBranch");
            }
        });

        // ================================ User Table ============================================
        // Store the selected row to allow it to get clicked afterwards
        userAccountTable.getSelectionModel().addListSelectionListener( e -> {
            if(!e.getValueIsAdjusting()) {
                lastSelectedRow[0] = userAccountTable.getSelectedRow();
            }
        });

        // Check if the selected row has been clicked
        userAccountTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                int clickedRow = userAccountTable.rowAtPoint(e.getPoint());

                // Check to make sure nothing will happen if we click an empty row
                Object testData = userAccountTable.getValueAt(clickedRow, 0);

                if(clickedRow == lastSelectedRow[0] && clickedRow >= 0 && testData != null) {
                    //Get The Selected Row's Information and Send it to the ShowButtonsFunction

                    // Convert the information from the table into an Object[]
                    int columnCount = userAccountModel.getColumnCount();
                    Object[] rowData = new Object[columnCount];
                    for (int i = 0; i < columnCount; i++) {
                        rowData[i] = userAccountModel.getValueAt(clickedRow, i);
                    }

                    // Send this to the Buttons
                    AdminUserButtons.showUserButtons(rowData, driverScreen, adminDashboardPanel);

                }
            }

        });

        // ======================================== Teller Table ==========================================
        // Store the selected row to allow it to get clicked afterwards
        tellerAccountTable.getSelectionModel().addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()) {
                lastSelectedRowTeller[0] = tellerAccountTable.getSelectedRow();
            }
        });

        // Check if the selected row has been clicked
        tellerAccountTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                int clickedRow = tellerAccountTable.rowAtPoint(e.getPoint());

                // Check to make sure nothing will happen if we click an empty row
                Object testData = tellerAccountTable.getValueAt(clickedRow, 0);

                if(clickedRow == lastSelectedRowTeller[0] && clickedRow >= 0 && testData != null) {
                    //Get The Selected Row's Information and Send it to the ShowButtonsFunction

                    // Convert the information from the table into an Object[]
                    int columnCount = tellerAccountModel.getColumnCount();
                    Object[] rowData = new Object[columnCount];
                    for (int i = 0; i < columnCount; i++) {
                        rowData[i] = tellerAccountModel.getValueAt(clickedRow, i);
                    }

                    // Send this to the Buttons
                    AdminTellerButtons.showTellerButtons(rowData, driverScreen, adminDashboardPanel);

                }
            }

        });

        // =============================== Branch Table =========================================
        branchTable.getSelectionModel().addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()) {
                lastSelectedRowBranch[0] = branchTable.getSelectedRow();
            }
        });

        // Check if the selected row has been clicked
        branchTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                int clickedRow = branchTable.rowAtPoint(e.getPoint());

                // Check to make sure nothing will happen if we click an empty row
                Object testData = branchTable.getValueAt(clickedRow, 0);

                if(clickedRow == lastSelectedRowBranch[0] && clickedRow >= 0 && testData != null) {
                    //Get The Selected Row's Information and Send it to the ShowButtonsFunction

                    // Convert the information from the table into an Object[]
                    int columnCount = branchModel.getColumnCount();
                    Object[] rowData = new Object[columnCount];
                    for (int i = 0; i < columnCount; i++) {
                        rowData[i] = branchModel.getValueAt(clickedRow, i);
                    }

                    // Send this to the Buttons
                    AdminBranchButtons.showBranchButtons(rowData, driverScreen, adminDashboardPanel);

                }
            }

        });
        createTellerAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                driverScreen.updateCreateTellerAccountPage();
                Container parent = adminDashboardPanel.getParent();
                CardLayout layout = (CardLayout) parent.getLayout();
                layout.show(parent, "createTellerAccount");
            }
        });
        createUserAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                driverScreen.updateCreateUserAccountPage();
                Container parent = adminDashboardPanel.getParent();
                CardLayout layout = (CardLayout) parent.getLayout();
                layout.show(parent, "createUserAccount");
            }
        });

        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int userID = (int) Authenticator.getAuthenticatorInstance().getCurrentUser().get("id");
                driverScreen.updateAccountInformationPage(userID);
                Container parent = adminDashboardPanel.getParent();
                CardLayout layout = (CardLayout) parent.getLayout();
                layout.show(parent, "accountInformation");
            }
        });
        enterUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCustomerSearch();
            }
        });

        enterTellerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTellerSearch();
            }
        });
        enterBranchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBranchSearch();
            }
        });
    }



    private void createUIComponents() {
        adminDashboardPanel = new JPanel();

        // ---------------------- User/Customer Account ---------------------------------
        String[] userColumns = {"Account ID", "Branch ID","Email", "First Name","Last Name", "Bank" , "Branch", "Balance"};      // Table Column Names

        // Make a model for userAccounts and lock the fields so they can't be edited in the UI
        userAccountModel = new DefaultTableModel(userColumns, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // all cells non-editable
            }
        };
        // Set up Table stuff
        userAccountTable = new JTable(userAccountModel);                                                // Initialize the User Account Table
        userAccountTable.getColumnModel().getColumn(0).setPreferredWidth(25);                // lower the column width of the Account ID
        userAccountTable.getColumnModel().getColumn(1).setPreferredWidth(25);                // lower the column width of the Account ID
        userAccountTable.setRowHeight(25);                                                              // Increase the height of a row


        // ============================================ Tellers ================================================
        String[] adminColumns = {"Account ID", "Branch ID","Email","First Name","Last Name","Bank","Branch"};
        tellerAccountModel = new DefaultTableModel(adminColumns, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tellerAccountTable = new JTable(tellerAccountModel);
        tellerAccountTable.getColumnModel().getColumn(0).setPreferredWidth(25);
        tellerAccountTable.getColumnModel().getColumn(1).setPreferredWidth(25);
        tellerAccountTable.setRowHeight(25);

        // ================================== Branches Stuff ====================================
        String[] branchColumns = {"Branch ID", "Branch","Location","Branch Phone", "Bank ID"};

        branchModel = new DefaultTableModel(branchColumns, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        branchTable = new JTable(branchModel);
        branchTable.getColumnModel().getColumn(0).setPreferredWidth(25);
        branchTable.setRowHeight(25);

        updateCustomerSearch();
        updateTellerSearch();
        updateBranchSearch();
    }

    public JPanel getPanel() {
        return adminDashboardPanel;

    }

    public void generateEmptyFillerRows(DefaultTableModel model) {
        int modelRowCount = model.getRowCount();

        for (int i = modelRowCount; i < AMOUNTOFTABLEROWS; i++) {
            Object[] emptyRow = new Object[model.getColumnCount()];
            model.addRow(emptyRow);
        }
    }

    public void updateAdminDashboardPage(){
        Authenticator auth = Authenticator.getAuthenticatorInstance();
        String adminName = auth.getCurrentUser().get("user_first_name").toString() + " " + auth.getCurrentUser().get("user_last_name");
        adminNameField.setText(adminName);
        headerNameField.setText(adminName);
        resetField();

        updateCustomerSearch();
        updateTellerSearch();
        updateBranchSearch();
    }


    // This method is called when the admin presses the 'Enter' button
    public void updateCustomerSearch() {
        try {
            JComboBox comboBox = customerComboBox;
            JTextField input = customerTextField;

            ConnectionDB db = ConnectionDB.getDatabaseInstance();
            Customer[] users = new Customer[]{};

            if(input == null || input.getText().equals("")){
                users = db.getAllCustomer();

            } else {
                if (comboBox.getSelectedItem().equals("AccountID")) {
                    users = db.searchCustomerId(input.getText());
                } else if (comboBox.getSelectedItem().equals("BranchID")) {
                    users = db.searchCustomerBranchId(input.getText());
                } else if (comboBox.getSelectedItem().equals("Name")) {
                    users = db.searchCustomerName(input.getText());
                } else {
                    db.getAllCustomer();
                }
            }

            userAccountModel.setRowCount(0);
            for(Customer customer : users) {
                userAccountModel.addRow(customer.display());
            }
            generateEmptyFillerRows(userAccountModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTellerSearch() {

        JComboBox comboBox = tellerComboBox;
        JTextField input = tellerTextField;

        try {
            ConnectionDB db = ConnectionDB.getDatabaseInstance();
            BankTeller[] users = new BankTeller[]{};

            if(input == null || input.getText().equals("")) {
                users = db.getAllTeller();

            } else {
                if (comboBox.getSelectedItem().equals("AccountID")) {
                    users = db.searchTellerId(input.getText());
                } else if (comboBox.getSelectedItem().equals("BranchID")) {
                    users = db.searchTellerBranchId(input.getText());
                } else if (comboBox.getSelectedItem().equals("Name")) {
                    users = db.searchTellerName(input.getText());
                } else {
                    users = db.getAllTeller();
                }
            }

            tellerAccountModel.setRowCount(0);
            for(BankTeller teller : users) {
                tellerAccountModel.addRow(teller.display());
            }
            generateEmptyFillerRows(tellerAccountModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBranchSearch() {

        JComboBox comboBox = branchComboBox;
        JTextField input = branchTextField;

        try {
            BankBranch[] branches = new BankBranch[]{};
            ConnectionDB db = ConnectionDB.getDatabaseInstance();

            if(input == null || input.getText().equals("")) {
                branches = BankBranch.getAllBankBranch();
            } else {
                if (comboBox.getSelectedItem().equals("BranchID")) {
                    branches = db.searchBranchId(input.getText());
                } else if (comboBox.getSelectedItem().equals("Name")) {
                    branches = db.searchBranchName(input.getText());
                }
            }
            branchModel.setRowCount(0);
            for(BankBranch branch : branches) {
                branchModel.addRow(branch.display());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void resetField() {
        customerTextField.setText("");
        tellerTextField.setText("");
        branchTextField.setText("");
    }


}
