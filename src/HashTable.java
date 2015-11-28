public class HashTable {
	
	//default table properties
	//prime numbers work best to avoid collision
	private int size = 240007;
	static int entries=0;
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
public HashTable(){
	
	table = new HashEntry[size];
	for(int i =0; i<=(size-1);i++){
		table[i]=null;
	}
}

public void addEntry(String k, String v){
	//if the amount of entries are larger than the threshold muliplied by size, reorder table
	
	if(entries > thresholdfactor*size)
		tablereorder();
	
	HashEntry temp = new HashEntry(k, v);
	temp.setHash(this.hashFunction(k));
	
	
	
	if(table[temp.getHash()]==null || table[temp.getHash()].getValue()=="AVAILABLE"||table[temp.getHash()].getValue().charAt(0)=='-'){
		
		table[temp.getHash()]=temp;
		entries++;
	}
	//if not deal using alterante collision handling
	else
	{
		//if collision is set to D, do double hashing	
		if(getCollisionHandling()=='D')
		{
			
			doubleHash(temp);
			table[temp.getHash()]=temp;
			entries++;
			
		}
		else {
			//if collision is set to Q, do quadratic handling. DEFAULT
				Quad(temp);
				table[temp.getHash()]=temp;
				entries++;
		}
		
	}
	}

//quadratic handling of the function here. Adds based on quadratic hashing method.
private void Quad(HashEntry temp) {
	
	int value = temp.getHash();
	int i= 1;
	
	while(table[value]!=null && table[value].getKey()!="AVAILABLE" && table[value].getKey().charAt(0)!='-'&& i <= size)
		
	{
		value = (int) ((value + Math.pow(i, 2))%size);
		i++;
		collisions++;
	}
	
	temp.setHash(value);
	
}
private int searchQuad(String k){
	
	int h = this.hashFunction(k);
	int i =1;
	
	while(table[h]!=null && table[h].getKey()!=k && table[h].getKey()!=("- "+k) && i<=size){
		
		h = (int) ((h + Math.pow(i, 2))%size);
		i++;
		
	}
	
	return h;
}


//new doublehash function defined here. Uses alternate hashing to store value
private void doubleHash(HashEntry temp) {
	
	int h1 = temp.getHash();
	int h2 = (239999-(h1%239999));
	int i = 1;
	
	while(table[h1]!=null && table[h1].getKey()!="AVAILABLE" && table[h1].getKey().charAt(0)!='-'&& i <= size)
		
	{
		h1 = (h1+h2)%size;
		collisions++;
	}
	
	temp.setHash(h1);
	
}

private int searchDoubleHash(String k) {
	
	int h1 = this.hashFunction(k);
	int h2 = (239999-(h1%239999));
	int i =1;
	
	while(table[h1]!=null && table[h1].getKey()!=k && table[h1].getKey()!=("- "+k)&& i <= size)
		
	{
		h1 = (h1+h2)%size;
	}
	
	return h1;
	
}

private HashEntry replace(int o, int i, int h2){
	
	int originalHash = o;
	int doubleHash1 = o;
	int doubleHash2 = h2;
	
	if(collisionHandling == 'D'){
		
		doubleHash1 = (doubleHash1+ doubleHash2)%size;
	
		if(table[doubleHash1]==null || originalHash!=hashFunction(table[doubleHash1].getKey())){
			
			return null;
		}
		else{
			HashEntry temp = new HashEntry(table[doubleHash1].getKey(), table[doubleHash1].getValue());
			temp.setHash(doubleHash1);
			table[originalHash]=temp;
			table[doubleHash1]= replace(originalHash,i+1, h2);
		}
	}
	else{
		
		int quadHash=(int) ((originalHash + Math.pow(i, 2))%size);
		
		if(table[quadHash]==null || originalHash!=hashFunction(table[quadHash].getKey()))
			return null;
		else{
			HashEntry temp = new HashEntry(table[quadHash].getKey(), table[quadHash].getValue());
			temp.setHash(quadHash);
			table[originalHash]=temp;
			table[quadHash]= replace(originalHash,i+1, h2);
		}
		
	}
	return null;
	
}

public Integer getIndex(String k){
	
	int h = this.hashFunction(k);
	
	if(table[h]!=null && table[h].getKey()==k)
		return hashFunction(k);
	else return null;
}

public String get(String k){
	
	int h = this.hashFunction(k);
	
	if(table[h]!=null)
		return table[h].getValue();
	
	else return null;
	
	
}

public String put(String k, String v){
	
	int h = this.hashFunction(k);
	
	if(table[h]!=null){
		
		String old = table[h].getValue();
		table[h].setValue(v);
		return old;
	}
	else return null;
	
}

public String remove(String k){
	
	int h = this.hashFunction(k);
	int d =(239999-(h%239999));
	String old;
	
	if(table[h]!= null && table[h].getValue()==k){
		old = table[h].getValue();

		switch(emptyMarkerScheme){
		case 'R':
			
			replace(h,1,d);
			entries--;
			return old;
			
		case 'N':
			table[h].setKey("- "+table[h].getKey());
			entries--;
			return old;
		default: 
			table[h].setKey("AVAILABLE");
			entries--;
			return old;
		}
	}
	else if(table[h]!= null && table[h].getValue()!=k){
		
		switch(emptyMarkerScheme){
		case 'R':
			
			if(collisionHandling == 'D'){
				
				h = searchDoubleHash(k);
				
				if(table[h]==null||table[h].getKey()!=k)
					return null;
				else{
					old = table[h].getValue();
					replace(h,1,d);
					entries--;
					return old;
				}
			}
			else{
				
				h = searchQuad(k);
				
				if(table[h]==null||table[h].getKey()!=k)
					return null;
				
				else{
					old = table[h].getValue();
					replace(h,1,d);
					entries--;
					return old;
				}
			}
		
		case 'N':
			
			if(collisionHandling == 'D'){
				
				h = searchDoubleHash(k);
				
				if(table[h]==null||table[h].getKey()==("- "+k)||table[h].getKey()!=k)
					return null;
				else{
					old = table[h].getValue();
					table[h].setKey("- "+table[h].getKey());
					entries--;
					return old;
				}
				
			}
			else{
				h = searchQuad(k);
				
				if(table[h]==null||table[h].getKey()==("- "+k)||table[h].getKey()!=k)
					return null;
				
				else{
					old = table[h].getValue();
					table[h].setKey("- "+table[h].getKey());
					entries--;
					return old;
				}
			}
			
		default:
			if(collisionHandling == 'D'){
				
				h = searchDoubleHash(k);
				
				if(table[h]==null||table[h].getKey()!=k)
					return null;
				else{
					old = table[h].getValue();
					table[h].setKey("AVAILABLE");
					entries--;
					return old;
				}
				
			}
			else{
				h = searchQuad(k);
				
				if(table[h]==null||table[h].getKey()!=k)
					return null;
				
				else{
				old = table[h].getValue();
				table[h].setKey("AVAILABLE");
				entries--;
				return old;
				}
			}
		
		}
	}
		
	
	else return null;
	
}

public void entrySet(){
	int counter2=0;
	for(HashEntry iterator:table)
	{
		if(iterator==null)
			continue;
		else
		{
		
		counter2++;
		System.out.println(counter2);
		System.out.println(iterator.getKey() + " " + iterator.getValue());
		
		}
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
	
	return entries;
	
}

public boolean isEmpty(){
	
	boolean empty=true;
	
	for(int i=0; i<=(size-1);i++){
		
		if(table[i]!=null || table[i].getKey()!="AVAILABLE" ||table[i].getKey().charAt(0)!='-' )
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

//Reorders table if a certain amount of entries get filled
/*eg if threshold is set to 80%, and 80% of the table is filled, 
*a new table is made that is the size multiplied by the rehash multiplier(default 2)
*copy the values of table 1 inside table 2 and then set table 1 as table 2
*/
public void tablereorder()
{
	System.out.println("CAPACITY REACHED. REORDERING TABLE....");
	table2 = new HashEntry[(int) (size* rehashmultiplyer)];
	size=(int) (size* rehashmultiplyer);
	

	for(int i =0; i<=((table2.length-1));i++)
	{
		table2[i]=null;
	}
	
	for(int j =0; j<=((table2.length-1));j++)
	{

		if(j>table.length-1)
			break;
		
		
		table2[j]=table[j];
	
		
	
		
	}
	
	table=table2;

		
		
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

public void printHashTableStatistics(){
	
	System.out.println("==============STATISTICS==============");
	System.out.println("Rehash Threshold: "+thresholdfactor);
	System.out.println("Rehash Factor: "+rehashmultiplyer);
	System.out.println("Collision Handling: "+collisionHandling);
	System.out.println("Empty Marker Scheme: "+emptyMarkerScheme);
	System.out.println("Entries: "+size());
	System.out.println("Load Percentage: "+(entries/size)+"%");
	System.out.println("Number of Collisions: "+collisions);
	System.out.println("Maximum # of Collisions for one cell: ");
	System.out.println("Average # of Collisions among cells: ");
	System.out.println("======================================");
	
}


public void resetHashTableStatistics(){
	
	thresholdfactor=0.5;
	rehashmultiplyer=1.15;
	collisionHandling='Q';
	emptyMarkerScheme='A';
	size=240007;
	entries=0;
	collisions=0;
	
	
	
	
}

}