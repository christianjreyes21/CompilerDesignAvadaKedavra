
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
							"NUMBER", // 0 1 2 3 4 5 6 7 8 9
							"ERROR" // error or unrecognized lexemes
							};
	
	public static Token[] Lex(String file)
	{
		int line = 1;
		int numTokens = 0;
		Token[] maxTokens = new Token[9999999];
		Token[] trimmedTokens;
		String possibleToken = "";
		
		// reading the whole file (medyo ito yung scanner)
		for (int i = 0; i < file.length(); i++)
		{
			// reads characters from start of a character to a space or newline
			if (!(file.charAt(i) == ' ' || file.charAt(i) == '\n'))
				possibleToken += file.charAt(i);
				
			//System.out.println(possibleToken);
			System.out.println("Real: " + i);
			// reads tab (indent)
			if (file.charAt(i) == '\t')
			{
				maxTokens[numTokens] = LexRecognizer.indent(possibleToken, line);
				numTokens++;
				possibleToken = "";
			}
			//
			else if (file.charAt(i) == ' ' || file.charAt(i) == '\n')
			{
				// for newlines that are not preceeded by another symbol
				if (file.charAt(i) == '\n' && possibleToken.length() == 0)
				{
					line++;
					continue;
				}
				
				if (possibleToken.charAt(0) == ':' || possibleToken.charAt(0) == ';' || possibleToken.charAt(0) == ',' || possibleToken.charAt(0) == '(' || possibleToken.charAt(0) == ')' || possibleToken.charAt(0) == '\'' || possibleToken.charAt(0) == '"')
				{
					//System.out.println("IN");
					if (possibleToken.length() == 2 && (possibleToken.charAt(0) == ':' || possibleToken.charAt(0) == '('))
					{
						if (possibleToken.charAt(0) == ':' && possibleToken.charAt(1) == '/')
						{
							while (file.charAt(i) != '\n')
							{
								possibleToken += file.charAt(i);
								System.out.println("Real2: " + i++);
							}
						}
						else if (possibleToken.charAt(0) == '(' && possibleToken.charAt(1) == ':')
						{
							while (file.charAt(i-1) != ':' && file.charAt(i) != ')')
							{
								possibleToken += file.charAt(i);

								System.out.println("Real2: " + i++);
							}
							
						}
						maxTokens[numTokens] = LexRecognizer.comment(possibleToken, line);
					}
					else 
						maxTokens[numTokens] = LexRecognizer.delim(possibleToken, line);
					
					numTokens++;
					possibleToken = "";
				}
				else if (possibleToken.charAt(0) == '@')
				{
					//System.out.println("IN");
					maxTokens[numTokens] = LexRecognizer.identifier(possibleToken, line);
					numTokens++;
					possibleToken = "";
				}
				else if (possibleToken.charAt(0) == '#' || possibleToken.charAt(0) == '%')
				{
					//System.out.println("IN");
					maxTokens[numTokens] = LexRecognizer.keyword(possibleToken, line);
					numTokens++;
					possibleToken = "";
				} 
				
				// for newlines that have a preceeding symbol or token or word
				if (file.charAt(i) == '\n')
				{
					line++;
					possibleToken = "";
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
		
		for (int i = 0; i < symbolTable.length; i++)
		{
			System.out.println(symbolTable[i].lineNumber + " " + symbolTable[i].tokenName + " " + symbolTable[i].tokenAttribute);
		}
	}
}
