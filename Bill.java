import java.io.Serializable;

/**
 * @author Karthikeyan Rajagopal
 * Student Number - 1246255
 * Username - rajagopalk@student.unimelb.edu.au
 * LMS username: rajagopalk
 * Bill contains all the information related to Bill file
 */
public class Bill implements Serializable{
	
	private String billId;
	private String memberId;
	private float amount;
	private boolean used;
	
	/**
	 * Initializes the Bill object
	 * @param billId
	 * @param memberId
	 * @param amount
	 * @param used
	 */
	public Bill(String billId, String memberId, float amount, boolean used) {
		this.billId = billId;
		this.memberId = memberId;
		this.amount = amount;
		this.used = used;
	}
	
	String getBillId() {
		return billId;
	}
	
	String getMemberId() {
		return memberId;
	}
	
	float getAmount() {
		return amount;
	}
	
	boolean getUsed() {
		return used;
	}
	
	void setBillId(String billId) {
		this.billId = billId;
	}
	
	void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	void setAmount(float amount) {
		this.amount = amount;
	}
	
	void setUsed(boolean used) {
		this.used = used;
	}
	
}
