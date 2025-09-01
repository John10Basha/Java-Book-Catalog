
public class BadPriceException extends Exception {
	
	public BadPriceException(String message, String record, String fileName) { 
    	super(message + ": " + record + " in file " + fileName); 
    }

}
