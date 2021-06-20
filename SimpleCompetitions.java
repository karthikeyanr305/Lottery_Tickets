import java.util.ArrayList;
import java.io.EOFException ;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;

/**
 * @author Karthikeyan Rajagopal
 * Student Number - 1246255
 * Username - rajagopalk@student.unimelb.edu.au
 * LMS username: rajagopalk
 * SimpleCompetitions handles the main programming function.
 * Maneuvers between the menu options.
 * Interacts with member functions and Competition class to carry out the process.
 */
public class SimpleCompetitions {
	
	ArrayList<Competition> newCompetition = new ArrayList<Competition>();
	
    public Competition addNewCompetition(Scanner keyboard, char competitionType, char mode) {
    	
    	Competition newCompetition = null;
						
    	if(Character.toLowerCase(competitionType) == 'r') {
    		RandomPickCompetition newCompetition1 = 
    				new RandomPickCompetition(keyboard, mode);
    		newCompetition = newCompetition1;
    	}
    	else if(Character.toLowerCase(competitionType) == 'l') {
    		LuckyNumbersCompetition newCompetition2 = 
    				new LuckyNumbersCompetition(keyboard, mode);
    		newCompetition = newCompetition2;
    	}
        
        return newCompetition;
    }

    /**
     * Prints the summary report.
     * Iteratively calls report function of the Competition object
     * stored in the newCompetition ArrayList.
     * @param sc - SimpleCompetitions object
     * @param newCompetition - ArrayList of Competition object
     */
    public void report(SimpleCompetitions sc) {    	
    	int totalCompetition = newCompetition.size();
    	int activeCompetition = 0;
    	int completedCompetition;
    	
    	if(newCompetition.get(newCompetition.size()-1).getActiveFlag() == 1) {
    		activeCompetition = 1;
    	}
    	completedCompetition = totalCompetition - activeCompetition;    	
    	System.out.printf("----SUMMARY REPORT----%n");
    	System.out.printf("+Number of completed competitions: %d%n", completedCompetition);
    	System.out.printf("+Number of active competitions: %d%n", activeCompetition);   	    	
    	for(int i = 0; i < newCompetition.size(); i++) {
    		System.out.printf("%n");
    		newCompetition.get(i).report();
    	}   	
    }
    
    /**
	 * Prints Main menu and stores user-input.
	 * Returns the user-input to take necessary action.
	 * @param keyboard - Scanner object
	 * @return userInput
	 */	
	public char mainMenu(Scanner keyboard) {
		System.out.printf("Please select an option. Type 5 to exit.%n");
    	System.out.printf("1. Create a new competition%n");
    	System.out.printf("2. Add new entries%n");
    	System.out.printf("3. Draw winners%n");
    	System.out.printf("4. Get a summary report%n");
    	System.out.printf("5. Exit%n");  	
    	char userInput = keyboard.next().charAt(0);    	
    	
    	return userInput;		
	}
	
	/**
	 * Uses switch() to maneuver between the menu options given by the user.
     * Uses while() within the switch-cases to maintain the loop performing different functions. 
     * Iteratively calls functions from Competition class to create competitions,
     * add entries, get report, draw winners, etc.
     * Uses ArrayList of Competition object to store individual competition details separately.
     * Calls itself(competitionSettings) within the switch statement to maintain program flow.
	 * @param keyboard Scanner object
	 * @param sc SimpleCompetitions object
	 * @param newCompetition - ArrayList of Competition object
	 * @param existId - Total number of entries currently existing. 
	 * @param mode - Stores program mode (Testing,Normal)
	 */
	public void competitionSettings(Scanner keyboard, SimpleCompetitions sc,
			 char mode, DataProvider dataProvider) {		
		
		char userInput;
        char moreEntries = 'y';
        char competitionType = 'l';
        char saveFile = 'n';
        String fileName;      
        
        userInput = sc.mainMenu(keyboard);
        
        switch(userInput) {
        case '1':       	
        	if(newCompetition.size() > 0) {
        		
        		if(newCompetition.get(newCompetition.size()-1).getActiveFlag() == 1) {
        			System.out.printf("There is an active competition. SimpleCompetitions does not"
        					+ " support concurrent competitions!%n");
        		}
        		else {
        			System.out.printf("Type of competition (L: LuckyNumbers, R: RandomPick)?:%n");
            		competitionType = keyboard.next().charAt(0);
            		while(!(Character.toLowerCase(competitionType) == 'l' ||
            				Character.toLowerCase(competitionType) == 'r')) {
                    	System.out.printf("Invalid competition type! Please choose again.%n");
                    	System.out.printf("Type of competition (L: LuckyNumbers, R: RandomPick)?:%n");
                    	competitionType = keyboard.next().charAt(0);
                    }
        			newCompetition.add(sc.addNewCompetition(keyboard, competitionType, mode));
                	newCompetition.get(newCompetition.size()-1)
                	.setupCompetition(newCompetition.size(), competitionType, dataProvider);        			
        		}        		
        	}
        	else if(newCompetition.size() == 0){
        		System.out.printf("Type of competition (L: LuckyNumbers, R: RandomPick)?:%n");
        		competitionType = keyboard.next().charAt(0);
        		while(!(Character.toLowerCase(competitionType) == 'l' ||
        				Character.toLowerCase(competitionType) == 'r')) {
                	System.out.printf("Invalid competition type! Please choose again.%n");
                	System.out.printf("Type of competition (L: LuckyNumbers, R: RandomPick)?:%n");
                	competitionType = keyboard.next().charAt(0);
                }
        		newCompetition.add(sc.addNewCompetition(keyboard, competitionType, mode));
            	newCompetition.get(newCompetition.size()-1)
            	.setupCompetition(newCompetition.size(), competitionType, dataProvider);
        	}
        	
        	competitionSettings(keyboard, sc, mode, dataProvider);
        	break;
        case '2':
        	while(Character.toLowerCase(moreEntries) == 'y') {       		
        		if(newCompetition.size() == 0) {
        			System.out.printf("There is no active competition. Please create one!%n");
        			break;
        		}
        		else if(newCompetition.get(newCompetition.size()-1).getActiveFlag() == 1) {
        			newCompetition.get(newCompetition.size()-1).setKeyboard(keyboard);
        			newCompetition.get(newCompetition.size()-1).addEntries();            		
            		System.out.printf("Add more entries (Y/N)?%n");
            		moreEntries = keyboard.next().charAt(0);
            		
            		while(!(Character.toLowerCase(moreEntries) == 'y' ||
            				Character.toLowerCase(moreEntries) == 'n')) {
            			
            			System.out.printf("Unsupported option. Please try again!%n");
            			System.out.printf("Add more entries (Y/N)?%n");
                		moreEntries = keyboard.next().charAt(0);
            		}            			       			
        		}
        		else {
        			System.out.printf("There is no active competition. Please create one!%n");
        			break;
        		}        		
        	}  
        	
        	competitionSettings(keyboard, sc, mode, dataProvider);
        	break;
        case '3':
        	if(newCompetition.size() == 0) {
    			System.out.printf("There is no active competition. Please create one!%n");
    		}
        	else if(newCompetition.get(newCompetition.size()-1).getActiveFlag() == 1) {
        		
        		if(newCompetition.get(newCompetition.size()-1).getEntries() == 0) {
        			System.out.printf("The current competition has no entries yet!%n");
        		}
        		else {
        			newCompetition.get(newCompetition.size()-1).drawWinners();
        			ArrayList<Bill> newBill;
        			newBill = newCompetition.get(newCompetition.size()-1).getBill();
        			dataProvider.setBill(newBill);
        		}    		
        	}
        	else {
        		System.out.printf("There is no active competition. Please create one!%n");
        	}  
        	
        	competitionSettings(keyboard, sc, mode, dataProvider);
        	break;
        case '4':
        	if(newCompetition.size() == 0) {
    			System.out.printf("No competition has been created yet!%n");
    		}
        	else {
        		sc.report(sc);
        	}
        	competitionSettings(keyboard, sc , mode, dataProvider);
        	break;
        case '5':
        	System.out.printf("Save competitions to file? (Y/N)?%n");
    		saveFile = keyboard.next().charAt(0);
        	
    		while(!(Character.toLowerCase(saveFile) == 'y' ||
			Character.toLowerCase(saveFile) == 'n')) {
        		
        		System.out.printf("Unsupported option. Please try again!%n");
        		System.out.printf("Save competitions to file? (Y/N)?%n");
        		saveFile = keyboard.next().charAt(0);       		
        	}
        	
        	if(Character.toLowerCase(saveFile) == 'y') {
        		System.out.printf("File name:%n");
        		fileName = keyboard.next();
        		writeBinary(fileName);
        		dataProvider.updateBillFile(dataProvider.getBill());       		
        	}
        	
        	System.out.printf("Goodbye!%n");
        	break;
    	default:
    		System.out.printf("Unsupported option. Please try again!%n");
    		competitionSettings(keyboard, sc , mode, dataProvider);
    		break;        	
        }		
	}
	
	/**
	 * Function to write the binary file to save competitions.
	 * @param fileName - Name of the binary file that needs to be written
	 */
	void writeBinary(String fileName) {
		try {
			ObjectOutputStream demoOut = new ObjectOutputStream(new FileOutputStream(fileName));
			demoOut.writeObject(newCompetition);
			demoOut.close();
			System.out.printf("Competitions have been saved to file.%n");    		
		}
		catch(IOException e) {
			System.out.println(e);
		}
	}

	/**
	 * Function to read the member and bill files.
	 * Tries to catch multiple exceptions while reading the file.
	 * @param keyboard - Scanner object
	 * @return dataProvider - an object of DataProvider which contains info on member and bill
	 */
	DataProvider fileDetails(Scanner keyboard) {
		
		String memberFile;
        String billFile;		
        DataProvider dataProvider = null;
        char rightFile = 'n';
        
        while(Character.toLowerCase(rightFile) == 'n') {
        	
        	try {
        		System.out.printf("Member file: %n");
                memberFile = keyboard.next();
                System.out.printf("Bill file: %n");
                billFile = keyboard.next();
            	dataProvider = new DataProvider(memberFile, billFile);
            	rightFile = 'y';
            }
            catch(DataAccessException e) {
            	System.out.println(e.getMessage());
            	rightFile = rightFileLoop(keyboard);
            }
            catch(DataFormatException e) {
            	System.out.println(e.getMessage());
            	rightFile = rightFileLoop(keyboard);
            }       	
        } 
        
        return dataProvider;
	}
	
	/**
	 * A re-usable function to loop through the re-entering file names.
	 * @param keyboard
	 * @return rightFile - character to identify whether the file is right or not
	 */
	char rightFileLoop(Scanner keyboard) {
		char rightFile = 'n';
		char stay = 'a';
		System.out.printf("Please choose 'Y' to re-enter the files or 'N' to exit. (Y/N)%n");           	
    	stay = keyboard.next().charAt(0);
    	while(!(Character.toLowerCase(stay) == 'y' ||
				Character.toLowerCase(stay) == 'n')) {
    		
    		System.out.printf("Unsupported option. Please Try again.%n");
    		System.out.printf("Please choose 'Y' to re-enter the files or 'N' to exit. (Y/N)%n");           	
        	stay = keyboard.next().charAt(0);           		
    	}
    	
    	if(Character.toLowerCase(stay) == 'y') {
    		rightFile = 'n';
    	}
    	else {
    		rightFile = 'y';
    		//dataProvider = null;
    		System.out.printf("Goodbye!%n");
    	}    	
    	return rightFile;
	}

    /**
    * Main program that uses the main SimpleCompetitions class
    * Introduces the program to the user and gets preferred mode.
    * Calls competitionSettings to handle the program flow.
    * @param args main program arguments
    */
    @SuppressWarnings("unchecked")
	public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        SimpleCompetitions sc = new SimpleCompetitions();
        DataProvider dataProvider = null; 
        char mode = 'a'; // test or normal mode
        char load; // to load from binary file or not
        String fileName; // name of the binary file to be read from. 
        char rightFile = 'n'; // to store if proper file names were given.
        boolean loadSuccess = false; //to store if binary file was loaded properly

        
        System.out.printf("----WELCOME TO SIMPLE COMPETITIONS APP----%n");
        
        System.out.printf("Load competitions from file? (Y/N)?%n");
        load = keyboard.next().charAt(0);
       
        while(!(Character.toLowerCase(load) == 'y' || Character.toLowerCase(load) == 'n')) {
        	
        	System.out.printf("Unsupported option. Please try again!%n");
        	System.out.printf("Load competitions from file? (Y/N)?%n");
            load = keyboard.next().charAt(0);
        }
        if(Character.toLowerCase(load) == 'y') {

        	while(rightFile == 'n') {
        		
        		try {
        			System.out.printf("File name:%n");
            		fileName = keyboard.next();
        			ObjectInputStream demoIn = new ObjectInputStream(new FileInputStream(fileName));
        			sc.newCompetition = (ArrayList<Competition>)demoIn.readObject(); // check warning
        			demoIn.close();        			
        			mode = sc.newCompetition.get(sc.newCompetition.size()-1).getMode();
        			rightFile = 'y';
        			loadSuccess = true;
        		}
        		catch(FileNotFoundException e){
        			//System.out.println(e);
        			System.out.printf("Input files do not exist.%n");
        			rightFile = sc.rightFileLoop(keyboard);
        			loadSuccess = false;        			
        		}
            	catch(IOException  e){
        			System.out.println(e.getMessage());
        			rightFile = sc.rightFileLoop(keyboard);
        			loadSuccess = false;
        		}
        		catch(ClassNotFoundException e){
        			System.out.println(e.getMessage());
        			rightFile = sc.rightFileLoop(keyboard);
        			loadSuccess = false;
        		}
        	}        	
        }
        else {        	
        	System.out.printf("Which mode would you like to run? (Type T for Testing, "
            		+ "and N for Normal mode):%n");
            mode = keyboard.next().charAt(0);
            
            while(!(Character.toLowerCase(mode) == 't' || Character.toLowerCase(mode) == 'n')) {
            	System.out.printf("Invalid mode! Please choose again.%n");
            	System.out.printf("Which mode would you like to run? (Type T for Testing, "
                		+ "and N for Normal mode):%n");
                mode = keyboard.next().charAt(0);
            }          
            loadSuccess = true;
        }
        
        if(loadSuccess) {
        	dataProvider = sc.fileDetails(keyboard);
        }       
        
        if(dataProvider != null) {
        	sc.competitionSettings(keyboard, sc, mode, dataProvider);
        }
        
        keyboard.close();
    }
    
}
