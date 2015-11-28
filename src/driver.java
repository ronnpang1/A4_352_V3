import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class driver {

	public static void main(String[] args) throws IOException {
		int counter=0;
		HashEntry[] table =new HashEntry[100];
		HashTable ht = new HashTable();
		//ht.setCollisionHandling('D');
		for(int i =0; i<=(100-1);i++){
			table[i]=null;
		}
		
		
		
		try {
	    	String line= null;
	        
	        FileReader fileReader = 
	            new FileReader("output.txt");
	        String[] a = new String[20];
	       ArrayList<String> listLines = new ArrayList<String>();
	        BufferedReader bufferedReader = 
	            new BufferedReader(fileReader);
	        StringBuffer stringBuffer = new StringBuffer();
	        while((line = bufferedReader.readLine()) != null) {
	        	counter++;
	        	System.out.print(counter);
	        	ht.addEntry(line,line);
	        	System.out.println(line);	 
	        	
	        }   
	       
	        //ht.keySet();
	        System.out.println("SIZE:"+ ht.entries);
	        System.out.println(ht.size());
	        System.out.println("TABLE 2");
	        ht.entrySet();
	        
	        bufferedReader.close();         
	    }
	    catch(FileNotFoundException ex) {
	    	
	    	
	    }
		

		
		/*HashTable ht = new HashTable();
		HashTable th = new HashTable();
		ht.addEntry("hello", "hello");
		ht.addEntry("hello", "hello");
		System.out.println(ht.size());
		System.out.println(th.size());
		ht.entrySet();
		ht.keySet();*/
	}

}