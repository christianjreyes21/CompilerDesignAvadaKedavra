
public class LexRecognizer {
	
	char charSet[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'I', 'j', 'k', 'l', 'm', 'n', 'ñ', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ';' , '=', '+', '-', '*', '/', '\\' , '&', '<', '>' , '(' , ')', '{' , '}', '[', ']' , '#' , ':' ,'\'', '.' , ',' , '%', '^', '!', '@', '~' , '$',  '\"', ' ', '\t' , '\n'};
<<<<<<< HEAD

=======
>>>>>>> origin/master
	public static Token keyword(String buffer, int characterAt)
	{
		Token token = new Token();
		token.tokenName="";
		token.tokenAttribute="";
		int tempCharAt = characterAt;
		
		if (buffer.charAt(characterAt) == '#')
		{
			while(buffer.charAt(tempCharAt) != ' ')
			{
				token.tokenName += buffer.charAt(tempCharAt);
				tempCharAt++;
			}
			
			while(buffer.charAt(tempCharAt) != '@')
			{
				tempCharAt++;
			}
			
			while(buffer.charAt(tempCharAt) != ' ' && buffer.charAt(tempCharAt) != '\n')
			{
				token.tokenAttribute += buffer.charAt(tempCharAt);
				tempCharAt++;
			}
		}
		return token;
	}
	
	public static Token comment(String buffer, int characterAt)
	{
		Token token = new Token();
		token.tokenAttribute="";
		if (buffer.charAt(characterAt) == ':' && buffer.charAt(characterAt+1) == '/')
		{
			token.tokenName="Single-line Comment";
			int tempCharAt = characterAt+2;
			while(buffer.charAt(tempCharAt) != '\n')
			{
				token.tokenAttribute += buffer.charAt(tempCharAt);
				tempCharAt++;
			}
		}
		if (buffer.charAt(characterAt) == '(' && buffer.charAt(characterAt+1) == ':')
		{
			token.tokenName="Multiple-line Comment";
			int tempCharAt = characterAt+2;
			while(buffer.charAt(tempCharAt) != ':' || buffer.charAt(tempCharAt+1) != ')')
			{
				token.tokenAttribute += buffer.charAt(tempCharAt);
				tempCharAt++;
			}
		}
		return token;
	}
}
