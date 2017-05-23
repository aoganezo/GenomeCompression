# __GenomeResequencing__ #

*PrevOcc = Previous Occurence, SA = Suffix Array*

## Previous Less Than and Previous Greater Than Suffix Array ##

* PrevSA.java builds the PreviousLessThan SA and PreviousGreaterThan SA using a hashmap. The keys are the suffixes, with values of previous occurences. 

* PrevSA_2.java builds the PreviousLessThan SA and PreviousGreaterThan SA using arrays. It stores only the prevOccs of each suffix in __lexicographic__ order of the suffixes. 
   * E.g. if SA is [c, bc, abc], the returned array stores the prevOcc of each SA[i] in the following order: [SA[2], SA[1], [SA[0]].

* PrevSA_3.java builds the PreviousLessThan SA and PreviousGreaterThan SA using arrays. It stores only the prevOccs of each suffix in __SA__ order. 
    * E.g. if SA is [c, bc, abc], the returned array stores the prevOcc of each SA[i] in the following order: [SA[0], SA[1], [SA[2]].

* Creating the Suffix Array: The suffix array is created by getSA(String S) at the top of which ever PrevSA you're using. 

*__The rest of the files are configured to use PrevSA_3 as of latest updates. You may run into issues if you choose another one.__*

## Computing Longest Previous Factor ##
 *LPF = Longest Previous Factor*
 
* This algorithm, as well as the ones described above, were developed based off the pseudo code from "Computing Longest Previous Factor in linear time and applications" by Maxime Crochemore and Lucian Ilie.
 
* computeLPF.java returns An array of 2 arrays. The first is the LPF array and the second is the PrevOcc array.
 
* Main() of computeLPF creates 2 arrays (prev less than and prev greater than) and builds them with prevSA_3.  
 
## Compression ##
 
Compression.java is an algorithm based off the one discussed in "Compressing Genome Resequencing Data via the Maximal Longest Factor" by Richard Beal, Aliya Farheen, and Don Adjeroh
