/**
 * This Project is about setting up a hash table structure that has seperated chaining, linear probing, quadratic probing, and bucket probing.
 *
 *  @author Irvin Lazaro
 *  @version Project 03
 *  @version CPE 103-07
 *  @version Winter 2016
 */

import java.util.*;
import java.lang.Math;

public class HashTableSC<T> {

	//all these arrays will be added, subtracted, and searched by the same input. 
	//How it will be handle depends on the how the program does linear, quadratic, and bucket probing

	private LinkedList<T>[] table; //LinkedList to chain collisions
	private T[] linear; //this table will be "sorted" by linear probing
	private T[] quad; //this table will be "sorted" by quadratic probing
	private T[] bucket; //this table will be "sorted" by buckets

	//whatever type of hash function the user wants to do
	private int func = 0;

	private int col = 0;
	private int linearCol = 0; //keeps track of every collision that occurs during linear probing
	private int linearCap = 0; //keeps track of the capacity of the linear items 
	private int quadCol = 0; //keeps track of collision during qadratic probing
	private int quadCap = 0; //keeps track of the capacity of the quadratic items
	private int buckCol = 0; //keeps track of collision during bucket probing
	private int bucketCap = 0; //keeps track of the capacity of the bucket elements

	private int numBuckets = 0;	//number of buckets that will be set by the user in one of the constructors
	private int elementsInBucket = 0; //number of elements in each bucket
	private int overFlow = 0; //the location of where the overflow should be
	private int size;

	//this constructor will set all the arrays to the size inside the parameter, then it takes the number of buckets, and finally will use the "default" hash function 
	public HashTableSC(int parameter, int numBuckets){

		size = parameter;

		if(numBuckets <= 0){
			this.numBuckets = 1;
		}
		else if(numBuckets > parameter){
			this.numBuckets = parameter;
		}
		else{
			this.numBuckets = numBuckets;
		}

		elementsInBucket = parameter/this.numBuckets;
		overFlow = elementsInBucket*(this.numBuckets);


		table = new LinkedList[parameter];
		
		//fill the table with empty linked list
		for(int i = 0; i < parameter; i++){
			table[i] = new LinkedList();
		}

		linear = (T[])new Object[parameter];
		quad = (T[])new Object[parameter];
		bucket = (T[])new Object[parameter];

		//sometimes the overflow goes over the last element(there is no overflow)
		if(overFlow >= bucket.length){
			extendOverFlow();
		}
	}

	//similar to the first constructor, only difference being that the user sets the hash function
	public HashTableSC(int size, int hFunct, int numBuckets){

		this.size = size;

		if(hFunct == 1 || hFunct == 2)
			func = hFunct;		

		if(numBuckets <= 0){
			this.numBuckets = 1;
		}
		else if(numBuckets > (size-1)){
			this.numBuckets = (size-1);
		}
		else{
			this.numBuckets = numBuckets;
		}

		elementsInBucket = size/this.numBuckets;
		overFlow = elementsInBucket*(this.numBuckets);

		table = new LinkedList[size];
		
		for(int i = 0; i < size; i++){
			table[i] = new LinkedList();
		}

		linear = (T[])new Object[size];
		quad = (T[])new Object[size];
		bucket = (T[])new Object[size];

		//sometimes the overflow goes over the last element(there is no overflow)
		if(overFlow >= bucket.length){
			extendOverFlow();
		}
	}

	//default hash function where the index return will be the modulus of the key with the size of the array
	private int hash(T parameter){

		int value = (Integer)parameter; //key turned into an integer
		return value%(table.length); //index being the modulus
	}


	private int linearProbing(int index, T object){

		int counter = 0; //so that it goes throught the array around once
		boolean done = false;


		//this loop should stop when the method either finds an empty space, sees a repeated value, or it has gone through the entire array
		while(!done){

			if(linear[index] == null){
				done = true;
			}else if(object.equals(linear[index])){
				done = true;
			}else if(counter >= linear.length){
				done = true;
			}else{

				index++; //move to the next element
				counter++; //keeps track of how many times it has looped
				linearCol++;

				//reaching the end of the array
				if(index > (linear.length - 1) )
					index = 0;
			}

		}
		
		return index;	
	}

	private int quadProbing(int index, T object){
		
		//if the index given does not return an empty space then that's when it does the quadratic probing

		int i = 1; //the value that gets squared and then added to the index;
		int original = index;

		while(quad[index] != null && !(object.equals( quad[index] )) && i != quad.length ){

			quadCol++; //colision has occured
			index = original + ( (int)Math.pow(i, 2) ); //move i^2 from where the original index value
			i++; //4, 9, 16, ...

			//when the index goes pass the length of the array
			if(index >= (quad.length) )

				//if for some reason the quadratic probing goes way to far and the index is greater than twice or three times the array length
				while(index > (quad.length - 1)){
					index -= quad.length;
				}
		}

		//whatever the index becomes at the end, whether the index gives back the right object or not will be delt with the the methods that use this index
		return index;
	}

	private int bucketProbing(int index, T object, int enable){

		//which bucket does the index belong to?
		int buckNumber = index%numBuckets;

		//finds the index of the first element inside the bucket
		index = buckNumber*elementsInBucket;

		int counter = 1; //counts until it reaches the end of the bucket

		//the loop will go on until it finds an empty spot, it discovers a repeated value, or it has gone through all elements in the bucket
		while(bucket[index] != null && !(object.equals( bucket[index]) ) && counter < elementsInBucket){

				if(enable == 1)
					buckCol++; //colision occurs

				index++; //moves until to the next index
				counter++; //keeps going until reaching the capacity of the bucket
		}

		//if you found the value you're looking for (especially for deleting and finding)
		if(object.equals( bucket[index] )){
			return index;
		}

		//if the end of the bucket is occupied
		else if(bucket[index] != null){

			index = overFlow; //goes to the first element in the overflow

			//if the overflow has an empty spot
			if(bucket[index] == null){
				return index;
			}else if(object.equals(bucket[index])){
				return index; //when it comes to the delete function, you want to keep that same index
			}else{
				//go until you reached the end of the array table, or you found a repeated value, or you found an empty spot
				while( bucket[index] != null && !(object.equals( bucket[index]) ) && index < (bucket.length - 1) ){
					
					if(enable == 1)
						buckCol++;//more collision

					index++;//move to the next value
				}

				//if you're at the end and it's full
				if(index == (bucket.length - 1) && bucket[(bucket.length-1)] != null){
					extendOverFlow();
					index++;
				}

			}

		}

		return index;  //will return the final location of where it should be (the other methods will figure out what to do with the index)
	}

	private void extendOverFlow(){

		T[] temp = (T[])new Object[bucket.length + elementsInBucket];

		for(int i = 0; i < bucket.length; i++){

			if(bucket[i] != null){

				temp[i] = bucket[i];

			}

		}

		bucket = temp;
	}

	private void assuringBucket(){

		T[] temp = (T[])new Object[bucket.length];
		int index = 0;
		int loc = 0;

		for(int i = 0; i < bucket.length; i++){

			if(bucket[i] != null){

				if(func == 1){
					index = hash_func1( (bucket[i]).toString() );
					loc = index;
				} else if( func == 2 ){
					index = hash_func2(  (bucket[i]).toString() );
					loc = index;
				} else {	
					index = hash( bucket[i] );
					loc = index;
				}

				loc = bucketProbing(index, bucket[i], 0);	

				if(temp[loc] == null)
					temp[loc] = bucket[i];
				
			}

		}

		bucket = temp;

	}

	public void insert(T object){
	
		int index = 0;
		int loc = 0;

		//which funtion should be used?
		if(func == 1){
			index = hash_func1( object.toString() );
			loc = index;
		} else if( func == 2 ){
			index = hash_func2(  object.toString() );
			loc = index;
		} else {	
			index = hash(object);
			loc = index;
		}
		
		//chaining in the same way as Lab 7
		if(!( ( table[index] ).contains( object ) )){
			
			if( (table[index]).size() == 0 ){
				(table[index]).add(object);
			}else{	
				col++;			
				(table[index]).addFirst(object);
			}
		}

		//linear probing

		//set the index to the value given by linear probing(this is where the object should be)
		loc = linearProbing(index, object);

		//if the index given by linear probing returns an empty space, fill it in with the object, otherwise, don't do anything
		if(linear[loc] == null){
			linear[loc] = object;
			linearCap++;
		}
		

		//quadratic probing

		//set the index to the value given by quadratic probing(this is where the object should be)
		loc = quadProbing(index, object);

		//if the index given by quadratic probing returns an empty space, fill it in with the object, otherwise, don't do anything
		if(quad[loc] == null){
			quad[loc] = object;
			quadCap++;
		}
		

		//bucket
		
		//finds the bucket, the index inside the bucket, and then an overflow value in case there is one
		loc = bucketProbing(index, object, 1);

		//does it give an empty spot, then insert to that spot
		if(bucket[loc] == null){
			bucket[loc] = object;
			bucketCap++;
		}
		
	}

	public void delete(T object){

		int index = 0; //index given by the hash function
		int loc = 0; //index value that will always be manipulated

		//which funtion should be used?
		if(func == 1){
			index = hash_func1( object.toString() );
			loc = index;
		} else if( func == 2 ){
			index = hash_func2( object.toString() );
			loc = index;
		} else {	
			index = hash(object);
			loc = index;
		}

		//removing the value from the chain
		if( ( table[index] ).contains( object ) )
			(table[index]).remove(object);

		//remove from the linear probe value
		
		//retriving the location of the object
		loc = linearProbing(index, object);

		//if the index returns that object
		if( (linear[loc]).equals(object) ){
			linear[loc] = null;
			linearCap--;
		}

		//removing the value from the quadratic probe
		
		//getting the location from qaud probing
		loc = quadProbing(index, object);

		if( (quad[loc]).equals(object) ){
			quad[loc] = null;
			quadCap--;
		}

		//buckets

		loc = bucketProbing(index, object, 1);

		if( bucket[loc] != null ){

			if( (bucket[loc]).equals(object) ){
				bucket[loc] = null;
				bucketCap--;
			}
		}

		assuringBucket();

	}

	public boolean find(T object){

		int index = 0; //index given by the hash function
		int loc = 0; //index value that will always be manipulated
		int pass = 0; //the number of passes that goes thorugh each check

		//which funtion should be used?
		if(func == 1){
			index = hash_func1( object.toString() );
			loc = index;
		} else if( func == 2 ){
			index = hash_func2( object.toString() );
			loc = index;
		} else {	
			index = hash(object);
			loc = index;
		}

		//chaining

		if( ( table[index] ).contains( object ) )
			pass++;

		//linear

		//retriving the location
		loc = linearProbing(index, object);

		//is it the same object
		if( linear[loc] == null ){

		}else if( (linear[loc]).equals(object) ){
			pass++;
		}

		//quadratic

		loc = quadProbing( index, object);

		if( quad[loc] == null ){

		}else if( (quad[loc]).equals(object) ){
			pass++;
		}

		//bucket

		loc = bucketProbing(index, object, 1);

		if( bucket[loc] == null ){

		}else if( (bucket[loc]).equals(object) ){
			pass++;
		}

		//if the method passes all the checks, then return four (there are a total of 4 checks)
		return (pass == 4);
	}

	//checking every array in the program
	public boolean isEmpty(){

		int fail = 0;

		for(int i = 0; i < table.length; i++){
			
			if( ( table[i] ).size() > 0 )
				fail++;			
		}

		for(int i = 0; i < linear.length; i++){

			if( linear[i] != null )
				fail++;

		}

		for(int i = 0; i < quad.length; i++){

			if( quad[i] != null )
				fail++;

		}

		for (int i = 0; i < bucket.length; i++ ) {
			
			if( bucket[i] != null)
				fail++;

		}

		return (fail == 0);
	}

	public void print(){

		System.out.println("Chaining:\n\n");

		String pLine = "";

		for(int i = 0; i < table.length; i++){

			pLine = pLine + i + ": ";

			for(T temp : table[i] ){
				
				pLine = pLine + temp + " ";
			}

			System.out.println(pLine);			
			pLine = "";
		}

		if(col > 0){

			pLine = "";

			System.out.println("\nLinear:\n\n");

			for(int i = 0; i < linear.length; i++){

				pLine = pLine + i + ": ";
	
				pLine = pLine + linear[i] + " ";

				System.out.println(pLine);			
				pLine = "";

			}

			System.out.println("\nColisions: " + linearCol + "\n");

		



			pLine = "";

			System.out.println("\nQuadratic:\n\n");

			for(int i = 0; i < quad.length; i++){

				pLine = pLine + i + ": ";
	
				pLine = pLine + quad[i] + " ";

				System.out.println(pLine);			
				pLine = "";
			}

			System.out.println("\nColisions: " + quadCol + "\n");


			pLine = "";

			int counter = 0;
			int bucketCounter = 0;

			System.out.println("\nBucket:\n\n");

			for(int i = 0; i < bucket.length; i++){

				if(i == overFlow){
					System.out.println("\nOverflow:\n");
				}
				if(i%elementsInBucket == 0  && i < overFlow){
					System.out.println("\nBucket " + (bucketCounter) + ":\n");
					bucketCounter++;
				}

				pLine = pLine + i + ": ";
	
				pLine = pLine + bucket[i] + " ";

				System.out.println(pLine);			
				pLine = "";

			}

			System.out.println("\nColisions: " + quadCol + "\n");
		}

	}

	//I fixed the problem of having to deal with the ascii values of integer values
	//if it is an integer Hashtable, then the program will deal with them as actual numbers
	//as oppose to being characters with ascii values

	private int hash_func1(String key){

		int value = 0;
		int keyValue = 0;
		int newVal = 0;
		int base = 1;

		if(key == null || key.equals("")){
			return 0;
		}


		for(int i = 0; i < key.length(); i++){

			keyValue = (int)(key.charAt(i));

			value += ( base*(keyValue) );
			base *=27;

		}

		newVal = value%size;
		return newVal;		
	}

	private int hash_func2(String key){

		long value = 0;
		long power = 0;
		long keyValue = 0;
		int size = table.length;
		int kSize = key.length();

		if(key == null){
			return 0;
		}

		for(int i = 0; i < kSize; i++){

			keyValue = (long)(key.charAt(i));

			power = (long)( Math.pow(37, i));
			value += ( power*(keyValue) );
		}
				
		long newVal = value%size;
		return ((int)newVal);
	}
}
