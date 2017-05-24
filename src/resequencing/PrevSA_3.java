package resequencing;

import java.util.Arrays;
import java.util.HashMap;

/*
 * THIS CLASS RETURNS PREV ARRAYS IN ARRAY FORM.
 * SORTED IN SA ORDER (e.g. suffix lengths shortest to longest).
 * 
 * 
 * Makes the SA with getSA(). Uses a Hashmap to store suffixes from SA with their prevOccs. 
 * Makes an array prev of size suffixArray. Goes through SA, and for each item in SA do:
 * prev[item] = Hashmap.getValue(item)
 * Then return prev for the desired prevSA's (whether it's the lessThan or greaterThan).
 */

public class PrevSA_3 {
	static int lenAlpha;
	public static String[] getSA(String alphabet) {
		lenAlpha = alphabet.length();
		//creates array of the alphabet's size
		String[] suffixArray = new String[lenAlpha];
		
		//prints the alphabet. Testing purposes. 
		System.out.println("String: " + alphabet);
		
		//Creates the suffix Array. Builds it one suffix at a time longest to shortest
		for (int i = 0; i < lenAlpha; i++) {
			suffixArray[i] = alphabet.substring(i, lenAlpha);
		}
		
		//Prints the suffix array. testing only.
		/*System.out.print("SA: [");
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
		
		//Prints the lexicographically sorted SA. Testing only.
		System.out.print("sorted SA: [");
		for (int i = 0; i < lenAlpha; i++) {
			System.out.print(sortedArray[i]+", ");
		}
		System.out.println("]");
	
		//creates a dictionary/HashMap of key,values that are <suffix, prevOcc>
		HashMap<String, Integer> prevLessThan = new HashMap<String, Integer>();
		
		//The first <prev occurrence is always -1
		prevLessThan.put(sortedArray[0], -1);
		
		//the previous occurrence variable to be used
		int prevOcc;
		
		//tracks the longest current string
		int currLong = sortedArray[0].length();
		
		//finds the prev occurrence of each suffix.
		for (int i = 1; i < lenAlpha; i++) {
			//always initialize to -1 in case we don't find a previous occurrence.
			prevOcc = -1;
			
			//if we found the longest current suffix, it can't be the suffix of anything, so skip the rest.
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
		
		//puts prevOccs into an SA sorted array (shortest to longest).
		int[] prevLessThanSA = new int[lenAlpha];
		int ct = 0;
		for (String s: suffixArray) {
			prevLessThanSA[ct] = prevLessThan.get(s); 
			ct++;
		}
		
		//prints the previousLessThan (that is, their prevOccs). Testing only. 
		System.out.print("prevLessThanSA: [");
		for (int x = 0; x < prevLessThanSA.length; x++) {
			System.out.print(prevLessThanSA[x]+", "); 
		}
		System.out.println("]");
		
		return prevLessThanSA;		
	}
	
	
	
	public static int[] getPrevGreaterThan(String alphabet){
		String[] suffixArray = new String[lenAlpha];
		//gets the SA
		suffixArray = getSA(alphabet);
		
		String[] sortedArray = new String[lenAlpha];
		System.arraycopy(suffixArray, 0, sortedArray, 0, lenAlpha);
		//sorts the new SA lexicographically		
		Arrays.sort(sortedArray);
		
		/* Prints the sorted Suffix array. //testing only
		System.out.print("sorted SA: [");
		for (int i = 0; i < lenAlpha; i++) {
			System.out.print(sortedArray[i]+", ");
		}
		System.out.println("]");*/
		
		//creates a HashMap of <key, values> that are <suffix, prevOcc>
		HashMap<String, Integer> prevGreaterThan = new HashMap<String, Integer>();
		
		//The last >prev occurrence is always -1
		prevGreaterThan.put(sortedArray[lenAlpha-1], -1);

		//the previous occurrence var to be used
		int prevOcc;
				
		//Finds the prev occurrence of each suffix.
		for (int i = 0; i < lenAlpha-1; i++) {
			prevOcc = -1;

			//for each suffix go through SA backwards starting at where you
			//currently are to see when the current suffix was last seen suffix.
			for (int j = i+1; j < lenAlpha-1; j++) {
				if(sortedArray[j].length() > sortedArray[i].length()) {
					prevOcc = Arrays.asList(suffixArray).indexOf(sortedArray[j]);
					prevGreaterThan.put(sortedArray[i], prevOcc);
					break;
				}
			}
			//if we didn't find it as the suffix of anything, the value is -1.
			if (!prevGreaterThan.containsKey(sortedArray[i])){
				prevGreaterThan.put(sortedArray[i], prevOcc);	
			}
		}
		
		//puts prevOccs into an SA sorted array.
		int[] prevGreaterThanSA = new int[lenAlpha];
		int ct = 0;
		for (String s: suffixArray) {
			prevGreaterThanSA[ct] = prevGreaterThan.get(s); 
			ct++;
		}
		//Prints the prevGreaterThanSA. //Testing purposes.
		System.out.print("prevGreaterThanSA: [");
		for (int x = 0; x < prevGreaterThanSA.length; x++) {
			System.out.print(prevGreaterThanSA[x]+", "); 
		}
		System.out.println("]");
		
		return prevGreaterThanSA;		
	}
	
	
	
	
	public static void main(String[] args) {
		String alphabet = "abbaabbbaaabab"; 
		getPrevLessThan(alphabet);
		getPrevGreaterThan(alphabet);
		
		//Uncomment this to test and time on a 19,000 long RNA string stored in rna1.txt 
		/*long startTime = System.currentTimeMillis();
		String rna1 = RunIt.testStuff();
		getPrevLessThan(rna1);
		getPrevGreaterThan(rna1);		
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);*/
	}
}
