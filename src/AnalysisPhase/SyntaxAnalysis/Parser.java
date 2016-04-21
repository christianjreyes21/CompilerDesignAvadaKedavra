package AnalysisPhase.SyntaxAnalysis;

import AnalysisPhase.LexicalAnalysis.Token;
import Utilities.Node;

public class Parser {

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
	Node<String> leafNode = null;
	Token nextToken = null;
	
	Token[] token;
	int tokenCount = 0;
	
	public void statement (Node<String> parent)
	{
		Node<String> statementNode = new Node<String>();
		assignNode.data = "<STATEMENT>";
		parent.children.add(statementNode);
		if (nextToken.getTokenName().equals("RESERVEDWORD_INTEGER") || nextToken.getTokenName().equals("RESERVEDWORD_DECIMAL") || nextToken.getTokenName().equals("RESERVEDWORD_CHARACTER") || nextToken.getTokenName().equals("RESERVEDWORD_STRING") || nextToken.getTokenName().equals("RESERVEDWORD_BOOLEAN"))
		{
			declaration(statementNode);
			nextToken = nextToken();
			space(statementNode);
			nextToken = nextToken();
		}
		else if(nextToken.getTokenName().equals("IDENTIFIER"))
		{
			input(statementNode);
			nextToken = nextToken();
			space(statementNode);
			nextToken = nextToken();
		}
		else if (nextToken.getTokenName().equals("KEYWORD_OUTPUT.PRINT") || nextToken.getTokenName().equals("KEYWORD_OUTPUT.PRINTLN"))
		{
			output(statementNode);
			nextToken = nextToken();
			space(statementNode);
			nextToken = nextToken();
		}
		else if (nextToken.getTokenName().equals("KEYWORD_FOR"))
		{
			forLoop(statementNode);
			nextToken = nextToken();
			space(statementNode);
			nextToken = nextToken();
		}
		else if (nextToken.getTokenName().equals("KEYWORD_SWITCH"))
		{
			switchCase(statementNode);
			nextToken = nextToken();
			space(statementNode);
			nextToken = nextToken();
		}
		else if (nextToken.getTokenName().equals("RESERVEDWORD_INTEGER") || nextToken.getTokenName().equals("RESERVEDWORD_DECIMAL") || nextToken.getTokenName().equals("RESERVEDWORD_CHARACTER") || nextToken.getTokenName().equals("RESERVEDWORD_STRING") || nextToken.getTokenName().equals("RESERVEDWORD_BOOLEAN") || nextToken.getTokenName().equals("IDENT"))
		{
			assign(statementNode);
			nextToken = nextToken();
			space(statementNode);
			nextToken = nextToken();
		}
		else
		{
			System.out.println("Syntax Analyzer: Statement Syntax Error; Line Number: " + nextToken.getLineNumber());
		}
		
	}
	
	public void declaration (Node<String> parent)
	{
		Node<String> declarationNode = new Node<String>();
		assignNode.data = "<DECLARATION_STMT>";
		parent.children.add(declarationNode);
		
		//nextToken = nextToken();
		if (nextToken.getTokenName().equals("RESERVEDWORD_INTEGER") || nextToken.getTokenName().equals("RESERVEDWORD_DECIMAL") || nextToken.getTokenName().equals("RESERVEDWORD_CHARACTER") || nextToken.getTokenName().equals("RESERVEDWORD_STRING") || nextToken.getTokenName().equals("RESERVEDWORD_BOOLEAN"))
		{
			dataType(assignNode);
			nextToken = nextToken();
			space(assignNode);
			nextToken = nextToken();
			if (nextToken.getTokenName().equals("IDENT"))
			{
				var(assignNode);
				nextToken = nextToken();
				space(assignNode);
				nextToken = nextToken();
			}
			else
			{
				System.out.println("Syntax Analyzer: Assignment Syntax Error; Identifier Expected; Line Number: " + nextToken.getLineNumber());
			}
		}
		else
		{
			System.out.println("Syntax Analyzer: Assignment Syntax Error; DataType Expected; Line Number: " + nextToken.getLineNumber());
		}
		
		
	}
	
	public void assign (Node<String> parent)
	{
		Node<String> assignNode = new Node<String>();
		assignNode.data = "<ASSIGN_STMT>";
		parent.children.add(assignNode);
		
		
		//nextToken = nextToken();
		if (nextToken.getTokenName().equals("RESERVEDWORD_INTEGER") || nextToken.getTokenName().equals("RESERVEDWORD_DECIMAL") || nextToken.getTokenName().equals("RESERVEDWORD_CHARACTER") || nextToken.getTokenName().equals("RESERVEDWORD_STRING") || nextToken.getTokenName().equals("RESERVEDWORD_BOOLEAN"))
		{
			dataType(assignNode);
			nextToken = nextToken();
			space(assignNode);
			nextToken = nextToken();
		}
		if (nextToken.getTokenName().equals("IDENT"))
		{
			var(assignNode);
			nextToken = nextToken();
			space(assignNode);
			nextToken = nextToken();
			if (nextToken.getTokenName().equals("IDENT"))//IF NG MGA EXPRESSION
			{
				expression(assignNode);
			}
			else
			{
				System.out.println("Syntax Analyzer: Assignment Syntax Error; Identifier Expected; Line Number: " + nextToken.getLineNumber());
			}
		}
		else
		{
			System.out.println("Syntax Analyzer: Assignment Syntax Error; Line Number: " + nextToken.getLineNumber());
		}
	}
	
	public void input (Node<String> parent)
	{
		Node<String> inputNode = new Node<String>();
		inputNode.data = "<INPUT_STMT>";
		parent.children.add(inputNode);
		
		nextToken = nextToken();
		if(nextToken.getTokenName().equals("IDENTIFIER"))
		{
			leafNode = new Node<String>();
			leafNode.data = nextToken.getTokenAttribute();
			parent.children.add(inputNode);
		}
	}
	
	public void output(Node<String> parent)
	{
		Node<String> outputNode = new Node<String>();
		outputNode.data = "<OUTPUT_STMT>";
		parent.children.add(outputNode);
		if (nextToken.getTokenName().equals("KEYWORD_OUTPUT.PRINT"))
		{
			leafNode = new Node<String>();
			leafNode.data = nextToken.getTokenAttribute();
			parent.children.add(leafNode);
			/// expression(outputNode);
		}
		else if (nextToken.getTokenName().equals("KEYWORD_OUTPUT.PRINTLN"))
		{
			leafNode = new Node<String>();
			leafNode.data = nextToken.getTokenAttribute();
			parent.children.add(leafNode);
			///// expressions(outputNode);
		}
		else
		{
			System.out.println("Syntax Analyzer: Invalid symbol detected");
		}
	}
	
	public void forLoop(Node<String> parent)
	{
		forNode = new Node<String>();
		forNode.data = "<FOR_STMT>";
		
		parent.children.add(forNode);
		
		if (nextToken.getTokenName().equals("KEYWORD_FOR"))
		{
			leafNode = new Node<String>();
			leafNode.data = nextToken.getTokenAttribute();
			
			forNode.children.add(leafNode);
			nextToken = nextToken();
			space(forNode);
			nextToken = nextToken();
			
			if (nextToken.getTokenName().equals("DELIM"))
			{
				leafNode = new Node<String>();
				leafNode.data = nextToken.getTokenAttribute();
				
				forNode.children.add(leafNode);
				nextToken = nextToken();
				space(forNode);
				nextToken = nextToken();
				// assignment(forNode);
				space(forNode);
				nextToken = nextToken();
				if (nextToken.getTokenName().equals("DELIM"))
				{
					leafNode = new Node<String>();
					leafNode.data = nextToken.getTokenAttribute();
					
					forNode.children.add(leafNode);
					nextToken = nextToken();
					space(forNode);
					// relExpression(forNode);
					space(forNode);
					nextToken = nextToken();
					
					if (nextToken.getTokenName().equals("DELIM"))
					{
						leafNode = new Node<String>();
						leafNode.data = nextToken.getTokenAttribute();
						
						forNode.children.add(leafNode);
						nextToken = nextToken();
						space(forNode);
						mathExpression(forNode);
						space(forNode);
						nextToken = nextToken();
						
						if (nextToken.getTokenName().equals("DELIM"))
						{
							leafNode = new Node<String>();
							leafNode.data = nextToken.getTokenAttribute();
							
							forNode.children.add(leafNode);
							
							// LOOP THE STATEMENTS HERE
						}
						else
						{
							System.out.println("Syntax Analyzer: Parenthesis is expected; Line Number: " + nextToken.getLineNumber());
						}
					}
					else
					{
						System.out.println("Syntax Analyzer: Semicolon is expected; Line Number: " + nextToken.getLineNumber());
					}
				}
				else
				{
					System.out.println("Syntax Analyzer: Semicolon is expected; Line Number: " + nextToken.getLineNumber());
				}
			}
			else
			{
				System.out.println("Syntax Analyzer: Parenthesis is expected; Line Number: " + nextToken.getLineNumber());
			}
		}
	}
	
	public void switchCase(Node<String> parent)
	{
		switchNode = new Node<String>();
		switchNode.data = "<SWITCH>";
		
		parent.children.add(switchNode);
		
		if (nextToken.getTokenName().equals("KEYWORD_SWITCH"))
		{
			leafNode = new Node<String>();
			leafNode.data = nextToken.getTokenAttribute();
			
			switchNode.children.add(leafNode);
			nextToken = nextToken();
			space(switchNode);
			nextToken = nextToken();
			
			if (nextToken.getTokenName().equals("DELIM"))
			{
				leafNode = new Node<String>();
				leafNode.data = nextToken.getTokenAttribute();
				
				switchNode.children.add(leafNode);
				nextToken = nextToken();
				space(switchNode);
				nextToken = nextToken();
				
				var(switchNode);
				
				nextToken = nextToken();
				space(switchNode);
				nextToken = nextToken();
				
				if (nextToken.getTokenName().equals("DELIM"))
				{
					nextToken = nextToken();
					space(switchNode);
					nextToken = nextToken();
					if (nextToken.getTokenName().equals("INDENT"))
					{
						leafNode = new Node<String>();
						leafNode.data = "INDENT";
						
						switchNode.children.add(leafNode);
						nextToken = nextToken();
						/// IKAW DITO
						while(nextToken.getTokenName().equals("KEYWORD_CASE"));
						{
							cases(switchNode);
						}
					}
					else
					{
						System.out.println("Syntax Analyzer: Indention expected; Line Number: " + nextToken.getLineNumber());
					}
				}
				else
				{
					System.out.println("Syntax Analyzer: Parenthesis expected; Line Number: " + nextToken.getLineNumber());
				}
			}
			else
			{
				System.out.println("Syntax Analyzer: Parenthesis expected; Line Number: " + nextToken.getLineNumber());
			}
		}
	}
	
	public void cases(Node<String> parent)
	{
		caseNode = new Node<String>();
		caseNode.data = "<CASE>";
		
		parent.children.add(caseNode);
		
		if (nextToken.getTokenName().equals("KEYWORD_CASE"))
		{
			leafNode = new Node<String>();
			leafNode.data = nextToken.getTokenAttribute();
			caseNode.children.add(leafNode);
			
			nextToken = nextToken();
			space(caseNode);
			nextToken = nextToken();
			
			if (nextToken.getTokenName().equals("DELIM"))
			{
				leafNode = new Node<String>();
				leafNode.data = nextToken.getTokenAttribute();
				caseNode.children.add(leafNode);
				
				nextToken = nextToken();
				space(caseNode);
				nextToken = nextToken();
				
				//statement(defaultNode);
				
				nextToken = nextToken();
				space(caseNode);
				nextToken = nextToken();
				
				if (nextToken.getTokenName().equals("KEYWORD_STOP"))
				{
					leafNode = new Node<String>();
					leafNode.data = nextToken.getTokenAttribute();
					caseNode.children.add(leafNode);
					
					nextToken = nextToken();
					space(caseNode);
					nextToken = nextToken();
				}
				else
				{
					System.out.println("Syntax Analyzer: Stop expected; Line Number: " + nextToken.getLineNumber());
				}
			}
			else
			{
				System.out.println("Syntax Analyzer: Colon expected; Line Number: " + nextToken.getLineNumber());
			}
		}
		else
		{
			System.out.println("Syntax Analyzer: Case expected; Line Number: " + nextToken.getLineNumber());
		}
		

		System.out.println("Exited Case");
	}
	
	public void caseDefault(Node<String> parent)
	{
		defaultNode = new Node<String>();
		defaultNode.data = "<DEFAULT>";
		
		parent.children.add(defaultNode);
		
		if (nextToken.getTokenName().equals("KEYWORD_DEFAULT"))
		{
			leafNode = new Node<String>();
			leafNode.data = nextToken.getTokenAttribute();
			defaultNode.children.add(leafNode);
			
			nextToken = nextToken();
			space(defaultNode);
			nextToken = nextToken();
			
			if (nextToken.getTokenName().equals("DELIM"))
			{
				leafNode = new Node<String>();
				leafNode.data = nextToken.getTokenAttribute();
				defaultNode.children.add(leafNode);
				
				nextToken = nextToken();
				space(defaultNode);
				nextToken = nextToken();
				
				//statement(defaultNode);
				
				nextToken = nextToken();
				space(defaultNode);
				nextToken = nextToken();
				
				if (nextToken.getTokenName().equals("KEYWORD_STOP"))
				{
					leafNode = new Node<String>();
					leafNode.data = nextToken.getTokenAttribute();
					defaultNode.children.add(leafNode);
					
					nextToken = nextToken();
					space(defaultNode);
					nextToken = nextToken();
				}
				else
				{
					System.out.println("Syntax Analyzer: Stop expected; Line Number: " + nextToken.getLineNumber());
				}
			}
			else
			{
				System.out.println("Syntax Analyzer: Colon expected; Line Number: " + nextToken.getLineNumber());
			}
		}

		System.out.println("Exited Default");
	}
	
	public void expression (Node<String> parent)
	{
		//GAWIN ITO
	}
	
	public void mathExpression(Node<String> parent)
	{
		mathExpressionNode = new Node<String>();
		mathExpressionNode.data = "<MATH_EXPR>";
		
		parent.children.add(mathExpressionNode);
		
		mathConst(mathExpressionNode);
		nextToken = nextToken();
		space(mathExpressionNode);
		nextToken = nextToken();
		
		if (nextToken.getTokenName().equals("ARITH_OP_ADD") || nextToken.getTokenName().equals("ARITH_OP_SUBT"))
		{
			leafNode = new Node<String>();
			leafNode.data = nextToken.getTokenAttribute();
			mathExpressionNode.children.add(leafNode);
			
			nextToken = nextToken();
			space(mathExpressionNode);
			nextToken = nextToken();
			
			mathExpression(mathExpressionNode);
		}
		else if (nextToken.getTokenName().equals("ARITH_OP_MULT") || nextToken.getTokenName().equals("ARITH_OP_DIV") || nextToken.getTokenName().equals("ARITH_OP_MOD") || nextToken.getTokenName().equals("ARITH_OP_DIV") || nextToken.getTokenName().equals("ARITH_OP_EXPON") || nextToken.getTokenName().equals("INTEGER") || nextToken.getTokenName().equals("DECIMAL"))
		{
			term(mathExpressionNode);
		}
		else
		{
			System.out.println("Syntax Analyzer: Invalid symbol detected");
		}
		
		System.out.println("Exited Math Expression");
	}
	
	public void term(Node<String> parent)
	{
		termNode = new Node<String>();
		termNode.data = "<TERM>";
		
		parent.children.add(termNode);
		
		if (nextToken.getTokenName().equals("ARITH_OP_MULT") || nextToken.getTokenName().equals("ARITH_OP_DIV") || nextToken.getTokenName().equals("ARITH_OP_MOD") || nextToken.getTokenName().equals("ARITH_OP_DIV"))
		{
			leafNode = new Node<String>();
			leafNode.data = nextToken.getTokenAttribute();
			termNode.children.add(leafNode);
			
			nextToken = nextToken();
			space(termNode);
			nextToken = nextToken();
			term(termNode);
		}
		else if (nextToken.getTokenName().equals("ARITH_OP_EXPON") || nextToken.getTokenName().equals("INTEGER") || nextToken.getTokenName().equals("DECIMAL"))
		{
			factor(termNode);
		}
		else
		{
			System.out.println("Syntax Analyzer: Invalid symbol detected; Line Number: " + nextToken.getLineNumber());
		}

		System.out.println("Exited Math Expression 2");
	}
	
	public void factor(Node<String> parent)
	{
		factorNode = new Node<String>();
		factorNode.data = "<FACTOR>";
		
		parent.children.add(factorNode);
		
		if (nextToken.getTokenName().equals("ARITH_OP_EXPON"))
		{
			leafNode = new Node<String>();
			leafNode.data = nextToken.getTokenAttribute();
			factorNode.children.add(leafNode);
			
			nextToken = nextToken();
			space(factorNode);
			nextToken = nextToken();
			factor(factorNode);
		}
		else if (nextToken.getTokenName().equals("DELIM_LEFT_PAREN") || nextToken.getTokenName().equals("INTEGER") || nextToken.getTokenName().equals("DECIMAL"))
		{
			parenthesis(factorNode);
		}
		else
		{
			System.out.println("Syntax Analyzer: Invalid symbol detected; Line Number: " + nextToken.getLineNumber());
		}

		System.out.println("Exited Math Expression 3");
	}
	
	public void parenthesis(Node<String> parent)
	{
		parenthesisNode = new Node<String>();
		parenthesisNode.data = "<PARENTHESIS>";
		
		parent.children.add(parenthesisNode);
		
		if (nextToken.getTokenName().equals("DELIM_LEFT_PAREN"))
		{
			if (nextToken.getTokenName().equals("DELIM_LEFT_PAREN"))
			{
				leafNode = new Node<String>();
				leafNode.data = nextToken.getTokenAttribute();
				
				nextToken = nextToken();
				space(parenthesisNode);
				nextToken = nextToken();
			}
			
			mathExpression(parenthesisNode);
			
			if (nextToken.getTokenName().equals("DELIM_RIGHT_PAREN"))
			{
				leafNode = new Node<String>();
				leafNode.data = nextToken.getTokenAttribute();
				
				nextToken = nextToken();
				space(parenthesisNode);
				nextToken = nextToken();
			}
			else
			{
				System.out.println("Syntax Analyzer: Parenthesis expected; Line Number: " + nextToken.getLineNumber());
			}
		} 
		else if (nextToken.getTokenName().equals("INTEGER") || nextToken.getTokenName().equals("DECIMAL"))
		{
			mathConst(parenthesisNode);
		}
		
		System.out.println("Exited Math Expression 4");
	}
	
	public void var(Node<String> parent)
	{
		varNode = new Node<String>();
		varNode.data = "<VAR>";
		
		parent.children.add(varNode);
		
		if (nextToken.getTokenName().equals("IDENT"))
		{
			leafNode = new Node<String>();
			leafNode.data = nextToken.getTokenAttribute();
			
			varNode.children.add(leafNode);
		}
		
		System.out.println("Exiting Var");
	}
	
	public void mathConst(Node<String> parent)
	{
		mathConstNode = new Node<String>();
		mathConstNode.data = "<CONST>";
		
		parent.children.add(mathConstNode);
		
		if (nextToken.getTokenName().equals("INTEGER") || nextToken.getTokenName().equals("DECIMAL") || nextToken.getTokenName().equals("IDENT"))
		{
			leafNode = new Node<String>();
			leafNode.data = nextToken.getTokenAttribute();
			
			mathConstNode.children.add(leafNode);
		}
		else
		{
			System.out.println("Syntax Analyzer: Invalid assignment made to identifier; Line Number: " + nextToken.getLineNumber());
		}
		
		System.out.println("Exited Math Constant");
	}
	
	public void booleanConstant(Node<String> parent)
	{
		boolConstNode = new Node<String>();
		boolConstNode.data = "<BOOL_CONST>";
		parent.children.add(boolConstNode);
		
		if (nextToken.getTokenName().equals("KEYWORD_BOOLEAN_TRUE") || nextToken.getTokenName().equals("KEYWORD_BOOLEAN_FALSE"))
		{
			leafNode = new Node<String>();
			leafNode.data = nextToken.getTokenAttribute();
			boolConstNode.children.add(leafNode);
		}
		else
		{
			System.out.println("Syntax Analyzer: Invalid assignment made to identifier; Line Number: " + nextToken.getLineNumber());
		}
		
		System.out.println("Exited Boolean Constant");
	}
	
	public void charConstNode(Node<String> parent)
	{
		charConstNode = new Node<String>();
		charConstNode.data = "<CONST>";
		
		parent.children.add(charConstNode);
		
		if (nextToken.getTokenName().equals("RESERVEDWORD_CHARACTER") || nextToken.getTokenName().equals("IDENT"))
		{
			leafNode = new Node<String>();
			leafNode.data = nextToken.getTokenAttribute();
			
			charConstNode.children.add(leafNode);
		}
		else 
		{
			System.out.println("Syntax Analyzer: Invalid assignment made to identifier; Line Number: " + nextToken.getLineNumber());
		}
		
		System.out.println("Exited Character Constant");
	}
	
	public void stringConstNode(Node<String> parent)
	{
		stringConstNode = new Node<String>();
		stringConstNode.data = "<CONST>";
		
		parent.children.add(stringConstNode);
		
		if (nextToken.getTokenName().equals("RESERVEDWORD_STRING") || nextToken.getTokenName().equals("IDENT"))
		{
			leafNode = new Node<String>();
			leafNode.data = nextToken.getTokenAttribute();
			
			stringConstNode.children.add(leafNode);
		}
		else 
		{
			System.out.println("Syntax Analyzer: Invalid assignment made to identifier; Line Number: " + nextToken.getLineNumber());
		}
		
		System.out.println("Exited String Constant");
	}
	
	public void space(Node<String> parent)
	{
		spaceNode = new Node<String>();
		spaceNode.data = "<SPACE>";
		
		parent.children.add(spaceNode);
		
		if (nextToken.getTokenName().equals("SPACE") || nextToken.getTokenName().equals("NEWLINE"))
		{
			leafNode = new Node<String>();
			leafNode.data = "space";
			
			spaceNode.children.add(leafNode);
		}
		else
		{
			System.out.println("Syntax Analyzer: You are missing a space; Line Number: " + nextToken.getLineNumber());
		}
		
		System.out.println("Exited Space");
	}
	
	public void dataType(Node<String> parent)
	{
		spaceNode = new Node<String>();
		spaceNode.data = "<DATA_TYPE>";
		
		parent.children.add(spaceNode);
		
		if (nextToken.getTokenName().equals("RESERVEDWORD_INTEGER") || nextToken.getTokenName().equals("RESERVEDWORD_DECIMAL") || nextToken.getTokenName().equals("RESERVEDWORD_CHARACTER") || nextToken.getTokenName().equals("RESERVEDWORD_STRING") || nextToken.getTokenName().equals("RESERVEDWORD_BOOLEAN"))
		{
			leafNode = new Node<String>();
			leafNode.data = nextToken.getTokenAttribute();
			
			spaceNode.children.add(leafNode);
		}
		else
		{
		//	System.out.println("Syntax Analyzer: Syntax Error on Data Type; Line Number: " + nextToken.getLineNumber());
		}
		
		System.out.println("Exited Space");
	}
	
	public Token nextToken()
	{
		if(tokenCount<token.length-1)
		{
		tokenCount++;
		return token[tokenCount];
		}
		else
			return token[tokenCount-1];
	}
}
