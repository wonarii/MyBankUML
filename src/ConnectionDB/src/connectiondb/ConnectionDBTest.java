import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



class ConnectionDBTest {
	private ConnectionDB db;
	private String testTellerEmail = "test_teller@email.com";
	private String testEmail = "test_user@gmail.com";
	private String testEmail2 = "test_user2@gmail.com";
	private String testEmail3 = "test_user3@gmail.com";
	
	@BeforeEach
	void setUp() {
		try {
			db = ConnectionDB.getDatabaseInstance();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
    
//    @Test
//    void testCreateBankTeller() throws Exception {
//        // Arrange
//        BankTeller teller = new BankTeller("John", "Doe", testTellerEmail, 1);
//
//        // Act
//        db.createBankTeller(teller);
//
//        // Assert
//        try (Connection conn = db.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(
//                     "SELECT * FROM banktellers WHERE email = ?"
//             )) {
//            stmt.setString(1, testTellerEmail);
//            ResultSet rs = stmt.executeQuery();
//
//            assertTrue(rs.next(), "BankTeller should be inserted into the database");
//            assertEquals("John", rs.getString("first_name"));
//            assertEquals("Doe", rs.getString("last_name"));
//            assertEquals(testTellerEmail, rs.getString("email"));
//            assertEquals(1, rs.getInt("branch"));
//        }
//    }

 
	
//
//	@Test
//	void testCreateCustomer() {
//		fail("Not yet implemented");
//	}
//
//
//
//	@Test
//	void testCreateAdministrator() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testCreateUser() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testDeleteUser() {
//		fail("Not yet implemented");
//	}
    
//    
//    @AfterEach
//    void cleanUp() throws Exception {
//        try (Connection conn = db.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(
//                     "DELETE FROM account_list WHERE user_email = ?"
//             )) {
//            stmt.setString(1, testEmail);
//            stmt.executeUpdate();
//        }
//    }

    @Test
    void testCreateUser_ValidCustomer() throws Exception {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        String password = "Password123!";
        double balance = 250.0;
        String role = "user";
        int bankId = 1;
        int branchId = 1;
        String birthday = "1990-05-01"; // adult
        String address = "123 Test Street";

        // Act
        boolean result = db.createUser(firstName, lastName, testEmail, password,
                balance, role, bankId, branchId, birthday, address);

        // Assert: method returned success
        assertTrue(result);

        // Assert: row is inserted in DB
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM account_list WHERE user_email = ?"
             )) {

            stmt.setString(1, testEmail);
            ResultSet rs = stmt.executeQuery();

            assertTrue(rs.next(), "User row should exist in the database");

            assertEquals(firstName, rs.getString("user_first_name"));
            assertEquals(lastName, rs.getString("user_last_name"));
            assertEquals(testEmail, rs.getString("user_email"));

            // hashed password should NOT match raw password
            assertNotEquals(password, rs.getString("user_password"));

            assertEquals(balance, rs.getDouble("user_balance"));
            assertEquals(role, rs.getString("user_role"));
            assertEquals(bankId, rs.getInt("user_bank_id"));
            assertEquals(branchId, rs.getInt("user_branch_id"));
        }
    }



	@Test
	void testCreateUser_InvalidRole() throws Exception {
	    boolean result = db.createUser(
	            "John", "Doe", testEmail2, "Password123!",
	            500.0,
	            "manager",    // INVALID ROLE
	            1, 1,
	            "1990-01-01",
	            "123 Test St"
	    );
	
	    assertFalse(result, "User creation should fail for invalid role");
	
	    // DB should contain NO row
	    try (Connection conn = db.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(
	                 "SELECT * FROM account_list WHERE user_email = ?")) {
	        stmt.setString(1, testEmail2);
	        ResultSet rs = stmt.executeQuery();
	        assertFalse(rs.next(), "No user should be inserted for invalid role");
	    }
	}





	@Test
	void testCreateUser_Underage() throws Exception {
	    boolean result = db.createUser(
	            "John", "Doe", testEmail3, "Password123!",
	            300.0,
	            "user",
	            1, 1,
	            "2010-01-01",   // UNDERAGE (14 yrs old)
	            "123 Test St"
	    );
	
	    assertFalse(result, "User creation should fail for underage user");
	
	    try (Connection conn = db.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(
	                 "SELECT * FROM account_list WHERE user_email = ?")) {
	        stmt.setString(1, testEmail3);
	        ResultSet rs = stmt.executeQuery();
	        assertFalse(rs.next(), "No user should be inserted for underage user");
	    }
	}
	
	
	

	
}


