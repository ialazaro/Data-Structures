/**
 * This is my generic MyStack class this will follow the instructions given
 *
 * @author Irvin Lazaro
 * @version Project 1
 * @version CPE 103-07
 * @version Winter 2016
 */

import java.util.*;

public class MyStack<T>{

	private Node firstNode;

	//constructor of an empty stack that takes no parameter and sets first node to null
		
	public MyStack(){
	
		firstNode = null; //set an empty list
	}
	
	/**
 	* Adds a node to the top of the list (since a Stack is a first in)
 	* the parameter is the value in which a node is created and place in the stack
 	* If the list is empty, the the first node is assigned to this new node
 	* otherwise, it sets the new node's next pointer to the first node and the first node becomes the new node 
 	*
 	* @param T type
 	*/
 	
	public void push(T type){
		
		Node current = new Node(type, null); //next is null depending on the size of the list
		
		if(isEmpty()){
			firstNode = current; //next stays null

		}else{

			current.next = firstNode; //on top of the list
			firstNode = current; //define the top of the list
		}
	}

	public T pop(){

		if(isEmpty())
			throw new EmptyStackException();

		T removed = topElement(); //value that was removed
		firstNode = firstNode.next; //the node before will have no more reference (deleted)
		
		return removed;
	}

	public T topElement(){
		
		if(isEmpty())
			throw new EmptyStackException();

		return firstNode.data; //first node is the top element and we want its data
	}

	public boolean isEmpty(){
		
		return (firstNode == null);
	}

	private class Node {

		public T data; //value in the node
		public Node next; //next pointer

		public Node(T data, Node next){

			this.data = data;
			this.next = next;
		}

	}
}

class StackTest{


	public static void main(String[] args){

		MyStack<String> list = new MyStack<String>();

		Scanner s1 = new Scanner(System.in);

		System.out.println("Choose one of the following operations:\n-push/add (enter the letter a)\n-pop/delete (enter the letter d)\n-topElement (enter the letter p)\n-check of the list is empty (enter the letter e)\n-Quit (enter the letter q)\n");

		String option = s1.nextLine();
		String input = ""; //value that will be added

		//as long as the user doesn't input "q"
		while(!(option.equals("q"))){

			//these are are the different options and what each option does
			switch (option) {
				
			case("a"):
				System.out.println("\nWhat would you like to add: ");
				input = s1.nextLine();
				list.push(input);
				System.out.println("\n" + input + " pushed in");
				break;
			case("d"):
				try{
					System.out.println("\n" + list.pop() + " popped out"); //instead of terminating, returns a message
				}
					
				catch(EmptyStackException e){
					System.out.println("\nInvalid operation on an empty stack");
				}
				break;
			case("p"):
				try{
					System.out.println("\n" + list.topElement() + " on the top" );
				}
				catch(EmptyStackException e){
					System.out.println("\nInvalid operation on an empty stack");
				}
				break;
			case("e"):
				if(list.isEmpty()){
					System.out.println("\nempty");
				}else{
					System.out.println("\nnot empty");
				}
				break;
			default:
				System.out.println("\nInvalid Choice");
				break;
			}
				
			System.out.println("\nEnter a menu choice");
			option = s1.nextLine(); //if the option is q, the loop ends
		}

		System.out.println("\nquitting\n\nHave a Nice Day");
	}
}

