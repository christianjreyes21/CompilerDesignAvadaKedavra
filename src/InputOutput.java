import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class InputOutput {

	public static String getText(String FileName) {
		String details = "";
		try {
			File myFile = new File(FileName);
			BufferedReader bf = new BufferedReader(new FileReader(myFile));
			String buffer;

			while ((buffer = bf.readLine()) != null) {
				details += buffer + "\n";
			}

			bf.close();
		} catch (Exception e) {
			System.out.println("FILE NOT FOUND");
		}
		return details;
	}
	
	public static void writeText(Token[] symbolTable)
	{
		File myFile = new File("symbol.txt");
		PrintWriter pw;
		try {
			pw = new PrintWriter(new FileWriter(myFile));
			pw.println("\t\t\tSYMBOL TABLE");
			for (int i = 0; i < symbolTable.length; i++)
				pw.println("LINE NUMBER" + symbolTable[i].lineNumber + " TOKEN: " + symbolTable[i].tokenName + " ATTRIBUTE: " + symbolTable[i].tokenAttribute);
			
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
