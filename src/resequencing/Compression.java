package resequencing;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class Compression {
	static String compression(String R, String T) { //R = reference --- T = Target
		//1
		int m = R.length();
		int n = T.length();
		String Z = R+T;
		
		int[] ZprevLess = PrevSA_2.getPrevLessThan(Z);
		int[] ZprevGreat = PrevSA_2.getPrevGreaterThan(Z);
		int[] LPFz = computeLPF.computeLPF(Z, ZprevLess, ZprevGreat)[0];
		int[] POSz = computeLPF.computeLPF(Z, ZprevLess, ZprevGreat)[1];
		System.out.println("1");
		//2
		ArrayList<Pair<Integer,Integer>> P = new ArrayList<Pair<Integer,Integer>>(); //pairs[i] = (pos, len)
		for (int i = 1; i <= n; i++) {
			P.add(new ImmutablePair<Integer,Integer>(i, LPFz[i+m-2])); //TODO
			//System.out.println("2[" + i + "]"); //testing purposes.
			
		}
		//One of these 2, or both of them, sorts P by y-cord (aka len).
		//Collections.sort(P, Comparator.comparing(p -> p.getRight()));
		Collections.sort(P, new Comparator<Pair<Integer, Integer>>() {
			@Override
			public int compare(final Pair<Integer, Integer> o1, final Pair<Integer, Integer> o2) {
				if (o1.getRight() > o2.getRight()) return 1;
				else if (o1.getRight() < o2.getRight()) return -1;
				else return 0;
			}
		});
		System.out.println("2...sorting..."); //testing purposes
		
		//3
		ArrayList<Pair<Integer,Integer>> W = new ArrayList<Pair<Integer,Integer>>(); //W[i] eventually = (dpos, len)
		//make W big enough
		 for (int i = 0; i <= n; i++) {
			 //System.out.println("3[" + i + "]");  //testing purposes
			 W.add(new MutablePair<Integer,Integer>(0,0));
		}
		
		//4&5
		int k = 4; //anything less than this number won't be encoded. It's random, but an optimal one is needed. 
		for (int i = n-1; i > 0; i--) { //possible out of bounds here
			//System.out.println("4[" + i + "]"); //testing purposes
			if (P.get(i).getRight() >= k) {
				//5a  W[i] = (dpos, len) ---- P[i] = (pos,len)
				//T = target to be compressed
				if (W.get(P.get(i).getLeft()).getLeft() != 0) {
					//go to 5c  (5c == i--)  
					continue; //necessary?
				}
				//5b
				else {
					int lower_q = P.get(i).getLeft(); //looking for q. This is first possibility
					int upper_q = P.get(i).getLeft() + P.get(i).getRight() - 1;
					int q = lower_q; //set q to the lowerbound
					if (W.get(q).getLeft() == 0) {  //if the first one doesn't work, look for one that does.
						for (int j = lower_q+1; j < upper_q; j++) {
							q = j;
							if (W.get(q).getLeft() != 0) {
								break;
							}
						}
					}
					int len_prime;
					if (W.get(q).getLeft() == 0) { //if W[q] == 0 still, q doesn't exist. 
						//This means we can encode the whole factor.
						len_prime = P.get(i).getRight();
					}
					else { //else only W[P[i].pos...q-1] can be encoded
						len_prime = q - P.get(i).getRight();
					}
					if (len_prime >= k) { //then it's too small for encoding //possibly reverse to >= and put else in here 
						//go to 5c, aka i--
					}
					else { //else it's long enough to encode
						((MutablePair<Integer, Integer>) W.get(P.get(i).getLeft())).setLeft(POSz[P.get(i).getLeft()+m]); 
						((MutablePair<Integer, Integer>) W.get(P.get(i).getLeft())).setRight(len_prime);
						for (int j = lower_q+1; j >= P.get(i).getLeft()+len_prime-1; j++) {
							((MutablePair<Integer, Integer>) W.get(P.get(i).getLeft())).setLeft(1);
						}
					}
				}
			}
			else {
				//go to 6. means the rest are less than k.
				break;
			}
		}
		
		//6
		String words = "";
		System.out.println("6");
		int[] encodings = {-1,-2,-3,-4};
		for (int i = 1; i < W.size(); i++) {
			System.out.println("6[" + i + "]");
			if (W.get(i).getLeft() == 0) {
				System.out.println("6[" + i + "]...ENCODING HERE!!!");
				switch (T.substring(i-1,i).toUpperCase()) {
					case "A":
						System.out.println("AAAA");
							words += encodings[0];
							break;
					case "C": 
						System.out.println("CCCC");
						words += encodings[1];
						break;
					case "G": 
						System.out.println("GGGG");
						words += encodings[2];
						break;	
					case "T": 
						System.out.println("TTTT");
						words += encodings[3];
						break;
					default:
						System.out.println("Error! T[i] is: " + T.substring(i).toUpperCase());
						break;
				}
			}
			else {
				System.out.println("6[" + i + "]...something...");
				words+=W.get(i).getLeft()+W.get(i).getRight();
				i+=W.get(i).getRight() - 1;
			}
			if (i >= n) {
				System.out.println("here. breaking...");
				break;
			}
		}
		return words;
		
	}
	public static void main(String[] args) {
		String r = "actg";
		String t = "acccttaacggctgcaaatacgggtttac";
		//System.out.println(t.substring());
		String result = compression(r,t);
		System.out.println("result: " + result);
	}

}

