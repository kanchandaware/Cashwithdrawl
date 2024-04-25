/**
 * 
 */
package atmProj;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

/**
 * 
 */
public class CashWithdrawl 
{
	/**
	 * 
	 */
	public Map<Integer, Integer> bankCurrency;
	
	/**
	 * 
	 */
	public CashWithdrawl() 
	{
		bankCurrency = new HashMap<>();
		bankCurrency.put(10, 100);
        bankCurrency.put(20, 50);
        bankCurrency.put(50, 100);
        bankCurrency.put(100, 100);
    }
	
	/**
	 * 
	 * @param amount
	 * @return
	 */
	public synchronized boolean withdrawCashFromATM(int amount) 
	{
		if (amount % 10 != 0) 
		{
			System.out.println("Amount must be a multiple of 10");
			return false;
		}

		if (!checkIfAmountAvailable(amount)) 
		{
			System.out.println("Insufficient funds for cash withdrawal in the ATM");
			return false;
		}

		Map<Integer, Integer> withdrawalNotes = calculateWithdrawal(amount);
		if (withdrawalNotes == null) 
		{
			System.out.println("Unable to dispense the amount with available denominations");
			return false;
		}

		updateDenominationInATM(withdrawalNotes);
		System.out.println("Dispensed amount: " + amount);
		displayRemainingNotes();
		return true;
	}
	/**
	 * 
	 * @param denomination
	 * @param quantity
	 * @return
	 */
	public synchronized boolean addCashInATM(int denomination, int quantity)
	{
		if (denomination <= 0 || quantity <= 0) 
		{
			System.out.println("Invalid input for adding cash to the ATM");
			return false;
		}
		bankCurrency.put(denomination, bankCurrency.getOrDefault(denomination, 0) + quantity);
		System.out.println("Added " + quantity + " notes of denomination " + denomination + " to the ATM");
		return true;
	}
	/**
	 * 
	 * @return
	 */
	public synchronized int checkBalance() 
	{
		int totalBalance = 0;
		for (int denomination : bankCurrency.keySet()) 
		{
			totalBalance = totalBalance+denomination * bankCurrency.get(denomination);
		}
		return totalBalance;
	}
	/**
	 * 
	 * @param amount
	 * @return
	 */
	private boolean checkIfAmountAvailable(int amount) 
	{
		int totalAvailable = 0;
		for (int denomination : bankCurrency.keySet()) 
		{
			totalAvailable = totalAvailable+denomination * bankCurrency.get(denomination);
		}
		return totalAvailable >= amount;
	}
	/**
	 * 
	 * @param amount
	 * @return
	 */
	private Map<Integer, Integer> calculateWithdrawal(int amount) 
	{
		Map<Integer, Integer> withdrawalNotes = new HashMap<>();
		int remainingAmount = amount;

		for (int denomination : bankCurrency.keySet()) 
		{
			int notesNeeded = remainingAmount / denomination;
			if (notesNeeded > 0 && bankCurrency.get(denomination) >= notesNeeded) 
			{
				withdrawalNotes.put(denomination, notesNeeded);
				remainingAmount = remainingAmount-denomination * notesNeeded;
			}
		}
		if (remainingAmount == 0) 
		{
			return withdrawalNotes;
		} 
		else 
		{
			return null;
		}
	}
	/**
	 * 
	 * @param withdrawalNotes
	 */
	private void updateDenominationInATM(Map<Integer, Integer> withdrawalNotes) 
	{
		for (int denomination : withdrawalNotes.keySet()) 
		{
			bankCurrency.put(denomination, bankCurrency.get(denomination) - withdrawalNotes.get(denomination));
		}
	}
	/**
	 * 
	 */
	private void displayRemainingNotes() 
	{
		System.out.println("Remaining notes in ATM present right now are:");
		for (int denomination : bankCurrency.keySet()) 
		{
			System.out.println(bankCurrency.get(denomination) + " notes of denomination " + denomination);
		}
	}

	public static void main(String[] args) 
	{
		CashWithdrawl atm = new CashWithdrawl();
		Scanner scanner = new Scanner(System.in);

		System.out.println("Welcome to the ATM of Bank");

		System.out.println("Enter the amount to withdraw (must be a multiple of 10):");
		int withdrawAmount = scanner.nextInt();
		if (withdrawAmount <= 0) 
		{
			System.out.println("Invalid withdrawal amount. Please enter a positive amount.");
		}

		if (!atm.withdrawCashFromATM(withdrawAmount)) 
		{
			System.out.println("Transaction failed. Please try again later.");
		}

		System.out.println("Current balances in ATM present right now is" + atm.checkBalance());
		scanner.close();
	}
}
