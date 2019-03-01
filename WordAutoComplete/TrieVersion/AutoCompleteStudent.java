//Author: Hari Patel
//Quarter: Fall 2015
//Auto Complete Words using PREFIX TREE 

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;
import java.util.*;

import javax.swing.*;
 
class AutoCompleteStudent extends JFrame implements KeyListener { 

	private static final long serialVersionUID = 1L;
	
	JTextArea output= new JTextArea();
	JTextArea input = new JTextArea();
	String partialWord = "";
	boolean inWord = false;
	String current = "";
	String temp = "";
	ArrayList<String> popular = null; // POPULAR WORDS ARRAY TO BE FILLED
   
   
   
	
	
	
	public AutoCompleteStudent() {
		JFrame frame = new JFrame("Preditive Application");
		frame.setSize(640,640);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLayout(new GridLayout(2,1));
		
		JPanel inputPanel = new JPanel();
		JPanel outPanel = new JPanel();
		
		outPanel.setBackground(Color.LIGHT_GRAY);
		inputPanel.setLayout(new GridLayout(1,1));
		outPanel.setLayout(new GridLayout(1,1));
		
		outPanel.add(output);
		inputPanel.add(input);
		output.setEditable(true);
		output.addKeyListener(this);
		input.setEditable(false);
		input.addKeyListener(this);
		input.setLineWrap(true);
		output.setLineWrap(true);
		//
		//change the font and the color in the input textArea
		Font font = new Font("Verdana", Font.BOLD, 16);
		input.setFont(font);
		input.setForeground(Color.BLUE);	

		frame.add(outPanel);
		frame.add(inputPanel);
		frame.setVisible(true);
		partialWord = "";  //this the prefix you are currently having.
      Trie2 tr = new Trie2(); // Empty Prefix Tree
      WordItem[] dict = new WordItem[25710]; // to store WordItems(storing word count) dictSmall- 30 words dictionary- 25710 words
      
      
      try{// TO CREATE DICT[] FIRST
      
      Scanner fon = new Scanner(new File("dictionary.txt")); 
      int i = 0;// index of dictionary
      
      while(fon.hasNext()) // Fill prefix tree and create Dictionary
      {// EACH LINE IN THE FILE HAS A STRING(WORD) AND AN INT(FREQ) 
         String str = fon.nextLine();
         String strr[] = str.split(",");
         dict[i++] = new WordItem(strr[0],Integer.parseInt(strr[1]));// insert new WordItem object into Dictionary  
      }// end while loop (DICTIONARY CREATED BY THIS POINT)
      
      }catch (FileNotFoundException ex)
      {
         ex.printStackTrace();
      }
      
           
      try{// FOR INSERTING INTO PREFIX TREE
      Scanner fin = new Scanner(new File("dictionary.txt")); 
      int i = 0;// index of dictionary
      
      while(fin.hasNext()) // Fill prefix tree and create Dictionary
      {// EACH LINE IN THE FILE HAS A STRING(WORD) AND AN INT(FREQ) 
         String str = fin.nextLine();// WILL NEED TO USE REGEX OR SPLIT TO PULL STRING AND INT SEPERATELY
         String strr[] = str.split(",");
         tr.insertString(strr[0],dict);// insert string into prefix tree;
         dict[i++] = new WordItem(strr[0],Integer.parseInt(strr[1]));// insert new WordItem object into Dictionary  
      }// end while loop (TREE FILLED AND DICTIONARY CREATED BY THIS POINT
      
      }catch (FileNotFoundException ex)
      {
         ex.printStackTrace();
      }
      
         
      
      

}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(! inWord) {
			inWord = true;
		}	
		
		int keyCode = e.getKeyCode();
		char ch = e.getKeyChar();
		int index = parseKeyCode(keyCode);
		
		// Handle regular alphabetic letter keys
		if ( index < 0 ) {	
			output.setEditable(true); //echo what we input
			
			if ( Character.isAlphabetic(ch) && inWord ) {
				partialWord += ch; //append the current character pressed into prefix

				System.out.println("Current Prefix:\"" + partialWord + "\"");
            popular = Trie2.findMFU(partialWord);

				input.setText(arrtoString(popular)); 
            
			}
		}
		else if( index >= 0 && index <= 9 ){ // if the key pressed is enter or space or numbers
			//System.out.println(index);
			output.setEditable(false);
			if(popular != null)
				current += popular.get(index) + " ";
			//System.out.println("curent2:" + current);
			output.setText(current);
			inWord = false;
			partialWord = "";
		}//end of outer else
		else if( index == 10 || index == 11) {
			output.setEditable(false);
			current = current.substring(0, current.length() - 1); //remove ending space
			if(index == 10) //comma
				current += ", ";
			else
				current += ". "; //period
			//System.out.println("curent2:" + current);
			output.setText(current);
		}
	
	}
	
	private int parseKeyCode(int code) {
		int index = 0;
		switch(code) {
		case KeyEvent.VK_ENTER :
		case KeyEvent.VK_SPACE :
			index = 0;
			break;
		case KeyEvent.VK_1 :
			index = 1;
			break;
		case KeyEvent.VK_2 :
			index = 2;
			break;	
		case KeyEvent.VK_3 :
			index = 3;
			break;
		case KeyEvent.VK_4 :
			index = 4;
			break;
		case KeyEvent.VK_5 :
			index = 5;
			break;
		case KeyEvent.VK_6 :
			index = 6;
			break;
		case KeyEvent.VK_7 :
			index = 7;
			break;
		case KeyEvent.VK_8 :
			index = 8;
			break;
		case KeyEvent.VK_9 :
			index = 9;
			break;
		case KeyEvent.VK_COMMA :
			index = 10;
			break;
		case KeyEvent.VK_PERIOD :
			index = 11;
			break;
		default:
			index = -1;	
		}		
		return index;
			
	}
	private String arrtoString(String a[]) {
		String ret = "";
		ret += "-->" + a[0] + "  ";
		for(int i = 1; i < a.length; i ++) {
			ret += i + ":" + a[i] + "  ";
			if (i == 4)
				ret += "\n        ";
		}
		return ret;
	}
	
	// this displays the list of most frequently used words in the bottom window
	private String arrtoString(ArrayList<String> a) {
		String ret = "";
		if(a == null)  //this is important, sometimes prefix is not in the tree.
			return ret;
		ret += "-->" + a.get(0) + "  ";
		for(int i = 1; i < a.size(); i ++) {
			ret += i + ":" + a.get(i) + "  ";
			if (i == 4)
				ret += "\n        ";
		}
		return ret;
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	public static String arrToString(WordItem d[]) {
		String ret = "";
		for(int i=0; i < d.length; i ++){
			ret += i + ":" + d[i] + "\n";
		}
		return ret;
	}
	
	public static void main(String[] args) throws IOException {
		//WordProcessor wp = new WordProcessor();
		System.out.println("Initializing .....");
		new AutoCompleteStudent(); 
		System.out.println("Done Intialization and Ready to type in!");
	}
}

