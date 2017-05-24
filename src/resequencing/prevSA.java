package resequencing;

import java.util.Arrays;
import java.util.HashMap;


/*
 * !!!USE PrevSA_3!!!
 * 
 * This class returns prev arrays in hashmap form.
 * <key, value> = <suffix, prevOcc>
 * Sorted random as per hashmap functionality.
 */
public class prevSA {
	static int lenAlpha;
	public static String[] getSA(String alphabet) {
		lenAlpha = alphabet.length();
		//creates array of the alphabet's size
		String[] suffixArray = new String[lenAlpha];
		
		//prints the alphabet
		System.out.println("String: " + alphabet);
		
		//loops through the alphabet, assigning the string with one less
		//character in front each time. Creates the suffix Array.
		for (int i = 0; i < lenAlpha; i++) {
			suffixArray[i] = alphabet.substring(i, lenAlpha);
		}
		
		//testing only.
		/*System.out.print("SA: [");
		for (int i = 0; i < lenAlpha; i++) {
			System.out.print(suffixArray[i]+", ");
		}
		System.out.println("]");*/
		
		return suffixArray;
	}
	

	public static HashMap<String, Integer> getPrevLessThan(String alphabet){
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
		HashMap<String, Integer> prevLessThan = new HashMap<String, Integer>();
		
		//The first < prev occurrence is always -1
		prevLessThan.put(sortedArray[0], -1);
		
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
				prevLessThan.put(sortedArray[i], prevOcc);
				currLong = sortedArray[i].length();
				continue;
			}
			//for each suffix go through SA backwards starting 
			//at where you currently are to see when the
			//current suffix was last a suffix.
			for (int j = i-1; j >= 0; j--) {
				if(sortedArray[j].length() > sortedArray[i].length()) {
					prevOcc = Arrays.asList(suffixArray).indexOf(sortedArray[j]);
					prevLessThan.put(sortedArray[i], prevOcc);
					break;
				}
			}
		}
		
		//Display elements in an order I do not yet know.
		System.out.print("the prev< HashMap: {");
		prevLessThan.forEach((k,v)-> System.out.print("(" + k + ", " + v + "),"));
		System.out.print("}");
		
		return prevLessThan;		
	}
	
	
	
	public static HashMap<String, Integer> getPrevGreaterThan(String alphabet){
		String[] suffixArray = new String[lenAlpha];
		//gets the SA
		suffixArray = getSA(alphabet);
		
		String[] sortedArray = new String[lenAlpha];
		System.arraycopy(suffixArray, 0, sortedArray, 0, lenAlpha);
		//sorts the new SA lexicographically		
		Arrays.sort(sortedArray);
		
		//testing only
		System.out.print("sorted SA: [");
		for (int i = 0; i < lenAlpha; i++) {
			System.out.print(sortedArray[i]+", ");
		}
		System.out.println("]");
		
		//creates a dictionary/HashMap of <key,
		//values> that are <suffix, prevOcc>
		HashMap<String, Integer> prevGreaterThan = new HashMap<String, Integer>();
		
		//The last > prev occurrence is always -1
		prevGreaterThan.put(sortedArray[lenAlpha-1], -1);

		//the previous occurrence var to be used
		int prevOcc;
				
		//find the prev occurrence of each one
		for (int i = 0; i < lenAlpha-1; i++) {
			prevOcc = -1;

			//for each suffix go through SA backwards starting 
			//at where you currently are to see when the
			//current suffix was last a suffix.
			for (int j = i+1; j < lenAlpha-1; j++) {
				if(sortedArray[j].length() > sortedArray[i].length()) {
					prevOcc = Arrays.asList(suffixArray).indexOf(sortedArray[j]);
					prevGreaterThan.put(sortedArray[i], prevOcc);
					break;
				}
			}
			//if we didn't find anything longer, 
			//the value is -1.
			if (!prevGreaterThan.containsKey(sortedArray[i])){
				prevGreaterThan.put(sortedArray[i], prevOcc);	
			}
		}
		
		//Display elements in an order I do not yet know.
		System.out.print("the prev> HashMap: {");
		prevGreaterThan.forEach((k,v)-> System.out.print("(" + k + ", " + v + "),"));
		System.out.print("}");
		
		return prevGreaterThan;		
	}
	
	
	
	
	public static void main(String[] args) {
		String alphabet = "abbaabbbaaabab"; 
		getPrevLessThan(alphabet);
		getPrevGreaterThan(alphabet);
		
		/*long startTime = System.currentTimeMillis();
		String rna1 = RunIt.testStuff();
		getPrevLessThan(rna1);
		getPrevGreaterThan(rna1);		
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);*/
	}
}
