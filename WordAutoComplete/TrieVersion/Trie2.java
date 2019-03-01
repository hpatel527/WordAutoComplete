import java.util.*;

public class Trie2 { 
	
	private class TrieNode {
		Map<Character, TrieNode> children = new TreeMap<>();//TreeMap is java build-in structure, 
		boolean aword;						//Basically it acts like a Hashtable or Hashmap, establishing a mapping between Key and Value
		ArrayList<String> mostFreqUsed = new ArrayList<>();          //Unlike hash table, keys in TreeMap are sorted!
	}
	
	private static TrieNode root; // ROOT NODE
   
	public Trie2() {
		this.root = new TrieNode();
	}
   
   public static ArrayList<String> findMFU(String word)// PURPOSE- TAKES IN A TRIENODE AND PULLS IT MFU ARRAYLIST AND RETURNS IT. TO DO- CREATE FIND METHOD TO GET NODE WHERE WORD IS LOCATED
   {  
      TrieNode cur= getNode(word,root);
      ArrayList<String> ara = cur.mostFreqUsed;
      return ara;
   }
   
   public static TrieNode getNode(String str,TrieNode rt) 
   {
   	Map<Character, TrieNode> children = rt.children; 
        TrieNode t = null;
        for(int i=0; i<str.length(); i++){
            char c = str.charAt(i);
            if(children.containsKey(c)){
                t = children.get(c);
                children = t.children;
            }else{
                return null;
            }
        }
 
        return t;
    }
    
    
    
      

	public void insertString(String s,WordItem[] dict) {
		insertString(dict,root, s);
	}
	
	private void insertString(WordItem[] dict,TrieNode root, String s) { 
		TrieNode cur = root;
      String prefix = "";
		for (char ch : s.toCharArray()) {
         prefix += ch;
			TrieNode next = cur.children.get(ch);
			if (next == null)
         {
				cur.children.put(ch, next = new TrieNode());
            next.mostFreqUsed = computeMFU(dict,prefix); 
         }
			cur = next;
		}
		cur.aword = true; // meaning this is a word in dict[]
	}
	
	public void printSorted() {
		printSorted(root, "");
	}

	private void printSorted(TrieNode node, String s) {
		if (node.aword) {
			System.out.println(s);
		}
		for (Character ch : node.children.keySet()) {
			printSorted(node.children.get(ch), s + ch);
		}
	}
	
	public boolean findWord(String s) {
		return findWord(root, s);
	}
	
	private boolean findWord(TrieNode node, String s) {
		if(s != null) {
			String rest = s.substring(1); //rest is a substring of s, by excluding the first character in s
			char ch = s.charAt(0);        //ch is the first letter of s
			TrieNode child = node.children.get(ch);	//return the child that ch associated with. 
			if(s.length() == 1 && child != null) //if s contains only one letter, and current node has a child associated with that letter, we find the prefix in Trie!
				return true;	                 //base case
			if(child == null)
				return false;
			else
				return findWord(child, rest);    //recursive, In this way, we follow the path of the trie from root down towards leaf
		}
		return false;
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
   
   
   
   
	// Usage example
	public static void main(String[] args) {
		
		// Trie2 tr = new Trie2();
// 		
// 		tr.insertString("hello");
// 		tr.insertString("world");
// 		tr.insertString("hi");
// 		tr.insertString("ant");
// 		tr.insertString("an");
// 		
// 		System.out.println(tr.findWord("ant"));
// 		System.out.println(tr.findWord("an"));
// 		System.out.println(tr.findWord("hello"));
// 		System.out.println(tr.findWord("cant"));
// 		System.out.println(tr.findWord("hig"));
// 		System.out.println(tr.findWord("he"));
// 		
// 		tr.printSorted();
	}
}
