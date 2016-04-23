package AnalysisPhase.SyntaxAnalysis;

import java.io.IOException;

import Utilities.Node;
import Utilities.ParseTreeGenerator;
import AnalysisPhase.LexicalAnalysis.InputOutput;
import AnalysisPhase.LexicalAnalysis.Lex;
import AnalysisPhase.LexicalAnalysis.Token;

public class AvadaKedavraParser {
	
	public static Lex lexer;
	public Token token;
	Node<String> programNode = null;
	Node<String> statementNode = null;
	Node<String> declarationNode = null;
	Node<String> assignNode = null;
	Node<String> inputNode = null;
	Node<String> outputNode = null;
	Node<String> forNode = null;
	Node<String> switchNode = null;
	Node<String> caseListNode = null;
	Node<String> caseNode = null;
	Node<String> defaultNode = null;
	Node<String> expressionNode = null;	
	Node<String> mathExpressionNode = null;
	Node<String> termNode = null;
	Node<String> factorNode = null;
	Node<String> parenthesisNode = null;	
	Node<String> relExpressionNode = null;
	Node<String> relExpression2Node = null;
	Node<String> relExpression3Node = null;
	Node<String> relExpression4Node = null;
	Node<String> varNode = null;
	Node<String> postIncrementNode = null;
	Node<String> postDecrementNode = null;
	Node<String> boolConstNode = null;
	Node<String> mathConstNode = null;
	Node<String> charConstNode = null;
	Node<String> stringConstNode = null;
	Node<String> spaceNode = null;
	Node<String> dataTypeNode = null;
	Node<String> goNode = null;
	Node<String> leafNode = null;
	
	public AvadaKedavraParser ()
	{
		lexer = new Lex();
		lexer.Lex(InputOutput.getText("a.hp"));
	}
	
	public void nextToken()
	{
		token = lexer.nextToken();
		if (token != null) System.out.println(token.getLineNumber() + " " + token.getTokenName());
	}
	
	public static void main(String args[]) throws IOException
	{
		AvadaKedavraParser akp = new AvadaKedavraParser();
		akp.program();
	}

	//// START PARSING FROM HERE <3
	public void program() throws IOException
	{
		programNode = new Node<String>();
		programNode.data = "<PROGRAM>";
		
		statement(programNode);
		//System.out.println(programNode.toString());
		ParseTreeGenerator ptg = new ParseTreeGenerator();
		ptg.generateTree(programNode);
	}
	
	public void statement(Node<String> parent)
	{
		statementNode = new Node<String>();
		statementNode.parent = parent;
		statementNode.data = "<STATEMENTS>";
		parent.children.add(statementNode);
		
		nextToken();
		if (token != null)
		{
			switch (token.getTokenName())
			{
				case "RESERVEDWORD_INTEGER":
				case "RESERVEDWORD_STRING":
				case "RESERVEDWORD_CHARACTER":
				case "RESERVEDWORD_DECIMAL":
				case "RESERVEDWORD_BOOLEAN":
					declaration(statementNode);
					break;
				case "KEYWORD_INPUT.GET":
					input(statementNode);
					break;
				case "KEYWORD_OUTPUT.PRINT":
				case "KEYWORD_OUTPUT.PRINTLN":
					output(statementNode);
					break;
				case "KEYWORD_FOR":
					forLoop(statementNode);
					break;
				case "KEYWORD_SWITCH":
					// switch();
					break;
				case "IDENT":
					switch (lexer.lookahead2().getTokenName())
					{
						case "ARITH_OP_ASSIGN":
							assign(statementNode);
							break;
						case "ARITH_OP_INCRE":
							postIncrement(statementNode);
							break;
						case "ARITH_OP_DECRE":
							postDecrement(statementNode);
							break;
					}
					break;
				case "NOISE_GO":
					go(statementNode);
					break;
				default:
					System.out.println("Line: " + token.getLineNumber() + " | Error: Invalid start of statement.");
					break;
			}
			newline(statementNode);
		}
	}
	
	public void declaration(Node<String> parent)
	{
		if (token != null)
		{
			declarationNode = new Node<String>();
			declarationNode.data = "<DECLARATION>";
			parent.children.add(declarationNode);

			/// ADD DATA TYPE TO THE LEAF NODE
			leafNode = new Node<String>();
			leafNode.data = token.getTokenAttribute();
			declarationNode.children.add(leafNode);
			
			/// SPACE
			nextToken();
			space(declarationNode);
			nextToken();
			/// VARIABLE
			var(declarationNode);
			
			//nextToken();
			//newline(declarationNode);
			////statement(parent);
		}
	}
	
	public void input(Node<String> parent)
	{
		if (token != null)
		{
			inputNode = new Node<String>();
			inputNode.data = "<INPUT_STMT>";
			
			parent.children.add(inputNode);
			
			//// ADD INPUT KEYWORD TO LEAF NODE
			leafNode = new Node<String>();
			leafNode.data = token.getTokenAttribute();
			
			inputNode.children.add(leafNode);
			//// SPACE
			nextToken();
			space(inputNode);
			nextToken();
			//// LEFT PARENTHESIS
			lparen(inputNode);
			nextToken();
			//// SPACE
			space(inputNode);
			nextToken();
			///// VAR
			var(inputNode);
			nextToken();
			///// SPACE
			space(inputNode);
			nextToken();
			///// RIGHT PARENTHESIS
			rparen(inputNode);
			nextToken();
			///// NEWLINE
			//newline(inputNode);
			/////statement(parent);
		}
	}
	
	public void output(Node<String> parent)
	{
		if (token != null)
		{
			inputNode = new Node<String>();
			inputNode.data = "<OUTPUT_STMT>";
			
			parent.children.add(inputNode);
			
			//// ADD INPUT KEYWORD TO LEAF NODE
			leafNode = new Node<String>();
			leafNode.data = token.getTokenAttribute();
			
			inputNode.children.add(leafNode);
			//// SPACE
			nextToken();
			space(inputNode);
			nextToken();
			//// LEFT PARENTHESIS
			lparen(inputNode);
			nextToken();
			//// SPACE
			space(inputNode);
			nextToken();
			///// VAR
			var(inputNode);
			nextToken();
			///// SPACE
			space(inputNode);
			nextToken();
			///// RIGHT PARENTHESIS
			rparen(inputNode);
			nextToken();
			//nextToken();
			///// NEWLINE
			//newline(inputNode);
			//////statement(parent);
		}
	}
	
	public void go(Node<String> parent)
	{
		if (token != null)
		{
			goNode = new Node<String>();
			goNode.data = "<GOTO_STMT>";
			
			parent.children.add(goNode);

			///// ADD GO STATEMENT TO LEAF NODE
			leafNode = new Node<String>();
			leafNode.data = token.getTokenAttribute();
			
			goNode.children.add(leafNode);
			/// SPACE
			nextToken();
			space(goNode);
			//// VAR
			nextToken();
			var(goNode);
			//// NEWLINE
			nextToken();
			//newline(goNode);
			/////statement(parent);
		}
	}
	
	public void assign(Node<String> parent)
	{
		if (token != null)
		{
			assignNode = new Node<String>();
			assignNode.data = "<ASSIGN_STMT>";
			
			parent.children.add(assignNode);
			
			///// ADD VAR TO ASSIGN NODE\
			var(assignNode);
			nextToken();
			///// SPACE
			space(assignNode);
			nextToken();
			///// ADD EQUAL_OP TO ASSIGN NODE
			equal(assignNode);
			nextToken();
			//// SPACE
			space(assignNode);
			nextToken();
			//// VAR, EXPR, CONST
			switch (lexer.lookahead2().getTokenName())
			{
				case "ARITH_OP_ADD":
				case "ARITH_OP_SUBT":
				case "ARITH_OP_MULT":
				case "ARITH_OP_DIVIDE":
				case "ARITH_OP_MOD":
				case "ARITH_OP_DIV":
				case "ARITH_OP_EXPON":
				case "REL_OP_LESS":
				case "REL_OP_GREATER":
				case "REL_OP_LESSEQUAL":
				case "REL_OP_GREATEREQUAL":
				case "REL_OP_EQUALTO":
				case "REL_OP_NOTEQUAL":
				case "LOG_OP_OR":
				case "LOG_OP_AND":
					expression(assignNode);
					break;
				default:
					switch (token.getTokenName())
					{
						case "INTEGER":
						case "BOOLEAN":
						case "STRING":
						case "CHARACTER":
						case "DECIMAL":
							constant(assignNode);
							break;
						case "IDENT":
							var(assignNode);
							break;
					}
			}
			////// NEWLINE
			//newline(assignNode);
			/////statement(parent);
		}
	}
	
	public void expression(Node<String> parent)
	{
		expressionNode = new Node<String>();
		expressionNode.data = "<EXPR>";
		parent.children.add(expressionNode);
		
		if (token != null)
		{
			//// DECIDE IF MATH OR LOGICAL
			switch (lexer.lookahead2().getTokenName())
			{
				case "ARITH_OP_ADD":
				case "ARITH_OP_SUBT":
				case "ARITH_OP_MULT":
				case "ARITH_OP_DIVIDE":
				case "ARITH_OP_MOD":
				case "ARITH_OP_DIV":
				case "ARITH_OP_EXPON":
					//// GETTING THE FIRST TOKEN OF THE EXPRESSION
					mathExpressionNode = new Node<String>();
					mathExpressionNode.data = "<MATH_EXPR>";
					/// ADDING THE MATH EXPRESSION SUBTREE TO THE EXPRESSION TREE
					expressionNode.children.add(mathExpressionNode);
					switch (token.getTokenName())
					{
						case "DECIMAL":
						case "INTEGER":
							constant(mathExpressionNode);
							break;
						case "IDENT":
							var(mathExpressionNode);
							break;
					}
					nextToken();
					/// SPACE AFTER THE FIRST TOKEN
					space(mathExpressionNode);
					nextToken();
					/// FUNCTION CALLING OF ALL THE EXPRESSIONS FOR MATH
					mathExpression(expressionNode);
					break;
				case "REL_OP_LESS":
				case "REL_OP_GREATER":
				case "REL_OP_LESSEQUAL":
				case "REL_OP_GREATEREQUAL":
				case "REL_OP_EQUALTO":
				case "REL_OP_NOTEQUAL":
				case "LOG_OP_OR":
				case "LOG_OP_AND":
				//// GETTING THE FIRST TOKEN OF THE EXPRESSION
					relExpressionNode = new Node<String>();
					relExpressionNode.data = "<REL_EXPR>";
					/// ADDING THE REL EXPRESSION SUBTREE TO THE EXPRESSION TREE
					expressionNode.children.add(relExpressionNode);
					switch (token.getTokenName())
					{
						case "DECIMAL":
						case "INTEGER":
							constant(relExpressionNode);
							break;
						case "IDENT":
							var(relExpressionNode);
							break;
					}
					nextToken();
					/// SPACE AFTER THE FIRST TOKEN
					space(relExpressionNode);
					nextToken();
					/// FUNCTION CALLING OF ALL THE EXPRESSIONS FOR MATH
					relationalExpression(expressionNode);					
					//// relational expression
					break;
			}
		}
	}
	
	public void mathExpression(Node<String> parent)
	{
		if (token != null)
		{
			while (token.getTokenName().equals("ARITH_OP_SUBT") || token.getTokenName().equals("ARITH_OP_ADD"))
			{
				leafNode = new Node<String>();
				leafNode.data = token.getTokenAttribute();
				mathExpressionNode.children.add(leafNode);
				//// SPACE
				nextToken();
				space(mathExpressionNode);
				nextToken();
				///// CONSTANTS
				switch (token.getTokenName())
				{
					case "DECIMAL":
					case "INTEGER":
						constant(mathExpressionNode);
						break;
					case "IDENT":
						var(mathExpressionNode);
						break;
				}
				
				nextToken();
				if (token.getTokenName().equals("SPACE"))
				{
					space(mathExpressionNode);
					nextToken();
				}
			}
			
			term(mathExpressionNode);
		}
	}

	public void term(Node<String> parent)
	{
		termNode = new Node<String>();
		termNode.data = "<TERM>";
		parent.children.add(termNode);
		
		if (token != null)
		{
			while (token.getTokenName().equals("ARITH_OP_MOD") || token.getTokenName().equals("ARITH_OP_DIV") || token.getTokenName().equals("ARITH_OP_MULT") || token.getTokenName().equals("ARITH_OP_DIVIDE"))
			{
				leafNode = new Node<String>();
				leafNode.data = token.getTokenAttribute();
				termNode.children.add(leafNode);
				//// SPACE
				nextToken();
				space(termNode);
				nextToken();
				///// CONSTANTS
				switch (token.getTokenName())
				{
					case "DECIMAL":
					case "INTEGER":
						constant(termNode);
						break;
					case "IDENT":
						var(termNode);
						break;
				}
				
				nextToken();
				if (token.getTokenName().equals("SPACE"))
				{
					space(termNode);
					nextToken();
				}
			}
			factor(termNode);
		}
	}
	
	public void factor(Node<String> parent)
	{
		factorNode = new Node<String>();
		factorNode.data = "<FACTOR>";
		parent.children.add(factorNode);
		
		if (token != null)
		{
			while (token.getTokenName().equals("ARITH_OP_EXPON"))
			{
				leafNode = new Node<String>();
				leafNode.data = token.getTokenAttribute();
				factorNode.children.add(leafNode);
				//// SPACE
				nextToken();
				space(factorNode);
				nextToken();
				///// CONSTANTS
				switch (token.getTokenName())
				{
					case "DECIMAL":
					case "INTEGER":
						constant(factorNode);
						break;
					case "IDENT":
						var(factorNode);
						break;
				}
				
				nextToken();
				if (token.getTokenName().equals("SPACE"))
				{
					space(factorNode);
					nextToken();
				}
			}
			
			switch (token.getTokenName())
			{
				case "ARITH_OP_ADD":
				case "ARITH_OP_SUBT":
				case "ARITH_OP_MULT":
				case "ARITH_OP_DIVIDE":
				case "ARITH_OP_MOD":
				case "ARITH_OP_DIV":
					mathExpression(factorNode);
			}
		}
	}
	
	public void postIncrement(Node<String> parent)
	{
		postIncrementNode = new Node<String>();
		postIncrementNode.data = "<INCRE_STMT>";
		parent.children.add(postIncrementNode);
		
		if (token != null)
		{
			/// ADDING VAR
			var(postIncrementNode);
			nextToken();
			//// SPACE
			space(postIncrementNode);
			nextToken();
			///// ADDING POST INCREMENT SUFFIX TO THE LEAF NODE
			leafNode = new Node<String>();
			leafNode.data = token.getTokenAttribute();
			postIncrementNode.children.add(leafNode);
		}
		nextToken();
		/////statement(parent);
	}
	
	public void postDecrement(Node<String> parent)
	{
		postDecrementNode = new Node<String>();
		postDecrementNode.data = "<DECRE_STMT>";
		parent.children.add(postDecrementNode);
		
		if (token != null)
		{
			/// ADDING VAR
			var(postDecrementNode);
			nextToken();
			//// SPACE
			space(postDecrementNode);
			nextToken();
			///// ADDING POST INCREMENT SUFFIX TO THE LEAF NODE
			leafNode = new Node<String>();
			leafNode.data = token.getTokenAttribute();
			postDecrementNode.children.add(leafNode);
		}
		nextToken();
		////statement(parent);
	}
	
	public void forLoop(Node<String> parent)
	{
		forNode = new Node<String>();
		forNode.data = "<FOR_STMT>";
		parent.children.add(forNode);
		
		if (token != null)
		{
			//// ADDING FOR STATEMENT TO LEAF NODE %FOR
			leafNode = new Node<String>();
			leafNode.data = token.getTokenAttribute();
			forNode.children.add(leafNode);
			/// SPACE                      " "
			nextToken();
			space(forNode);
			//// LEFT PARENTHESIS          (
			nextToken();
			lparen(forNode);
			//// SPACE                     " "
			nextToken();
			space(forNode);
			//// ASSIGN STATEMENT           @aa = 1
			nextToken();
			assign(forNode);
			/// SPACE                       " "
			nextToken();
			space(forNode);
			////// SEMICOLON 				;
			nextToken();
			semicolon(forNode);
			//// SPACE						" "
			nextToken();
			space(forNode);
			///// RELATIONAL EXPRESSION		2 < 3
			nextToken();
			switch (lexer.lookahead2().getTokenName())
			{
				case "REL_OP_LESS":
				case "REL_OP_GREATER":
				case "REL_OP_LESSEQUAL":
				case "REL_OP_GREATEREQUAL":
				case "REL_OP_EQUALTO":
				case "REL_OP_NOTEQUAL":
				case "LOG_OP_OR":
				case "LOG_OP_AND":
					relExpressionNode = new Node<String>();
					relExpressionNode.data = "<REL_EXPR>";
					forNode.children.add(relExpressionNode);
					switch (token.getTokenName())
					{
						case "DECIMAL":
						case "INTEGER":
							constant(relExpressionNode);
							break;
						case "IDENT":
							var(relExpressionNode);
							break;
					}
					nextToken();
					/// SPACE AFTER THE FIRST TOKEN
					space(relExpressionNode);
					nextToken();
					/// FUNCTION CALLING OF ALL THE EXPRESSIONS FOR MATH
					relationalExpression(expressionNode);
					break;
				default:
					System.out.println("Line: " + token.getLineNumber() + " | Error: Relational expression expected");
			}
			/////// SPACE					" "
			nextToken();
			space(forNode);
			///// SEMICOLON					;
			nextToken();		
			semicolon(forNode);
			//////// SPACE					" "
			nextToken();
			space(forNode);
			///// POST INCREMENT OR POST DECREMENT	@aa ++
			nextToken();
			switch (lexer.lookahead2().getTokenName())
			{
				case "ARITH_OP_INCRE":
					postIncrement(forNode);
					break;
				case "ARITH_OP_DECRE":
					postDecrement(forNode);
					break;
				default:
					System.out.println("Line: " + token.getLineNumber() + " | Error: Increment or decrement statement expected");
			}
			//// SPACE
			nextToken();
			space(forNode);
			///// RIGHT PARENTHESIS
			nextToken();
			rparen(forNode);
			////// NEWLINE
			nextToken();
			newline(forNode);
			/////// INDENT THEN STATEMENTS
			nextToken();
			while (token.getTokenName().equals("INDENT"))
			{
				leafNode = new Node<String>();
				leafNode.data = "INDENT";
				forNode.children.add(leafNode);
				
				nextToken();
				statement(forNode);
				nextToken();
			}
			nextToken();
		}
	}
	
	public void relationalExpression(Node<String> parent)
	{
		if (token != null)
		{
			while (token.getTokenName().equals("REL_OP_LESS") || token.getTokenName().equals("REL_OP_GREATER") || token.getTokenName().equals("REL_OP_LESSEQUAL") || token.getTokenName().equals("REL_OP_GREATEREQUAL") || token.getTokenName().equals("REL_OP_EQUALTO") || token.getTokenName().equals("REL_OP_NOTEQUALTO") || token.getTokenName().equals("LOG_OP_AND") || token.getTokenName().equals("OR_OP"))
			{
				leafNode = new Node<String>();
				leafNode.data = token.getTokenAttribute();
				relExpressionNode.children.add(leafNode);
				//// SPACE
				nextToken();
				space(relExpressionNode);
				nextToken();
				///// CONSTANTS
				switch (token.getTokenName())
				{
					case "DECIMAL":
					case "INTEGER":
					case "STRING":
					case "CHARACTER":
					case "BOOLEAN":
						constant(relExpressionNode);
						break;
					case "IDENT":
						var(relExpressionNode);
						break;
				}
				
				nextToken();
				if (token.getTokenName().equals("SPACE"))
				{
					space(relExpressionNode);
					nextToken();
				}
			}
		}
	}
	
	public void relExpression2(Node<String> parent)
	{
		relExpression2Node = new Node<String>();
		relExpression2Node.data = "<REL_EXPR2>";
		
		parent.children.add(relExpression2Node);
		if (token != null)
		{
			if (token.getTokenName().equals("LOG_OP_NOT"))
			{
				leafNode = new Node<String>();
				leafNode.data = token.getTokenAttribute();
				relExpression3Node.children.add(leafNode);
				//// SPACE
				nextToken();
				space(relExpression3Node);
				nextToken();
				relationalExpression(relExpression2Node);
			}
		}
	}
	
	public void equal(Node<String> parent)
	{
		if (token != null)
		{
			//// ADD PARENTHESIS TO THE LEAF NODE
			if (token.getTokenName().equals("ARITH_OP_ASSIGN"))
			{
				leafNode = new Node<String>();
				leafNode.data = token.getTokenAttribute();
				
				parent.children.add(leafNode);
			}
			else
			{
				System.out.println("Line: " + token.getLineNumber() + " | Error: Equal operator expected");
			}
		}
	}
	
	public void semicolon(Node<String> parent)
	{
		if (token != null)
		{
			//// ADD PARENTHESIS TO THE LEAF NODE
			if (token.getTokenName().equals("DELIM"))
			{
				leafNode = new Node<String>();
				leafNode.data = token.getTokenAttribute();
				
				parent.children.add(leafNode);
			}
			else
			{
				System.out.println("Line: " + token.getLineNumber() + " | Error: Semicolon expected");
			}
		}
	}
	
	public void constant(Node<String> parent)
	{
		if (token != null)
		{
			/// ADD CONSTANT TO LEAF NODE
			leafNode = new Node<String>();
			leafNode.data = token.getTokenAttribute();
			
			parent.children.add(leafNode);
		}
		else
		{
			System.out.println("Line: " + token.getLineNumber() + " | Error: Constant expected");
		}
	}
	
	public void lparen(Node<String> parent)
	{
		if (token != null)
		{
			//// ADD PARENTHESIS TO THE LEAF NODE
			if (token.getTokenName().equals("DELIM_LPAREN"))
			{
				leafNode = new Node<String>();
				leafNode.data = token.getTokenAttribute();
				
				parent.children.add(leafNode);
			}
			else
			{
				System.out.println("Line: " + token.getLineNumber() + " | Error: Left parenthesis expected");
			}
		}
	}
	
	public void rparen(Node<String> parent)
	{
		if (token != null)
		{
			//// ADD PARENTHESIS TO THE LEAF NODE
			if (token.getTokenName().equals("DELIM_RPAREN"))
			{
				leafNode = new Node<String>();
				leafNode.data = token.getTokenAttribute();
				
				parent.children.add(leafNode);
			}
			else
			{
				System.out.println("Line: " + token.getLineNumber() + " | Error: Right parenthesis expected");
			}
		}
	}
	
	public void var(Node<String> parent)
	{
		if (token != null)
		{
			varNode = new Node<String>();
			varNode.data = "<VAR>";
			parent.children.add(varNode);
			
			
			//// ADD IDENTIFIER TO THE LEAF NODE
			if (token.getTokenName().equals("IDENT"))
			{
				leafNode = new Node<String>();
				leafNode.data = token.getTokenAttribute();
				
				varNode.children.add(leafNode);
			}
			else
			{
				System.out.println("Line: " + token.getLineNumber() + " | Error: Identifier expected");
			}
		}
	}
	
	public void space(Node<String> parent)
	{
		if (token != null)
		{
			//// ADD SPACE TO THE SPACENODE
			if (token.getTokenName().equals("SPACE"))
			{
				leafNode = new Node<String>();
				leafNode.data = "SPACE";
				
				parent.children.add(leafNode);
			}
			else
			{
				System.out.println("Line: " + token.getLineNumber() + " | Error: Space expected");
			}
		}
	}
	
	public void newline(Node<String> parent)
	{
		if (token != null)
		{
			/// ADD NEWLINE TO THE NEWLINENODE
			if (token.getTokenName().equals("NEWLINE"))
			{
				leafNode = new Node<String>();
				leafNode.data = "newline";
				
				parent.children.add(leafNode);
			}
			else
			{
				System.out.println("Line: " + token.getLineNumber() + " | Error: Newline expected");
			}
		}
	}
}
