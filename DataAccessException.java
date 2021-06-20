/**
 * @author Karthikeyan Rajagopal
 * Student Number - 1246255
 * Username - rajagopalk@student.unimelb.edu.au
 * LMS username: rajagopalk
 * DataAccessException contains all the exception related to Data Access
 */
public class DataAccessException extends Exception {
	
	public DataAccessException() {
		super("Input files do not exist.");
	}
	
	public DataAccessException(String message) {
		super(message);
	}
	
}
