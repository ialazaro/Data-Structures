/**
 * This is the converter class that will implement and test the Stack class created
 * 
 * @author Irvin Lazaro
 * @version Project 1
 * @version CPE 103-07
 * @version Winter 2016
 */

import java.util.*;

public class Converter{
	
	//no constructor since the methods will be static

	/**
 	* This method converts a String written in infix to a new string that is postfix.
 	* It starts with creating a stack that will store operands depending on the rules of conversion
 	* The a scanner is created to read the input string 
 	* each operation/operand will be saved in a string "op"
 	* String "result" will be returned
 	* Depending on what "op" is, it will go into several rules to either place "op" in the stack to place it in "result"
 	*
 	* @param expression String 
 	* @return String result
 	*/

	public static String infixToPostfix(String expression){

		MyStack<String> storage = new MyStack<String>();

		Scanner s1 = new Scanner(expression);
		String result = "";
		String op = "";

		while(s1.hasNext()){

			op = s1.next();

			switch(op){
			
			//checks if operation or not
			case "+":

				if(storage.isEmpty()){
					storage.push(op); //top priority
			
				}else if((storage.topElement()).equals("*") || (storage.topElement()).equals("/") ){
					
					//since * and / are higher priority, then the stack will be poped until the stack is empty or we encounter a "("
					boolean done = false; //should we need to end the loop

					while(!done){
						
						result = result + storage.pop() + " ";

						if(storage.isEmpty()){
							done = true;

						}else if (storage.topElement().equals("(")){
							done = true;
						}
					}

					storage.push(op); //pushed after loop

				} else if(storage.topElement().equals("(")){
					storage.push(op); //"+" is put on top of "("
				
				} else if((storage.topElement()).equals("+") || (storage.topElement()).equals("-") ){

					result = result + storage.pop() + " "; //puts the old value of "+" or "-" in result
					storage.push(op);//pushes new operator
				}

				break;
			case "-":
				//similar rules as "+"

				if(storage.isEmpty()){
					storage.push(op);

				}else if((storage.topElement()).equals("*") || (storage.topElement()).equals("/") ){
				
					boolean done = false;
	
					while(!done){

						result = result + storage.pop() + " ";

						if(storage.isEmpty()){
							done = true;
						}else if (storage.topElement().equals("(")){
							done = true;
						}
					}

					storage.push(op);
 
				} else if(storage.topElement().equals("(") ){
					storage.push(op);
				
				} else if((storage.topElement()).equals("+") || (storage.topElement()).equals("-") ){
					
					result = result + storage.pop() + " ";
					storage.push(op);
				}
				
				break;

			case "*":
				if(storage.isEmpty()){
					storage.push(op); //higher priority
				}else if((storage.topElement()).equals("+") || (storage.topElement()).equals("-") || (storage.topElement()).equals("(") ){
					storage.push(op); //higher priority in any of these cases
				}
				
				else if((storage.topElement()).equals("*") || (storage.topElement()).equals("/") ){
					
					result = result + storage.pop() + " "; //pops the old operand into result
					storage.push(op); //pushes new operand
				}
				break;
	
			case "/":
				//same rules as "*"

				if(storage.isEmpty()){
					storage.push(op);
				}else if(storage.topElement().equals("+") || storage.topElement().equals("-") || storage.topElement().equals("(") ){
					storage.push(op);

				}else if((storage.topElement()).equals("*") || (storage.topElement()).equals("/") ){
					
					result = result + storage.pop() + " ";
					storage.push(op);
				}
				break;

			case "(":
				storage.push(op); //highest priority
				break;

			case ")":
				while(!(storage.topElement().equals("("))){
					//goes until we reached "(" (assuming the expression input is in correct format)
					result = result + storage.pop() + " ";
				}
				storage.pop();
				break;
			
			default:
				//if it's just a value
				result = result + op + " ";
				break;
			}
		}

		while(!(storage.isEmpty())){
			//returns the remaining operations inside the stack (since it's postfix)
			result = result + storage.pop() + " ";

		}
		
		return result;

	}

	/**
 	* This method just solves the postfix expression (that is assumed to be in double values for operands).
 	* Depending on the rules for a postfix evaluation, the method will store operands(that have been parsed to double) to a stack and +,-,*, or / the first two values inside the stack.
 	* 
 	* @param expression String
 	* @return double result  
 	*/

	public static double postfixValue(String expression){

	double result = 0; //result of the operation
	double value1 = 0; //value given by the second value in the stack
	double value2 = 0; //calue given by the first value in the stack(topElement)

	MyStack<Double> list = new MyStack<Double>(); //stack that stores doubles

	Scanner s1 = new Scanner(expression);
	
	String op = "";

	while(s1.hasNext()){

		op = s1.next();

		switch(op){
		//all operations assume that the stack has two values stored
		case "+":
			value2 = list.pop(); //first element
			value1 = list.pop(); //second element
			result = value1 + value2; //operation
			list.push(result); //pushes result in stack
			break;

		case "-":
			value2 = list.pop(); //first
			value1 = list.pop(); //second
			result = value1 - value2; //operation
			list.push(result); //pushes result in stack		
			break;

		case "*":
			value2 = list.pop(); //first
			value1 = list.pop(); //second
			result = value1 * value2; //operation
			list.push(result); //result pushed in stack
			break;
		
		case "/":
			value2 = list.pop(); //first
			value1 = list.pop(); //second
			result = value1 / value2; //operation
			list.push(result); //result pushed in stack
			break;
		
		default:
			value1 = Double.parseDouble(op); //converts the string value to a double			
			list.push(value1); //pushes value without being operated

			break;

		}

	} 


		return list.pop(); //the final result
	}

}

class ConverterTest{


	public static void main(String [] args){
		
		Scanner s1 = new Scanner(System.in);

		String option = ""; //option in the menu
		String expression = ""; //expression after option was given

		System.out.println("Choose one of the following operations:\n\t-Infix to postfix conversion (enter the letter i)\n\t-Postfix expression evaluation (enter the letter p),\n\t-Arithmetic expression evaluation (enter the letter a)\n\t-Quit the program (enter the letter q)");

		option = s1.nextLine();

		while(!(option.equals("q"))){

			switch(option){
				
				case "i":
					System.out.println("\nExpression\n");
					expression = s1.nextLine();
					System.out.println("\nthe postfix expression is: " + Converter.infixToPostfix(expression)); //returns converted
					break;
				case "p":
					System.out.println("\nExpression:\n");
					expression = s1.nextLine();
					System.out.println("\nthe postfix expression is: " + Converter.postfixValue(expression));//final result returned
					break;
				case "a":
					
					System.out.println("\nExpression:\n");
					expression = s1.nextLine();
					expression = Converter.infixToPostfix(expression); //first converts	
					System.out.println("\nthe value of the arithmetic is: " + Converter.postfixValue(expression));	//gives final result		
					break;
				default:
					System.out.println("\nInvalid choice");
					break;
			}
			
			System.out.println("\nOption: "); //asks for another option
			option = s1.nextLine();		
		}

		System.out.println("quitting");
		System.out.println("\n\nHave a Nice Day");
	}

}
