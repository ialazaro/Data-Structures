/**
 * This will be teh tester to the hashTable created in this lab
 *
 *  @author Irvin Lazaro
 *  @version Lab 7
 *  @version CPe 103-07
 *  @version Winter 2016
 */

import java.util.*;

public class HashTest{

	public static void main(String[] args){
	
		Scanner s1 = new Scanner(System.in);
		System.out.println("Welcome to the Hash Table, Please input the Size of the table:\n");

		String option = "";
		String junk = "";
		int size = -1;
		int nOption = -1;
		int buckets = 0;
		int funct = -1;
		int inputVal = 0;

		do{
			if( s1.hasNextInt() ){
				size = s1.nextInt();

				if(size < 0)
					System.out.println("Please input a POSITIVE number:\n");
				
			}else{
				option = s1.nextLine();
				System.out.println("Please input a NUMBER:\n");

			}
			
		}while( size < 0);

		System.out.println("\nNumber of Buckets:\n");

		do{
			if( s1.hasNextInt() ){
				buckets = s1.nextInt();

				if(buckets <= 0)
					System.out.println("Please input a number greater than 0:\n");
				
			}else{
				option = s1.nextLine();
				System.out.println("Please input a NUMBER:\n");

			}
			
		}while( buckets <= 0);

		System.out.println("\nHash Function(1 or 2):\n");

		if(s1.hasNextInt() ){
			funct = s1.nextInt();
		}


		HashTableSC<Integer> tList = new HashTableSC<Integer>(size, funct, buckets);
		System.out.println("Menu:\n\ta(add/insert), f(find), d(delete), e(is empty), p(print), and q(quit).\n");
		junk = s1.nextLine();
		option = s1.nextLine(); 	
		nOption = value(option);

		while(nOption != 6){

			switch(nOption){

				case 1:
					System.out.println("What value do you wish to add:\n");
					if(s1.hasNextInt())
						inputVal = s1.nextInt();

					tList.insert(inputVal);

					System.out.println("\n" + inputVal + " has been added\n");

					System.out.println("\na(add/insert), f(find), d(delete), e(is empty), p(print), and q(quit).\n");
					junk = s1.nextLine();
					option = s1.nextLine(); 	
					nOption = value(option);

					break;
				case 2:
					System.out.println("What value do you wish to find:\n");
					
					if(s1.hasNextInt()){
						if( tList.find(s1.nextInt()) ){
							System.out.println("This value is inside the list");
						}else{
							System.out.println("This value is not inside the list");
						}
					}
					System.out.println("\na(add/insert), f(find), d(delete), e(is empty), p(print), and q(quit).\n");
					junk = s1.nextLine();
					option = s1.nextLine(); 	
					nOption = value(option);

					break;
				case 3:
					System.out.println("What value do you wish to delete:\n");		
					if(s1.hasNextInt())
						inputVal = s1.nextInt();

					if( !(tList.find(inputVal)) ){
						System.out.println("\nValue can not be found\n");
					}else{
						tList.delete(inputVal);
						System.out.println("\n" + inputVal + " has been deleted\n");
					}

					System.out.println("\na(add/insert), f(find), d(delete), e(is empty), p(print), and q(quit).\n");
					junk = s1.nextLine();
					option = s1.nextLine(); 	
					nOption = value(option);

					break;
				case 4:
					if(tList.isEmpty())
						System.out.println("The list IS empty");
					else
						System.out.println("The list is NOT empty");

					System.out.println("\na(add/insert), f(find), d(delete), e(is empty), p(print), and q(quit).\n");
					
					option = s1.nextLine(); 	
					nOption = value(option);

					break;
				case 5:
					System.out.println();
					tList.print();
					System.out.println("\na(add/insert), f(find), d(delete), e(is empty), p(print), and q(quit).\n");
					
					option = s1.nextLine(); 	
					nOption = value(option);

					break;
				case 6:
					break;
				default:
					System.out.println("\na(add/insert), f(find), d(delete), e(is empty), p(print), and q(quit).\n");
					junk = s1.nextLine();
					option = s1.nextLine(); 	
					nOption = value(option);
					break;
			}
		}

		System.out.println("Have a Nice Day");
	}

	private static int value(String option){

		option = option.toLowerCase();

		if(option.charAt(0) == 'a')
			return 1;
		if(option.charAt(0) == 'f')
			return 2;
		if(option.charAt(0) == 'd')
			return 3;
		if(option.charAt(0) == 'e')
			return 4;
		if(option.charAt(0) == 'p')
			return 5;
		if(option.charAt(0) == 'q')
			return 6;		
		
		return -1;
	}
}
