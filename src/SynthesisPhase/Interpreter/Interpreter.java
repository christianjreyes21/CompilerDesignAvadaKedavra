package SynthesisPhase.Interpreter;

import java.util.ArrayList;
import java.util.Scanner;

import Utilities.Infix;
import Utilities.Node;
import Utilities.MemoryCell;

public class Interpreter {
	
	ArrayList<MemoryCell> MemoryAllocation = new ArrayList<MemoryCell>();
	boolean errorDetected = false;
	
	public MemoryCell retrieveCell(String data) //// retrieve variables!
	{
		for (int i = 0; i < MemoryAllocation.size(); i++)
		{
			if (MemoryAllocation.get(i).cellName.equals(data))
				return MemoryAllocation.get(i);
		}
		
		return null;
	}
	
	public void execute(Node<String> root) ///// Execute the whole tree from the top 
	{
		//System.out.println("ROOT");
		for (int i = 0; i < root.children.size(); i++)
		{
			if (root.children.get(i).children.isEmpty())
				break;
			
			//System.out.print(i + ": ");
			statementExecute(root.children.get(i));
			//System.out.println();
		}
		warningCheck();
	}
	
	public void statementExecute(Node<String> statement) //// Decide which kind of statement to execute
	{
		//System.out.println(statement.children.get(0));
		switch (statement.children.get(0).data)
		{
			case "<DECLARATION>":
				declarationExecute(statement.children.get(0));
				break;
			case "<INPUT_STMT>":
				if(!errorDetected)
				inputExecute(statement.children.get(0));
				break;
			case "<OUTPUT_STMT>":
				outputExecute(statement.children.get(0));
				break;
			case "<GOTO_STMT>":
				gotoExecute(statement.children.get(0));
				break;
			case "<ASSIGN_STMT>":
				assignExecute(statement.children.get(0));
				break;
			case "<INCRE_STMT>":
				increExecute(statement.children.get(0));
				break;
			case "<DECRE_STMT>":
				decreExecute(statement.children.get(0));
				break;
			case "<FOR_STMT>":
				//System.out.println(statement.children.get(0));
				forExecute(statement.children.get(0));
				break;
			case "<SWITCH_STMT>":
				switchExecute(statement.children.get(0));
				break;
		}
	}
	
	public void declarationExecute(Node<String> declaration)
	{
		//System.out.println("DECLARATION");
		MemoryCell existing = null;
		existing = retrieveCell(declaration.children.get(2).children.get(0).data);
		
		if (existing == null)
		{
			MemoryCell cell = new MemoryCell();
			cell.tokenName = declaration.children.get(0).data;
			cell.cellName = declaration.children.get(2).children.get(0).data;
			MemoryAllocation.add(cell);
		}
		else
		{
			System.out.println("Line: " + declaration.children.get(0).lineNumber + " | Semantic Error: Double declaration detected");
			errorDetected=true;
		}
	}
	
	public void inputExecute(Node<String> input)
	{
		//System.out.println("INPUT");
		MemoryCell cell = retrieveCell(input.children.get(4).children.get(0).data);
		Scanner in = new Scanner(System.in);
		try
		{
			cell.cellValue = in.next();
			try
			{
				if (cell.tokenName.equals("#INTEGER"))
					Integer.parseInt(cell.cellValue);
				else if (cell.tokenName.equals("#DOUBLE"))
					Double.parseDouble(cell.cellValue);
				else if (cell.tokenName.equals("#BOOLEAN") && (!cell.cellValue.equals("%TRUE") || !cell.cellValue.equals("%FALSE")))
					throw new Exception();
				else if (cell.tokenName.equals("#STRING") && !(cell.cellValue.charAt(0) == '\"'))
					throw new Exception();
				else if (cell.tokenName.equals("#CHARACTER") && !(cell.cellValue.charAt(0) == '\''))
					throw new Exception();
			}
			catch (Exception e)
			{
				cell.cellValue = null;
				System.out.println("Line: " + input.children.get(0).lineNumber + " | Semantic Error: Type mismatch detected");
				errorDetected = true;
				in.close();
			}
		}
		catch (Exception e)
		{
			System.out.println("Line: " + input.children.get(0).lineNumber + " | Semantic Error: Variable not declared");
			errorDetected = true;
		}
		//System.out.println(cell.cellName);
		
		in.close();
	}
	
	public void outputExecute(Node<String> output)
	{
		//System.out.println("OUTPUT");
		MemoryCell existing = null;
		existing = retrieveCell(output.children.get(4).children.get(0).data);
		
		if (existing == null)
		{
			System.out.println("Line: " + output.children.get(0).lineNumber + " | Semantic Error: Variable hasn't been declared");
		}
		else
		{
			if (existing.cellValue != null)
			{	
				if (output.children.get(0).data.equals("%OUTPUT.PRINT"))
					System.out.print(existing.cellValue);
				else
					System.out.println(existing.cellValue);
			}
			else
			{
				System.out.println("Line: " + output.children.get(0).lineNumber + " | Semantic Error: Variable hasn't been assigned a value");
				errorDetected = true;
			}
		}
	}
	
	public void assignExecute(Node<String> assign)
	{
		//System.out.println("ASSIGNMENT");
		MemoryCell existing = retrieveCell(assign.children.get(0).children.get(0).data);
		//System.out.println(assign.children.get(0).children.get(0).data);
		if (existing == null)
		{
			System.out.println("Line: " + assign.children.get(0).children.get(0).lineNumber + " | Semantic Error: Variable not declared");
			errorDetected = true;
			/*MemoryCell cell = new MemoryCell();
			if (assign.children.get(4).data.startsWith("<"))
			{
				if (assign.children.get(4).children.get(0).data.equals("<MATH_EXPR>"))
				{
					cell.cellValue = mathExecute(assign.children.get(4).children.get(0));
					if ((cell.tokenName.equals("#INTEGER") && cell.cellValue.contains(".") || cell.cellValue == null))
					{
						System.out.println("Line: " + assign.children.get(0).children.get(0).lineNumber + " | Semantic Error: The right hand side must be an integer value. Please recheck your code");
					}
				}
				else
				{
					cell.cellValue = relationalExecute(assign.children.get(4).children.get(0));
					if (cell.tokenName.equals("#BOOLEAN") && (!cell.cellValue.equals("true") || !cell.cellValue.equals("false") || !cell.cellValue.equals("%FALSE") || !cell.cellValue.equals("%TRUE")))
					{
						System.out.println("Line: " + assign.children.get(0).children.get(0).lineNumber + " | Semantic Error: The right hand side must be a boolean value. Please recheck your code");
					}
				}
			}
			else
			{
				if (cell.cellValue.equals("%TRUE"))
					cell.cellValue = "true";
				else if (cell.cellValue.equals("%FALSE"))
					cell.cellValue = "true";
				else
					cell.cellValue = assign.children.get(4).data;
			}
			
			cell.cellName = assign.children.get(0).children.get(0).data;
			MemoryAllocation.add(cell);*/
			
		}
		else
		{
			try{
			if (assign.children.get(4).data.startsWith("<"))
			{
				if (assign.children.get(4).children.get(0).data.equals("<MATH_EXPR>"))
				{
					existing.cellValue = mathExecute(assign.children.get(4).children.get(0));
					if ( existing.cellValue == null || (existing.tokenName.equals("#INTEGER") && existing.cellValue.contains(".")))
					{
						System.out.println("Line: " + assign.children.get(0).children.get(0).lineNumber + " | Semantic Error: The right hand side must be an integer value. Please recheck your code");
					}
				}
				else
				{
					existing.cellValue = relationalExecute(assign.children.get(4).children.get(0));
					if (existing.tokenName.equals("#BOOLEAN") && !(existing.cellValue.equals("true") || existing.cellValue.equals("false")))
					{
						errorDetected = true;
						System.out.println("Line: " + assign.children.get(0).children.get(0).lineNumber + " | Semantic Error: The right hand side must be a boolean value. Please recheck your code");
					}
				}
			}
			else
			{
				existing.cellValue = assign.children.get(4).data;
				//System.out.println(existing.cellValue);
				try
				{
					if (existing.tokenName.equals("#INTEGER"))
						Integer.parseInt(existing.cellValue);
					else if (existing.tokenName.equals("#DOUBLE"))
						Double.parseDouble(existing.cellValue);
					else if (existing.tokenName.equals("#BOOLEAN") && (!existing.cellValue.equals("%TRUE") || !existing.cellValue.equals("%FALSE")))
						throw new Exception();
					else if (existing.tokenName.equals("#STRING") && !(existing.cellValue.charAt(0) == '\"'))
						throw new Exception();
					else if (existing.tokenName.equals("#CHARACTER") && !(existing.cellValue.charAt(0) == '\''))
						throw new Exception();
					else if (existing.tokenName.equals("#CHARACTER") && (existing.cellValue.charAt(0) == '\''))
						existing.cellValue = Character.toString(assign.children.get(4).data.charAt(1));
					else if (existing.tokenName.equals("#STRING") && (existing.cellValue.charAt(0) == '\"'))
						existing.cellValue = assign.children.get(4).data.substring(1, assign.children.get(4).data.length() - 1);

				}
				catch (Exception ea)
				{
					System.out.println("Variable " + existing.cellValue + " | Semantic Error: Type mismatch detected");
					errorDetected = true;
					existing.cellValue = null;
				}
			}
			}catch (Exception e)
			{
				try
				{
					existing.cellValue = assign.children.get(4).data;
					//System.out.println(existing.cellValue);
					
					if (existing.tokenName.equals("#INTEGER"))
						Integer.parseInt(existing.cellValue);
					else if (existing.tokenName.equals("#DOUBLE"))
						Double.parseDouble(existing.cellValue);
					else if (existing.tokenName.equals("#BOOLEAN") && (!existing.cellValue.equals("%TRUE") || !existing.cellValue.equals("%FALSE")))
						throw new Exception();
					else if (existing.tokenName.equals("#STRING") && !(existing.cellValue.charAt(0) == '\"'))
						throw new Exception();
					else if (existing.tokenName.equals("#CHARACTER") && !(existing.cellValue.charAt(0) == '\''))
						throw new Exception();
					else if (existing.tokenName.equals("#CHARACTER") && (existing.cellValue.charAt(0) == '\''))
						existing.cellValue = Character.toString(assign.children.get(4).data.charAt(1));
					else if (existing.tokenName.equals("#STRING") && (existing.cellValue.charAt(0) == '\"'))
						existing.cellValue = assign.children.get(4).data.substring(1, assign.children.get(4).data.length() - 1);
				}
				catch (Exception ea)
				{
					System.out.println("Variable " + existing.cellValue + " | Semantic Error: Type mismatch detected");
					errorDetected = true;
					existing.cellValue = null;
				}
			}
			//System.out.println(existing.cellValue + " " + existing.cellName);
		}
	}
	
	public void increExecute(Node<String> incre)
	{
		//System.out.println("INCRE");
		MemoryCell existing = retrieveCell(incre.children.get(0).children.get(0).data);
		
		if (existing == null)
		{
			System.out.println("Line: " + incre.children.get(2).lineNumber + " | Semantic Error: The variable must be declared and assigned an integer value");
			errorDetected = true;
		}
		else
		{
			if (existing.tokenName.equals("#INTEGER"))
			{
				existing.cellValue = Integer.toString(Integer.parseInt(existing.cellValue) + 1);
			}
			else
			{
				System.out.println("Line: " + incre.children.get(2).lineNumber + " | Semantic Error: The variable must have an integer value");
				errorDetected = true;
			}
		}
	}
	
	public void decreExecute(Node<String> decre)
	{
		//System.out.println("DECRE");
		MemoryCell existing = retrieveCell(decre.children.get(0).children.get(0).data);
		
		if (existing == null)
		{
			System.out.println("Line: " + decre.children.get(2).lineNumber + " | Semantic Error: The variable must be declared and assigned an integer value");
			errorDetected = true;
		}
		else
		{
			if (existing.tokenName.equals("#INTEGER"))
			{
				existing.cellValue = Integer.toString(Integer.parseInt(existing.cellValue) - 1);
			}
			else
			{
				System.out.println("Line: " + decre.children.get(2).lineNumber + " | Semantic Error: The variable must have an integer value");
				errorDetected = true;
			}
		}
	}
	
	public void forExecute(Node<String> forLoop)
	{
		assignExecute(forLoop.children.get(4));
		
		while(true)
		{
			boolean condition = Boolean.parseBoolean(relationalExecute(forLoop.children.get(8)));
			//System.out.print(condition);
			if (!condition)
				break;
			
			for (int i = 16; ;)
			{
				statementExecute(forLoop.children.get(i));
				i += 2;
				if (forLoop.children.size() < i)
					break;
			}
			
			increExecute(forLoop.children.get(11));
		}
	}
	
	public void switchExecute(Node<String> switchNode)
	{
		MemoryCell cell1;
		cell1 = retrieveCell(switchNode.children.get(4).children.get(0).data);
		if (cell1 == null)
		{
			System.out.println("Line: " + switchNode.children.get(4).children.get(0).lineNumber + " | Semantic Error: Variable not declared");
			errorDetected = true;
		}
		else
		{
			String value = cell1.cellValue;	
			for (int i = 9; ;)
			{
				if (value.equals(switchNode.children.get(i).children.get(2).data))
				{
					statementExecute(switchNode.children.get(i).children.get(6));
					i += 5;
					if (switchNode.children.size() < i)
						break;
				}
			}
		}
	}
	
	public String relationalExecute(Node<String> relational)
	{
		int a = 0;
		MemoryCell cell1;
		MemoryCell cell2;
		try{
		
		if (relational.children.get(5).data.startsWith("<") && relational.children.get(0).data.startsWith("<"))
		{
			cell1 = retrieveCell(relational.children.get(0).children.get(0).data);
			cell2 = retrieveCell(relational.children.get(5).children.get(0).data);
			
			a = cell1.cellValue.compareTo(cell2.cellValue);
		}
		else if (relational.children.get(5).data.startsWith("<"))
		{
			cell1 = retrieveCell(relational.children.get(5).children.get(0).data);
			//System.out.print(cell1);
			a = relational.children.get(0).data.compareTo(cell1.cellValue);
		}
		else if (relational.children.get(0).data.startsWith("<"))
		{
			cell1 = retrieveCell(relational.children.get(0).children.get(0).data);
			//System.out.print(cell1.cellValue);
			a = cell1.cellValue.compareTo(relational.children.get(5).data);
		}
		else
			a = relational.children.get(0).data.compareTo(relational.children.get(5).data);
		//System.out.println(relational.children.get(0).data);
		}
		catch(Exception e){}
		//System.out.println(a);
		boolean ans = true;
		
		switch (relational.children.get(2).data)
		{
			case "<":
				if (a < 0)
					ans = true;
				else 
					ans = false;
				break;
			case ">":
				if (a > 0)
					ans = true;
				else 
					ans = false;
				break;
			case "==":
				if (a == 0)
					ans = true;
				else
					ans = false;
				break;
			case ">=":
				if (a >= 0)
					ans = true;
				else 
					ans = false;
				break;
			case "<=":
				if (a <= 0)
					ans = true;
				else 
					ans = false;
				break;
			case "!=":
				if (a != 0)
					ans = true;
				else
					ans = false;
				break;
		}
			
		return Boolean.toString(ans);
	}
	
	public String mathExecute(Node<String> math)
	{
		String expression = "";
		for (int i = 0; i < math.children.size(); i++)
		{
			if (math.children.get(i).data.startsWith("<") && !math.children.isEmpty())
			{
				for (int j = 0; j < math.children.get(i).children.size(); j++)
				{
					if (!math.children.get(i).children.isEmpty() && math.children.get(i).children.get(j).data.startsWith("<"))
					{
						if (!math.children.get(i).children.get(j).children.isEmpty())
						{
							MemoryCell existing;
							existing = retrieveCell(math.children.get(i).children.get(j).children.get(0).data);
							if (existing != null)
								expression += existing.cellValue;
						}
						
					}
					else if (!math.children.get(i).children.get(j).data.equals("SPACE"))
						expression += math.children.get(i).children.get(j).data;
					
				}
			}
			else if (!math.children.get(i).data.equals("SPACE"))
				expression += math.children.get(i).data;
		}
		
		try 
		{
			String ans = Double.toString(new Infix().infix(expression));
			
			return ans;
		}
		catch (Exception e)
		{
			System.out.println("Line: " + math.children.get(0).lineNumber + " | Semantic Error: Must only operate on numeric types");
			errorDetected = true;
			return null;
		}
	}
	
	public void gotoExecute(Node<String> gotoStmt)
	{
		
	}
	
	public void warningCheck()
	{
		for (int i = 0; i < MemoryAllocation.size(); i++)
		{
			if (MemoryAllocation.get(i).cellValue == null)
			{
				System.out.println("Warning: " + MemoryAllocation.get(i).cellName + " was not used");
			}
		}
	}
}
