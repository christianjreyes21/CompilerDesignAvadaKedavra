package AnalysisPhase.SyntaxAnalysis;

import Utilities.Node;
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
	
	public static void main(String args[])
	{
		AvadaKedavraParser akp = new AvadaKedavraParser();
		akp.program();
	}

	//// START PARSING FROM HERE <3
	public void program()
	{
		programNode = new Node<String>();
		programNode.data = "<PROGRAM>";
		
		statement(programNode);
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
					// input();
					break;
				case "KEYWORD_OUTPUT.PRINT":
				case "KEYWORD_OUTPUT.PRINTLN":
					// output();
					break;
				case "KEYWORD_FOR":
					// for();
					break;
				case "KEYWORD_SWITCH":
					// switch();
					break;
				case "IDENTIFIER":
					// assign();
					break;
				case "NOISE_GO":
					// go();
					break;
				default:
					System.out.println("Line: " + token.getLineNumber() + " | Error: Invalid start of statement.");
					break;
			}
		}
		else
		{
			System.out.println(programNode.toString());
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
			/// VARIABLE
			nextToken();
			var(declarationNode);
			nextToken();
			newline(declarationNode);
			statement(programNode);
		}
		else
		{
			System.out.println(programNode.toString());
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
			
		}
		else
		{
			System.out.println(programNode.toString());
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
				
				varNode.children.add(varNode);
			}
			else
			{
				System.out.println("Line: " + token.getLineNumber() + " | Error: Identifier missing");
			}
		}
		else
		{
			System.out.println(programNode.toString());
		}
	}
	
	public void space(Node<String> parent)
	{
		if (token != null)
		{
			spaceNode = new Node<String>();
			spaceNode.data = "<SPACE>";
			
			parent.children.add(spaceNode);
			
			//// ADD SPACE TO THE SPACENODE
			if (token.getTokenName().equals("SPACE"))
			{
				leafNode = new Node<String>();
				leafNode.data = "space";
				
				spaceNode.children.add(leafNode);
			}
			else
			{
				System.out.println("Line: " + token.getLineNumber() + " | Error: Space missing");
			}
		}
		else
		{
			System.out.println(programNode.toString());
		}
	}
	
	public void newline(Node<String> parent)
	{
		if (token != null)
		{
			spaceNode = new Node<String>();
			spaceNode.data = "<NEWLINE>";
			
			parent.children.add(spaceNode);
			
			/// ADD NEWLINE TO THE NEWLINENODE
			if (token.getTokenName().equals("NEWLINE"))
			{
				leafNode = new Node<String>();
				leafNode.data = "newline";
				
				spaceNode.children.add(leafNode);
			}
			else
			{
				System.out.println("Line: " + token.getLineNumber() + " | Error: Newline missing");
			}
		}
		else
		{
			System.out.println(programNode.toString());
		}
	}
}
