import java.util.Arrays;
import java.io.Serializable;
import java.util.Scanner;

/**
 * @author Karthikeyan Rajagopal
 * Student Number - 1246255
 * Username - rajagopalk@student.unimelb.edu.au
 * LMS username: rajagopalk
 * NumbersEntry handles all entryId related input, output and storing processes.
 * Extends from Entry uses the base class variables.
 * Takes in the manual entries and returns them
 */
public class NumbersEntry extends Entry implements Serializable{
    private int[] numbers;
    public static final int ENTRY_LENGTH = 7;
    public static final int ENTRY_MAX = 35;
    protected int numberMatch; /* Number of Matched entry numbers with the winning entry.
     Used in AutoNumbersEntry as well.*/
    transient Scanner keyboard;
    
    /**
     * Initializes the NumbersEntry object
     * @param keyboard - Scanner object
     * @param memberId
     * @param billId
     * @param memberName
     * @param entryId
     */
    public NumbersEntry(Scanner keyboard, String memberId, String billId, String memberName, int entryId) {
		super(keyboard, memberId, billId, memberName, entryId);
		this.keyboard = super.keyboard;
	}
    
    /**
     * Initializes the NumbersEntry object
     * @param entryId
     */
    public NumbersEntry(int entryId) {
    	super(entryId);
    }
    
    void setNumbers(int[] numbers) {
    	this.numbers = numbers;
    }
    
    
    int[] getNumbers() {
    	return numbers;
    }
    
    void setNumberMatch(int numberMatch) {
    	this.numberMatch = numberMatch;
    }
    
    int getNumberMatch() {
    	return numberMatch;
    }
    
    /**
     * Checks whether the entered numbers are distinct are not.
     * @param numbers
     * @return boolean
     */
    public boolean distinctArray(int[] numbers) {    	    	
    	for(int i = 0; i<numbers.length-1; i++) {
    		for(int j = i+1; j<numbers.length; j++) {
    			if(numbers[i] == numbers[j]) {
    				return false;
    			}
    		}
    	}
    	return true;
    }
    
    /**
     * Uses while() and if() to store the right format of entry numbers.
     * Uses try and catch to find any exceptions to the user input and redirect accordingly
     */
    public void inputNumbers() {   	
    	numbers = new int[ENTRY_LENGTH];
    	String inputString = keyboard.nextLine();
    	int numberLength = 0;
    	String[] numberStringArray;	
    	boolean distinctNumbers = false;
    	boolean callDistinct;
    	
    	while(!(numberLength == ENTRY_LENGTH && distinctNumbers)) {    		
    		System.out.printf("Please enter 7 different numbers (from the range 1 to 35)"
        			+ " separated by whitespace.%n");
        	inputString = keyboard.nextLine();
        	numberLength = inputString.split(" ").length;
        	numberStringArray = inputString.split(" ");   
        	
    		if(numberLength < ENTRY_LENGTH) {
    			System.out.printf("Invalid input! Fewer than 7 numbers are provided. "
    					+ "Please try again!%n");
    		}
    		else if(numberLength > ENTRY_LENGTH) {
    			System.out.printf("Invalid input! More than 7 numbers are provided. "
    					+ "Please try again!%n");
    		}
    		else if(numberLength == ENTRY_LENGTH){
    			callDistinct = true;
    			for(int i=0; i < ENTRY_LENGTH; i++) {
    				try {
    					numbers[i] = Integer.parseInt(numberStringArray[i]);   					
    				}
    				catch(NumberFormatException e) {
    					System.out.printf("Invalid input! Numbers are expected. Please try again!%n");
    					callDistinct = false;
    					break;
    				}   				
    			}
    			if(callDistinct) {
    				distinctNumbers = distinctArray(numbers);
    			}
    			else {
    				distinctNumbers = false;
    				continue;
    			}
    			
    			if(!(distinctNumbers)) {
    				System.out.printf("Invalid input! All numbers must be different!%n");
    			}
    			else {
    				boolean greater = false;
    				for(int i=0; i < ENTRY_LENGTH; i++) {
    					if(numbers[i] > 35) {
    						greater = true;
    						break;
    					}
    				}
    				if(greater) {
    					System.out.printf("Invalid input! All numbers must be in the range from 1 to 35!%n");
    					distinctNumbers = false;
    				}        					   					        				
    			}
    		} 		  		
    	}
    	Arrays.sort(numbers);
    	setNumbers(numbers);
    }
    
}
