package resequencing;

import java.util.Arrays;
import java.util.HashMap;

/*
 * THIS CLASS RETURNS PREV ARRAYS IN ARRAY FORM.
 * SORTED IN LEXICOGRAPHIC ORDER.
 */


public class PrevSA_2 {
	static int lenAlpha;
	public static String[] getSA(String alphabet) {
		lenAlpha = alphabet.length();
		//creates array of the alphabet's size
		String[] suffixArray = new String[lenAlpha];
		
		//prints the alphabet
		//System.out.println("String: " + alphabet);
		
		//loops through the alphabet, assigning the string with one less
		//character in front each time. Creates the suffix Array.
		for (int i = 0; i < lenAlpha; i++) {
			suffixArray[i] = alphabet.substring(i, lenAlpha);
		}
		
		/*//testing only.
		System.out.print("SA: [");
		for (int i = 0; i < lenAlpha; i++) {
			System.out.print(suffixArray[i]+", ");
		}
		System.out.println("]");*/
		
		return suffixArray;
	}
	

	public static int[] getPrevLessThan(String alphabet){
		String[] suffixArray = new String[lenAlpha];
		//gets the SA
		suffixArray = getSA(alphabet);
		
		String[] sortedArray = new String[lenAlpha];
		System.arraycopy(suffixArray, 0, sortedArray, 0, lenAlpha);
		//sorts the new SA lexicographically		
		Arrays.sort(sortedArray);
		
		//testing only
		/*System.out.print("sorted SA: [");
		for (int i = 0; i < lenAlpha; i++) {
			System.out.print(sortedArray[i]+", ");
		}
		System.out.println("]");*/
	
		//creates a dictionary/HashMap of key,
		//values that are <suffix, prevOcc>
		int[] prevLessThan = new int[lenAlpha];
		
		//The first < prev occurrence is always -1
		prevLessThan[0] = -1;
		
		//the previous occurrence var to be used
		int prevOcc;
		
		//track the longest current string
		int currLong = sortedArray[0].length();
		
		//find the prev occurrence of each one
		for (int i = 1; i < lenAlpha; i++) {
			prevOcc = -1;
			
			//if we found the longest current suffix,
			//it can't be the suffix of anything.
			if (sortedArray[i].length() > currLong) {
				prevLessThan[i] = prevOcc;
				currLong = sortedArray[i].length();
				continue;
			}
			//for each suffix go through SA backwards starting 
			//at where you currently are to see when the
			//current suffix was last a suffix.
			for (int j = i-1; j >= 0; j--) {
				if(sortedArray[j].length() > sortedArray[i].length()) {
					prevOcc = Arrays.asList(suffixArray).indexOf(sortedArray[j]);
					prevLessThan[i] = prevOcc;
					break;
				}
			}
		}
		
		//Display elements 
		System.out.print("prevLessThan: [");
		for (int x = 0; x < prevLessThan.length; x++) {
			System.out.print(prevLessThan[x]+", "); 
		}
		System.out.println("]");

		return prevLessThan;		
	}
	
	
	
	public static int[] getPrevGreaterThan(String alphabet){
		String[] suffixArray = new String[lenAlpha];
		//gets the SA
		suffixArray = getSA(alphabet);
		
		//flag for end
		boolean flag;
		
		String[] sortedArray = new String[lenAlpha];
		System.arraycopy(suffixArray, 0, sortedArray, 0, lenAlpha);
		//sorts the new SA lexicographically		
		Arrays.sort(sortedArray);
		
		//testing only
		/*System.out.print("sorted SA: [");
		for (int i = 0; i < lenAlpha; i++) {
			System.out.print(sortedArray[i]+", ");
		}
		System.out.println("]");*/
		
		//creates a dictionary/HashMap of <key,
		//values> that are <suffix, prevOcc>
		int[] prevGreaterThan = new int[lenAlpha];
		
		//The last > prev occurrence is always -1
		prevGreaterThan[lenAlpha-1] = -1;

		//the previous occurrence var to be used
		int prevOcc;
				
		//find the prev occurrence of each one
		for (int i = 0; i < lenAlpha-1; i++) {
			prevOcc = -1;
			flag = false;
			//for each suffix go through SA backwards starting 
			//at where you currently are to see when the
			//current suffix was last a suffix.
			for (int j = i+1; j < lenAlpha-1; j++) {
				if(sortedArray[j].length() > sortedArray[i].length()) {
					prevOcc = Arrays.asList(suffixArray).indexOf(sortedArray[j]);
					prevGreaterThan[i]= prevOcc;
					break;
				}
			}
			//if we didn't find anything longer, 
			//the value is -1.
			if (!flag){
				prevGreaterThan[i] = prevOcc;	
			}
		}
		
		//Display elements 
		System.out.print("prevGreaterThan: [");
		for (int x = 0; x < prevGreaterThan.length; x++) {
			System.out.print(prevGreaterThan[x]+", "); 
		}
		System.out.println("]");
	
		
		return prevGreaterThan;		
	}
	
	
	
	
	public static void main(String[] args) {
		String alphabet = "abbaabbbaaabab"; 
		getPrevLessThan(alphabet);
		getPrevGreaterThan(alphabet);
		
		/*String rna1 = RunIt.testStuff();
		long startTime = System.currentTimeMillis();		
		getPrevLessThan(rna1);
		getPrevGreaterThan(rna1);		
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);*/
		
	}
}
