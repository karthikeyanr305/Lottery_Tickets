import java.util.Scanner;
import java.io.Serializable;

/**
 * @author Karthikeyan Rajagopal
 * Student Number - 1246255
 * Username - rajagopalk@student.unimelb.edu.au
 * LMS username: rajagopalk
 * Entry stores all entryId related info.
 */
public class Entry implements Serializable{
    private int entryId;
    private String billId;
    private String memberId;
    private String memberName;
    protected int manualFlag = 1; //Flag to indicate manual entry(1)/ automatic entry(0)
    transient Scanner keyboard;
    private int prize = 0;
    
    /**
     * Initializes the Entry object
     * @param keyboard - Scanner object
     * @param memberId 
     * @param billId
     * @param memberName
     * @param entryId
     */
    public Entry(Scanner keyboard, String memberId, String billId, String memberName, int entryId) {
    	this.keyboard = keyboard;
    	this.memberId = memberId;
    	this.billId = billId;
    	this.entryId = entryId;
    	this.memberName = memberName;
    }
    
    /**
     * Initializes the entry object
     * @param entryId
     */
    public Entry(int entryId) {
    	this.entryId = entryId;    	
    }
    
    String getBillId() {
    	return billId;
    }
    
    String getMemberId() {
    	return memberId;
    }
    
    String getMemberName() {
    	return memberName;
    }
    
    int getManualFlag() {
    	return manualFlag;
    }
    
    int getEntryId() {
    	return entryId;
    }
    
    void setPrize(int prize) {
    	this.prize = prize;
    }
    
    int getPrize() {
    	return prize;
    }
    
}
