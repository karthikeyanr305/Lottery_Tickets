import java.util.Scanner;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Karthikeyan Rajagopal
 * Student Number - 1246255
 * Username - rajagopalk@student.unimelb.edu.au
 * LMS username: rajagopalk
 * Competition stores all the general information about the competition and contains all the 
 * common functions that can be used by both LuckyNumbersCompetition and RandomPickCompetition.
 * Connects the SimpleCompetition class to all other classes.
 * Uses ArrayList of member and bill to store member and bill file info
 */
public abstract class Competition implements Serializable {
    private String name; //competition name
    private int id; //competition identifier
    private int entries;
    private char mode;
    private int activeFlag; //Flag to indicate whether the competition is active
    transient Scanner keyboard = new Scanner(System.in);
    private int entrySize; // number of entries in the competition
    private int winnerSize; // number of winning entries
    private int totalPrize; 
    private String competitionType;
    private Boolean isTest = false;
    public static final int ENTRY_LENGTH = 7;
    public static final int MIN_PRICE = 50;
    public static final int ID_LENGTH = 6;   
    private ArrayList<Member> member = new ArrayList<Member>();
    private ArrayList<Bill> bill = new ArrayList<Bill>();
    private DataProvider dataProvider;
    
    /**
     * Initializes the competition object
     * @param keyboard - Scanner Object
     * @param mode - test or normal mode 
     */
    public Competition(Scanner keyboard, char mode) {
    	this.keyboard = keyboard;	
    	this.entries = 0;
    	this.mode = mode;
   	
    	if(Character.toLowerCase(mode) == 't') {
    		setIsTestingMode(true);
    	}
    }
    
    void setIsTestingMode(Boolean isTest) {
    	this.isTest = true;
    }
    
    void setId(int id) {
    	this.id = id;
    }
    
    void setName(String name) {
    	this.name = name;
    }
    
    void setActiveFlag(int activeFlag) {
    	this.activeFlag = activeFlag;
    }
    
    
    void setEntrySize(int entrySize) {
    	this.entrySize = entrySize;
    }
    
    void setWinnerSize(int winnerSize) {
    	this.winnerSize = winnerSize;
    }
    
    void setTotalPrize(int totalPrize) {
    	this.totalPrize = totalPrize;
    }
    
    void setMember(ArrayList<Member> member) {
    	this.member = member;
    }
    
    void setBill(ArrayList<Bill> bill) {
    	this.bill = bill;
    }
    
    void setCompetitionType(String competitionType) {
    	this.competitionType = competitionType;
    }
    
    void setKeyboard(Scanner keyboard) {
    	this.keyboard = keyboard;
    }
    
    int getId() {
    	return this.id;
    }
    
    String getName() {
    	return this.name;
    }
    
    int getActiveFlag() {
    	return activeFlag;
    }
    
    int getEntries() {
    	return entries;
    } 
    
    Boolean getIsTestingMode() {
    	return isTest;
    }
 
    String getCompetitionType() {
    	return competitionType;
    }
    
    ArrayList<Bill> getBill(){
    	return bill;
    }
    
    Scanner getKeyboard() {
    	return keyboard;
    }
    
    char getMode() {
    	return mode;
    }
    
	public abstract void addEntries();

    public abstract void drawWinners();
       
    /**
     * Sets up the initial competition details - competition name, id, type.
     * Sets activeFlag to 1. 
     * @param competitionId
     */
    public void setupCompetition(int competitionId, char competitionType,
    		DataProvider dataProvider) {
    	
    	if(Character.toLowerCase(competitionType) == 'r') {
    		setCompetitionType("RandomPickCompetition");
    	}
    	else {
    		setCompetitionType("LuckyNumbersCompetition");
    	}
    	System.out.printf("Competition name: %n");
    	name = keyboard.next();
    	id = competitionId;
    	System.out.printf("A new competition has been created!%n");
    	System.out.printf("Competition ID: %d, Competition Name: %s, Type: %s%n",
    			id, name, getCompetitionType());
    	this.dataProvider = dataProvider;
    	setId(id);
    	setName(name);
    	setActiveFlag(1);
    	setMember(dataProvider.getMember());
    	setBill(dataProvider.getBill());
    	setKeyboard(keyboard);
    }
    
    /**
     * Gets the correct format of MemberId/BillId 
     * Uses try and catch to identify exceptions and redirect accordingly.
     * Calls validateBill() to find whether the bill is valid enough to proceed. 
     * @return billId - stores id of bill
     */
    protected String idFormat() {
    	boolean isError = true;
    	String billId = "0";
    	keyboard.nextLine();
    	
    	while(isError) {
    		System.out.printf("Bill ID: %n");
    		billId = keyboard.nextLine();

    		while(billId == null) {
    			billId = keyboard.nextLine();
    		}
    		try {
    			Double.parseDouble(billId);
    			isError = false;
    			if(billId.length() != ID_LENGTH) {
        			System.out.printf("Invalid bill id! It must be a 6-digit number. "
            				+ "Please try again.%n");       			
        			isError = true;
        			continue;
        		}
    			if(!validateBill(billId, "bill")) {
    				System.out.printf("This bill does not exist. "
            				+ "Please try again.%n");
        			isError = true;
        			continue;
    			}
    			if(!validateBill(billId, "member")) {
    				System.out.printf("This bill has no member id. "
            				+ "Please try again.%n");
        			isError = true;
        			continue;
    			}
    			if(validateBill(billId, "used")) {
    				System.out.printf("This bill has already been used for a competition. "
            				+ "Please try again.%n");
        			isError = true;
        			continue;
    			}
    		}
    		catch(NumberFormatException e) {
    			System.out.printf("Invalid bill id! It must be a 6-digit number. "
        				+ "Please try again.%n");
    			isError = true;
    			continue;
    		}
    	}
    	return billId;   	
    }
    
    /**
     * Runs for-loop through bill(Array List of bill objects) to validate.
     * @param billId
     * @param part - To check the part of the bill is valid (eg: memberId, used)
     * @return billIndex - Boolean to say whether the bill is valid
     */
    private boolean validateBill(String billId, String part) {
    	boolean validate = false;
    	
    	for(int i=0; i < bill.size(); i++) {
    		if((bill.get(i).getBillId()).equals(billId)) {
    			if(part.equals("bill")) {
					validate = true;
					break;
				}
    			for(int j=0; j<member.size(); j++) {
    				if((bill.get(i).getMemberId()).equals(member.get(j).getMemberId())) {
    					if(part.equals("member")) {
    						validate = true;
        					break;
    					}
    					else if(part.equals("used")) {
    						validate = bill.get(i).getUsed();
    						break;
    					}  					
    				}
    			}
    		}
    	}
    	return validate;
    }
    
    /**
     * Gives the index of the bill object for given billId
     * @param billId
     * @return billIndex - Index of the Bill object in the ArrayList bill 
     */
    protected int billDetails(String billId) {
    	int billIndex = 0;
    	for(billIndex = 0; billIndex< bill.size(); billIndex++) {
    		if((bill.get(billIndex).getBillId()).equals(billId)) {
    			break;
    		}
    	}
    	return billIndex;
    }
    
    /**
     * Updates the 'used' column of bill object in the ArrayList bill given a billIndex
     * @param billIndex
     */
    protected void updateBill(int billIndex) {
    	bill.get(billIndex).setUsed(true);
    }
       
    /**
     * Gives the memberName for a given memberId.
     * Runs for loop through the ArrayList member.
     * @param memberId
     * @return memberName 
     */
    protected String memberDetailsName(String memberId) {
    	int memberIndex = 0;
    	for(memberIndex = 0; memberIndex<member.size(); memberIndex++) {
    		if(member.get(memberIndex).getMemberId().equals(memberId)) {
    			break;
    		}
    	}
    	return member.get(memberIndex).getMemberName();
    }
    
    /**
     * Calculates 1 winning prize per member and updates
     *  the winnerList to the correct list of entrIds
     * Uses nested for-loop with multiple if conditions to satisfy biggest prize per member and
     * smallest entryId for equal prizes constraint.
     * @param winnerList - Contains all the winning entries(irrespective of 1 prize per member)
     * @param entryList - ArrayList of AutoNumbersEntry objects which stores all the info of entryId
     * @return distinctWinner - stores the final entryIds of winners(follows 1 prize per member)
     */
    protected int[] maxPrize( int[] winnerList, ArrayList<AutoNumbersEntry> entryList) {
    	ArrayList<String> tempMemberList = new ArrayList<String>();
    	ArrayList<Integer> tempEntryList = new ArrayList<Integer>();
    	String[] memberList = new String[winnerList.length];
    	String[] distinctMember; //List of distinct members among all the winning entries
    	int[] distinctWinner; //List of EntryIds after restrict 1 prize per member
    	
    	for(int i = 0; i< winnerList.length; i++) {
    		memberList[i] = entryList.get(winnerList[i] - 1).getMemberId();
    	}
    	Arrays.sort(memberList);   	
    	tempMemberList.add(memberList[0]);
    	for(int j = 1; j < memberList.length; j++) {
    		if(!(tempMemberList.get(tempMemberList.size()-1).equals(memberList[j]))) {
    			tempMemberList.add(memberList[j]);
    		}
    	}    	
    	distinctMember = new String[tempMemberList.size()];
    	
    	for(int k = 0; k<tempMemberList.size(); k++) {
    		distinctMember[k] = tempMemberList.get(k);
    	}
    	
    	for(int m = 0; m < distinctMember.length; m++) {
    		int maxEntry = 0;
    		int maxPrize = 0;    		
    		
    		for(int n = 0; n < winnerList.length; n++) {
    			if(distinctMember[m].equals(entryList.get(winnerList[n]-1).getMemberId())) {
    				if(entryList.get(winnerList[n]-1).getPrize() > maxPrize) {
    					maxPrize = entryList.get(winnerList[n]-1).getPrize();
    					maxEntry = entryList.get(winnerList[n]-1).getEntryId();
    				}
    				else if(entryList.get(winnerList[n]-1).getPrize() == maxPrize) {
    					maxEntry = Math.min(maxEntry, entryList.get(winnerList[n]-1)
    							.getEntryId());
    				}
    			}			
    		}
    		tempEntryList.add(maxEntry);
    	}    	
    	distinctWinner = new int[tempEntryList.size()];
    	
    	for(int p = 0; p < tempEntryList.size(); p++) {
    		distinctWinner[p] = tempEntryList.get(p);
    	}    	
    	Arrays.sort(distinctWinner);    	
    	
    	return distinctWinner;
    }
    
    /**
     * Prints report of the current competition
     */
    public void report() {
    	if(getActiveFlag() == 0) {
    		System.out.printf("Competition ID: %d, name: %s, active: no%n", id, name);
    		System.out.printf("Number of entries: %d%n", entrySize);
    		System.out.printf("Number of winning entries: %d%n", winnerSize);
    		System.out.printf("Total awarded prizes: %d%n", totalPrize);		
    	}
    	else {
    		System.out.printf("Competition ID: %d, name: %s, active: yes%n", id, name);
    		System.out.printf("Number of entries: %d%n", entrySize);
    	}   	
    }
    
}
