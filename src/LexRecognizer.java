
public class LexRecognizer {
	
	char charSet[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'I', 'j', 'k', 'l', 'm', 'n', 'ñ', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ';' , '=', '+', '-', '*', '/', '\\' , '&', '<', '>' , '(' , ')', '{' , '}', '[', ']' , '#' , ':' ,'\'', '.' , ',' , '%', '^', '!', '@', '~' , '$',  '\"', ' ', '\t' , '\n'};
	/*static char Integer[] = {'I', 'N', 'T', 'E', 'G', 'E', 'R'};
	static char Decimal[] = {'D', 'E', 'C', 'I', 'M', 'A', 'L'};
	static char Character[] = {'C', 'H', 'A', 'R', 'A', 'C', 'T', 'E', 'R'};
	static char String[] = {'S', 'T', 'R', 'I', 'N', 'G'};
	static char Boolean[] = {'B', 'O', 'O', 'L', 'E', 'A', 'N'};
	static char Switch[] = {'S', 'W', 'I', 'T', 'C', 'H'};
	static char Case[] = {'C', 'A', 'S', 'E'};
	static char Stop[] = {'S', 'T', 'O', 'P'};
	static char Default[] = {'D', 'E', 'F', 'A', 'U', 'L', 'T'};
	static char For[] = {'F', 'O', 'R'};
	static char OP[] = {'O', 'U', 'T', 'P', 'U', 'T', '.', 'P', 'R', 'I', 'N', 'T'};
	static char OPl[] = {'O', 'U', 'T', 'P', 'U', 'T', '.', 'P', 'R', 'I', 'N', 'T', 'L', 'N'};
	static char IG[] = {'I', 'N', 'P', 'U', 'T', '.', 'G', 'E', 'T'};*/
	static String Keywords[] = {"INTEGER","DECIMAL","CHARACTER","STRING","BOOLEAN","SWITCH","CASE","STOP","DEFAULT","FOR","OUTPUT.PRINT","OUTPUT.PRINTLN","INPUT.GET"};
	
	
	public static Token keyword(String str)
	{
		Token token = new Token();
		token.tokenName = "KEYWORD";
		token.tokenAttribute = str;
		boolean valid = true;
		
		for (int i = 0; i < Keywords.length; i++)
		{
		    for (int j=0; j<str.length(); j++)
		    {
		    	boolean keywordStateValid;
		    	if(str.charAt(j) != Keywords[i].charAt(j))
		    	{
		    		break;
		    	}
		    	else
		    	{
		    		keywordStateValid = true;
		    	}
		    }
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
