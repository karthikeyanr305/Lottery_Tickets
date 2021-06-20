import java.util.ArrayList;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Karthikeyan Rajagopal
 * Student Number - 1246255
 * Username - rajagopalk@student.unimelb.edu.au
 * LMS username: rajagopalk
 * Takes care of the all auto entry input, output and storing process.
 * Extends from NumbersEntry uses the base class variables.
 * Calculates and returns prize won.
 */
public class AutoNumbersEntry extends NumbersEntry implements Serializable{
    private final int NUMBER_COUNT = 7;
    private final int MAX_NUMBER = 35;
    private final int PRIZE1 = 50000;
    private final int PRIZE2 = 5000;
    private final int PRIZE3 = 1000;
    private final int PRIZE4 = 500;
    private final int PRIZE5 = 100;
    private final int PRIZE6 = 50;
    private int[] numbers;
	transient Scanner keyboard;
	private int autoNumberMatch; //Number of matched entry numbers with the winning entry.
	private int prize; //Prize amount won
	
	/**
	 * Initializes the AutoNumbersEntry object
	 * @param keyboard
	 * @param memberId
	 * @param billId
	 * @param memberName
	 * @param entryIdComp
	 */
	public AutoNumbersEntry(Scanner keyboard, String memberId, String billId, String memberName, int entryIdComp) {
		super(keyboard, memberId, billId, memberName, entryIdComp);
		autoNumberMatch = 0;
		this.prize = 0;
	}
	
	/**
	 * Initializes the AutoNumbersEntry object
	 * @param entryId
	 */
    public AutoNumbersEntry(int entryId) {
    	super(entryId);
    }
    
    void setManualFlag() {
    	manualFlag = 0;
    }
    
    void setNumbers(int[] tempNumbers) {
    	this.numbers = tempNumbers;
    }
    
    void setAutoNumberMatch(int autoNumberMatch) {
    	this.autoNumberMatch = autoNumberMatch;
    }
    
    int[] getNumbers() {
    	return numbers;
    }
    
    int getAutoNumberMatch() {
    	return autoNumberMatch;
    }
    
    int getManualFlag() {
    	return manualFlag;
    }
		
    public int[] createNumbers (int seed) {
        ArrayList<Integer> validList = new ArrayList<Integer>();
	int[] tempNumbers = new int[NUMBER_COUNT];
        for (int i = 1; i <= MAX_NUMBER; i++) {
    	    validList.add(i);
        }
        Collections.shuffle(validList, new Random(seed));
        for (int i = 0; i < NUMBER_COUNT; i++) {
    	    tempNumbers[i] = validList.get(i);
        }
        Arrays.sort(tempNumbers);
        setNumbers(tempNumbers);
        return tempNumbers;
    }    
    
	/**
	 * Creates list of random numbers with seed value for N mode
	 * @return tempNumbers - Array of Random numbers
	 */
	public int[] createNumbers () {
        ArrayList<Integer> validList = new ArrayList<Integer>();
	int[] tempNumbers = new int[ENTRY_LENGTH];
        for (int i = 1; i <= ENTRY_MAX; i++) {
    	    validList.add(i);
        }
        Collections.shuffle(validList, new Random());
        for (int i = 0; i < ENTRY_LENGTH; i++) {
    	    tempNumbers[i] = validList.get(i);
        }
        Arrays.sort(tempNumbers);
        setNumbers(tempNumbers);
        return tempNumbers;
    }
	
	/**
	 * Calculates the prize money won according to matching entries.
	 * Sets the appropriate prize value.
	 * The prizes are given according to the details given in the problem statement.
	 */
	public void prizeMoney() {
    	if(autoNumberMatch == 2 || numberMatch == 2) {
    		prize = PRIZE6;
    	}
    	else if(autoNumberMatch == 3 || numberMatch == 3) {
    		prize = PRIZE5;
    	}
    	else if(autoNumberMatch == 4 || numberMatch == 4) {
    		prize = PRIZE4;
    	}
    	else if(autoNumberMatch == 5 || numberMatch == 5) {
    		prize = PRIZE3;
    	}
    	else if(autoNumberMatch == 6 || numberMatch == 6) {
    		prize = PRIZE2;
    	}
    	else if(autoNumberMatch == 7 || numberMatch == 7) {
    		prize = PRIZE1;
    	}
    	setPrize(prize);
    }
	
}
