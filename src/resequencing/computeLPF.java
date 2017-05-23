package resequencing;

public class computeLPF {
	static int[][] computeLPF(String w, int[] prevLess, int[] prevGreat) {
		int size = w.length()-1;
		int[] LPF = new int[size];
		int[] LPFless = new int[size];
		int[] LPFgreater = new int[size];
		int[] prevOcc = new int[size];
		int[][] LPFAndPrevOcc = new int[2][size];
		int j;
		int k;
		String[] test1 = PrevSA_3.getSA(w); //testing with the actual SA.
		
		LPF[0] = 0;
		LPFless[0] = 0;
		LPFgreater[0] = 0;
		///////////////////		
		for(int i = 1; i < size; i++) {
			j = Math.max(LPFless[i-1]-1, 0);
			k = Math.max(LPFgreater[i-1]-1, 0);
			
			if(prevLess[i] == -1) {
				LPFless[i] = 0;
			}
			else {
				while((i+j) <= size && (prevLess[i]+j) <= size && w.charAt(i+j) == w.charAt(prevLess[i]+j)) {
					j++;
				}
				LPFless[i] = j;
			}
			////////////////////////////////////
			if (prevGreat[i] == -1) {
				LPFgreater[i] = 0;
			}
			else {
				while ((i+k) <= size && (prevGreat[i]+k) <= size && w.charAt(i+k) == w.charAt(prevGreat[i]+k)) {
					k++;
				}
				LPFgreater[i] = k;
			}
			////////////////////////////////////
			LPF[i] = Math.max(LPFless[i], LPFgreater[i]);
			////////////////////////////////////
			//System.out.println("i:"+i+", prevLess[i]:"+prevLess[i]+", prevGreat[i]:"+prevGreat[i]+", SA[i]:"+test1[i]);
			if (LPF[i] == 0) {
				prevOcc[i] = -1;
			}
			else if (LPFless[i] > LPFgreater[i]) {
				prevOcc[i] = prevLess[i];
			}
			else {
				prevOcc[i] = prevGreat[i];
			}
		}
		LPFAndPrevOcc[0] = LPF;
		LPFAndPrevOcc[1] = prevOcc;
		
		///* ***testing*** 
		//testing with the actual SA (test1).
		System.out.print("LPF: [");
		for (int x = 0; x < LPF.length; x++) {
			System.out.print(LPF[x]+":" + test1[x] + ", "); 
		}
		System.out.println("]");
		System.out.print("prevOcc: [");
		for (int y = 0; y < prevOcc.length; y++) {
			System.out.print(prevOcc[y]+":" + test1[y]+", "); 
		}
		System.out.println("]");
		////////////////////////////////*/
		
		
		return LPFAndPrevOcc;
	}
	

	public static void main(String[] args) {
		String w = "abbaabbbaaabab";

		int[] prevL = PrevSA_3.getPrevLessThan(w);
		int[] prevG = PrevSA_3.getPrevGreaterThan(w);
		
		
		/*System.out.print("prevL: [");
		for (int y = 0; y < prevL.length; y++) {
			System.out.print(prevL[y]+", "); 
		}
		System.out.println("]");
		System.out.print("prevG: [");
		for (int x = 0; x < prevG.length; x++) {
			System.out.print(prevG[x]+", "); 
		}
		System.out.println("]");*/
		
		computeLPF(w, prevL, prevG);
		
		/*
		String rna1 = RunIt.testStuff();
		long startTime   = System.currentTimeMillis();
	
		int[] prevL = PrevSA_3.getPrevLessThan(rna1);
		int[] prevG = PrevSA_3.getPrevGreaterThan(rna1);
		
		computeLPF(rna1, prevL, prevG);
		
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);
		*/
	}

}
