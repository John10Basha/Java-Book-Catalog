import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class SyntaxSorter {
	
	//list of valid genres
	private static final String[] VALID_GENRES = {"CCB", "HCB", "MTV", "MRB", "NEB", "OTR", "SSM", "TPA"};
				
	
public void do_part1() {
		
		
		String mainFileName = "C:\\Users\\jbash\\eclipse-workspace\\249 Assignment 2 FOR REAL\\Comp249_F24_Assg2\\part1_input_file_names.txt";
		String directoryPath = "C:/Users/jbash/eclipse-workspace/249 Assignment 2 FOR REAL/Comp249_F24_Assg2/";
		
		
		try (
				// Initialize BufferedWriter for each genre and BufferedReader
				BufferedWriter cartoonsComicsWriter = new BufferedWriter(new FileWriter(directoryPath+ "Cartoons_Comics.csv.txt"));
				BufferedWriter hobbiesCollectiblesWriter = new BufferedWriter(new FileWriter(directoryPath+ "Hobbies_Collectibles.csv.txt"));
				BufferedWriter moviesTvWriter = new BufferedWriter(new FileWriter(directoryPath+ "Movies_TV_Books.csv.txt"));
				BufferedWriter musicRadioWriter = new BufferedWriter(new FileWriter(directoryPath+ "Music_Radio_Books.csv.txt"));
				BufferedWriter nostalgiaEclecticWriter = new BufferedWriter(new FileWriter(directoryPath+ "Nostalgia_Eclectic_Books.csv.txt"));
				BufferedWriter oldTimeRadioWriter = new BufferedWriter(new FileWriter(directoryPath+ "Old_Time_Radio_Books.csv.txt"));
				BufferedWriter sportsMemorabiliaWriter = new BufferedWriter(new FileWriter(directoryPath+ "Sports_Memorabilia_Books.csv.txt"));
				BufferedWriter trainsPlanesAutomobilesWriter = new BufferedWriter(new FileWriter(directoryPath+ "Trains_Planes_Automobiles_Books.csv.txt"));
				BufferedWriter badBookWriter = new BufferedWriter(new FileWriter(directoryPath+ "syntax_error_file.txt"));
				BufferedReader br = new BufferedReader(new FileReader(mainFileName));
			)
		{	
			BufferedWriter[] myWriters = {cartoonsComicsWriter, hobbiesCollectiblesWriter, moviesTvWriter, musicRadioWriter, nostalgiaEclecticWriter,
					oldTimeRadioWriter, sportsMemorabiliaWriter, trainsPlanesAutomobilesWriter};
			
			//read each file path line by line
			String aFileName;
			while((aFileName = br.readLine()) != null) 
			{
				File theFile = new File(aFileName.trim());
				
				//check if the file is empty
				if(theFile.exists() && theFile.length() == 0) 
				{
					System.out.println("Skipping the empty file" +theFile);
					continue;
				}
				
				String fullPath = directoryPath + aFileName;
				//System.out.println("Reading the file " + fullPath);
				
				//open a new BufferedReader for each individual file
				try(BufferedReader fileReader = new BufferedReader(new FileReader(fullPath))) 
				{
					String aLine;
					while((aLine = fileReader.readLine()) != null) 
					{
						try 
						{
							if(!isValidLine(aLine, myWriters, aFileName)) 
							{
								badBookWriter.write(aLine);
								badBookWriter.newLine();
							}
						}
						catch(TooFewFieldsException | TooManyFieldsException | MissingFieldException | UnknownGenreException e) 
						{
							//System.out.println(e.getMessage());
                            badBookWriter.write(e.getMessage());
                            badBookWriter.newLine();
						}
					}
				}
				catch(IOException e) 
				{
					System.out.println("Error reading file: " + aFileName + " - " + e.getMessage());
				}
			}
			
			
		}	
		catch(IOException e) 
		{
			System.out.println("Error with file processing: " + e.getMessage());
		}
		
	}
	
	//validate each line according to the rules
	private boolean isValidLine(String x, BufferedWriter[] myWriters, String fileName) throws TooManyFieldsException, TooFewFieldsException, 
	MissingFieldException, UnknownGenreException {
		//removing any unnecessary spaces
		x = x.trim();
		
		//regex to help us handle if the title has quotation marks or not and continue smoothly
		Pattern pattern = Pattern.compile("\"([^\"]*)\"|([^,]+)");
		Matcher matcher = pattern.matcher(x);
		String[] fields = new String[6];
		int i = 0;
		
		//get the correct field based on the regex
		while(matcher.find()) 
		{
			if(i < 6) 
			{
				fields[i++] = matcher.group(1) != null ? matcher.group(1) : matcher.group(2);
			}
		}
		
		//if the number of fields is not exactly 6, throw the specific exceptions
		if (i < 6) throw new TooFewFieldsException("Too few fields in record", x, fileName);
        if (i > 6) throw new TooManyFieldsException("Too many fields in record", x, fileName);
	    
	    //validate the genre (field 5) against the list of valid genres
	    String genre = fields[4].trim();
	    boolean validGenre = false;
	    for (String valid : VALID_GENRES) 
	    {
	        if (genre.equalsIgnoreCase(valid)) 
	        {
	            validGenre = true;
	            break;
	        }
	    }
	    
	    //if the genre is invalid throw UnknownGenreException
	    if (!validGenre) throw new UnknownGenreException("Unknown genre", x, fileName);
	    
	    
	    
	    //if all checks pass, writer line to the correct file based on genre
	    try 
	    {
	    	switch(genre.toUpperCase())
	    	{
	    		case "CCB":
	    			myWriters[0].write(x);
	    			myWriters[0].newLine();
	    			break;
	    		case "HCB":
	    			myWriters[1].write(x);
	    			myWriters[1].newLine();
	                break;
	            case "MTV":
	            	myWriters[2].write(x);
	            	myWriters[2].newLine();
	                break;
	            case "MRB":
	            	myWriters[3].write(x);
	            	myWriters[3].newLine();
	                break;
	            case "NEB":
	            	myWriters[4].write(x);
	            	myWriters[4].newLine();
	                break;
	            case "OTR":
	            	myWriters[5].write(x);
	            	myWriters[5].newLine();
	                break;
	            case "SSM":
	            	myWriters[6].write(x);
	            	myWriters[6].newLine();
	                break;
	            case "TPA":
	            	myWriters[7].write(x);
	            	myWriters[7].newLine();
	                break;	
	    	}
	    }
	    catch(IOException e) {
	    	System.out.println("Error writing line to genre file: " + e.getMessage());
	        return false;
	    }
	  
	    //all checks passed, return true
	    return true;
		
	}
	


}
