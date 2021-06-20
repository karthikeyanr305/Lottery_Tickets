import java.util.ArrayList;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Karthikeyan Rajagopal
 * Student Number - 1246255
 * Username - rajagopalk@student.unimelb.edu.au
 * LMS username: rajagopalk
 * RandomPickCompetition handles all the calculation and processing work and provides the output.
 * Uses functions from Parent class and member functions.
 * Uses entryList(ArrayList of AutoNumbersEntry objects) and creates new object for every entryId.
 *
 */
public class RandomPickCompetition extends Competition implements Serializable{
    private final int FIRST_PRIZE = 50000;
    private final int SECOND_PRIZE = 5000;
    private final int THIRD_PRIZE = 1000;
    private final int[] prizes = {FIRST_PRIZE, SECOND_PRIZE, THIRD_PRIZE};
    private String memberId;
	private String billId;
	private String memberName;
	private float amount;
	private int entries;
	transient Scanner keyboard = new Scanner(System.in);
	private char mode;
	private int totalPrize = 0;
	private int [] winnerList; //Stores EntryID of all winners
	private ArrayList<AutoNumbersEntry> entryList = new ArrayList<AutoNumbersEntry>();
	private ArrayList<Bill> bill = new ArrayList<Bill>();
	
    private final int MAX_WINNING_ENTRIES = 3;
    
    /**
     * Initializes the RandomPickCompetition object
     * @param keyboard - Scanner Object
     * @param mode - test or normal mode 
     */
    public RandomPickCompetition(Scanner keyboard, char mode) {
    	super(keyboard, mode);
    	this.keyboard = keyboard;	
    	this.entries = 0;
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
     */
    public void addEntries() {   	
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
    		System.out.printf("This bill ($%.1f) is eligible for %d entries.%n", amount, eligibleEntries);
       	
        	// for loop to store entry numbers
        	for(int i = 0; i < eligibleEntries; i++) {
        		entries++;
        		//entryList.add(new Entry(keyboard, memberId, billId, entries));
        		entryList.add(new AutoNumbersEntry(keyboard, memberId, billId, memberName, entries));
        	}
        	
        	updateBill(billIndex);
        	setEntrySize(entryList.size());
        	printEntries(eligibleEntries);
        	setKeyboard(keyboard);
    	}    	  	
    }
    
    /**
     * Print entries given by the user.
     * @param eligibleEntries - Number entries that can be filled with the given amount.
     */
    private void printEntries(int eligibleEntries) {
    	System.out.printf("The following entries have been automatically generated:%n");
    	
    	for(int i = entries-eligibleEntries; i<entries; i++) {
    		System.out.printf("Entry ID: %-6d%n", entryList.get(i).getEntryId());
  		
    	}   	
    }
    
    /**
     * Selects Random Winner
     * Uses nested for-loops to access the entry numbers stored in entryList and
     * compare with the winning entry.
     * Calls prizeMoney in AutoEntry to calculate the prize won according to the matched numbers.
     * Stores all winning entryId in winnerList(irrespective of 1 prize per member constraint).
     * Calls maxPrize (in Competition) to restrict 1 winning entry per member.
     * Calls printWinner to print the winning details.
     */
    public void drawWinners() {
    	int[] distinctWinner;
    	setActiveFlag(0);
    	int [] storeIndex = new int[MAX_WINNING_ENTRIES];
    	winnerList = new int[MAX_WINNING_ENTRIES];
        Random randomGenerator = null;
        
        if (this.getIsTestingMode()) {
            randomGenerator = new Random(this.getId());
        } else {
            randomGenerator = new Random();
        }
        int winningEntryCount = 0;
        while (winningEntryCount < MAX_WINNING_ENTRIES) {
        	int contains = 0;
            int winningEntryIndex = randomGenerator.nextInt(entryList.size());

            for(int i=0; i<winningEntryCount; i++) {
            	if(winningEntryCount == 0) {
            		break;
            	}
            	else if(storeIndex[i] == winningEntryIndex) {
            		contains = 1;
            		break;
            	}
            	else {
            		continue;
            	}
            }
            
            if(contains == 1) {
            	continue;
            }
            else {
            	storeIndex[winningEntryCount] = winningEntryIndex;
            }
	
            Entry winningEntry = entryList.get(winningEntryIndex);		    
            winnerList[winningEntryCount] = winningEntry.getEntryId();
          
            if (winningEntry.getPrize() == 0) {
                int currentPrize = prizes[winningEntryCount];
                winningEntry.setPrize(currentPrize);
                winningEntryCount++;
            }     	
        }
		
        Arrays.sort(winnerList);
        distinctWinner = maxPrize(winnerList, entryList); 
        setWinnerList(distinctWinner);
        printWinners();
    }
    
    
    /**
     * Prints the required details of all the winners
     * Winners are got from the EntryIds stored from winnerList and accessing it from entryList
     * @param winnerList
     */
    private void printWinners() {  	
    	int id = getId();
    	String name = getName();
    	System.out.printf("Competition ID: %d,"
    			+ " Competition Name: %s,"
    			+ " Type: %s%n", id, name, getCompetitionType());
    	System.out.printf("Winning entries:%n");   	
    	
    	for(int index = 0; index < winnerList.length; index++) {    		
    		System.out.printf("Member ID: %s, Member Name: %s, Entry ID: %d, Prize: %-5d%n", 
    				entryList.get(winnerList[index] - 1).getMemberId(),
    				entryList.get(winnerList[index] - 1).getMemberName(),
    				winnerList[index], 
    				entryList.get(winnerList[index] - 1).getPrize());   		
    		totalPrize = totalPrize + entryList.get(winnerList[index] - 1).getPrize();
    		
    	}
    	
    	setWinnerSize(winnerList.length);
    	setTotalPrize(totalPrize);
    }
    
}
