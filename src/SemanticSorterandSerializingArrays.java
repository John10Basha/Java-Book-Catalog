import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class SemanticSorterandSerializingArrays {
	
	//counters for each genre
	private static int ccbCount = 0, hcbCount = 0, mtvCount = 0, mrbCount = 0, nebCount = 0, otrCount = 0, ssmCount = 0, tpaCount = 0;
	
	//indexs for each genre
	int ccbIndex = 0, hcbIndex = 0, mtvIndex = 0, mrbIndex = 0, nebIndex = 0, otrIndex = 0, ssmIndex = 0, tpaIndex = 0;

	public void do_part2() {
			String directoryPath = "C:/Users/jbash/eclipse-workspace/249 Assignment 2 FOR REAL/Comp249_F24_Assg2/";
			
		    //paths to each genre-specific file
		    String[] genreFilePaths = {directoryPath+ "Cartoons_Comics.csv.txt", directoryPath+ "Hobbies_Collectibles.csv.txt", 
		    		directoryPath+ "Movies_TV_Books.csv.txt", directoryPath+ "Music_Radio_Books.csv.txt",
		    		directoryPath+ "Nostalgia_Eclectic_Books.csv.txt", directoryPath+ "Old_Time_Radio_Books.csv.txt", 
		    		directoryPath+ "Sports_Memorabilia_Books.csv.txt", directoryPath+ "Trains_Planes_Automobiles_Books.csv.txt"};

		    String semanticErrorFile = directoryPath+ "semantic_error_file.txt";
		    
		    //create a 2D array of Book arrays to store different genre arrays
		    Book[][] allBooks = new Book[8][];

		    try (BufferedWriter errorWriter = new BufferedWriter(new FileWriter(semanticErrorFile))) 
		    {
		    	int genreCtr = 0;
		    	
		    	//first pass: count valid books for each genre
		        for (String filePath : genreFilePaths) 
		        {
		        	String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
		        	String genre = getGenreFromFileName(fileName); //helper to get genre code
		            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) 
		            {
		                String line;
		                while ((line = br.readLine()) != null) 
		                {
		                    try {
		                        Book book = validateAndCreateBook(line, fileName);
		                        if(book != null) 
		                        {
		                        	//count only valid books
		                        	incrementGenreCounter(genre); 
		                        }
		                        
		                    } 
		                    catch (BadIsbn10Exception | BadIsbn13Exception | BadPriceException | BadYearException e) 
		                    {
		                    	//System.out.println(e.getMessage());
		                        errorWriter.write(e.getMessage());
		                        errorWriter.newLine();
		                    }
		                }
		            } 
		            catch (IOException e) {
		                System.out.println("Error reading file: " + filePath + " - " + e.getMessage());
		            }
		             
		            
		            // Initialize each genre array
		            initGenreArry(genreCtr++, allBooks);
		            
		        }
		        
		        
		        //second pass: populate arrays with valid books only
	            //index counters for each genre array
	            for (String filePath_2 : genreFilePaths) 
	            {
	                String fileName_2 = filePath_2.substring(filePath_2.lastIndexOf("/") + 1);
	                String genre_2 = getGenreFromFileName(fileName_2);
	                
	                //check if genre_2 is null
	                if (genre_2 == null) 
	                {
	                    System.out.println("Invalid genre found for file: " + fileName_2);
	                    continue; //skip this iteration if genre is null
	                }
	                
	                try (BufferedReader br = new BufferedReader(new FileReader(filePath_2))) 
	                {
	                    String line;

	                    while((line = br.readLine()) != null) 
	                    {
	                        try 
	                        {
	                            Book book = validateAndCreateBook(line, fileName_2);
	                            if (book != null) 
	                            {
	                                addBookToGenreArray(genre_2, book, allBooks);
	                            }
	                        } 
	                        catch (BadIsbn10Exception | BadIsbn13Exception | BadPriceException | BadYearException e) 
	                        {
	                            //handled in first pass
	                        }
	                    }
	                } 
	                catch (IOException e) 
	                {
	                    System.out.println("Error reading file: " + filePath_2 + " - " + e.getMessage());
	                }
	            }
		        
		        //making sure all books are created properly
		        for(int i = 0 ; i < allBooks.length; i++) 
		        {
		        	 for(int j = 0; j < allBooks[i].length; j++) 
		        	 {
				        	System.out.println(allBooks[i][j].toString());
				     }
		        	 System.out.println();
		        }
		       
		        //serialize each genre array
		        serializeGenreArray(allBooks[0], directoryPath + "Cartoons_Comics.csv.ser");
	            serializeGenreArray(allBooks[1], directoryPath + "Hobbies_Collectibles.csv.ser");
	            serializeGenreArray(allBooks[2], directoryPath + "Movies_TV_Books.csv.ser");
	            serializeGenreArray(allBooks[3], directoryPath + "Music_Radio_Books.csv.ser");
	            serializeGenreArray(allBooks[4], directoryPath + "Nostalgia_Eclectic_Books.csv.ser");
	            serializeGenreArray(allBooks[5], directoryPath + "Old_Time_Radio_Books.csv.ser");
	            serializeGenreArray(allBooks[6], directoryPath + "Sports_Memorabilia_Books.csv.ser");
	            serializeGenreArray(allBooks[7], directoryPath + "Trains_Planes_Automobiles_Books.csv.ser");

		        
		    } 
		    catch (IOException e) 
		    {
		        System.out.println("Error writing to semantic error file: " + e.getMessage());
		    }
		    

	}

		//method to validate each line and create a Book object if valid
		private Book validateAndCreateBook(String line, String fileName) throws BadIsbn10Exception, BadIsbn13Exception, BadPriceException, BadYearException {
			String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
			if (fields.length != 6) 
			{
		        return null; 
		    }
		    String title = fields[0].trim();
		    String authors = fields[1].trim();
		    double price = Double.parseDouble(fields[2].trim());
		    String isbn = fields[3].trim();
		    String genre = fields[4].trim();
		    int year = Integer.parseInt(fields[5].trim());

		    //price check
		    if (price < 0) 
		    	throw new BadPriceException("Invalid price", line, fileName);

		    //year check
		    if (year < 1995 || year > 2010) 
		    	throw new BadYearException("Invalid year", line, fileName);

		    //ISBN validation
		    isValidISBN(isbn, line, fileName);

		    //if all checks pass, create and return Book object
		    return new Book(title, authors, price, isbn, genre, year);
		}
		
		//method to validate ISBN
		public boolean isValidISBN(String isbn, String line, String fileName) throws BadIsbn10Exception, BadIsbn13Exception {
			if(isbn.length() == 10) 
			{
				int sum = 0;
				for(int i = 0 ; i < 10 ; i++) 
				{
					char c = isbn.charAt(i);
					if(!Character.isDigit(c)) 
					{
						throw new BadIsbn10Exception("Invalid 10ISBN", line, fileName);
					}
					sum += Character.getNumericValue(c);
				}
				if(sum % 11 != 0) 
				{
					throw new BadIsbn10Exception("Invalid 10ISBN", line, fileName);
				}
				//valid 10ISBN
				return true;
			}
			else if(isbn.length() == 13) 
			{
				int sum = 0;
				for(int i = 0 ; i < 13 ; i++) 
				{
					char c = isbn.charAt(i);
					if(!Character.isDigit(c)) 
					{
						throw new BadIsbn13Exception("Invalid 13ISBN", line, fileName);
					}
					sum += Character.getNumericValue(c);
				}
				if(sum % 13 != 0)
				{
					throw new BadIsbn13Exception("Invalid 13ISBN", line, fileName);
				}
				//valid 13ISBN
				return true;
			}
			return false;
		}

		//method to determine genre-based file path for serialization
		private String getGenreFromFileName(String fileName) {
			switch (fileName) {
				case "Cartoons_Comics.csv.txt": 
			        return "CCB";
			    case "Hobbies_Collectibles.csv.txt": 
			        return "HCB";
			    case "Movies_TV_Books.csv.txt": 
			        return "MTV";
			    case "Music_Radio_Books.csv.txt": 
			        return "MRB";
			    case "Nostalgia_Eclectic_Books.csv.txt": 
			        return "NEB";
			    case "Old_Time_Radio_Books.csv.txt": 
			        return "OTR";
			    case "Sports_Memorabilia_Books.csv.txt": 
			        return "SSM";
			    case "Trains_Planes_Automobiles_Books.csv.txt": 
			        return "TPA";
			    default: 
			        System.out.println("Invalid file name: " + fileName);
			        return null; 
			    }
			}
	

		//method to increment genre counters
		private void incrementGenreCounter(String genre) {
			if (genre == null) 
			{
				System.out.println("Error: genre is null. Check the file name.");
				return; 
			}
			
		    switch (genre) {
		        case "CCB": 
		        	ccbCount++; 
		        	break;
		        case "HCB": 
		        	hcbCount++; 
		        	break;
		        case "MTV": 
		        	mtvCount++; 
		        	break;
		        case "MRB": 
		        	mrbCount++; 
		        	break;
		        case "NEB": 
		        	nebCount++; 
		        	break;
		        case "OTR": 
		        	otrCount++; 
		        	break;
		        case "SSM": 
		        	ssmCount++; 
		        	break;
		        case "TPA": 
		        	tpaCount++; 
		        	break;
		        default:
		        	System.out.println("Unexpected genre: " + genre);
		            break;
		       
		    }
		    
		}
		
	    
	    //helper to add a book to the correct array based on genre
	    private void addBookToGenreArray(String genre, Book book, Book[][] allBooks) {
	        switch (genre) {
	            case "CCB":
	            	if(ccbIndex < allBooks[0].length) {
	            		allBooks[0][ccbIndex++] = book;
	            	}	
	            	break;
	            case "HCB":
	            	if(hcbIndex < allBooks[1].length) {
	            		allBooks[1][hcbIndex++] = book; 
	            	}
	            	break;
	            case "MTV":
	            	if(mtvIndex < allBooks[2].length) {
	            		allBooks[2][mtvIndex++] = book; 
	            	}
	            	break;
	            case "MRB":
	            	if(mrbIndex < allBooks[3].length) {
	            		allBooks[3][mrbIndex++] = book;
	            	}
	            	break;
	            case "NEB":
	            	if(nebIndex < allBooks[4].length) {
	            		allBooks[4][nebIndex++] = book;
	            	}	
	            	break;
	            case "OTR":
	            	if(otrIndex < allBooks[5].length) {
	            		allBooks[5][otrIndex++] = book; 
	            	}	
	            	break;
	            case "SSM":
	            	if(ssmIndex < allBooks[6].length) {
	            		allBooks[6][ssmIndex++] = book;
	            	}
	            	break;
	            case "TPA":
	            	if(tpaIndex < allBooks[7].length) {
	            		allBooks[7][tpaIndex++] = book; 
	            	}
	            	break;
	        }
	    }
	    
	    // Helper to serialize an array of books to a file
	    private void serializeGenreArray(Book[] books, String filePath) throws FileNotFoundException, IOException {
	    	ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath));
	    	
	        try  
	        {       	       	
	        	for(Book book : books) 
	        	{
	        		//book.toString();
	        		if(book != null) 
	        		{
	        			oos.writeObject(book);
	        		
	        		}
	        		
	        	}
	            
	        } 
	        
	        catch (IOException e) 
	        {
	            System.out.println("Error serializing file: " + filePath + " - " + e.getMessage());
	        }
	        
	        finally 
		    {
		    	oos.close();
		    }
	    }
	    
	    private void initGenreArry(int genreCtr, Book[][] allBooks) {
	    	switch(genreCtr) {
	    		case 0:
	    			allBooks[0] = new Book[ccbCount];
	    			break;
	    		case 1:
	    			allBooks[1] = new Book[hcbCount];
	    			break;
	    		case 2:
	    			allBooks[2] = new Book[mtvCount];
	    			break;	
	    		case 3:
	    			allBooks[3] = new Book[mrbCount];
	    			break;	
	    		case 4:
	    			allBooks[4] = new Book[nebCount];
	    			break;	
	    		case 5:
	    			allBooks[5] = new Book[otrCount];
	    			break;	
	    		case 6:
	    			allBooks[6] = new Book[ssmCount];
	    			break;	
	    		case 7:
	    			allBooks[7] = new Book[tpaCount];
	    			break;	
	    	}
	    }
        
		
}
		