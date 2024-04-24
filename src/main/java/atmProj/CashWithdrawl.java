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


    private Map<Integer, Integer> bankNotes;

    public CashWithdrawl() {
        bankNotes = new HashMap<>();
        // Initialize the ATM with some bank notes
        bankNotes.put(10, 100);
        bankNotes.put(20, 100);
        bankNotes.put(50, 100);
        bankNotes.put(100, 100);
        // Add more denominations if needed
    }

    public synchronized boolean withdraw(int amount) {
        if (amount % 10 != 0) {
            System.out.println("Amount must be a multiple of 10");
            return false;
        }

        if (!isAmountAvailable(amount)) {
            System.out.println("Insufficient funds in the ATM");
            return false;
        }

        Map<Integer, Integer> withdrawalNotes = calculateWithdrawal(amount);
        if (withdrawalNotes == null) {
            System.out.println("Unable to dispense the amount with available denominations");
            return false;
        }

        updateATM(withdrawalNotes);
        System.out.println("Dispensed amount: " + amount);
        return true;
    }

    private boolean isAmountAvailable(int amount) {
        int totalAvailable = 0;
        for (int denomination : bankNotes.keySet()) {
            totalAvailable += denomination * bankNotes.get(denomination);
        }
        return totalAvailable >= amount;
    }

    private Map<Integer, Integer> calculateWithdrawal(int amount) {
        Map<Integer, Integer> withdrawalNotes = new HashMap<>();
        int remainingAmount = amount;

        for (int denomination : bankNotes.keySet()) {
            int notesNeeded = remainingAmount / denomination;
            if (notesNeeded > 0 && bankNotes.get(denomination) >= notesNeeded) {
                withdrawalNotes.put(denomination, notesNeeded);
                remainingAmount -= denomination * notesNeeded;
            }
        }

        if (remainingAmount == 0) {
            return withdrawalNotes;
        } else {
            return null;
        }
    }

    private void updateATM(Map<Integer, Integer> withdrawalNotes) {
        for (int denomination : withdrawalNotes.keySet()) {
            bankNotes.put(denomination, bankNotes.get(denomination) - withdrawalNotes.get(denomination));
        }
    }
    public static void main(String[] args) {
    	CashWithdrawl atm = new CashWithdrawl();

        // Example usage: Withdrawal of $50
        atm.withdraw(50);
    }

    // Unit tests can be added here
}

