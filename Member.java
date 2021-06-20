import java.io.Serializable;

/**
 * @author Karthikeyan Rajagopal
 * Student Number - 1246255
 * Username - rajagopalk@student.unimelb.edu.au
 * LMS username: rajagopalk
 * Member contains all the information related to Member file
 */
public class Member implements Serializable{
	String memberId;
	String memberName;
	String memberMail;
	
	/**
	 * Initializes the Member object
	 * @param memberDetails
	 */
	public Member(String [] memberDetails) {
		this.memberId = memberDetails[0];
		this.memberName = memberDetails[1];
		this.memberMail = memberDetails[2];
	}
	
	String getMemberId() {
		return memberId;
	}
	
	String getMemberName() {
		return memberName;
	}
	
	String getMemberMail() {
		return memberId;
	}
	
	void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	
	void setMemberMail(String memberMail) {
		this.memberMail = memberMail;
	}

}
