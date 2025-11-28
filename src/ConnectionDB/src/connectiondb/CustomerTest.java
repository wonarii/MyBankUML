import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerTest {
	private Customer customer , customer2;
	private BankBranch branch, branch2;
	private Bank bank , bank2 ;
	Map<String, Object> currentUser ;
	
	@BeforeEach
	void setUp() {
		branch = new BankBranch(4001 , "Desjardins" , "2457, Saint Mishelle, Montreal, QC, HY0 5B2" , 2001 , "1234567070");
		bank = new Bank("Bank_1" , 2001);
		customer = new Customer("Ahmed" , "Khan" , "ahmedKhan1990@gmail.com" , branch , bank ,"s09977fh" , "707897900" , "1990-07-20" , 4000);
		
	     // Set authenticator to Sarah Williams
		currentUser = new HashMap<>();
        currentUser.put("user_email", "sara123@gmail.com");
        currentUser.put("user_role", "user");
        currentUser.put("user_first_name", "Sara");
        currentUser.put("user_last_name", "Williams");
        currentUser.put("user_bank_id", 1);
        currentUser.put("user_branch_id", 1);

        Authenticator.getAuthenticatorInstance().setCurrentUser(currentUser);
    }
	




	

	@Test
	@Order(1)
	void testDeposit() {
		customer.deposit(500);
		assertEquals(4500 , customer.getBalance());
		
		
		
	}

	@Test
	@Order(2)
	//will use the customer information in the setup , do not think after the above deposit amount gets updated
	void testWithdraw() {
		customer.withdraw(1000);
		assertEquals(3000 , customer.getBalance());
		
	}
	  @Order(3)
	  @Test
	    void testGetBalanceForSarah() {
	        Double balance = Customer.getBalanceFromDatabase();

	        assertEquals(11000.0, balance);

	        System.out.println("Balance is correct: " + balance);
	        
	    }
	  
	  @Order(4)
	  @Test
	  void testGetBalance_NoUserLoggedIn() {
	      // simulate logout
	      Authenticator auth = Authenticator.getAuthenticatorInstance();
	      auth.setCurrentUser(null);

	      Double balance = Customer.getBalanceFromDatabase();

	      assertEquals(0.0, balance);

	      System.out.println("No user logged in → Balance returned: " + balance);
	  }

	  
	  
	  
	
	
}
	
	
	       



