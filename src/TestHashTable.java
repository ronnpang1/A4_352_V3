
public class TestHashTable {

	//default table properties
	//prime numbers work best to avoid collision
	private int size = 240007;
	private int entries=0;
	private int collisions=0;
	HashEntry[] table;
	HashEntry[] table2;
	
	//factor of 0.5,  quadratic collision handling optimal when list is at most half full.
	private double thresholdfactor=0.5;
	private double rehashmultiplyer=1.15;
	
	//Default collision Handling and empty market scheme
	private char collisionHandling='Q';
	private char emptyMarkerScheme='A';

//HashTable constructor, Initialises table elements.
public TestHashTable(){
	
	table = new HashEntry[size];
	for(int i =0; i<=(size-1);i++){
		table[i]=null;
	}
}
	
public void entrySet(){
	
	for(HashEntry iterator:table)
	{
		if(iterator==null)
			continue;
		else
		System.out.println(iterator.getKey() + " " + iterator.getValue());
		
	}
	
}

public void keySet(){
	for(HashEntry iterator:table)
	{
		if(iterator==null)
			continue;
		else
		System.out.println(iterator.getKey());
		
	}
	
	
}

public void valueSet(){
	
	for(HashEntry iterator:table)
	{
		if(iterator==null)
			continue;
		else
		System.out.println(iterator.getValue());
	}
	
	
}



public int size(){
	
	/*	
	for(int i =0; i<=(size-1); i++){
		
		if(table[i]!=null)
			entries++;
	}
	*/
	return entries;
}

public boolean isEmpty(){
	
	boolean empty=true;
	
	for(int i=0; i<=(size-1);i++){
		
		if(table[i]!=null)
			empty=false;
		
	}
	
	return empty;
	
}

private int hashFunction(String s){
	
	int h = 0;
	int length = s.length();
	
	for(int i = 0; i <= (length-1); i++){
		
		 h = h+((length-i)*s.charAt(i));
		 
		}
	
	h = ((((3000*h)+12678)%240011)%240007);
	
	return h;
	}


public void setCollisionHandling(char type)
{
	
	
	collisionHandling=type;
		
}

public void setRehashThreshold(double loadFactor)
{

	
	thresholdfactor=loadFactor;	

}

public void setRehashFactor(double factorOrNumber){
	
	rehashmultiplyer = factorOrNumber;
	
}

public char getCollisionHandling() {
	
	return collisionHandling;
}


public void setEmptyMarkerScheme(char c){
	
	emptyMarkerScheme = c;
	
	
}

public char getEmptyMarkerScheme(){
	
	return emptyMarkerScheme;
}
	
}
