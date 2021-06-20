import java.util.ArrayList;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Karthikeyan Rajagopal
 * Student Number - 1246255
 * Username - rajagopalk@student.unimelb.edu.au
 * LMS username: rajagopalk
 * LuckyNumbersCompetition handles all the calculation and processing work and provides the output.
 * Uses functions from Parent class and member functions.
 * Uses entryList(ArrayList of AutoNumbersEntry objects) and creates new object for every entryId.
 *
 */
public class LuckyNumbersCompetition extends Competition implements Serializable{
	
	private String memberId;
	private String billId;
	private String memberName;
	private int entries;
	private float amount;
	transient Scanner keyboard = new Scanner(System.in);
	private ArrayList<AutoNumbersEntry> entryList = new ArrayList<AutoNumbersEntry>();
    public static final int ENTRY_LENGTH = 7;
    public static final int MIN_PRICE = 50;
    public static final int ID_LENGTH = 6;    
    private int [] winnerList; //Stores EntryID of all winners
    private char mode;
    private int totalPrize = 0;
    
    private ArrayList<Bill> bill = new ArrayList<Bill>();
	
    /**
     * Initializes the LuckyNumbersCompetition object
     * @param keyboard - Scanner Object
     * @param mode - test or normal mode 
     */
    public LuckyNumbersCompetition(Scanner keyboard, char mode) {
    	super(keyboard, mode);
    	this.keyboard = keyboard;	
    	this.entries = 0;
    	//this.existId = existId;
    	this.mode = mode;
				
	}
      
    void setWinnerList(int[] winnerList) {
    	this.winnerList = winnerList;
    }

    int getEntries() {
    	return entries;
    }
    
    /**
     * Details of membersId, memberName, billId, entryId stored in AutoNumbersEntry object.
     * EntryIds are stored in entryList.
     * Calls idFormat() in Competitions to get the right format of billId from the user.
     * Gets the index of the bill object stored in bill(ArrayList of Bill objects) by calling
     * billDetails() (in Competition).
     * Gets memberName by calling memberDetailsName() (in Competition).
     * ManualFlags are set to identify auto entries and manual entries
     */
    public void addEntries() {
    	
    	if(keyboard == null) {
    		keyboard = getKeyboard();
    	}  	
    	bill = getBill();
    	billId = idFormat();
    	int billIndex = billDetails(billId);
    	
    	Bill billObject = bill.get(billIndex); 
    	memberId = billObject.getMemberId();
    	memberName = memberDetailsName(memberId);
    	amount = billObject.getAmount();
    	
    	if(amount < MIN_PRICE) {
    		System.out.printf("This bill is not eligible for an entry. "
    				+ "The total amount is smaller than $50.0%n");
    	}
    	else {    		
    		int eligibleEntries = (int) Math.floor(amount/50);
        	int manualEntries;
        	int autoEntries;
        	
        	System.out.printf("This bill ($%.1f) is eligible for %d entries. "
        			+ "How many manual entries did the customer fill up?: %n", amount, eligibleEntries);
        	manualEntries = keyboard.nextInt();
        	
        	while(manualEntries > eligibleEntries) {
        		System.out.printf("The number must be in the range from 0 to %d. Please try again.%n", eligibleEntries);    
        		manualEntries = keyboard.nextInt();
        	}
        	autoEntries = eligibleEntries - manualEntries;
        	
        	// for loop to store manual entry numbers
        	for(int i = 0; i < manualEntries; i++) {
        		entries++;
        		entryList.add(new AutoNumbersEntry(keyboard, memberId, billId, memberName, entries));
            	entryList.get(entryList.size() - 1).inputNumbers();
        	}
        	
        	// for loop to store automatic entry numbers
        	for(int j = 0; j < autoEntries; j++) {
        		entries++;
        		entryList.add(new AutoNumbersEntry(keyboard, memberId, billId, memberName, entries));
        		if(Character.toLowerCase(mode) == 't') {
        			entryList.get(entryList.size() - 1).createNumbers(entries-1);
        		}
        		else {
        			entryList.get(entryList.size() - 1).createNumbers();
        		}            	
            	entryList.get(entryList.size() - 1).setManualFlag();
        	}       
        	updateBill(billIndex);
        	setEntrySize(entryList.size());
        	printEntries(eligibleEntries);
    	}    	  	
    }
    
    /**
     * Print entries given by the user.
     * Prints the automatic entries as well.
     * @param eligibleEntries - Number entries that can be filled with the given amount.
     */
    private void printEntries(int eligibleEntries) {
    	System.out.printf("The following entries have been added:%n");
    	
    	for(int i = entries-eligibleEntries; i<entries; i++) {
    		System.out.printf("Entry ID: %-7d", entryList.get(i).getEntryId());
    		System.out.printf("Numbers:");
    		
    		for(int numberIndex = 0; numberIndex < ENTRY_LENGTH; numberIndex++) {
    			System.out.printf(" %2d", entryList.get(i).getNumbers()[numberIndex]);
    		}
    		if(entryList.get(i).getManualFlag() == 0) {
    			System.out.printf(" [Auto]%n");
    		}
    		else {
    			System.out.printf("%n");
    		}   		
    	}   	
    }
    
    
    /**
     * Selects Lucky Winner
     * Uses nested for-loops to access the entry numbers stored in entryList and
     * compare with the winning entry.
     * Calls prizeMoney in AutoNumbersEntry to calculate the prize according to matched numbers.
     * Stores all winning entryId in winnerList(irrespective of 1 prize per member constraint).
     * Calls maxPrize (in Competition) to restrict 1 winning entry per member.
     * Calls printWinner to print the winning details.
     */
    public void drawWinners() {    	
    	int[] distinctWinner;
    	int id = getId();
    	String name = getName();
    	setActiveFlag(0);
    	entries++;
    	int winnerCount;
    	ArrayList<Integer> winnerEntry = new ArrayList<Integer>();
    	AutoNumbersEntry luckyWinner = new AutoNumbersEntry(entries);
    	
    	System.out.printf("Competition ID: %d,"
    			+ " Competition Name: %s,"
    			+ " Type: %s%n", id, name, getCompetitionType());
    	System.out.printf("Lucky Numbers:");
    	   	
    	if(Character.toLowerCase(mode) == 't') {
    		luckyWinner.createNumbers(id);
    	}
    	else {
    		luckyWinner.createNumbers();
    	}
    	int [] winner = luckyWinner.getNumbers();
    	
    	for(int index=0; index<winner.length; index++) {
    		System.out.printf(" %2d",winner[index]);
    	}
    	System.out.printf(" [Auto]%n");
    	
    	for(int listIndex=0; listIndex < entryList.size(); listIndex++) {
    		int numberMatch = 0;   		
    		
    		for(int winnerIndex=0; winnerIndex < winner.length; winnerIndex++) {
    			
    			for(int index = 0; index < entryList.get(listIndex).getNumbers().length; index++) {
    				if(winner[winnerIndex]==entryList.get(listIndex).getNumbers()[index]) {
    					numberMatch++;
    				}
    			}
    		}
    		if(entryList.get(listIndex).getManualFlag() == 0) {
    			entryList.get(listIndex).setAutoNumberMatch(numberMatch);
    		}
    		else {
    			entryList.get(listIndex).setNumberMatch(numberMatch);
    		}
    		entryList.get(listIndex).prizeMoney();
    		if(numberMatch>=2) {
    			winnerEntry.add(entryList.get(listIndex).getEntryId());
    		}
    	}
    	winnerCount = winnerEntry.size();
    	winnerList = new int[winnerCount];
    	
    	for(int i = 0; i < winnerCount; i++) {
    		winnerList[i] = winnerEntry.get(i);
    	}
    	Arrays.sort(winnerList);   	
    	   
    	distinctWinner = maxPrize(winnerList, entryList);
    	setWinnerList(distinctWinner);
    	printWinners();  	
    	setKeyboard(keyboard);
    }
    
 
    /**
     * Prints the required details of all the winners
     * Winners are got from the EntryIds stored from winnerList and accessing it from entryList
     * @param winnerList
     */
    private void printWinners() {  	
    	
    	System.out.printf("Winning entries:%n");   	
    	
    	for(int index = 0; index < winnerList.length; index++) {    		
    		System.out.printf("Member ID: %s, Member Name: %s," 
    				+ " Prize: %-5d%n",
    				entryList.get(winnerList[index] - 1).getMemberId(),
    				entryList.get(winnerList[index] - 1).getMemberName(),    				
    				entryList.get(winnerList[index] - 1).getPrize());   		
    		System.out.printf("--> Entry ID: %d, "   				
    				+ "Numbers:",
    				winnerList[index]);
    		totalPrize = totalPrize + entryList.get(winnerList[index] - 1).getPrize();
    		
    		for(int i = 0; i < ENTRY_LENGTH; i++) {
    			System.out.printf(" %2d", entryList.get(winnerList[index] - 1).getNumbers()[i]);
    		}
    		if(entryList.get(winnerList[index] - 1).getManualFlag() == 0) {
    			System.out.printf(" [Auto]%n");
    		}
    		else {
    			System.out.printf("%n");
    		}
    	}
    	
    	setWinnerSize(winnerList.length);
    	setTotalPrize(totalPrize);
    }
    
}
