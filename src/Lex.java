
public class Lex {

	char charSet[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'I', 'j', 'k', 'l', 'm', 'n', 'ñ', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ';' , '=', '+', '-', '*', '/', '\\' , '&', '<', '>' , '(' , ')', '{' , '}', '[', ']' , '#' , ':' ,'\'', '.' , ',' , '%', '^', '!', '@', '~' , '$',  '\"', ' ', '\t' , '\n'};
	
	
	
	String tokenNames[] = {	"IDENT", // identifiers  
							"KEYWORD", // keywords ( INTEGER , DECIMAL , CHARACTER , STRING , BOOLEAN , SWITCH , CASE , STOP , DEFAULT , FOR , OUTPUT.PRINT , OUTPRINT.PRINTLN , INPUT.GET )
							"REL_OP", // relational operators ( < , > , <= , >=, == , != )
							"ARITH_OP", // arithmetic operators ( + , - , / , * , MOD, DIV, ^ )
							"LOG_OP", // logical operators ( AND , OR , NOT )
							"COMMENT", // comments 
							"NOISE", // noise words ( GOTO )
							"DELIM", // ( ) " ' , { } [ ] ; 
							};
	
	public static void main (String args[])
	{
		
		System.out.println(" \t ==LEXICAL ANALYZER==");
		//System.out.println(InputOutput.getText("a.hp"));
		System.out.println(LexRecognizer.keyword(InputOutput.getText("a.hp"), 0).tokenName + "\n" + LexRecognizer.keyword(InputOutput.getText("a.hp"), 0).tokenAttribute);
		
	}
	
	
}
