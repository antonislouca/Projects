/*Author:Antonis Louca 
 * Written:27/10/2018
 * Last updated:28/10/2018
 * Compilation:javac PlayeComputerMM
 * Execution: java PlayerComputerMM <max tries>
 * Description:The computer tries to guess a number that the user thought.During that process
 * the computer the computer guesses a number and the user answers with a full and partial match.
 * Each step the computer gets closer to the number.If the computer manages to find the number before 
 * the limited tries run out  then the game goes to the computer else the user is the winner of the game.
 *
 * */
public class PlayerComputerMM {
	//finds the numbers that appear in the combination
	private static int[] findnums(int table[]) {
		int table2[]=new int[4];
		int j=0;
		for(int i=0;i<10;i++) {
			if (table[i]!=0) {
				
				for(int c=0;c<table[i];c++) {
					table2[j]=i;
					j++;
				}
			}
		}
	return table2;
	}

//prints the guess 
	private static void show(int ALL[][], int x) {
		 System.out.print("The computer guesses: ");
 	 for(int c=0;c<4;c++) {
 		 System.out.print(ALL[x][c]);
 	 }
 	 System.out.print("\n"+"Indicate full and partial matches:");
	return;
	}
	// find the  first value  in the perm table which  is not a zero
	private static String check(String perm[]) {
		String s="";
		for(int i=0;i<perm.length;i++) {
			if (!perm[i].equals("")) {
				s=perm[i];
				break;
			}
		}
		return s;
	}
//it converts the string into intgers and puts them into the main table ALL
private static void convert(int table[][],int x,String s) {
	 for (int i=0;i<4;i++) {
		 table[x][i]=Integer.parseInt(""+s.charAt(i));
	 }
}
// it modifies the perm table and sets the zero value for the not existent  permutations
private static void modify (String perm[],String s,int f) {
	int pl=0;
	for(int i=0;i<perm.length;i++) {
		pl = 0;
		if(perm[i]!="") {
		for(int c=0;c<4;c++) {
			if (perm[i].charAt(c)==s.charAt(c)) {
				pl++;
			}
	}
	}
		if(pl<f || perm[i].equals(s)) {
			perm[i]="";
		}
 }
	return;
}
//This method calculates the permutations of the intigers that are in the table using recursion
private static String permute(int[] nums, int startpoint, int endpoint) { 
	String s = "";

    if (startpoint == endpoint) 
        s = s + convertString(nums);
    else { 
        for (int i = startpoint; i <= endpoint; i++) { 
            int[] nums1 = swap(nums,startpoint,i); 
            permute(nums1, (startpoint+1), endpoint); 
            nums = swap(nums,startpoint,i); 
        	s=s+permute(nums, (startpoint+1), endpoint);
        } 
    }
	return s; 
} 


//it swaps two places that are given to it 
private static int[] swap(int[] num, int i, int j) { 
	
	int[] temp = new int[num.length];
	
	for (int c = 0; c < temp.length; c++) {
		if (c == i)
			temp[c] = num[j];
		else if (c== j)
			temp[c] = num[i];
		else temp[c] = num[c];
	}
	
	return temp;
} 
// converts an integer into a string 
private static String convertString(int[] nums) {
	String s = "";
	for (int i = 0; i < nums.length; i++)
		s += nums[i];
	return s;
}
private static int[][] ALL = new int[30][4];
private static int GCount = 0;
	public static void Play (int tries){
		 System.out.println("\n\nA GAME BEGINS: The Computer does the");
		 System.out.print(" Guessing!!\n");
		 System.out.print(">>>>> User please think of a configuration and ");
		 System.out.print("keep it secret!");
		 GCount = 0;
		  int appear[]= {0,0,0,0,0,0,0,0,0,0};
		 int fullmatch=0,partialm=0;
		 int digits=0;
		 boolean flag=true;
		  while ((GCount<tries)&&(fullmatch!=4)) {
			     if ((GCount<10)&&(digits!=4)){
			    	 for(int c=0;c<4;c++) {
			    		 ALL[GCount][c]=GCount;
			    	 }
			    	 show(ALL,GCount);
			    	 fullmatch=StdIn.readInt();
			    	 partialm=StdIn.readInt();
			    	 if (fullmatch!=0) {
			    		 digits=digits+fullmatch;
			    		 appear[GCount]=fullmatch;
			    	 }
			    	 else {appear[GCount]=0;}
			    	 if ((digits>=3)&&(flag)){
			    		 flag=false;
			    		 System.out.println("I FOUND AT LEAST 3 DIGITS! ");
			    	 }
			    	 GCount++;		    
			     }
			 else {  
				 String perm[]=new String [24];
				 int nums[]=findnums(appear);
				 perm = permute(nums, 0, 3).split("(?<=\\G....)");
				 while((fullmatch!=4)&&(GCount<tries)) {
				 String s=check(perm);
				 convert(ALL,GCount,s);
				 show(ALL,GCount);
				 fullmatch=StdIn.readInt();
		    	 partialm=StdIn.readInt();
		    	 modify(perm,s,fullmatch);
				 GCount++; 
			 } 
	} 
}
if((fullmatch==4)&&(GCount<tries)){
	System.out.print("GREAT!! I FOUND IT");
	PlayMasterMind.WIN=true;
}
else {
	System.out.print("I LOST THE GAME ");
	PlayMasterMind.WIN=false;}
}

public static void main(String[] args){
	 GCount = 0;
	 System.out.println("\n***** WELCOME TO MASTER MIND *****");
	 System.out.println("\n\nThis is a standalone game where ");
	 System.out.print("the Computer is guessing\n");
	 System.out.println("User please think of a four digit configuration ");
	 System.out.print("and keep it secret!\n");
	 Play(Integer.parseInt(args[0]));
	}
}
