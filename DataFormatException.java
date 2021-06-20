/**
 * @author Karthikeyan Rajagopal
 * Student Number - 1246255
 * Username - rajagopalk@student.unimelb.edu.au
 * LMS username: rajagopalk
 * DataFormatException contains all the exception related to Data Format
 */
public class DataFormatException extends Exception {
	
	public DataFormatException() {
		super("The files do not have the correct data type.");
	}
	
	public DataFormatException(String message) {
		super(message);
	}
	
	public DataFormatException(int column) {
		super("The number of columns do not match.");
	}
	
}
