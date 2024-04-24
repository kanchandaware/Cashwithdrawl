package testpackage;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import atmProj.CashWithdrawl;

public class CashwithdrawlTest {
    private CashWithdrawl CashWithdrawl;

    @Before
    public void setUp() {
    	CashWithdrawl = new CashWithdrawl();
    }

    @Test
    public void testWithdrawWithSuccess() {
        assertTrue(CashWithdrawl.withdrawCashFromATM(500));
    }

    @Test
    public void testWithdrawAmounIfmultipleOfTen() {
        assertFalse(CashWithdrawl.withdrawCashFromATM(45));
    }

    @Test
    public void testIfInsufficientFunds() {
    	CashWithdrawl cash = spy(CashWithdrawl.class);
        Map<Integer, Integer> bankNotes = new HashMap<>();
        bankNotes.put(20, 1);
        doReturn(bankNotes).when(cash).getBankNotes();
        cash.getBankNotes(bankNotes);

        assertFalse(cash.withdrawCashFromATM(20));
    }

    @Test
    public void testWithdrawForDenominations() {
    	CashWithdrawl cashAmount = spy(CashWithdrawl.class);
    	
        Map<Integer, Integer> bankNotes = new HashMap<>();
        bankNotes.put(20, 1);
        
        doReturn(bankNotes).when(cashAmount).getBankNotes();
        cashAmount.getBankNotes(bankNotes);
        assertFalse(cashAmount.withdrawCashFromATM(15));
    }
}
