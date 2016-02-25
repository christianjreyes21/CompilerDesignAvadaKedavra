
public class LexRecognizer {
	
	char charSet[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'I', 'j', 'k', 'l', 'm', 'n', 'ñ', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ';' , '=', '+', '-', '*', '/', '\\' , '&', '<', '>' , '(' , ')', '{' , '}', '[', ']' , '#' , ':' ,'\'', '.' , ',' , '%', '^', '!', '@', '~' , '$',  '\"', ' ', '\t' , '\n'};

	public static Token keyword(String str)
	{
		Token token = new Token();
		token.tokenName = "KEYWORD";
		token.tokenAttribute = str;
		boolean valid = true;
		
		for (int i = 0; i < str.length(); i++)
		{
			//if (//str.charAt(i)) //pattern
				//continue;
			//else
			//{
				//valid = false;
				//break;
			//}
		}
		
		if (!valid)
			token.tokenName = "ERROR";
			
		return token;
	}
	
	public static Token comment(String str)
	{
		Token token = new Token();
		token.tokenName="COMMENT";
		token.tokenAttribute= str;
		
		if (!(str.charAt(0) == ':' && str.charAt(1) == '/') || !(str.charAt(0) == '(' && str.charAt(1) == ':' && str.charAt(str.length() - 1) == ':' && str.charAt(str.length() - 2) == ')'))	
			token.tokenName = "ERROR";
		return token;
	}
}
