//----------------------------------------------------------------------------------- 
// Assignment 2  
// Question: ---- validating syntax, semantics, seralizing and deserializing						
// Written by: John Basha ID 40286668
// Due November 14th
//-----------------------------------------------------------------------------------
import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelpingMrBooker {
		

	public static void main(String[] args) {
		
		SyntaxSorter b1 = new SyntaxSorter();
		b1.do_part1();
		SemanticSorterandSerializingArrays c1 = new SemanticSorterandSerializingArrays();
		c1.do_part2();
		UserNavigation d1 = new UserNavigation();
		d1.do_part3();
		

	}

}
