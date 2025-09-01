
public class BadIsbn10Exception extends Exception {

	public BadIsbn10Exception(String message, String record, String fileName) { 
    	super(message + ": " + record + " in file " + fileName); 
    }

}
