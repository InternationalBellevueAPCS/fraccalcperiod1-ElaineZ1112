import java.util.*;
import java.lang.*;

public class FracCalc 
{

    /**
     * Prompts user for input, passes that input to produceAnswer, then outputs the result.
     * @param args - unused
     */
    public static void main(String[] args) throws Exception
    {
        // TODO: Read the input from the user and call produceAnswer with an equation
        // Checkpoint 1: Create a Scanner, read one line of input, pass that input to produceAnswer, print the result.
        // Checkpoint 2: Accept user input multiple times.
    	
    	
    	Scanner scanner = new Scanner(System.in);
    	boolean done = false;

    	
    	while(!done)
    	{
    		System.out.print("Input an equation: (type \"quit\" to stop) ");    	
    		String input = scanner.nextLine();
    		
    		if (input.equals("quit")) done = true;
    		
    		if (!done) 
    		{
    			System.out.println(produceAnswer(input));
    		}
    	}

    	scanner.close();
    }
    
    /**
     * produceAnswer - This function takes a String 'input' and produces the result.
     * @param input - A fraction string that needs to be evaluated.  For your program, this will be the user input.
     *      Example: input ==> "1/2 + 3/4"
     * @return the result of the fraction after it has been calculated.
     *      Example: return ==> "1_1/4"
     */
    public static String produceAnswer(String input)
    { 
        // TODO: Implement this function to produce the solution to the input
        // Checkpoint 1: Return the second operand.  Example "4/5 * 1_2/4" returns "1_2/4".
        // Checkpoint 2: Return the second operand as a string representing each part.
        //               Example "4/5 * 1_2/4" returns "whole:1 numerator:2 denominator:4".
        // Checkpoint 3: Evaluate the formula and return the result as a fraction.
        //               Example "4/5 * 1_2/4" returns "6/5".
        //               Note: Answer does not need to be reduced, but it must be correct.
        // Final project: All answers must be reduced.
        //               Example "4/5 * 1_2/4" returns "1_1/5".
        
    	boolean addition = false;
    	boolean subtraction = false;
    	boolean multiplication = false;
    	boolean division = false;
    	
    	String first;
    	String operator;
    	String second;
    	
    	int index = input.indexOf(' ');
    	first = input.substring(0, index);
    	input = input.substring(index + 1);
    	
    	index = input.indexOf(' ');
    	operator = input.substring(0, index);
    	input = input.substring(index + 1);
    	
    	second = input;
    	
    	addition = operator.equals("+");
    	subtraction = operator.equals("-");
    	multiplication = operator.equals("*");
    	division = operator.equals("/");
    	
    	int[] firstNumber = returnNumberFromString(first);
    	int[] secondNumber = returnNumberFromString(second);
    	int[] answer = new int[3];
    	
    	if (addition)
    	{
    		answer = addition(firstNumber, secondNumber);
    	}
    	else if (subtraction)
    	{
    		answer = subtraction(firstNumber, secondNumber);
    	}
    	else if (multiplication)
    	{
    		answer = multiplication(firstNumber, secondNumber);
    	}
    	else if (division)
    	{
    		answer = division(firstNumber, secondNumber);
    	}
    	
    	int[] simplified = simplify(answer);
    	
    	String value = "";
    	
    	if (simplified[0] != 0 && simplified[1] != 0)
    	{
    		value += simplified[0] + "_" + simplified[1] + "/" + simplified[2];
    	}
    	else if (simplified[1] != 0)
    	{
    		value += simplified[1] + "/" + simplified[2];
    	}
    	else if (simplified[0] != 0)
    	{
    		value += simplified[0];
    	}
    	
        return value;
    }
    
    public static int[] addition(int[] firstNumber, int[] secondNumber)
    {
    	int[] answer = new int[3];
    	answer[0] = firstNumber[0] + secondNumber[0];
    	answer[2] = firstNumber[2] * secondNumber[2];
    	answer[1] = (firstNumber[1] * secondNumber[2]) + (secondNumber[1] * firstNumber[2]);
    	
    	return answer;
    }
    
    public static int[] subtraction(int[] firstNumber, int[] secondNumber)
    {
    	int[] answer = new int[3];
    	answer[0] = firstNumber[0] - secondNumber[0];
    	answer[2] = firstNumber[2] * secondNumber[2];
    	if (firstNumber[1] > secondNumber[1])
    	{
    		answer[1] = (firstNumber[1] * secondNumber[2]) - (secondNumber[1] * firstNumber[2]);
    	}
    	else
    	{
    		answer[0]--;
    		answer[1] = (firstNumber[1] * secondNumber[2] + answer[2]) - (secondNumber[1] * firstNumber[2]);
    	}
    	return answer;
    }
    
    public static int[] multiplication(int[] firstNumber, int[] secondNumber)
    {
    	int[] answer = new int[3];
    	answer[1] = (firstNumber[1] + firstNumber[0] * firstNumber[2]) * (secondNumber[1] + secondNumber[0] * secondNumber[2]);
    	answer[2] = firstNumber[2] * secondNumber[2];
    	return answer;
    }
    
    public static int[] division(int[] firstNumber, int[] secondNumber)
    {
    	int[] answer = new int[3];
    	answer[1] = (firstNumber[1] + firstNumber[0] * firstNumber[2]) * secondNumber[2];
    	answer[2] = firstNumber[2] * (secondNumber[1] + secondNumber[0] * secondNumber[2]);
    	return answer;
    }
    
    public static int[] simplify(int[] number)
    {
    	int gcd = greatestCommonDivisor(number[1], number[2]);
    	number[1] /= gcd;
    	number[2] /= gcd;
    	
    	if (number[1] >= number[2]) 
    	{
    		number[0] += number[1] / number[2];
    		number[1] = number[1] % number[2];
    	}
    	
    	return number;
    }
    
    public static int[] returnNumberFromString(String input)
    {
    	int[] number = new int[3];
    	number[1] = 0;
    	number[2] = 1;
    	
    	
    	
    	if (input.contains("_") && input.contains("/"))
    	{
    		int index = input.indexOf('_');
    		number[0] = Integer.parseInt(input.substring(0, index));
    		input = input.substring(index + 1);
    	} 
    	else if (!input.contains("_") && !input.contains("/"))
    	{
    		number[0] = Integer.parseInt(input);
    	}
    	
    	if (input.contains("/"))
		{
    		int index = input.indexOf('/');
    		number[1] = Integer.parseInt(input.substring(0, index));
    		number[2] = Integer.parseInt(input.substring(index + 1));
		}
    	
    	if (number[1] > number[2]) 
    	{
    		number[0] += number[1] / number[2];
    		number[1] = number[1] % number[2];
    	}
    	return number;
    }
    

    // TODO: Fill in the space below with helper methods
    
    /**
     * greatestCommonDivisor - Find the largest integer that evenly divides two integers.
     *      Use this helper method in the Final Checkpoint to reduce fractions.
     *      Note: There is a different (recursive) implementation in BJP Chapter 12.
     * @param a - First integer.
     * @param b - Second integer.
     * @return The GCD.
     */
    public static int greatestCommonDivisor(int a, int b)
    {
        a = Math.abs(a);
        b = Math.abs(b);
        int max = Math.max(a, b);
        int min = Math.min(a, b);
        while (min != 0) {
            int tmp = min;
            min = max % min;
            max = tmp;
        }
        return max;
    }
    
    /**
     * leastCommonMultiple - Find the smallest integer that can be evenly divided by two integers.
     *      Use this helper method in Checkpoint 3 to evaluate expressions.
     * @param a - First integer.
     * @param b - Second integer.
     * @return The LCM.
     */
    public static int leastCommonMultiple(int a, int b)
    {
        int gcd = greatestCommonDivisor(a, b);
        return (a*b)/gcd;
    }
}
