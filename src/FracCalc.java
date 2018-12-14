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
    	
    	//
    	// this part checks for any unnecessary negative signs in the numerator and denominator and corrects them.
    	//
    	if (simplified[1] < 0 && simplified[0] != 0) 
    	{
    		simplified[1] = Math.abs(simplified[1]);
    	}
    	if (simplified[2] < 0) 
    	{
    		simplified[2] = Math.abs(simplified[2]);
    	}

    	String value = "";
    	
    	if (simplified[0] != 0 && simplified[1] != 0)
    	{
    		value += simplified[0] + "_" + simplified[1] + "/" + simplified[2];
    	}
    	else if (simplified[1] != 0 && simplified[2] != 1)
    	{
    		value += simplified[1] + "/" + simplified[2];
    	}
    	else if (simplified[0] != 0)
    	{
    		value += simplified[0];
    	}
    	else
    	{
    		value = "0";
    	}
    	
        return value;
    }
    
    private static int[] addition(int[] firstNumber, int[] secondNumber)
    {    	
    	int[] answer = new int[3];
    	
    	//
    	// some booleans for organization and so I can type less
    	//
    	boolean addition = false;
    	boolean negative = false;
    	
    	//
    	// Checks if both numbers are positive, if so, add the two normally
    	//
    	if (!isNegative(firstNumber) && !isNegative(secondNumber))
    	{
    		addition = true;
    	}
    	
    	//
    	// Checks if both numbers are negative, if so, turn both into positives, add normally, and then multiply by -1
    	//
    	else if (isNegative(firstNumber) && isNegative(secondNumber))
    	{
    		addition = true;
    		negative = true;
    		Math.abs(firstNumber[0]);
    		Math.abs(firstNumber[1]);
    		Math.abs(secondNumber[0]);
    		Math.abs(secondNumber[1]);
    	}
    	
    	if (addition)
    	{
    		//
    		// just normal fraction addition
    		//
    		answer[0] = firstNumber[0] + secondNumber[0];
    		answer[2] = firstNumber[2] * secondNumber[2];
    		answer[1] = (firstNumber[1] * secondNumber[2]) + (secondNumber[1] * firstNumber[2]);
    	}
    	else
    	{
    		int[] positiveNumber = new int[3];
    		int[] negativeNumber = new int[3];
    		//
    		// if only one of the numbers is negative, that's a pretty standard subtraction equation. No use in rewriting it.
    		//
    		if(firstNumber[0] < 0 || firstNumber[1] < 0) 
    		{
    			//
    			// if the first number is negative... Turn that number positive and throw it in subtraction
    			//
    			negativeNumber = turnPositive(firstNumber);
    			positiveNumber = secondNumber;
    		}
    		else
    		{
    			//
    			// if the second number is negative... Turn that number positive and throw it in subtraction
    			//
    			negativeNumber = turnPositive(secondNumber);
    			positiveNumber = firstNumber;
    		}
    		answer = subtraction(positiveNumber, negativeNumber);
    	}
    	
    	if (negative)
    	{
    		answer = turnNegative(answer);
    	}
    	
    	return answer;
    }
    
    private static int[] subtraction(int[] firstNumber, int[] secondNumber)
    {
    	//
    	// Same structure as addition. Only do subtraction if both numbers are positive or both are negative.
    	// If the second number is negative and the first is positive, or first is negative and second is positive, do addition.
    	//
    	
    	boolean subtraction = false;
    	boolean bothNegative = false;
    	int[] answer = new int[3];
    	
    	if (!isNegative(firstNumber) && !isNegative(secondNumber))
    	{
    		subtraction = true;
    	}
    	else if (isNegative(firstNumber) && isNegative(secondNumber))
    	{
    		subtraction = true;
    		bothNegative = true;
    	}
    	else if (isNegative(firstNumber))
    	{
    		answer = addition(firstNumber, turnNegative(secondNumber));
    	}
    	else
    	{
    		answer = addition(firstNumber, turnPositive(secondNumber));
    	}
    	
    	if (subtraction)
    	{
    		// 
    		// Step 1: Subtract whole numbers from each other
    		// Step 2: Check if first fraction is larger than other
    		// 				if so, subtract normally. If not, subtract one from whole number and add to numerator.
    		//
    		
        	
        	//
        	// Set the larger number as the minuend and the smaller as the subtrahend 
        	//
        	
        	int[] positiveNumber = greaterNumber(turnPositive(firstNumber), turnPositive(secondNumber));
        	int[] negativeNumber = lesserNumber(turnPositive(firstNumber), turnPositive(secondNumber));
        	
        	//
        	// checks if final answer needs to be turned negative
        	//
        	boolean turnNegativeAtEnd = false;
        	if (Arrays.equals(positiveNumber, turnPositive(secondNumber)) && !bothNegative)
        	{
        		turnNegativeAtEnd = true;
        	}
        	else if (Arrays.equals(positiveNumber, turnPositive(firstNumber)) && bothNegative)
        	{
        		turnNegativeAtEnd = true; 
        	}
        	
        	//
        	// This gives both numbers the same denominator and changes the numerators accordingly.
        	// Since we need to use the values we're changing, we'll assign the changed values to temporary variables and then assign them at the end
        	//
        	
        	
        	
        	answer[2] = positiveNumber[2];
    		if (positiveNumber[2] != negativeNumber[2])
    		{
    			int firstDen = positiveNumber[2] * negativeNumber[2];
    			int secondDen = positiveNumber[2] * negativeNumber[2];
    			int firstNum = positiveNumber[1] * negativeNumber[2];
    			int secondNum = positiveNumber[2] * negativeNumber[1];
    			answer[2] = positiveNumber[2] * negativeNumber[2];
    			
    			positiveNumber[2] = firstDen;
        		negativeNumber[2] = secondDen;
        		positiveNumber[1] = firstNum;
        		negativeNumber[1] = secondNum;
    		}

        	//
    		// This subtracts the whole numbers
    		//
    		answer[0] = positiveNumber[0] - negativeNumber[0];
        	
        	//
        	// if the minuend's numerator is larger than the subtrahend's... 
        	//
    		if (positiveNumber[1] >= negativeNumber[1])
    		{
    			answer[1] = positiveNumber[1] - negativeNumber[1];
    		}
    		//
    		// if it's not 
    		//
    		else
    		{
    			answer[0]--;
    			answer[1] = (positiveNumber[1] + answer[2]) - negativeNumber[1];
    		}
    		
    		//
    		// turn answer negative if needed
    		// 
    		
    		if (turnNegativeAtEnd)
    		{
    			answer = turnNegative(answer);
    		}
    	}
    	return answer;
    }

	private static int[] multiplication(int[] firstNumber, int[] secondNumber)
    {
		//
		// Turn both numbers positive and multiply, then change the sign accordingly.
		//
    	int[] answer = new int[3];
    	boolean positive = true;
    	
    	if((!isNegative(firstNumber) && isNegative(secondNumber)) || (isNegative(firstNumber)&& !isNegative(secondNumber)))
    	{
    		positive = false;
    	}
    	
    	firstNumber = turnPositive(firstNumber);
    	secondNumber = turnPositive(secondNumber);
    	
    	answer[1] = (firstNumber[1] + firstNumber[0] * firstNumber[2]) * (secondNumber[1] + secondNumber[0] * secondNumber[2]);
    	answer[2] = firstNumber[2] * secondNumber[2];
    	
    	if (!positive)
    	{
    		answer = turnNegative(answer);
    	}
    	return answer;
    }
    
    private static int[] division(int[] firstNumber, int[] secondNumber)
    {
    	//
		// Turn both numbers positive and divide, then change the sign accordingly.
		//
    	int[] answer = new int[3];
    	boolean positive = true;
    	
    	
    	if((!isNegative(firstNumber) && isNegative(secondNumber)) || (isNegative(firstNumber) && !isNegative(secondNumber)))
    	{
    		positive = false;
    	}
    	
    	firstNumber = turnPositive(firstNumber);
    	secondNumber = turnPositive(secondNumber);
    	
    	answer[1] = (firstNumber[1] + firstNumber[0] * firstNumber[2]) * secondNumber[2];
    	answer[2] = firstNumber[2] * (secondNumber[1] + secondNumber[0] * secondNumber[2]);
    	if (!positive)
    	{
    		answer = turnNegative(answer);
    	}
    	
    	return answer;
    }
    
    private static int[] simplify(int[] number)
    {
    	int gcd = greatestCommonDivisor(number[1], number[2]);
    	number[1] /= gcd;
    	number[2] /= gcd;
    	
    	if (isNegative(number) && number[1] > 0)
    	{
    		number[0] -= number[1] / number[2];
    	}
    	else
    	{
    		number[0] += number[1] / number[2] ;
    	}
    	number[1] = number[1] % number[2];

    	return number;
    }
    
    private static int[] returnNumberFromString(String input)
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
    	
    	if (number[0] != 0) number[1] = Math.abs(number[1]);
    	
    	return number;
    }
    
    private static boolean isNegative(int[] number)
    {
    	return number[0] < 0 || number[1] < 0;
    }
    
    private static int[] turnPositive(int[] number)
    {
    	//
		// Makes the number positive
		//
    	number[0] = Math.abs(number[0]);
		number[1] = Math.abs(number[1]);
		return number;
    }
    
    private static int[] turnNegative(int[] number)
    {
    	//
		// Makes the number negative
		//
		number[0] = Math.abs(number[0]) * -1;
		if (number[0] == 0)
		{
			number[1] = Math.abs(number[1]) * -1;
		}

		return number;
    }
    
    private static int[] lesserNumber(int[] firstNumber, int[] secondNumber) 
    {
    	//
    	// returns the smaller number
    	//
		int[] number;
		if (firstNumber[0] < secondNumber[0])
		{
			number = firstNumber;
		}
		else if (firstNumber[0] > secondNumber[0])
		{
			number = secondNumber;
		}
		else if (firstNumber[1] < secondNumber[1])
		{
			number = firstNumber;
		}
		else if (firstNumber[1] > secondNumber[1])
		{
			number = secondNumber;
		}
		else
		{
			number = firstNumber;
		}
		return number;
	}

	private static int[] greaterNumber(int[] firstNumber, int[] secondNumber) 
	{
		//
		// returns the larger number
		//
		int[] number;
		if (firstNumber[0] > secondNumber[0])
		{
			number = firstNumber;
		}
		else if (firstNumber[0] < secondNumber[0])
		{
			number = secondNumber;
		}
		else if (firstNumber[1] > secondNumber[1])
		{
			number = firstNumber;
		}
		else if (firstNumber[1] < secondNumber[1])
		{
			number = secondNumber;
		}
		else
		{
			number = firstNumber;
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
    private static int greatestCommonDivisor(int a, int b)
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
    private static int leastCommonMultiple(int a, int b)
    {
        int gcd = greatestCommonDivisor(a, b);
        return (a*b)/gcd;
    }
}
