
public class LexRecognizer {
	
	char charSet[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'ñ', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ';' , '=', '+', '-', '*', '/', '\\' , '&', '<', '>' , '(' , ')', '{' , '}', '[', ']' , '#' , ':' ,'\'', '.' , ',' , '%', '^', '!', '@', '~' , '$',  '\"', ' ', '\t' , '\n'};
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
	static String Keywords[] = {"#INTEGER","#DECIMAL","#CHARACTER","#STRING","#BOOLEAN","%SWITCH","%CASE","%STOP","%DEFAULT","%FOR","%OUTPUT.PRINT","%OUTPUT.PRINTLN","%INPUT.GET"};
	
	public static Token arithmeticAndRelationalOperator(String str, int line)
	{
		Token token = new Token();
		token.tokenName = "ERROR";
		token.tokenAttribute = str;
		token.lineNumber = line;
		
		if (str.charAt(0) == '+' || str.charAt(0) == '-' || str.charAt(0) == '*' || str.charAt(0) == '/' || str.charAt(0) == '^' || str.charAt(0) == '=')
		{
			if (str.length()==1)
				token.tokenName = "ARITH_OP";
			
			if (str.length() == 2 && str.charAt(0) != '=')
				if (((str.charAt(0) == '+') && (str.charAt(1) == '+')) || ((str.charAt(0) == '-') && (str.charAt(1) == '-')))
					token.tokenName = "ARITH_OP";
			if (str.length() == 2 && str.charAt(0) == '=')
				if (str.charAt(0) == '=' && str.charAt(1) == '=')
					token.tokenName = "REL_OP";	
		}
		if (str.charAt(0) == '<' || str.charAt(0) == '>')
		{
			if (str.length()==1)
				token.tokenName = "REL_OP";
			
			if (str.length() == 2)
				if (((str.charAt(0) == '<') && (str.charAt(1) == '=')) || ((str.charAt(0) == '>') && (str.charAt(1) == '=')))
					token.tokenName = "REL_OP";
		}
		if (str.charAt(0) == '!')
		{
			if (str.length()==1)
				token.tokenName = "ERROR";
			
			if (str.length() == 2)
				if (((str.charAt(0) == '!') && (str.charAt(1) == '=')))
					token.tokenName = "REL_OP";
		}
		
		return token;

	}
	public static Token logicalOperator(String str, int line)
	{
		Token token = new Token();
		token.tokenName = "ERROR";
		token.tokenAttribute = str;
		token.lineNumber = line;
		
		if (str.charAt(0) == '&')
		{
			if (str.length()==1)
			{
				token.tokenName = "ERROR";
			}
			if (str.length() == 3)
			{	
				if(str.charAt(1) == 'O' && str.charAt(2) == 'R')
				token.tokenName = "LOG_OP";
			}
			if (str.length() == 4)
			{	
				if(str.charAt(1) == 'A' && str.charAt(2) == 'N' && str.charAt(3) == 'D')
				{
					token.tokenName = "LOG_OP";
				}
				if(str.charAt(1) == 'N' && str.charAt(2) == 'O' && str.charAt(3) == 'T')
				{
					token.tokenName = "LOG_OP";
				}
			}
			
		}	
		
		return token;
	}
	
	public static Token noise(String str, int line)
	{
		Token token = new Token();
		token.tokenName = "ERROR";
		token.tokenAttribute = str;
		token.lineNumber = line;
		
		if (str.charAt(0) == '?')
		{
			if (str.length()==1)
			{
				token.tokenName = "ERROR";
			}
			if (str.length() == 5)
			{	
				if(str.charAt(1) == 'G' && str.charAt(2) == 'O' && str.charAt(3) == 'T' && str.charAt(4) == 'O')
				{
					token.tokenName = "NOISE";
				}
			}
			
		}	
		
		return token;
	}
	
	public static Token keyword(String str, int line)
	{
		Token token = new Token();
		token.tokenName = "KEYWORD";
		token.tokenAttribute = str;
		token.lineNumber = line;
		boolean valid = false;
		
		for (int i = 0; i < Keywords.length; i++)
		{
		    for (int j=0; j<Keywords[i].length(); j++)
		    {
		    	//System.out.println(str.charAt(j) + " " + Keywords[i].charAt(j) + j+str.length() + valid);
		    	if(str.charAt(j) != Keywords[i].charAt(j))
		    	{
		    		break;
		    	}
		    	else
		    	{
		    		if(j==str.length()-2)
		    		{
		    			valid=true;
		    		}
		    	}
		    }
		}
		
		
		if (!valid)
			token.tokenName = "ERROR";
		
		System.out.println("Keyword");
		return token;
	}
	
	public static Token comment(String str, int line)
	{
		Token token = new Token();
		token.tokenName="ERROR";
		token.tokenAttribute= str;
		token.lineNumber = line;

		if ((str.charAt(0) == ':' && str.charAt(1) == '/') || (str.charAt(0) == '(' && str.charAt(1) == ':' && str.charAt(str.length() - 1) == ':' && str.charAt(str.length() - 2) == ')'))	
			token.tokenName = "COMMENT";
		
		System.out.println("Comment");
		return token;
	}
	
	public static Token identifier(String str, int line)
	{
		Token token = new Token();
		token.tokenName = "IDENT";
		token.tokenAttribute = str;
		boolean valid = true;
		token.lineNumber = line;
		
		// check if the symbol preceeding @ is a part of the alphabet
		if (!(str.charAt(1) == 'A' || str.charAt(1) == 'B' || str.charAt(1) == 'C' || str.charAt(1) == 'D' || str.charAt(1) == 'E' || str.charAt(1) == 'F' || str.charAt(1) == 'G' || str.charAt(1) == 'H' || str.charAt(1) == 'I' || str.charAt(1) == 'J' || str.charAt(1) == 'K' || str.charAt(1) == 'L' || str.charAt(1) == 'M' || str.charAt(1) == 'N' || str.charAt(1) == 'O' || str.charAt(1) == 'P' || str.charAt(1) == 'Q' || str.charAt(1) == 'R' || str.charAt(1) == 'S' || str.charAt(1) == 'T' || str.charAt(1) == 'U' || str.charAt(1) == 'V' || str.charAt(1) == 'W' || str.charAt(1) == 'X' || str.charAt(1) == 'Y' || str.charAt(1) == 'Z' || str.charAt(1) == 'a' || str.charAt(1) == 'b' || str.charAt(1) == 'c' || str.charAt(1) == 'd' || str.charAt(1) == 'e' || str.charAt(1) == 'f' || str.charAt(1) == 'g' || str.charAt(1) == 'h' || str.charAt(1) == 'i' || str.charAt(1) == 'j' || str.charAt(1) == 'k' || str.charAt(1) == 'l' || str.charAt(1) == 'm' || str.charAt(1) == 'n' || str.charAt(1) == 'o' || str.charAt(1) == 'p' || str.charAt(1) == 'q' || str.charAt(1) == 'r' || str.charAt(1) == 's' || str.charAt(1) == 't' || str.charAt(1) == 'u' || str.charAt(1) == 'v' || str.charAt(1) == 'w' || str.charAt(1) == 'x' || str.charAt(1) == 'y' || str.charAt(1) == 'z'))
			valid = false;
		else
		{	// check if the rest is composed of valid characters
			for (int i = 2; i < str.length(); i++)
				if (!(str.charAt(1) == 'A' || str.charAt(1) == 'B' || str.charAt(1) == 'C' || str.charAt(1) == 'D' || str.charAt(1) == 'E' || str.charAt(1) == 'F' || str.charAt(1) == 'G' || str.charAt(1) == 'H' || str.charAt(1) == 'I' || str.charAt(1) == 'J' || str.charAt(1) == 'K' || str.charAt(1) == 'L' || str.charAt(1) == 'M' || str.charAt(1) == 'N' || str.charAt(1) == 'O' || str.charAt(1) == 'P' || str.charAt(1) == 'Q' || str.charAt(1) == 'R' || str.charAt(1) == 'S' || str.charAt(1) == 'T' || str.charAt(1) == 'U' || str.charAt(1) == 'V' || str.charAt(1) == 'W' || str.charAt(1) == 'X' || str.charAt(1) == 'Y' || str.charAt(1) == 'Z' || str.charAt(1) == 'a' || str.charAt(1) == 'b' || str.charAt(1) == 'c' || str.charAt(1) == 'd' || str.charAt(1) == 'e' || str.charAt(1) == 'f' || str.charAt(1) == 'g' || str.charAt(1) == 'h' || str.charAt(1) == 'i' || str.charAt(1) == 'j' || str.charAt(1) == 'k' || str.charAt(1) == 'l' || str.charAt(1) == 'm' || str.charAt(1) == 'n' || str.charAt(1) == 'o' || str.charAt(1) == 'p' || str.charAt(1) == 'q' || str.charAt(1) == 'r' || str.charAt(1) == 's' || str.charAt(1) == 't' || str.charAt(1) == 'u' || str.charAt(1) == 'v' || str.charAt(1) == 'w' || str.charAt(1) == 'x' || str.charAt(1) == 'y' || str.charAt(1) == 'z'|| str.charAt(1) == '0' || str.charAt(1) == '1' || str.charAt(1) == '2' || str.charAt(1) == '3' || str.charAt(1) == '4' || str.charAt(1) == '5' || str.charAt(1) == '6' || str.charAt(1) == '7' || str.charAt(1) == '8' || str.charAt(1) == '9' || str.charAt(1) == '-' || str.charAt(1) == '_'))
				{
					valid = false;
					break;
				}
		}		
		
		if (!valid)
			token.tokenName = "ERROR";
		System.out.println("Identifier");
		return token;
	}
	
	public static Token indent(String str, int line)
	{
		Token token = new Token();
		token.tokenName = "INDENT";
		token.tokenAttribute = str;
		token.lineNumber = line;
		
		System.out.println("Indent");
		return token;
	}
	
	public static Token delim(String str, int line)
	{
		Token token = new Token();
		token.tokenName = "DELIM";
		token.tokenAttribute = str;
		token.lineNumber = line;
		
		if(str.length() != 1)
			token.tokenName = "ERROR";
		
		System.out.println("Delimeter");
		return token;
	}
	
	public static Token number(String str, int line)
	{
		Token token = new Token();
		token.tokenName = "NUMBER";
		token.tokenAttribute = str;
		token.lineNumber = line;
		
		// start sa 1 kasi pwedeng negative. Yung pagcheck ng negative nasa Lex na function, kasama na sya dun sa if statement. Pag nagstart yung character sa - 0 1 2 3 4 5 6 7 8 9, bale, pag nagstart sa - understood na yun na negative tas dito
		// checheck nalang yung remaining symbols kung number ba talaga sila
		for (int i = 1; i < str.length(); i++)
		{
			if (!(str.charAt(i) == '0' || str.charAt(i) == '1' || str.charAt(i) == '2' || str.charAt(i) == '3' || str.charAt(i) == '4' || str.charAt(i) == '5' || str.charAt(i) == '6' || str.charAt(i) == '7' || str.charAt(i) == '8' || str.charAt(i) == '9'))
			{
				token.tokenName = "ERROR";
				break;
			}
		}
		
		if (str.charAt(0) == '-' && str.length() == 1)
			token.tokenName = "ERROR";
		
		System.out.println("Number");
		return token;
	}
}
