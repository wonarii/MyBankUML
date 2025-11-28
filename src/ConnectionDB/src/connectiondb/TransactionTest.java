
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

class TransactionTest {
	//private Customer customer ;
	private Customer customer;
	private BankBranch branch;
	private Bank bank;
	
	@BeforeEach
	void setUp() {
		
		Map<String, Object> user = new HashMap<>() {{
		    put("user_email", "sara123@gmail.com");
		    put("user_role", "user");
		}};
		Authenticator.getAuthenticatorInstance().setCurrentUser(user);


		bank = new Bank("Desjardins", 1);


		branch = new BankBranch(1, "Desjardins_Montreal", "2457, Saint Mishelle, Montreal, QC, HY0 5B2", 1, "5143261010");


		customer = new Customer("Sara", "Williams", "sara123@gmail.com", branch, bank, "somePassword123", "5143261010", "2001-06-13", 2000.0);
	}


	@Test
	void testCreateTransaction() {
		int result = Transaction.createTransaction(9000 , "deposit");
		assertEquals(0 , result);
		
		
		
	}
	
	void testCreateTransaction_invalid() {
		int result = Transaction.createTransaction(150000 , "withdraw");
		assertEquals(-1 , result);
		
		
		
	}

}
