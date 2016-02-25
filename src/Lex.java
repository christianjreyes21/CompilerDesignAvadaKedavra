
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
		String possibleToken = "";
		
		// reading the whole file (medyo ito yung scanner)
		for (int i = 0; i < file.length(); i++)
		{
			possibleToken += file.charAt(i);
			System.out.println(possibleToken);
			if (file.charAt(i) == ' ' || file.charAt(i) == '\n')
			{	
				if (possibleToken.charAt(0) == '@')
				{
					System.out.println("IN");
					maxTokens[numTokens] = LexRecognizer.identifier(possibleToken, line);
					numTokens++;
					possibleToken = "";
				}
				else if (possibleToken.charAt(0) == '#' || possibleToken.charAt(0) == '%')
				{
					System.out.println("IN");
					maxTokens[numTokens] = LexRecognizer.keyword(possibleToken, line);
					numTokens++;
					possibleToken = "";
				} 
				

				if (file.charAt(i) == '\n')
				{
					line++;
					possibleToken ="";
				}
				/*else if (possibleToken.charAt(i) == ':' && possibleToken.charAt(i + 1) == '/')	
				{
				
				}*/
				// operators here paaa
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
		
		System.out.println("\nToken Name   Line Number   Token Attribute");
		for (int i = 0; i < symbolTable.length; i++)
		{
			System.out.println(symbolTable[i].tokenName + " " + symbolTable[i].lineNumber + " " + symbolTable[i].tokenAttribute);
		}
	}
	
	
}
