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
	Node<String> varNode = null;
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
		statementNode.data = "<STATEMENT>";
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
					// for();
					break;
				case "KEYWORD_SWITCH":
					// switch();
					break;
				case "IDENT":
					assign(statementNode);
					break;
				case "NOISE_GO":
					go(statementNode);
					break;
				default:
					System.out.println("Line: " + token.getLineNumber() + " | Error: Invalid start of statement.");
					break;
			}
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
			
			nextToken();
			newline(declarationNode);
			statement(programNode);
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
			newline(inputNode);
			statement(programNode);
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
			///// NEWLINE
			newline(inputNode);
			statement(programNode);
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
			newline(goNode);
			statement(programNode);
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
			//switch (lexer.lookahead().getTokenName())
			//{
				//case "SPACE":
					// expression();
				//	break;
			//	case "NEWLINE":
					var(assignNode);
					nextToken();
				//	break;
			//}
			////// NEWLINE
			newline(assignNode);
			statement(programNode);
		}
	}
	
	public void expression(Node<String> parent)
	{
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
					/// math expression
					break;
				case "REL_OP_LESS":
				case "REL_OP_GREATER":
				case "REL_OP_LESSEQUAL":
				case "REL_OP_GREATEREQUAL":
				case "REL_OP_EQUALTO":
				case "REL_OP_NOTEQUAL":
				case "LOG_OP_OR":
				case "LOG_OP_AND":
					//// relational expression
					break;
			}
		}
	}
	
	public void mathExpression(Node<String> parent)
	{
		mathExpressionNode = new Node<String>();
		mathExpressionNode.data = "<MATH_EXPR>";
		
		parent.children.add(mathExpressionNode);
		
		//// ADDING LEAF OR CONSTANT TO THE LEAF NODe
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
		//// SPACE
		nextToken();
		space(mathExpressionNode);
		//// ADD OR SUBTRACT OR OTHERS
		nextToken();
		switch(token.getTokenName())
		{
			case "ARITH_OP_ADD":
			case "ARITH_OP_SUBT":
				leafNode = new Node<String>();
				leafNode.data = token.getTokenAttribute();
				mathExpressionNode.children.add(leafNode);
				/// SPACE
				nextToken();
				if (token.getTokenName().equals("SPACE"))
				{
					nextToken();
					mathExpression(mathExpressionNode);
				}
				break;
			case "ARITH_OP_MULT":
			case "ARITH_OP_DIVIDE":
			case "ARITH_OP_MOD":
			case "ARITH_OP_DIV":
			case "INTEGER":
			case "DECIMAL":
			case "IDENT":
				term(mathExpressionNode);
				break;
		}
	}
	
	public void term(Node<String> parent)
	{
		termNode = new Node<String>();
		termNode.data = "<TERM>";
		
		parent.children.add(termNode);
		
		switch(token.getTokenName())
		{	
			case "ARITH_OP_MULT":
			case "ARITH_OP_DIVIDE":
			case "ARITH_OP_MOD":
			case "ARITH_OP_DIV":
				leafNode = new Node<String>();
				leafNode.data = token.getTokenAttribute();
				termNode.children.add(leafNode);
				/// SPACE
				nextToken();
				if (token.getTokenName().equals("SPACE"))
				{
					nextToken();
					term(termNode);
				}
				break;
			case "ARITH_OP_EXPON":
			case "INTEGER":
			case "DECIMAL":
			case "IDENT":
				factor(termNode);
				break;
		}
	}
	
	public void factor(Node<String> parent)
	{
		factorNode = new Node<String>();
		factorNode.data = "<FACTOR>";
		
		parent.children.add(factorNode);
		
		switch(token.getTokenName())
		{	
			case "ARITH_OP_EXPON":
				leafNode = new Node<String>();
				leafNode.data = token.getTokenAttribute();
				factorNode.children.add(leafNode);
				/// SPACE
				nextToken();
				if (token.getTokenName().equals("SPACE"))
				{
					nextToken();
					factor(factorNode);
				}
				break;
			case "INTEGER":
			case "DECIMAL":
				constant(factorNode);
				nextToken();
				break;
			case "IDENT":
				var(factorNode);
				nextToken();
				break;
			case "ARITH_OP_ADD":
			case "ARITH_OP_SUBT":
			case "ARITH_OP_MULT":
			case "ARITH_OP_DIVIDE":
			case "ARITH_OP_MOD":
			case "ARITH_OP_DIV":	
				mathExpression(factorNode);
				
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
