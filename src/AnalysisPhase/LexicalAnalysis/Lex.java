package AnalysisPhase.LexicalAnalysis;

import javax.swing.JOptionPane;

import AnalysisPhase.SyntaxAnalysis.SyntaxAnalyzer;

public class Lex {

	String tokenNames[] = {	"IDENTIFIER", // identifiers  
							"KEYWORD", // keywords (BOOLEAN , SWITCH , CASE , STOP , DEFAULT , FOR , OUTPUT.PRINT , OUTPRINT.PRINTLN , INPUT.GET )
							"RESERVEDWORD", // INTEGER , DECIMAL , CHARACTER , STRING , BOOLEAN
							"BOOLEAN", //boolean values (true, false)
							"REL_OP", // relational operators ( < , > , <= , >=, == , != )
							"ARITH_OP", // arithmetic operators ( + , - , / , * , MOD, DIV, ^ )
							"LOG_OP", // logical operators ( AND , OR , NOT )
							"COMMENT", // comments 
							"NOISE", // noise words ( GOTO )
							"DELIM", // ( ) " ' , ; 
							"INDENT", // \t
							"NUMBER", // 0 1 2 3 4 5 6 7 8 9
							"SPACE",
							"NEWLINE",
							"ERROR" // error or unrecognized lexemes
							};
	
	public static Token[] Lex(String file)
	{
		int line = 1;
		int numTokens = 0;
		Token[] maxTokens = new Token[999999];
		Token[] trimmedTokens;
		String possibleToken = "";
		
		
		// reading the whole file (medyo ito yung scanner)
		for (int i = 0; i < file.length(); i++)
		{
			// reads characters from start of a character to a space or newline
			if (!(file.charAt(i) == ' ' || file.charAt(i) == '\n' || file.charAt(i) == '\t'))
				possibleToken += file.charAt(i);
			// reads tab (indent)
			System.out.println(possibleToken);
			if (file.charAt(i) == '\t')
			{
				maxTokens[numTokens] = LexRecognizer.indent("", line);
				numTokens++;
				possibleToken = "";
				continue;
			}
			else if (file.charAt(i) == ' ' || file.charAt(i) == '\n')
			{
				// for newlines and spaces that do not have a preceeding symbol or token or word
				if (possibleToken.length() == 0)
				{
					possibleToken += file.charAt(i);
					maxTokens[numTokens] = LexRecognizer.blank(possibleToken, line);
					numTokens++;
					possibleToken = "";
					if (file.charAt(i) == '\n')
						line++;
					continue;
				}
				
				// delimeters
				if (possibleToken.charAt(0) == ':' || possibleToken.charAt(0) == ';' || possibleToken.charAt(0) == ',' || possibleToken.charAt(0) == '(' || possibleToken.charAt(0) == ')' || possibleToken.charAt(0) == '\'' || possibleToken.charAt(0) == '"')
				{
					//System.out.println("IN");
					if (possibleToken.length() == 2 && (possibleToken.charAt(0) == ':' || possibleToken.charAt(0) == '('))
					{
						if (possibleToken.charAt(0) == '(' && possibleToken.charAt(1) == ':')
						{
							while (i < file.length() - 2)
							{
								System.out.println(possibleToken);
								// pag may newline sa comment diba. para madagdagan parin yung line counter
								if (file.charAt(i) == '\n')
									line++;
								
								if (file.charAt(i) == ':' && file.charAt(i+1) == ')')
								{
									possibleToken += file.charAt(i);
									possibleToken += file.charAt(i + 1);
									i = i + 2;
									System.out.println(possibleToken);
									break;
								}
								possibleToken += file.charAt(i);
								i++;
							}
						}
						else if (possibleToken.charAt(0) == ':' && possibleToken.charAt(1) == '/')
						{
							while (file.charAt(i) != '\n')
							{
								possibleToken += file.charAt(i);
								i++;
							}
						}
						
						maxTokens[numTokens] = LexRecognizer.comment(possibleToken, line);
					}
					else
						maxTokens[numTokens] = LexRecognizer.delim(possibleToken, line);
					
					numTokens++;
					possibleToken = "";
				}
				// identifier
				else if (possibleToken.charAt(0) == '@')
				{
					//System.out.println("IN");
					maxTokens[numTokens] = LexRecognizer.identifier(possibleToken, line);
					numTokens++;
					possibleToken = "";
				}
				// keywords
				else if (possibleToken.charAt(0) == '%')
				{
					//System.out.println("IN");
					maxTokens[numTokens] = LexRecognizer.keyword(possibleToken, line);
					numTokens++;
					possibleToken = "";
				}
				// reserved words
				else if (possibleToken.charAt(0) == '#')
				{
					//System.out.println("IN");
					maxTokens[numTokens] = LexRecognizer.reservedWord(possibleToken, line);
					numTokens++;
					possibleToken = "";
				}
				// arith, rela, and logi operators
				else if (possibleToken.charAt(0) == '!' || possibleToken.charAt(0) == '=' || possibleToken.charAt(0) == '+' || possibleToken.charAt(0) == '-' || possibleToken.charAt(0) == '/' || possibleToken.charAt(0) == '*' || possibleToken.charAt(0) == '>' || possibleToken.charAt(0) == '<' || possibleToken.charAt(0) == '^' || possibleToken.charAt(0) == '$' || possibleToken.charAt(0) == '&')
				{
					// this if is for negative numbers
					if (possibleToken.charAt(0) == '-' && possibleToken.length() > 1)
					{
						if(possibleToken.length() == 2 && possibleToken.charAt(1) == '-')
						{
							maxTokens[numTokens] = LexRecognizer.operator(possibleToken, line);
							numTokens++;
							possibleToken = "";
						}
						else
						{
							maxTokens[numTokens] = LexRecognizer.number(possibleToken, line);
							numTokens++;
							possibleToken = "";
						}
					}
					else
					{
						maxTokens[numTokens] = LexRecognizer.operator(possibleToken, line);
						numTokens++;
						possibleToken = "";
					}
				}
				// noise
				else if (possibleToken.charAt(0) == '?')
				{
					maxTokens[numTokens] = LexRecognizer.noise(possibleToken, line);
					numTokens++;
					possibleToken = "";
				}
				// numbers
				else if (possibleToken.charAt(0) == '0' || possibleToken.charAt(0) == '1' || possibleToken.charAt(0) == '2' || possibleToken.charAt(0) == '3' || possibleToken.charAt(0) == '4' || possibleToken.charAt(0) == '5' || possibleToken.charAt(0) == '6' || possibleToken.charAt(0) == '7' || possibleToken.charAt(0) == '8' || possibleToken.charAt(0) == '9')
				{
					maxTokens[numTokens] = LexRecognizer.number(possibleToken, line);
					numTokens++;
					possibleToken = "";
				}
				// anything else na erroneous
				else if ((possibleToken.charAt(0) == 'A' || possibleToken.charAt(0) == 'B' || possibleToken.charAt(0) == 'C' || possibleToken.charAt(0) == 'D' || possibleToken.charAt(0) == 'E' || possibleToken.charAt(0) == 'F' || possibleToken.charAt(0) == 'G' || possibleToken.charAt(0) == 'H' || possibleToken.charAt(0) == 'I' || possibleToken.charAt(0) == 'J' || possibleToken.charAt(0) == 'K' || possibleToken.charAt(0) == 'L' || possibleToken.charAt(0) == 'M' || possibleToken.charAt(0) == 'N' || possibleToken.charAt(0) == 'O' || possibleToken.charAt(0) == 'P' || possibleToken.charAt(0) == 'Q' || possibleToken.charAt(0) == 'R' || possibleToken.charAt(0) == 'S' || possibleToken.charAt(0) == 'T' || possibleToken.charAt(0) == 'U' || possibleToken.charAt(0) == 'V' || possibleToken.charAt(0) == 'W' || possibleToken.charAt(0) == 'X' || possibleToken.charAt(0) == 'Y' || possibleToken.charAt(0) == 'Z' || possibleToken.charAt(0) == 'a' || possibleToken.charAt(0) == 'b' || possibleToken.charAt(0) == 'c' || possibleToken.charAt(0) == 'd' || possibleToken.charAt(0) == 'e' || possibleToken.charAt(0) == 'f' || possibleToken.charAt(0) == 'g' || possibleToken.charAt(0) == 'h' || possibleToken.charAt(0) == 'i' || possibleToken.charAt(0) == 'j' || possibleToken.charAt(0) == 'k' || possibleToken.charAt(0) == 'l' || possibleToken.charAt(0) == 'm' || possibleToken.charAt(0) == 'n' || possibleToken.charAt(0) == 'o' || possibleToken.charAt(0) == 'p' || possibleToken.charAt(0) == 'q' || possibleToken.charAt(0) == 'r' || possibleToken.charAt(0) == 's' || possibleToken.charAt(0) == 't' || possibleToken.charAt(0) == 'u' || possibleToken.charAt(0) == 'v' || possibleToken.charAt(0) == 'w' || possibleToken.charAt(0) == 'x' || possibleToken.charAt(0) == 'y' || possibleToken.charAt(0) == 'z'))
				{
					Token error = new Token();
					maxTokens[numTokens] = LexRecognizer.charOrString(possibleToken, line);
					numTokens++;
					possibleToken = "";
				}
				
				// for newlines and spaces that have a preceeding symbol or token or word
				possibleToken += file.charAt(i);
				maxTokens[numTokens] = LexRecognizer.blank(possibleToken, line);
				numTokens++;
				possibleToken = "";
				if (file.charAt(i) == '\n')
					line++;
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
		String file = JOptionPane.showInputDialog("Please enter the full path of the file");
		Token[] symbolTable;
		//System.out.println(file);
		if (file.charAt(file.length() - 2) == 'h' && file.charAt(file.length() - 1) == 'p')
		{
			symbolTable = Lex(InputOutput.getText(file));
			SyntaxAnalyzer syntax = new SyntaxAnalyzer();
			syntax.analyze();
			for (int i = 0; i < symbolTable.length; i++)
			{
				//System.out.println(symbolTable[i].lineNumber + " " + symbolTable[i].tokenName + " " + symbolTable[i].tokenAttribute);
			}
			InputOutput.writeText(symbolTable);
		}
		else
			System.out.println("INVALID FILE. PLEASE ENTER A .HP FILE");	
	}
}
