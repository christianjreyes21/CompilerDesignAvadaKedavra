import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;


public class InputOutput {

	public static String getText(String FileName)
	{
		String details = "";
		try
		{
		File myFile = new File (FileName);
		BufferedReader bf = new BufferedReader(new FileReader(myFile));
		String buffer;
		
		
		while ((buffer = bf.readLine()) != null)
		{
			details += buffer + "\n";
		}
		
		
		bf.close();
		
		}
		catch (Exception e)
		{
			System.out.println("FILE NOT FOUND");
		}
		return details;
	}
}
