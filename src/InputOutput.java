import java.util.Scanner;


public class InputOutput {

	public static String getText()
	{
		System.out.print("Enter sample text: ");
		Scanner reader = new Scanner(System.in);
		return reader.nextLine();
	}
}
