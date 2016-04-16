package AnalysisPhase.SyntaxAnalysis;

import AnalysisPhase.LexicalAnalysis.InputOutput;
import AnalysisPhase.LexicalAnalysis.Lex;
import AnalysisPhase.LexicalAnalysis.Token;
import Utilities.Node;



public class SyntaxAnalyzer {

	// in order na to according sa documentation
	// in order din sa kung paano gagawing yung functions (R.I.P)
	
	Node<String> programNode = null;
	Node<String> functionNode = null;
	Node<String> mainFunctionNode = null;
	Node<String> subFunctionsNode = null;
	Node<String> subFunctionNode = null;
	Node<String> paramsNode = null;
	Node<String> statementsNode = null;
	Node<String> moreStatementNode = null;
	Node<String> statementNode = null;
	Node<String> declarationNode = null;
	Node<String> assignmentNode = null;
	Node<String> ioNode = null;
	Node<String> controlFlowNode = null;
	Node<String> expressionStatementsNode = null;
	Node<String> returnNode = null;
	Node<String> dataTypeNode = null;
	Node<String> inputStatementNode = null;
	Node<String> outputStatementNode = null;
	Node<String> conditionalNode = null;
	Node<String> loopingNode = null;
	Node<String> expressionsNode = null;
	Node<String> callParametersNode = null;
	Node<String> switchStatementNode = null;
	Node<String> forStatementNode = null;
	Node<String> incrementStatementNode = null;
	Node<String> decrementStatementNode = null;
	Node<String> postIncrementNode = null;
	Node<String> postDecrementNode = null;
	Node<String> mathExpressionNode = null;
	Node<String> mathExpression2Node = null;
	Node<String> mathExpression3Node = null;
	Node<String> mathExpression4Node = null;
	Node<String> stringExpressionStatement = null;
	Node<String> relationalExpressionNode = null;
	Node<String> relationalExpression2Node = null;
	Node<String> relationalExpression3Node = null;
	Node<String> relationalExpression4Node = null;
	Node<String> relationalExpression5Node = null;
	Node<String> valueNode = null;
	Node<String> constantNode = null;
	Node<String> booleanConstantNode = null;
	Node<String> spaceNode = null;
	Node<String> leafNode = null;
	Token nextToken = null;
	
	Token[] token;
	int tokenCount = 0;
	
	public void analyze()
	{
		token = Lex.Lex(InputOutput.getText("a.hp"));
		System.out.println("analyze"+token.length);
		nextToken = token[tokenCount];
		Node<String> syntaxNode = new Node<String>();
		syntaxNode.data = "<PARENT>";
		while (tokenCount < token.length)
		{
			//System.out.println(token[tokenCount].getTokenName());
			if((token[tokenCount].getTokenName()).equals("RESERVEDWORD_INTEGER") || (token[tokenCount].getTokenName()).equals("RESERVEDWORD_DOUBLE") || (token[tokenCount].getTokenName()).equals("RESERVEDWORD_CHAR"))
			{
				System.out.println("hello");
				declaration(syntaxNode);
			}
			else if(token[tokenCount].getTokenName().equals("KEYWORD_BOOLEAN_TRUE") || token[tokenCount].getTokenName().equals("KEYWORD_BOOLEAN_FALSE"))
			{
				System.out.println("Entering booleanConstant");
				booleanConstant(syntaxNode);
			}
			else if(token[tokenCount].getTokenName().equals("KEYWORD_FOR"))
			{
				System.out.println("Entering FOR STATEMENT");
				forStatement(syntaxNode);
			}
			else if(token[tokenCount].getTokenName().equals("IDENT"))
			{
				if (token[tokenCount+2].getTokenName().equals("ARITH_OP_INCRE"))
				{
					System.out.println("Entering INCREMENT");
					postIncrement(syntaxNode);
				}
				else if (token[tokenCount+2].getTokenName().equals("ARITH_OP_DECRE"))
				{
					System.out.println("Entering DECREMENT");
					postDecrement(syntaxNode);
				}
			}
			else if((token[tokenCount].getTokenName().equals("INTEGER") || token[tokenCount].getTokenName().equals("DECIMAL")))
			{
				if (token[tokenCount+2].getTokenName().equals("ARITH_OP_ADD") || token[tokenCount+2].getTokenName().equals("ARITH_OP_SUBT") || token[tokenCount+2].getTokenName().equals("ARITH_OP_MULT") || token[tokenCount+2].getTokenName().equals("ARITH_OP_DIV") || token[tokenCount+2].getTokenName().equals("ARITH_OP_EXPON"))
				{
					System.out.println("Entering MATH EXPRESSION");
					mathExpression(syntaxNode);
				}
				else
				{
					System.out.println("Entering RELATIONAL EXPRESSION");
					relationalExpression(syntaxNode);
				}
			}
			
			nextToken = nextToken();
		}
	}
	
	public void switchCase(Node<String> parent)
	{
		switchStatementNode = new Node<String>();
		switchStatementNode.data = "<SWITCH_STMT>";
		
		parent.children.add(switchStatementNode);
		
		if (nextToken.getTokenName().equals("SWITCH"))
		{
			nextToken = nextToken();
			space(switchStatementNode);
			nextToken = nextToken();
			
			if (nextToken.getTokenName().equals("DELIM"))
			{
				leafNode = new Node<String>();
				leafNode.data = nextToken.getTokenAttribute();
				switchStatementNode.children.add(leafNode);
				
				nextToken = nextToken();
				space(switchStatementNode);
				
				declaration(switchStatementNode);
				
				nextToken = nextToken();
				space(switchStatementNode);
				nextToken = nextToken();
				
				if (nextToken.getTokenName().equals("DELIM"))
				{
					leafNode = new Node<String>();
					leafNode.data = nextToken.getTokenAttribute();
					
					nextToken = nextToken();
					space(switchStatementNode);
					nextToken = nextToken();
					
					while (nextToken.getTokenName().equals("CASE"))
					{
						leafNode = new Node<String>();
						leafNode.data = nextToken.getTokenAttribute();
						
						nextToken = nextToken();
						space(switchStatementNode);
						nextToken = nextToken();
						statement(switchStatementNode);
						nextToken = nextToken();
						space(switchStatementNode);
						nextToken = nextToken();
						
						if (nextToken.getTokenName().equals("STOP"))
						{
							leafNode = new Node<String>();
							leafNode.data = nextToken.getTokenAttribute();
						}
						else
						{
							System.out.println("Syntax Analyzer: Stop expected here");
						}
					}
				}
				else
				{
					System.out.println("Syntax Analyzer: Delimiter Expected");
				}
			}
			else
			{
				System.out.println("Syntax Analyzer: Delimiter Expected");
			}
		}
	}
	
	public void statement(Node<String> parent)
	{
		if((token[tokenCount].getTokenName()).equals("RESERVEDWORD_INTEGER") || (token[tokenCount].getTokenName()).equals("RESERVEDWORD_DOUBLE") || (token[tokenCount].getTokenName()).equals("RESERVEDWORD_CHAR"))
		{
			declaration (parent);
		}
		else
		{
			expressions(parent);
		}
	}
	
	public void declaration(Node<String> parent)
	{
		nextToken(); // SPACE after IDENTIFIER
		if(!nextToken().getTokenAttribute().equals("=") && !token[tokenCount].getTokenAttribute().equals(";")) // NAME DAPAT ITO NG VARIABLE
		{
			System.out.println(token[tokenCount].getTokenAttribute());
			if(nextToken().getTokenAttribute().equals(";") || token[tokenCount].getTokenName().equals("NEWLINE"))
			{
				
				System.out.println("IDENTIFIER: "+ token[tokenCount-3].getTokenAttribute() +" VarName: "+ token[tokenCount-1].getTokenAttribute());
			}
			else if(token[tokenCount].getTokenName().equals("SPACE") && nextToken().getTokenAttribute().equals("=") && nextToken().getTokenName().equals("SPACE") && nextToken().getTokenName().equals("INTEGER") && (nextToken().getTokenName().equals("NEWLINE") || (token[tokenCount].getTokenName().equals("SPACE") && nextToken().getTokenAttribute().equals(";"))))
			{
				
				System.out.println("IDENTIFIER: "+ token[tokenCount-8].getTokenAttribute() +" VarName: "+ token[tokenCount-6].getTokenAttribute() +" Value: "+ token[tokenCount-2].getTokenAttribute());
			}
			else
			{
				System.out.println("Syntax Analyzer: Invalid symbol detected");
			}
		}
	}
	
	public Token nextToken()
	{
		tokenCount++;
		return token[tokenCount];
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
			expressions(outputNode);
		}
		else if (nextToken.getTokenName().equals("KEYWORD_OUTPUT.PRINTLN"))
		{
			leafNode = new Node<String>();
			leafNode.data = nextToken.getTokenAttribute();
			parent.children.add(leafNode);
			expressions(outputNode);
		}
		else
		{
			System.out.println("Syntax Analyzer: Invalid symbol detected");
		}
	}
	
	public void conditional(Node<String> parent)
	{
		conditionalNode = new Node<String>();
		conditionalNode.data = "<CONDITIONAL>";
		parent.children.add(conditionalNode);
		//switchCase(parent);
		System.out.println("Exited conditional");
	}
	
	public void looping(Node<String> parent)
	{
		loopingNode = new Node<String>();
		loopingNode.data = "<LOOPING>";
		parent.children.add(loopingNode);
		forStatement(parent);
		System.out.println("Exited looping");
	}
	
	public void expressions(Node<String> parent)
	{
		expressionsNode = new Node<String>();
		expressionsNode.data = "<EXPRESSIONS>";
		
		parent.children.add(expressionsNode);
		
		if (nextToken.getTokenName().equals("RESERVEDWORD_INTEGER") || nextToken.getTokenName().equals("RESERVEDWORD_DECIMAL"))
		{
			leafNode = new Node<String>();
			leafNode.data = nextToken.getTokenAttribute();
			expressionsNode.children.add(leafNode);
			
			nextToken = nextToken();
			space(forStatementNode);
			nextToken = nextToken();
			
			mathExpression(expressionsNode);
		}
		else if (nextToken.getTokenName().equals("RESERVEDWORD_BOOLEAN") || nextToken.getTokenName().equals("RESERVEDWORD_CHARACTER") || nextToken.getTokenName().equals("RESERVEDWORD_STRING"))
		{
			relationalExpression(expressionsNode);
		}
		else
		{
			System.out.println("Syntax Analyzer: Invalid symbol detected");
		}

		System.out.println("Exited Relational Expression");
	}
	
	/*
	public void callParameters(Node<String> parent)
	{
		relationalExpressionNode = new Node<String>();
		relationalExpressionNode.data = "<CALL_PARAMS>";
		
		parent.children.add(callParametersNode);
		
		if (nextToken.getTokenName().equals("PARAM_SEP"))
		{
			leafNode = new Node<String>();
			leafNode.data = nextToken.getTokenAttribute();
			callParametersNode.children.add(leafNode);
			
			nextToken = nextToken();
			
			relationalExpression(callParametersNode);
		}
		else if (nextToken.getTokenName().equals("PARAM_SEP"))
		{
			callParameters(relationalExpressionNode);
		}
		else
		{
			System.out.println("Syntax Analyzer: Invalid symbol detected");
		}

		System.out.println("Exited Relational Expression");
	}
	*/
	public void dataType(Node<String> parent)
	{
		dataTypeNode = new Node<String>();
		dataTypeNode.data = "<DATATYPE>";
		
	}
	
	public void forStatement(Node<String> parent)
	{
		forStatementNode = new Node<String>();
		forStatementNode.data = "<FOR_STMT>";
		
		parent.children.add(forStatementNode);
		
		if (nextToken.getTokenName().equals("KEYWORD_FOR"))
		{
			leafNode = new Node<String>();
			leafNode.data = "KEYWORD_FOR";
			forStatementNode.children.add(leafNode);
			nextToken = nextToken();
			space(forStatementNode);
			nextToken = nextToken();
			
			if (nextToken.getTokenName().equals("DELIM"))
			{
				leafNode = new Node<String>();
				leafNode.data = "DELIM_LEFT_PAREN";
				forStatementNode.children.add(leafNode);
				nextToken = nextToken();
				space(forStatementNode);
				nextToken = nextToken();
				System.out.println(nextToken.getTokenName());
				if (nextToken.getTokenName().equals("RESERVEDWORD_INTEGER") || nextToken.getTokenName().equals("RESERVEDWORD_DECIMAL") || nextToken.getTokenName().equals("RESERVEDWORD_STRING") || nextToken.getTokenName().equals("RESERVEDWORD_CHARACTER") || nextToken.getTokenName().equals("RESERVEDWORD_BOOLEAN"))
				{
					// declaration(forStatementNode);
					declaration(forStatementNode);
					if(nextToken().getTokenName().equals("DELIM"))
					{
						leafNode = new Node<String>();
						leafNode.data = "DELIM";
						forStatementNode.children.add(leafNode);
						nextToken = nextToken();
						space(forStatementNode);
						nextToken = nextToken();
						relationalExpression(forStatementNode);
						nextToken = nextToken();
						space(forStatementNode);
						nextToken = nextToken();
						mathExpression(forStatementNode);
						space(forStatementNode);
						nextToken = nextToken();
						if(nextToken.getTokenName().equals("DELIM"))
						{
							leafNode = new Node<String>();
							leafNode.data = "DELIM";
							forStatementNode.children.add(leafNode);
							nextToken = nextToken();
							space(forStatementNode);
							nextToken = nextToken();
							while(nextToken.getTokenName().equals("INDENT"))
							{
								leafNode = new Node<String>();
								leafNode.data = "INDENT";
								forStatementNode.children.add(leafNode);
							}
						}
						else
						{
							System.out.println("Syntax Analyzer: Invalid symbol detected for DELIM_RIGHT_PAREN");
						}
					}
					else
					{
						System.out.println("Syntax Analyzer: Invalid symbol detected for DELIM_SEMICOLON");
					}
					//if relational operatior yung condition
						//if expression 
							// if delim right paren
								// if statements
				}
				else
				{
					System.out.println("Syntax Analyzer: Invalid symbol detected for INITIALIZE");
				}
				
				// if condition
			}
			else
			{
				System.out.println("Syntax Analyzer: Invalid symbol detected for DELIM_LEFT_PAREN");
			}
		}
		else
		{
			System.out.println("Syntax Analyzer: Invalid symbol detected for KEYWORD_FOR");
		}
	}
	
	public void incrementStatement(Node<String> parent)
	{
		incrementStatementNode = new Node<String>();
		incrementStatementNode.data = "<INC_STMT>";
		
		parent.children.add(incrementStatementNode);
		
		postIncrement(incrementStatementNode);
	}
	
	public void decrementStatement(Node<String> parent)
	{
		decrementStatementNode = new Node<String>();
		decrementStatementNode.data = "<DEC_STMT>";
		
		parent.children.add(decrementStatementNode);
		
		postDecrement(decrementStatementNode);
	}
	
	public void postIncrement(Node<String> parent)
	{
		postIncrementNode = new Node<String>();
		postIncrementNode.data = "<POST_INC>";
		
		parent.children.add(postIncrementNode);
		
		if (nextToken.getTokenName().equals("IDENT"))
		{
			mathConstant(postIncrementNode);
			nextToken = nextToken();
			space(postIncrementNode);
			nextToken = nextToken();
			System.out.println(nextToken.getTokenName());
			if (nextToken.getTokenName().equals("ARITH_OP_INCRE"))
			{
				leafNode = new Node<String>();
				leafNode.data = nextToken.getTokenAttribute();
				
				postIncrementNode.children.add(leafNode);
			}
			else
			{
				System.out.println("Syntax Analyzer: Invalid symbol detected");
			}
		}
	}
	
	public void postDecrement(Node<String> parent)
	{
		postIncrementNode = new Node<String>();
		postIncrementNode.data = "<POST_DEC>";
		
		parent.children.add(postIncrementNode);
		
		if (nextToken.getTokenName().equals("IDENT"))
		{
			mathConstant(postIncrementNode);
			nextToken = nextToken();
			
			space(postIncrementNode);
			
			nextToken = nextToken();
			System.out.println(nextToken.getTokenName());
			if (nextToken.getTokenName().equals("ARITH_OP_DECRE"))
			{
				leafNode = new Node<String>();
				leafNode.data = nextToken.getTokenAttribute();
				
				postIncrementNode.children.add(leafNode);
			}
			else
			{
				System.out.println("Syntax Analyzer: Invalid symbol detected");
			}
		}
	}
	
	public void mathExpression(Node<String> parent)
	{
		mathExpressionNode = new Node<String>();
		mathExpressionNode.data = "<MATH_EXPR>";
		
		parent.children.add(mathExpressionNode);
		
		mathConstant(mathExpressionNode);
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
			mathExpression2(mathExpressionNode);
		}
		else
		{
			System.out.println("Syntax Analyzer: Invalid symbol detected");
		}
		
		System.out.println("Exited Math Expression");
	}
	
	public void mathExpression2(Node<String> parent)
	{
		mathExpression2Node = new Node<String>();
		mathExpression2Node.data = "<MATH_EXPR2>";
		
		parent.children.add(mathExpression2Node);
		
		if (nextToken.getTokenName().equals("ARITH_OP_MULT") || nextToken.getTokenName().equals("ARITH_OP_DIV") || nextToken.getTokenName().equals("ARITH_OP_MOD") || nextToken.getTokenName().equals("ARITH_OP_DIV"))
		{
			leafNode = new Node<String>();
			leafNode.data = nextToken.getTokenAttribute();
			mathExpression2Node.children.add(leafNode);
			
			nextToken = nextToken();
			space(mathExpression2Node);
			nextToken = nextToken();
			mathExpression2(mathExpression2Node);
		}
		else if (nextToken.getTokenName().equals("ARITH_OP_EXPON") || nextToken.getTokenName().equals("INTEGER") || nextToken.getTokenName().equals("DECIMAL"))
		{
			mathExpression3(mathExpression2Node);
		}
		else
		{
			System.out.println("Syntax Analyzer: Invalid symbol detected");
		}

		System.out.println("Exited Math Expression 2");
	}
	
	public void mathExpression3(Node<String> parent)
	{
		mathExpression3Node = new Node<String>();
		mathExpression3Node.data = "<MATH_EXPR3>";
		
		parent.children.add(mathExpression3Node);
		
		if (nextToken.getTokenName().equals("ARITH_OP_EXPON"))
		{
			leafNode = new Node<String>();
			leafNode.data = nextToken.getTokenAttribute();
			mathExpression3Node.children.add(leafNode);
			
			nextToken = nextToken();
			space(mathExpression3Node);
			nextToken = nextToken();
			mathExpression3(mathExpression3Node);
		}
		else if (nextToken.getTokenName().equals("DELIM_LEFT_PAREN") || nextToken.getTokenName().equals("INTEGER") || nextToken.getTokenName().equals("DECIMAL"))
		{
			mathExpression4(mathExpression3Node);
		}
		else
		{
			System.out.println("Syntax Analyzer: Invalid symbol detected");
		}

		System.out.println("Exited Math Expression 3");
	}
	
	public void mathExpression4(Node<String> parent)
	{
		mathExpression4Node = new Node<String>();
		mathExpression4Node.data = "<MATH_EXPR4>";
		
		parent.children.add(mathExpression4Node);
		
		if (nextToken.getTokenName().equals("DELIM_LEFT_PAREN"))
		{
			if (nextToken.getTokenName().equals("DELIM_LEFT_PAREN"))
			{
				leafNode = new Node<String>();
				leafNode.data = nextToken.getTokenAttribute();
				
				nextToken = nextToken();
				space(mathExpression4Node);
				nextToken = nextToken();
			}
			
			mathExpression(mathExpression4Node);
			
			if (nextToken.getTokenName().equals("DELIM_RIGHT_PAREN"))
			{
				leafNode = new Node<String>();
				leafNode.data = nextToken.getTokenAttribute();
				
				nextToken = nextToken();
				space(mathExpression4Node);
				nextToken = nextToken();
			}
		} 
		else if (nextToken.getTokenName().equals("INTEGER") || nextToken.getTokenName().equals("DECIMAL"))
		{
			mathConstant(mathExpression4Node);
		}
		System.out.println("Exited Math Expression 4");
	}
	
	public void relationalExpression(Node<String> parent)
	{
		relationalExpressionNode = new Node<String>();
		relationalExpressionNode.data = "<REL_EXPR2>";
		
		parent.children.add(relationalExpressionNode);
		
		mathConstant(relationalExpressionNode);
		nextToken = nextToken();
		space(relationalExpressionNode);
		nextToken = nextToken();
		System.out.println(nextToken.getTokenName());
		
		if (nextToken.getTokenName().equals("OR_OP"))
		{
			leafNode = new Node<String>();
			leafNode.data = nextToken.getTokenAttribute();
			relationalExpressionNode.children.add(leafNode);
			
			nextToken = nextToken();
			space(relationalExpressionNode);
			nextToken = nextToken();
			
			relationalExpression(relationalExpressionNode);
		}
		else
		{
			relationalExpression2(relationalExpressionNode);
		}

		System.out.println("Exited Relational Expression");
	}
	
	public void relationalExpression2(Node<String> parent)
	{
		relationalExpression2Node = new Node<String>();
		relationalExpression2Node.data = "<REL_EXPR2>";
		
		parent.children.add(relationalExpression2Node);
		
		if (nextToken.getTokenName().equals("LOG_OP_AND"))
		{
			leafNode = new Node<String>();
			leafNode.data = nextToken.getTokenAttribute();
			relationalExpression2Node.children.add(leafNode);
			
			nextToken = nextToken();
			space(relationalExpression2Node);
			nextToken = nextToken();
			
			relationalExpression2(relationalExpression2Node);
		}
		else
		{
			relationalExpression3(relationalExpression2Node);
		}

		System.out.println("Exited Relational Expression 2");
	}
	
	public void relationalExpression3(Node<String> parent)
	{
		relationalExpression3Node = new Node<String>();
		relationalExpression3Node.data = "<REL_EXPR3>";
		
		parent.children.add(relationalExpression3Node);
		
		if (nextToken.getTokenName().equals("ARITH_OP_ASSIGN") || nextToken.getTokenName().equals("LOG_OP_NOT"))
		{
			leafNode = new Node<String>();
			leafNode.data = nextToken.getTokenAttribute();
			relationalExpression3Node.children.add(leafNode);
			
			nextToken = nextToken();
			space(relationalExpression3Node);
			nextToken = nextToken();
			
			relationalExpression3(relationalExpression3Node);
		}
		else
		{
			relationalExpression4(relationalExpression3Node);
		}

		System.out.println("Exited Relational Expression 3");
	}
	
	public void relationalExpression4(Node<String> parent)
	{
		relationalExpression4Node = new Node<String>();
		relationalExpression4Node.data = "<REL_EXPR4>";
		
		parent.children.add(relationalExpression4Node);

		System.out.println(nextToken.getTokenName());
		if (nextToken.getTokenName().equals("REL_OP_GREATER") || nextToken.getTokenName().equals("REL_OP_LESS") || nextToken.getTokenName().equals("REL_OP_GREATEREQUAL") || nextToken.getTokenName().equals("REL_OP_LESSEQUAL"))
		{
			leafNode = new Node<String>();
			leafNode.data = nextToken.getTokenAttribute();
			relationalExpression4Node.children.add(leafNode);
			
			nextToken = nextToken();
			space(relationalExpression4Node);
			nextToken = nextToken();
			
			relationalExpression4(relationalExpression4Node);
		}
		else
		{
			relationalExpression5(relationalExpression4Node);
		}

		System.out.println("Exited Relational Expression 4");
	}
	
	public void relationalExpression5(Node<String> parent)
	{
		relationalExpression5Node = new Node<String>();
		relationalExpression5Node.data = "<REL_EXPR5>";
		
		parent.children.add(relationalExpression5Node);
		
		if (nextToken.getTokenName().equals("LOG_OP_NOT"))
		{
			nextToken = nextToken();
			space(relationalExpression5Node);
			nextToken = nextToken();
			// relationalExpressionNode(relationalExpression5Node);		
		}
		else if (nextToken.getTokenName().equals("BOOLEAN_TRUE") || nextToken.getTokenName().equals("BOOLEAN_FALSE"))
		{
			booleanConstant(relationalExpression5Node);
		}
		else
		{
			System.out.println("Syntax Analyzer: Invalid symbol before relational expression");
		}
		
		System.out.println("Exited Relational Expression 5");
	}
	
	public void stringConstant(Node<String> parent)
	{
		constantNode = new Node<String>();
		constantNode.data = "<CONST>";
		
		parent.children.add(constantNode);
		
		if (nextToken.getTokenName().equals("RESERVEDWORD_STRING") || nextToken.getTokenName().equals("IDENT"))
		{
			leafNode = new Node<String>();
			leafNode.data = nextToken.getTokenAttribute();
			
			constantNode.children.add(leafNode);
		}
		else 
		{
			System.out.println("Syntax Analyzer: Invalid assignment made to identifier");
		}
		
		System.out.println("Exited String Constant");
	}
	
	public void characterConstant(Node<String> parent)
	{
		constantNode = new Node<String>();
		constantNode.data = "<CONST>";
		
		parent.children.add(constantNode);
		
		if (nextToken.getTokenName().equals("RESERVEDWORD_CHARACTER") || nextToken.getTokenName().equals("IDENT"))
		{
			leafNode = new Node<String>();
			leafNode.data = nextToken.getTokenAttribute();
			
			constantNode.children.add(leafNode);
		}
		else 
		{
			System.out.println("Syntax Analyzer: Invalid assignment made to identifier");
		}
		
		System.out.println("Exited Character Constant");
	}
	
	public void mathConstant(Node<String> parent)
	{
		constantNode = new Node<String>();
		constantNode.data = "<CONST>";
		
		parent.children.add(constantNode);
		
		if (nextToken.getTokenName().equals("INTEGER") || nextToken.getTokenName().equals("DECIMAL") || nextToken.getTokenName().equals("IDENT"))
		{
			leafNode = new Node<String>();
			leafNode.data = nextToken.getTokenAttribute();
			
			constantNode.children.add(leafNode);
		}
		else
		{
			System.out.println("Syntax Analyzer: Invalid assignment made to identifier");
		}
		
		System.out.println("Exited Math Constant");
	}
	
	public void booleanConstant(Node<String> parent)
	{
		booleanConstantNode = new Node<String>();
		booleanConstantNode.data = "<BOOL_CONST>";
		parent.children.add(booleanConstantNode);
		
		if (nextToken.getTokenName().equals("KEYWORD_BOOLEAN_TRUE") || nextToken.getTokenName().equals("KEYWORD_BOOLEAN_FALSE"))
		{
			leafNode = new Node<String>();
			leafNode.data = nextToken.getTokenAttribute();
			booleanConstantNode.children.add(leafNode);
		}
		else
		{
			System.out.println("Syntax Analyzer: Invalid assignment made to identifier");
		}
		
		System.out.println("Exited Boolean Constant");
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
			System.out.println("Syntax Analyzer: You are missing a space");
		}
		
		System.out.println("Exited Space");
	}
}
