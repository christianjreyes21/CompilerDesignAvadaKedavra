
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
		int cursor = 0;
		Token[] maxTokens = new Token[99999999];
		Token[] trimmedTokens;
		String possibleToken = "";
		
		// reading the whole file (medyo ito yung scanner)
		while (true)
		{
			if (cursor == file.length() - 1)
				break;
			
			if (file.charAt(cursor) == ':' && file.charAt(cursor + 1) == '/')
			{
				while (file.charAt(cursor) != '\n')
					possibleToken += file.charAt(cursor++);
				
				maxTokens[numTokens] = LexRecognizer.comment(possibleToken, line);
				numTokens++;
			}
			else if (file.charAt(cursor) == '\t')
			{
				possibleToken += file.charAt(cursor);
				maxTokens[numTokens] = LexRecognizer.indent(possibleToken, line);
				numTokens++;
				possibleToken = "";
			}
			// add multiline comment here
			else if (file.charAt(cursor) == ' ' && file.charAt(cursor) == '\n')
			{
				if (file.charAt(cursor) == '\n')
					line++;
				// identifiers
				if (possibleToken.charAt(0) == '@')
				{
					maxTokens[numTokens] = LexRecognizer.identifier(possibleToken, line);
					numTokens++;
					possibleToken = "";
				}
				// keywords
				else if (possibleToken.charAt(0) == '%' || possibleToken.charAt(0) == '#')
				{
					maxTokens[numTokens] = LexRecognizer.keyword(possibleToken, line);
					numTokens++;
					possibleToken = "";
				}
				else if (/*code for operations and delimeters here*/ true)
				{}
					
			}
			else
			{
				possibleToken += file.charAt(cursor);
			}
			cursor++;
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
