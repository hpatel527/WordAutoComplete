import java.util.*;

/*
 * Each WordItem represent a word extracted from the text file.
 * It has three fields. String word stores the literal English word.
 * int count records the number of occurrence for that word in text file.
 * ArrayList atLines records a list of line numbers where the word appears in
 * the original text file. 
 * In this class, differs from the WordItem used in homework 2, we do not care about the
 * atLines ArrayList.
 * 
 *  
 */

public class WordItem implements Comparable {
	private String word;
	private int count;
	private ArrayList<Integer> atLines;
	
	public WordItem(String word, int c, int atLine) {
		this.word = word;
		this.count = c;
		this.atLines = new ArrayList<Integer>();
		atLines.add(atLine);
	}
	
	public WordItem(String word, int c) { // CONSTRUCTOR THAT WILL BE USED
		this.word = word;
		this.count = c;
	}
	
	public static class WordComparator implements Comparator<WordItem>
   {

		private boolean compareOnCount;
		
		public WordComparator(boolean compOnCount) {
			this.compareOnCount = compOnCount;
		}
		@Override
		public int compare(WordItem w1, WordItem w2) {
			if(compareOnCount) {
				return w2.count - w1.count;
			}
			else
				return w1.word.compareToIgnoreCase(w2.word);
		}

        
		
	}//end of class

	
	public int compareTo2(Object o) {
		WordItem other = null;
		try{
			other = (WordItem) o;
		}catch(ClassCastException cce) {
			cce.printStackTrace();
		}
		if (this.count == other.getCount())
			return 0;
		else
			return (this.count < other.getCount()) ? -1 : 1;
	}
	
	@Override
	public int compareTo(Object other) {
			return this.word.compareToIgnoreCase( ((WordItem)other).getWord() ) ;
	}
	
	public String getWord() {
		return this.word;
	}
	
	public void setWord(String w) {
		this.word = new String(w);
	}
	

	public int getCount() {
		return this.count;
	}
	
	public void incrCount(int appearAt) {
		this.count ++; 
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        WordItem other = (WordItem) obj;
        if (this.word.compareToIgnoreCase(other.getWord()) != 0)
        	return false;

        return true;
    }
	
	@Override
	public String toString() {
		String ret = "";
		ret += word.toLowerCase() + "," + this.count;
		return ret;
	}
   
   public static ArrayList<String> sort(ArrayList<WordItem> prefixes)// PURPOSE- RETURNS A SORTED BY COUNT ARRAYLIST. 
   {                                                                 
      ArrayList<String> finalPre = new ArrayList<String>();
      Collections.sort(prefixes, new WordComparator(true));
      
      
      for(int i = 0; i < prefixes.size(); i++)
      {
        finalPre.add(prefixes.get(i).getWord());
      } 
      
      if(finalPre.size() > 9)
      {
         ArrayList<String> fl =  new ArrayList<String>(finalPre.subList(0,9));
         return fl;
      }     
      
      return finalPre; 
      
   }
       
	
}//end of class
