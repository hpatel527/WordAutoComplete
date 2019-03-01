import java.util.LinkedList;
import java.util.*;

public class Hashtable {
	private class HashtableNode {
		private Object key;
		private Object data;

		public HashtableNode() {
			this.key = null;
			this.data = null;
		}

		public HashtableNode(Object inKey, Object inData) {
			this.key = inKey;
			this.data = inData;
		}

		/* Equality can be based on key alone because there can't be
		 * 2 nodes with the same key in the table */
		public boolean equals(Object obj) {
			if (obj instanceof HashtableNode) {
				HashtableNode node = (HashtableNode)obj;
				return this.key.equals(node.key);
			}
			else {
				return false;
			}
		}
      
      public Object getData()
      {
         return this.data;
      }
      
      public Object getKey()
      {
         return  this.key;
      }

		public String toString() {
			return this.data.toString();
		}
	}
	//
	//
	private final int tableSize = 257100;
	private int numElements;
	private Object [] table;
	
	//constructor
	public Hashtable(int realSize) {
		this.table = new Object[realSize];
		this.numElements = 0;
	}
	
	public Hashtable() {
		this.table = new Object[this.tableSize];
		this.numElements = 0;
	}
	
	private int hash(Object key) {

		/* Start with a base, just so that it's not 0 for empty strings */
		int result = 42; //start at hard coded base

		String inputString = key.toString().toLowerCase();
		//System.out.println("hash string is:" + inputString + "\n");

		char [] characters = inputString.toCharArray();
		for (int i = 0; i < characters.length; i++) {
			char currentChar = characters[i];

			if (currentChar == 'a' || currentChar == 'b' || currentChar == 'c' ||
					currentChar == 'd' || currentChar == 'e' || currentChar == 'f') {
				result += Integer.parseInt(""+currentChar, 16);
			}

			int j = (int)currentChar;
			//System.out.println("j = " + j );
			result += j;
		}

		return (result % this.tableSize);
	}

	public void add(Object key, Object data) {
		if (data == null || key == null) {
			System.err.println("ERROR: Either the key or the data are null");
			return;
		}

		// If trying to add duplicate keys, that means 
		// we like to update the value associated with that existing key.
		// We then first delete the existing mapping, then insert a new record that key.
		if (this.contains(key)) {
			remove(key);
		}

		// Find out where in our array should the new item goes 
		int position = this.hash(key);

		// If nothing exists in the position, create a new linked list there 
		if (this.table[position] == null) {
			this.table[position] = new LinkedList<HashtableNode>();
		}
      
      else
      {
         LinkedList<HashtableNode> htn = (LinkedList<HashtableNode>)this.table[position];
         HashtableNode node = htn.getFirst();
         Object k = node.getKey();
         
         if(!k.equals(key))
         {
            position = linearProbe(position);
            this.table[position] = new LinkedList<HashtableNode>();
         }
      }
      
      

		// Add to the linked list in the appropriate position
		((LinkedList<HashtableNode>)this.table[position]).add(new HashtableNode(key, data));
		this.numElements++;
	}
   
   
   private int linearProbe(int pos, Object key)
   { 
      for(int i = pos; i < table.length; i++)
      {         
         if(this.table[i] != null)
         {
            LinkedList<HashtableNode> tmp = (LinkedList<HashtableNode>)this.table[i];
            HashtableNode n = tmp.getFirst();
            Object k = n.getKey();      
                
            if(k.equals(key))
            {
               return i;
            }
         }
      }
      
      for(int i = 0; i < pos; i++)
      {
         if(this.table[i] != null)
         {
            LinkedList<HashtableNode> tmp = (LinkedList<HashtableNode>)this.table[i];
            HashtableNode n = tmp.getFirst();
            Object k = n.getKey();  
            
            if(k.equals(key))
            {
               return i;
            }
         }
      }
      
      return pos;
   }
   
   private int linearProbe(int pos)
   {
      for(int i = pos; i < table.length; i++)
      {
         if(this.table[i] == null)
         {
            return i;
         }
     }
     
     for(int i = 0; i < pos; i++)
     {
       if(this.table[i] == null)
       {
         return i;
       }
     }
     
     return pos;
   }
   
	
	public void add(Object [] keys, Object [] inputData) {
		for (int i = 0; i < inputData.length; i++) {
			this.add(keys[i], inputData[i]);
		}
	}
   
  /* public ArrayList<String> get(String key)// Purpose: Searchs Hashtable for key and returns value as String
   {                             
         int position = this.hash(key);
         int positionn = this.hash('m'); // HASH METHOD MIGHT HAVE A PROBLEM. SAME KEY FOR M AND B WHICH SHOULDN"T HAPPEN
         Object stuff = this.table[position]; // WILL RETURN AN OBJECT THAT CONTAINS THE MFU WORDS. MOST LIKELY HAVE TO USE TOSTRING AND THEN SPLIT INTO ARRLIST
         String str = stuff.toString();
         String mfuStrr = str.replaceAll("\\[", "").replaceAll("\\]","");
         String mfuStr = mfuStrr.replaceAll("\\s+","");
         
         String[] arr = mfuStr.split(",");
         ArrayList<String> arrList = new ArrayList<String>();
         
         for(int i = 0; i < arr.length ; i++)
         {
            arrList.add(arr[i]);
         }
                     
         return arrList;     
   
   }// end get method */
   
   public Object get(Object key)
   {
      int hash = this.hash(key);

      if(this.table[hash] != null)
      {
         LinkedList<HashtableNode> tmp = (LinkedList<HashtableNode>)this.table[hash];
         HashtableNode n = tmp.getFirst();
         Object k = n.getKey();
         
         ArrayList<WordItem> w = (ArrayList<WordItem>)n.getData();
         
         if(!k.equals(key))
         {
            hash = linearProbe(hash, key);
            LinkedList<HashtableNode> htn = (LinkedList<HashtableNode>)this.table[hash];
            HashtableNode node = htn.getFirst();
            Object o = node.getKey();           
            
            w = (ArrayList<WordItem>)node.getData();
            
            return w;
         }
         
         return w;

      }
      
      return null;
   }
   

	//return type is different from the standard Map interface.
	public void remove(Object key) {
		int hashVal = this.hash(key);

		if (this.table[hashVal] != null) {
			HashtableNode node = new HashtableNode();
			node.key = key; //in order to use the equals() method in HashtableNode

			if (((LinkedList<HashtableNode>)this.table[hashVal]).indexOf(node) > -1) {
				((LinkedList<HashtableNode>)this.table[hashVal]).remove(node);
				if( ((LinkedList<HashtableNode>)this.table[hashVal]).size() == 0 )
					this.table[hashVal] = null;
	
				this.numElements--;
			}
		}
	}

	public void remove(Object [] keys) {
		for (int i = 0; i < keys.length; i++) {
			this.remove(keys[i]);
		}
	}

	public String toString() {
		String buffer = "";

		buffer += "{\n";
		for (int i = 0; i < this.table.length; i++) {
			if (this.table[i] != null) {
				buffer  = buffer +  "\t" + (LinkedList)this.table[i] + "\n" ;
			}
		}
		buffer += "}";
		return buffer;
	}

	public int getNumElements() {
		return this.numElements;
	}

	public boolean contains(Object key) {
		boolean result = false;
		int hash = this.hash(key);

		if (this.table[hash] != null) {
			HashtableNode node = new HashtableNode();
			node.key = key;
			if (((LinkedList<HashtableNode>)this.table[hash]).indexOf(node) > -1) {
				result = true;
			}
		}
		return result;
	}
   
      	
   public void insertString(WordItem[] dict,String s)// inserts string into hashtable and creates MFU for that key NEED TO CREATE GET METHOD THAT WILL RETURN THE MFU AS AN ARRAYLIST
   {                                                 
		
      String prefix = "";
		for (char ch : s.toCharArray()) {
         prefix += ch; 
			//if (!this.contains(ch)) // if prefix doesn't exist
         //{
            ArrayList<String> mostFreqUsed = computeMFU(dict,prefix);// creates MFU
            //String mfuStr = this.convertArray(mostFreqUsed); // Creates a String from the ArrList that contains all the MFU words and then will be hashed
            this.add(prefix,mostFreqUsed); // Adds the key and value to Hashtable. NEED TO CREATE ADD METHOD FOR ARRAYLIST
         //}
			
		}
		
	}// end insertString
   
   private String convertArray(ArrayList<String> mfu)// Purpose- Converts MFU arraylist to a single string to adhere to Hashtable.add parameters
   {
      String mfuStr = "";
      
      for(String s : mfu)
      {
        mfuStr += "," + s;
      }
      
      return mfuStr;
   }// end convertArray
   
   public Object getMFU(String prefix)// Purpose- Searchs Hashtable for the key(prefix) and then pulls the String value and converts to an arrlist to return TO AUTOCOMPLETE
   { 
     
     //Object sortedMFU = new ArrayList<String>();
     Object sortedMFU = this.get(prefix);
     
     //String orig = this.get(prefix);// Finds value(mfuString) from the key. needs to be implemented
     //String mfuStrr = orig.replaceAll("\\[", "").replaceAll("\\]","");
     //String mfuStr = mfuStrr.replaceAll("\\s+","");
     
     
     //String[] arr = mfuStr.split(",");
     
     /*for(int i=0; i< arr.length; i++)
     {
       sortedMFU.add(arr[i]);
     } */
     
     
     
     return sortedMFU;
     
	}
   
   public ArrayList<String> computeMFU(WordItem[] dict, String prefix)// Parameters(array,string).Purpose- To create an ArrList of MFU words for a given prefix using WordItem's count variable
   {                                                                  // returns ArrList
      int start = this.firstIndex(dict,prefix);
      int end = this.lastIndex(dict,prefix);
      
      ArrayList<WordItem> prefixes = new ArrayList<>(); // TEMPORARY LIST TO STORE. THEN SORT AND ADD ONLY 9 MFU WORDS
      ArrayList<String> finalPre = new ArrayList<String>(9);
      
      for(int i = start; i <= end; i++)
      {
         prefixes.add(dict[i]);
      }
      
      
      finalPre = WordItem.sort(prefixes);// sorts list by descending order using count and returns a trimmed list of words 
      
      
      
      return finalPre;
     
       
   }// end computeMFU method
   
   private int firstIndex(WordItem[] dict, String prefix)// HElper method for computeMFU- Finds the first index(int) of a word containing prefix. 
   {
      int low = 0;
      int high = dict.length -1;
      
      while(low <= high)
      {
         int mid= (low+high)/2;
         
         if(dict[mid].getWord().startsWith(prefix))
         {
            if (mid == 0 || !dict[mid-1].getWord().startsWith(prefix))
            {
               return mid;
            }
            else
            {
               high = mid-1;
            }
         }
         else if(prefix.compareTo(dict[mid].getWord()) > 0)
         {
            low = mid+1;
         }
         
         else
         {
            high = mid -1;
         }
         
       }// end while loop
       
       return low; 
	   
   }// end firstIndex method
   
   private int lastIndex(WordItem[] dict, String prefix)// Helper method for computeMFU- Finds the last index(int) of a word containing prefix. 
   {
      int low = 0;
      int high = dict.length-1;
      
      while(low <= high)
      {
         int mid= (low+high)/2;
         
         if(dict[mid].getWord().startsWith(prefix))
         {
            if (mid == dict.length - 1 || !dict[mid+1].getWord().startsWith(prefix))
            {
               return mid;
            }
            else
            {
               low = mid + 1;
            }
         }
         else if(prefix.compareTo(dict[mid].getWord()) > 0)
         {
            low = mid+1;
         }
         
         else
         {
            high = mid -1;
         }
         
       }// end while loop
       
       return high; 
	   
   }// end firstIndex method

   
   
   
   
   
   
   
   
   
   
   
   

}// end Hashtable class
