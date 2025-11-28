import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TellerDashboard {
    private JPanel tellerDashboardPanel;
    private JLabel headerNameField;
    private JButton logOutButton;
    private JComboBox customerComboBox;
    private JButton enterButton;
    private JLabel tellerNameField;
    private JButton createUserAccountButton;
    private JScrollPane userAccountsScrollPane;
    private JTable userAccountTable;
    private JTextField customerTextField;

    private final int AMOUNTOFTABLEROWS = 4;
    private DefaultTableModel userAccountModel;

    private DriverScreen driverScreen;

    final int[] lastSelectedRow = {-1};

    public TellerDashboard(DriverScreen driverScreen) {
        this.driverScreen = driverScreen;
        userAccountsScrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));


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
                    AdminUserButtons.showUserButtons(rowData, driverScreen, tellerDashboardPanel);

                }
            }

        });

        createUserAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                driverScreen.updateCreateUserAccountPage();
                resetField();
                Container parent = tellerDashboardPanel.getParent();
                CardLayout layout = (CardLayout) parent.getLayout();
                layout.show(parent, "createUserAccount");
            }
        });

        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateUserTable();
            }
        });
//        updateUserTable();
    }

    private void createUIComponents() {

        tellerDashboardPanel = new JPanel();

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
        userAccountTable.setRowHeight(25);

        updateUserTable();

    }

    public void updateTellerDashboardPage(){
        Authenticator auth = Authenticator.getAuthenticatorInstance();
        String tellerName = auth.getCurrentUser().get("user_first_name").toString() + " " + auth.getCurrentUser().get("user_last_name");
        tellerNameField.setText(tellerName);
        headerNameField.setText(tellerName);
        resetField();

        updateUserTable();
    }

    /**
     * This function will update the table for the users
     */
    public void updateUserTable(){
        try {
            JComboBox comboBox = customerComboBox;
            JTextField input = customerTextField;

            ConnectionDB db = ConnectionDB.getDatabaseInstance();
            Customer[] users;

            if (input == null || input.getText().equals("")) {
                users = db.getAllCustomer();

            } else {
                if (comboBox.getSelectedItem().equals("AccountID")) {
                    users = db.searchCustomerId(input.getText());
                } else if (comboBox.getSelectedItem().equals("BranchID")) {
                    users = db.searchCustomerBranchId(input.getText());
                } else if (comboBox.getSelectedItem().equals("Name")) {
                    users = db.searchCustomerName(input.getText());
                } else {
                    users = db.getAllCustomer();
                }
            }

            userAccountModel.setRowCount(0);
            for (Customer tempCustomer : users) {
                userAccountModel.addRow(tempCustomer.display());
            }
            generateEmptyFillerRows(userAccountModel);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void generateEmptyFillerRows(DefaultTableModel model) {
        int modelRowCount = model.getRowCount();

        for (int i = modelRowCount; i < AMOUNTOFTABLEROWS; i++) {
            Object[] emptyRow = new Object[model.getColumnCount()];
            model.addRow(emptyRow);
        }
    }

    public JPanel getPanel(){

        return tellerDashboardPanel;
    }

    public void resetField(){
        customerTextField.setText("");
    }
}
