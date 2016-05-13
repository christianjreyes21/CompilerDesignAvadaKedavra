package Utilities;

import java.util.Stack;
import java.util.StringTokenizer;

public class Infix
{

    public double infix(String expression)
    {
        //remove white space and add evaluation operator
        expression=expression.replaceAll("[\t\n ]", "")+"=";
        String operator="*/+-=";
        //split up the operators from the values
        StringTokenizer tokenizer=new StringTokenizer(expression, operator, true);
        Stack operatorStack=new Stack();
        Stack valueStack=new Stack();
        while(tokenizer.hasMoreTokens())
        {
            //add the next token to the proper stack
            String token=tokenizer.nextToken();
            if(operator.indexOf(token)<0)
                valueStack.push(token);
            else
                operatorStack.push(token);
            //perform any pending operations
            resolve(valueStack, operatorStack);
        }
        //return the top of the value stack
        String lastOne=(String)valueStack.pop();
        return Double.parseDouble(lastOne);   
    }
        
    public int getPriority(String op)
    {
        if(op.equals("*") || op.equals("/"))
            return 1;
        else if(op.equals("+") || op.equals("-"))
            return 2;
        else if(op.equals("="))
            return 3;
        else
            return Integer.MIN_VALUE;
    }
    
    public void resolve(Stack values, 
            Stack operators)
    {
        while(operators.size()>=2)
        {
            String first=(String)operators.pop();
            String second=(String)operators.pop();
            if(getPriority(first)<getPriority(second))
            {
                operators.push(second);
                operators.push(first);
                return;
            }
            else
            {
                String firstValue=(String)values.pop();
                String secondValue=(String)values.pop();
                values.push(getResults(secondValue, second, firstValue));
                operators.push(first);
            }
        }
    }
    
    public String getResults(String operand1, String operator, String operand2)
    {
        double op1=Double.parseDouble(operand1);
        double op2=Double.parseDouble(operand2);
        if(operator.equals("*"))
            return ""+(op1*op2);
        else if(operator.equals("/"))
            return ""+(op1/op2);
        else if(operator.equals("+"))
            return ""+(op1+op2);
        else if(operator.equals("-"))
            return ""+(op1-op2);
        else
            return null;
    }
    
    public static void main(String[] args)
    {
        Infix fix=new Infix();
        String expression="4-4*4/7+5";
        System.out.println(expression+"="+fix.infix(expression));
    }
}