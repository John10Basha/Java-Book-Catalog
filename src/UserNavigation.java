import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

public class UserNavigation {
	
	private Book[][] allBooks = new Book[8][];
	
	public void do_part3() {
		
		String directoryPath = "C:/Users/jbash/eclipse-workspace/249 Assignment 2 FOR REAL/Comp249_F24_Assg2/";
		
		//file paths for each genre's binary serialized array
        String[] genreFilePaths = {directoryPath + "Cartoons_Comics.csv.ser", directoryPath + "Hobbies_Collectibles.csv.ser", 
            directoryPath + "Movies_TV_Books.csv.ser", directoryPath + "Music_Radio_Books.csv.ser", 
            directoryPath + "Nostalgia_Eclectic_Books.csv.ser", directoryPath + "Old_Time_Radio_Books.csv.ser", 
            directoryPath + "Sports_Memorabilia_Books.csv.ser", directoryPath + "Trains_Planes_Automobiles_Books.csv.ser"};
        
        //deserialize each genre file into the allBooks array
        for (int i = 0; i < genreFilePaths.length; i++) 
        {
        	initializeArr(genreFilePaths[i], i);
            deserializeBooks(genreFilePaths[i], i);
        }
        
        String[] fileNames = {"Cartoons_Comics.csv.ser", "Hobbies_Collectibles.csv.ser", "Movies_TV_Books.csv.ser",
	            "Music_Radio_Books.csv.ser", "Nostalgia_Eclectic_Books.csv.ser", "Old_Time_Radio_Books.csv.ser", 
	            "Sports_Memorabilia.csv.ser", "Trains_Planes_Automobiles.csv.ser"};
        
        int selectedFileIndex = 0; //default to the first file
		Scanner kb = new Scanner (System.in);
		String choice;
        
        while(true) 
        {
        	System.out.println("-----------------------------");
        	System.out.println("          Main Menu          ");
        	System.out.println("-----------------------------");
        	System.out.println("v   View selected file: " + fileNames[selectedFileIndex] + 
        			" (" + allBooks[selectedFileIndex].length + " records)");
        	System.out.println("s   Select a file to view");
        	System.out.println("x   Exit");
        	System.out.println("-----------------------------");
        	System.out.println();
        	System.out.print("Enter Your Choice: ");
        	
        	choice = kb.nextLine().toLowerCase();
        	
        	switch(choice){
        		case "v":
        			viewFile(selectedFileIndex, kb);
                    break;
        		
        		case "s":
        			displayFileSubMenu();
        			int fileSubChoice = kb.nextInt();
        			kb.nextLine();
        			
        			if(fileSubChoice >= 1 && fileSubChoice <= 8) 
        			{
        				selectedFileIndex = fileSubChoice - 1;
        			}
        			else if(fileSubChoice == 9) 
        			{
        				//go back to main menu
        				continue;
        			}
        			else 
        			{
        				System.out.println("Invalid choice. Please select a valid option.");
        			}
        			break;
        		
        		case "x":
        			System.out.println("Exiting the program.");
                    kb.close();
                    return;
                    
                default:
                	System.out.println("Invalid choice. Please select a valid option.");
                    break;
        	}
        }
        
	}
	
	private void initializeArr(String filePath,int Gid) {
		int count = 0;
		
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) 
        {
        	
        	while (true) 
        	{
        		
        		Book book = (Book) ois.readObject();
        		count++; 
        		
        	}
        	
        }
		 catch(EOFException e) 
        {
			allBooks[Gid] = new Book[count];
        	System.out.println("End of file is read");
        }
		catch (IOException | ClassNotFoundException e) 
        {
            System.out.println("Error deserializing file: " + filePath);
            e.printStackTrace();
        }
	}
	
	//method to deserialize a binary file into a Book array
    private void deserializeBooks(String filePath,int Gid){
    	
    	//how many objects in the serialized file
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) 
        {
        	int arrayIndex = 0;        	
        	while (true) 
        	{
        		allBooks[Gid][arrayIndex++] = (Book) ois.readObject();
        	}
        }	
        	       	
        catch(EOFException e) 
        {
        	System.out.println("End of file is read");
        }
        catch (IOException | ClassNotFoundException e) 
        {
            System.out.println("Error deserializing file: " + filePath);
            e.printStackTrace();
        }
    }
    
    private void displayFileSubMenu() {
        //display file sub-menu to select a file
        System.out.println("-----------------------------------");
        System.out.println("            File Sub-Menu          ");
        System.out.println("-----------------------------------");
        System.out.println("1 Cartoons_Comics.csv.ser (" + (allBooks[0] != null ? allBooks[0].length : 0) + " records)");
        System.out.println("2 Hobbies_Collectibles.csv.ser (" + (allBooks[1] != null ? allBooks[1].length : 0) + " records)");
        System.out.println("3 Movies_TV_Books.csv.ser (" + (allBooks[2] != null ? allBooks[2].length : 0) + " records)");
        System.out.println("4 Music_Radio_Books.csv.ser (" + (allBooks[3] != null ? allBooks[3].length : 0) + " records)");
        System.out.println("5 Nostalgia_Eclectic_Books.csv.ser (" + (allBooks[4] != null ? allBooks[4].length : 0) + " records)");
        System.out.println("6 Old_Time_Radio_Books.csv.ser (" + (allBooks[5] != null ? allBooks[5].length : 0) + " records)");
        System.out.println("7 Sports_Memorabilia_Books.csv.ser (" + (allBooks[6] != null ? allBooks[6].length : 0) + " records)");
        System.out.println("8 Trains_Planes_Automobiles_Books.csv.ser (" + (allBooks[7] != null ? allBooks[7].length : 0) + " records)");
        System.out.println("9 Exit");
        System.out.print("Enter Your Choice: ");
    }
    
    private void viewFile(int selectedFileIndex, Scanner kb) {
    	Book[] selectedBooks = allBooks[selectedFileIndex];
    	int currentIndex = 0;
    	
    	while(true) 
    	{
    		System.out.print("Enter a number n (which is a view range) or 0 to exit viewing :");
    		int n = kb.nextInt();
    		kb.nextLine();
    		
    		if(n == 0) 
    		{
    			System.out.println("Exiting view mode.");
                break;
    		}
    		
    		if(n > 0) 
    		{
    			int endIndex = Math.min(currentIndex + n - 1, selectedBooks.length - 1);
                for(int i = currentIndex; i <= endIndex; i++) 
                {
                    if (selectedBooks[i] != null) 
                    {
                        System.out.println(selectedBooks[i]);
                    } else 
                    {
                        System.out.println("Empty record or null entry found at index " + i);
                    }
                } 
                currentIndex = endIndex;

                if(endIndex == selectedBooks.length - 1) 
                {
                    System.out.println("EOF has been reached.");
                }
    		}
    		else if(n < 0) 
    		{
                int startIndex = Math.max(0, currentIndex + n + 1);
                for(int i = startIndex; i <= currentIndex; i++) 
                {
                    if (selectedBooks[i] != null) 
                    {
                        System.out.println(selectedBooks[i]);
                    } else 
                    {
                        System.out.println("Empty record or null entry found at index " + i);
                    }
                }
                currentIndex = startIndex;

                if(startIndex == 0) 
                {
                    System.out.println("BOF has been reached.");
                }
            }

            System.out.println("Current index is now at: " + currentIndex);
    		
    	}
    	
    }

}
