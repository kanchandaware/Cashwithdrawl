package testpackage;




import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class CashwithdrawlTest {
    private CashWithdrawl atm;

    @Before
    public void setUp() {
        atm = new ATM();
    }

    @Test
    public void testWithdrawSuccessful() {
        assertTrue(atm.withdraw(50));
    }

    @Test
    public void testWithdrawAmountNotMultipleOf10() {
        assertFalse(atm.withdraw(55));
    }

    @Test
    public void testWithdrawInsufficientFunds() {
        ATM atmSpy = spy(atm);
        Map<Integer, Integer> bankNotes = new HashMap<>();
        bankNotes.put(10, 1);
        doReturn(bankNotes).when(atmSpy).getBankNotes();
        assertFalse(atmSpy.withdraw(20));
    }

    @Test
    public void testWithdrawUnsuccessfulDueToDenominations() {
        ATM atmSpy = spy(atm);
        Map<Integer, Integer> bankNotes = new HashMap<>();
        bankNotes.put(10, 1);
        doReturn(bankNotes).when(atmSpy).getBankNotes();
        assertFalse(atmSpy.withdraw(15));
    }
}
