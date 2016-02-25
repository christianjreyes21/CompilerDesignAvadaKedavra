
public class Lex {

	String tokenNames[] = {	"IDENTIFIER", // identifiers  
							"KEYWORD", // keywords ( INTEGER , DECIMAL , CHARACTER , STRING , BOOLEAN , SWITCH , CASE , STOP , DEFAULT , FOR , OUTPUT.PRINT , OUTPRINT.PRINTLN , INPUT.GET )
							"REL_OP", // relational operators ( < , > , <= , >=, == , != )
							"ARITH_OP", // arithmetic operators ( + , - , / , * , MOD, DIV, ^ )
							"LOG_OP", // logical operators ( AND , OR , NOT )
							"COMMENT", // comments 
							"NOISE", // noise words ( GOTO )
							"DELIM", // ( ) " ' , ;
							"INDENT", // \t
							"ERROR" // error or unrecognized lexemes
							};
	
	public static Token[] Lex(String file)
	{
		int line = 0;
		int numTokens = 0;
		Token[] maxTokens = new Token[99999999];
		Token[] trimmedTokens;
		char firstChar = file.charAt(0);
		String possibleToken = "";
		
		// reading the whole file (medyo ito yung scanner)
		for (int i = 0; i < file.length(); i++)
		{
			if (file.charAt(i) == ' ' || file.charAt(i) == '\n')
			{
				if (possibleToken.charAt(0) == '@')
					maxTokens[numTokens] = LexRecognizer.identifier(possibleToken, line);
				else if (possibleToken.charAt(0) == '#' || possibleToken.charAt(0) == '%')
					LexRecognizer.keyword(possibleToken, line);
				// tuloy nyo tong if else para sa Relational Operators, Arithmetic Operators tsaka Logical Operators
				// OPERATORS HERE PLZ
				
				if (file.charAt(i) == '\n')
					i++;
	
				possibleToken = "";
				numTokens++;
			}
			// SINGLE LINE COMMENT
			else if (file.charAt(i) == ':' && file.charAt(i+1) == '/')
			{
				while (file.charAt(i) != '\n')
				{
					possibleToken += file.charAt(i);
					i++;
				}
				maxTokens[numTokens] = LexRecognizer.comment(possibleToken, line);
				numTokens++;
			}
			// MULTI LINE COMMENT
			else if (file.charAt(i) == '(' && file.charAt(i+1) == ':')
			{
				while (file.charAt(i) != ':' && file.charAt(i + 1) != ')')
				{
					possibleToken += file.charAt(i);
					i++;
				}
				maxTokens[numTokens] = LexRecognizer.comment(possibleToken, line);
				numTokens++;
			}
			// FOR TABS (ito yung nag-aadd ng character sa string every time na nag-iiterate sya unless token yung mabasa)
			else
			{
				possibleToken += file.charAt(i);
				if (possibleToken.charAt(0) == '\t')
				{
					maxTokens[numTokens] = LexRecognizer.indent(possibleToken, line); 
					possibleToken = "";
					numTokens++;
				}
			}
		}
		
		// FROM MAX ARRAY TO RIGHT NUMBER OF ARRAY
		trimmedTokens = new Token[numTokens];
		for (int i = 0; i < numTokens; i++)
			trimmedTokens[i] = maxTokens[i];
		
		return trimmedTokens;
	}
	
	public static void main (String args[])
	{
		
		System.out.println(" \t ==LEXICAL ANALYZER==");
		//System.out.println(InputOutput.getText("a.hp"));
		//System.out.println(LexRecognizer.keyword(InputOutput.getText("a.hp"), 0).tokenName + "\n" + LexRecognizer.keyword(InputOutput.getText("a.hp"), 0).tokenAttribute);
		//System.out.println(LexRecognizer.keyword("FOR").tokenName);
		
		// CALL IT Lex HERE
		
		Token[] symbolTable = Lex(InputOutput.getText("a.hp"));
		
		System.out.println("Token Name \t\t\t Line Number \t\t\t Token Attribute");
		for (int i = 0; i < symbolTable.length; i++)
		{
			System.out.println(symbolTable[i].tokenName + "\t\t\t" + symbolTable[i].lineNumber + "\t\t\t" + symbolTable[i].tokenAttribute);
		}
	}
	
	
}
