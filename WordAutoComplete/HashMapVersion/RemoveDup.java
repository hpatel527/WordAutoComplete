
public class RemoveDup {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RandomString rs = new RandomString(2);
		int count = 900;
		String allStr[] = new String[count]; 
		
		//generate all random Strings
		for( int i = 0; i < count; i ++ ) {
			allStr[i] = rs.nextString();
		}
		
		System.out.println("--------------------------------------before duplicates removed-----");
		for( String word : allStr ) {
			System.out.println("\"" + word + "\"");
		}
		
		System.out.println("--------------------------------------after duplicates removed-----");
		Hashtable myhash = new Hashtable(1000);
		for(String word : allStr) {
			myhash.add(word, 1);
		}
		System.out.println("After duplicates removed, hashtalbe is: \n" + myhash);
	}

}
