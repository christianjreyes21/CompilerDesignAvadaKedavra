package AnalysisPhase.SyntaxAnalysis;

import AnalysisPhase.LexicalAnalysis.InputOutput;
import AnalysisPhase.LexicalAnalysis.Lex;
import AnalysisPhase.LexicalAnalysis.Token;
import Utilities.Node;



public class SyntaxAnalyzer {

	// in order na to according sa documentation
	// in order din sa kung paano gagawing yung functions (R.I.P)
	
	Node programNode = null;
	Node functionNode = null;
	Node mainFunctionNode = null;
	Node subFunctionsNode = null;
	Node subFunctionNode = null;
	Node paramsNode = null;
	Node statementsNode = null;
	Node moreStatementNode = null;
	Node statementNode = null;
	Node declarationNode = null;
	Node assignmentNode = null;
	Node ioNode = null;
	Node controlFlowNode = null;
	Node expressionStatementsNode = null;
	Node returnNode = null;
	Node dataTypeNode = null;
	Node inputStatement = null;
	Node outputStatement = null;
	Node conditionalNode = null;
	Node loopingNode = null;
	Node expressionsNode = null;
	Node callParametersNode = null;
	Node switchStatement = null;
	Node<String> forStatement = null;
	Node incrementStatement = null;
	Node decrementStatement = null;
	Node postIncrementStatement = null;
	Node postDecrementStatement = null;
	Node mathExpressionStatement = null;
	Node mathExpression2Statement = null;
	Node mathExpression3Statement = null;
	Node mathExpression4Statement = null;
	Node stringExpressionStatement = null;
	Node relationalExpressionStatement = null;
	Node relationalExpression2Statement = null;
	Node relationalExpression3Statement = null;
	Node relationalExpression4Statement = null;
	Node relationalExpression5Statement = null;
	Node valueNode = null;
	Node constantNode = null;
	Node booleanConstantNode = null;
	
	Token[] token;
	int tokenCount = 0;
	
	public void analyze()
	{
		token = Lex.Lex(InputOutput.getText("a.hp"));
		System.out.println("analyze"+token.length);
		while (tokenCount < token.length)
		{
			//System.out.println(token[tokenCount].getTokenName());
			if((token[tokenCount].getTokenName()).equals("RESERVEDWORD"))
			{
				System.out.println("hello");
				lineIdentifier();
			}
			tokenCount++;
		}
	}
	
	public void lineIdentifier()
	{
		nextToken(); // SPACE after IDENTIFIER
		if(!nextToken().getTokenAttribute().equals("=") && !token[tokenCount].getTokenAttribute().equals(";")) // NAME DAPAT ITO NG VARIABLE
		{
			System.out.println(token[tokenCount].getTokenAttribute());
			if(nextToken().getTokenAttribute().equals(";") || token[tokenCount].getTokenName().equals("NEWLINE"))
			{
				System.out.println("PUTANGINA MO SAGUM NAKITA NYA NA YUNG IDENTIFIER!!!");
				System.out.println("IDENTIFIER: "+ token[tokenCount-3].getTokenAttribute() +" VarName: "+ token[tokenCount-1].getTokenAttribute());
			}
			else if(token[tokenCount].getTokenName().equals("SPACE") && nextToken().getTokenAttribute().equals("=") && nextToken().getTokenName().equals("SPACE") && nextToken().getTokenName().equals("ERROR") && (nextToken().getTokenAttribute().equals(";") || token[tokenCount].getTokenName().equals("NEWLINE")))
			{
				System.out.println("PUTANGINA MO SAGUM NAKITA NYA NA YUNG IDENTIFIER WITH VALUE!!!");
				System.out.println("IDENTIFIER: "+ token[tokenCount-7].getTokenAttribute() +" VarName: "+ token[tokenCount-5].getTokenAttribute() +" Value: "+ token[tokenCount-1].getTokenAttribute());
			}
		}
	}
	
	public Token nextToken()
	{
		tokenCount++;
		return token[tokenCount];
	}
}
