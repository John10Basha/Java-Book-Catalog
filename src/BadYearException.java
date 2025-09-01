
public class BadYearException extends Exception {
	
	public BadYearException(String message, String record, String fileName) { 
    	super(message + ": " + record + " in file " + fileName); 
    }

}
