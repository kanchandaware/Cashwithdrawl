/**
 * 
 */
package atmProj;
import java.util.Map;
import java.util.HashMap;


/**
 * 
 */
public class CashWithdrawl {


    private Map<Integer, Integer> bankCurrency;

    public CashWithdrawl() {
    	bankCurrency = new HashMap<>();
    	bankCurrency.put(10, 100);
    	bankCurrency.put(20, 50);
    	bankCurrency.put(50, 100);
    	bankCurrency.put(100, 100);
    }

    public synchronized boolean withdrawCashFromATM(int amount)
    {
        if (amount % 10 != 0) {
            System.out.println("Amount must be a multiple of 10");
            return false;
        }

        if (!checkIfAmountAvailable(amount)) {
            System.out.println("Insufficient funds for cash withdrawl in the ATM");
            return false;
        }

        Map<Integer, Integer> withdrawalNotes = calculateWithdrawal(amount);
        if (withdrawalNotes == null) {
            System.out.println("Unable to dispense the amount with available denominations");
            return false;
        }

        updateDenominationInAtm(withdrawalNotes);
        System.out.println("Dispensed amount: " + amount);
        return true;
    }

    private boolean checkIfAmountAvailable(int amount) {
        int totalAvailable = 0;
        for (int denomination : bankCurrency.keySet()) {
            totalAvailable = totalAvailable+denomination * bankCurrency.get(denomination);
        }
        return totalAvailable >= amount;
    }

    private Map<Integer, Integer> calculateWithdrawal(int amount) {
        Map<Integer, Integer> withdrawalNotes = new HashMap<>();
        int remainingAmount = amount;

        for (int denomination : bankCurrency.keySet()) {
            int notesNeeded = remainingAmount / denomination;
            if (notesNeeded > 0 && bankCurrency.get(denomination) >= notesNeeded) {
                withdrawalNotes.put(denomination, notesNeeded);
                remainingAmount =remainingAmount- denomination * notesNeeded;
            }
        }

        if (remainingAmount == 0) {
            return withdrawalNotes;
        } else {
            return null;
        }
    }

    private void updateDenominationInAtm(Map<Integer, Integer> withdrawalNotes) {
        for (int denomination : withdrawalNotes.keySet()) {
        	bankCurrency.put(denomination, bankCurrency.get(denomination) - withdrawalNotes.get(denomination));
        }
    }
    public static void main(String[] args) {
    	CashWithdrawl cashWithdrawl = new CashWithdrawl();
    	cashWithdrawl.withdrawCashFromATM(5000);
    }

}

