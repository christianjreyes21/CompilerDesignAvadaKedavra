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
		System.out.println(token.getLineNumber() + " " + token.getTokenName());
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
		switch (token.getTokenName())
		{
			case "RESERVEDWORD_INTEGER":
			case "RESERVEDWORD_STRING":
			case "RESERVEDWORD_CHARACTER":
			case "RESERVEDWORD_DECIMAL":
			case "RESERVEDWORD_BOOLEAN":
				// declaration()
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
	
	public void declaration(Node<String> parent)
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
	}
	
	public void space(Node<String> parent)
	{
		spaceNode = new Node<String>();
		spaceNode.data = "<SPACE>";
		
		parent.children.add(spaceNode);
		
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
	
	public void newline(Node<String> parent)
	{
		spaceNode = new Node<String>();
		spaceNode.data = "<NEWLINE>";
		
		parent.children.add(spaceNode);
		
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
	
	//public void
}
