
package testpackage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import atmProj.CashWithdrawl;


public class CashWithdrawlTest {

    private CashWithdrawl cashWithdrawl;
    private Map<Integer, Integer> mockBankCurrency;

    @Before
    public void setUp() {
        cashWithdrawl = new CashWithdrawl();
        mockBankCurrency = new HashMap<>();
    }

    @Test
    public void testWithdrawCashFromATM_InsufficientFunds() {
        mockBankCurrency.put(10, 10); 
        cashWithdrawl.bankCurrency = mockBankCurrency;

        assertFalse(cashWithdrawl.withdrawCashFromATM(100));
        assertFalse(cashWithdrawl.withdrawCashFromATM(0)); 
        assertFalse(cashWithdrawl.withdrawCashFromATM(-100)); 
        assertFalse(cashWithdrawl.withdrawCashFromATM(2000)); 
        assertTrue(cashWithdrawl.withdrawCashFromATM(1000));
    }

    @Test
    public void testWithdrawCashFromATM_NonMultipleOf10() {
        assertFalse(cashWithdrawl.withdrawCashFromATM(15));
    }

    @Test
    public void testWithdrawCashFromATM_SuccessfulWithdrawal() {
        mockBankCurrency.put(10, 100);
        mockBankCurrency.put(20, 50);
        mockBankCurrency.put(50, 100);
        mockBankCurrency.put(100, 100);

        cashWithdrawl.bankCurrency = mockBankCurrency;

        assertTrue(cashWithdrawl.withdrawCashFromATM(100));
    }
}