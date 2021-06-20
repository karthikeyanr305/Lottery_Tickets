import java.util.ArrayList;
import java.io.Serializable;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.PrintWriter;

/**
 * @author Karthikeyan Rajagopal
 * Student Number - 1246255
 * Username - rajagopalk@student.unimelb.edu.au
 * LMS username: rajagopalk
 * DataProvider handles all the reading and writing of files.
 * Maneuvers between the menu options.
 * Interacts with member functions and Competition class to carry out the process.
 */
public class DataProvider  implements Serializable{
	transient Scanner memberInput = null;
	transient Scanner billInput = null;
	private ArrayList<Member> memberArray = new ArrayList<Member>();
	private ArrayList<Bill> billArray = new ArrayList<Bill>();
	private String memberFile;
	private String billFile;
	
	private final int MEMBER_COLUMNS = 3;
	private final int BILL_COLUMNS = 4;
	private final int BILLID_INDEX = 0;
	private final int MEMBERID_INDEX = 1;
	private final int AMOUNT_INDEX = 2;
	private final int USED_INDEX = 3;
	private final int MEMBERID2_INDEX = 0; //Column number of memberId in member file
    
	/**
     * 
     * @param memberFile A path to the member file (e.g., members.csv)
     * @param billFile A path to the bill file (e.g., bills.csv)
     * @throws DataAccessException If a file cannot be opened/read
     * @throws DataFormatException If the format of the the content is incorrect
     */
     public DataProvider(String memberFile, String billFile) 
                        throws DataAccessException, DataFormatException {
    	 File memberObject = new File(memberFile);
    	 File billObject = new File(billFile);  	 
    	 setBillFile(billFile);
    	 setMemberFile(memberFile);   	 
    	 dataProvide(memberObject, billObject);	 
     }
     
     /**
      * Reads the member and bill files and stores the info in ArrayLists of Bill and Member.
      * Checks for various exceptions while reading the file. 
      * @param memberObject - File object of the member file (e.g., members.csv)
      * @param billObject - File object of the bill file (e.g., bills.csv)
      * @throws DataAccessException If a file cannot be opened/read
      * @throws DataFormatException If the format of the the content is incorrect
      */
     private void dataProvide(File memberObject, File billObject)
    		 throws DataAccessException, DataFormatException {
    	 
    	 String []memberDetails;
    	 String []billDetails;
    	 
    	 if(!(memberObject.exists()) || !(billObject.exists())){
    		 throw new DataAccessException();
    	 }
    	     	 
    	 try {
    		 memberInput = new Scanner(new FileInputStream(memberFile));   		
    	 }
    	 catch(FileNotFoundException e) {
    		 throw new DataAccessException();
    	 }
    	 
    	 try {
    		 billInput = new Scanner(new FileInputStream(billFile));   		
    	 }
    	 catch(FileNotFoundException e) {
    		 throw new DataAccessException();
    	 }
    	 
    	 if(!(memberInput.hasNextLine())) {
    		 throw new DataFormatException();
    	 }
    	 while(memberInput.hasNextLine()) {
    		 memberDetails = memberInput.nextLine().split(",");
    		 if(memberDetails.length != MEMBER_COLUMNS) {
    			 throw new DataFormatException(MEMBER_COLUMNS);
    		 }
    		 try {
    			 Double.parseDouble(memberDetails[MEMBERID2_INDEX]);
    			 memberArray.add(new Member(memberDetails));
    		 }
    		 catch(NumberFormatException e) {
    			 throw new DataFormatException();
    		 }  		 
    	 }
    	 memberInput.close();
    	 
    	 if(!(billInput.hasNextLine())) {
    		 throw new DataFormatException();
    	 }
    	 while(billInput.hasNextLine()) {
    		 float amount;
    		 boolean used;
    		 
    		 billDetails = billInput.nextLine().split(",");
    		 if(billDetails.length != BILL_COLUMNS) {
    			 throw new DataFormatException(BILL_COLUMNS);
    		 }
    		 try {
    			 Double.parseDouble(billDetails[BILLID_INDEX]);
    			 if(!(billDetails[MEMBERID_INDEX].equals(""))) {
    				 Double.parseDouble(billDetails[MEMBERID_INDEX]);
    			 }       		 
        		 amount = Float.parseFloat(billDetails[AMOUNT_INDEX]);
        		 used = Boolean.parseBoolean(billDetails[USED_INDEX]);
        		 billArray.add(new Bill(billDetails[BILLID_INDEX],
        				 billDetails[MEMBERID_INDEX],
        				 amount,
        				 used)); 
    		 }
    		 catch(NumberFormatException e) {
    			 throw new DataFormatException();
    		 }   		     		 
    	 }
    	 billInput.close();   	 
     }
     
     /**
      * Function to write the updated bill objects to the bill file
      * @param bill - Array list of bill objects that are to be updated.
      */
     void updateBillFile(ArrayList<Bill> bill ) {

    	 try {
    		 PrintWriter billWrite = new PrintWriter(new FileOutputStream(billFile));
    		 for(int i=0; i<bill.size(); i++) {
    			 billWrite.println(bill.get(i).getBillId() + "," +
    					 bill.get(i).getMemberId() + "," +
    					 bill.get(i).getAmount() + "," +
    					 bill.get(i).getUsed());
    		 }
    		 billWrite.close();
    		 System.out.printf("The bill file has also been automatically updated.%n");
    	 }
    	 catch(FileNotFoundException e) {
    		 System.out.printf("Error in writing to the following file:%n");
    		 System.out.println(e.getMessage());
    		 System.out.printf("The bill file was not updated. Please run the program again.%n");
    	 }   	 
     }
     
     ArrayList<Member> getMember() {
    	 return memberArray;
     }
     
     ArrayList<Bill> getBill() {
    	 return billArray;
     }
     
     void setBill(ArrayList<Bill> billArray) {
    	 this.billArray = billArray;
     }
     
     void setBillFile(String billFile) {
    	 this.billFile = billFile;
     }
     
     String getBillFile() {
    	 return billFile;
     }
     
     void setMemberFile(String memberFile) {
    	 this.memberFile = memberFile;
     }
     
     String getMemberFile() {
    	 return memberFile;
     }
     
}
