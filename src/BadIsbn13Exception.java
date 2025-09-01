
public class BadIsbn13Exception extends Exception {
	
	public BadIsbn13Exception(String message, String record, String fileName) { 
    	super(message + ": " + record + " in file " + fileName); 
    }

}
