package org.kmerpkg;

import java.util.ArrayList;

public class KmerBlast
{
	public static final int alphabetLength = 4; // TODO support different alphabets

	private ArrayList<KmerResult> blastList;

	// Create a list of KmserResults, with strings kmerLength long.  
	//   KmerBlast contains all possible string results using the base pair alphabet
	public KmerBlast(int kmerLength){
		blastList = new ArrayList<KmerResult>();
		
		char [] seed = new char[kmerLength];  //Starting string
		for (int i = 0; i < kmerLength; i++){
			seed[i] = 'A';
		}

		blastList.add(new KmerResult(new String(seed)));// First entry at index 0
		//remaining entry, string at index(n+1) equals the string increment of index(n) string) 
		for (int i = 1; i < Math.pow(4, kmerLength); i++){
			blastList.add(new KmerResult(increment(blastList.get(i-1).getStr())));
		}
	}

	// Convert a list of strings into a KmerBlast stinr
	public KmerBlast(ArrayList<String> targets){
		blastList = new ArrayList<KmerResult>();
		//TODO make sure all strings are the same length
		for (String target : targets){
			blastList.add(new KmerResult(target));
		}
	}
	
	
	private static char AdvanceChar(char c){
		switch(c){
		case 'A':
			return 'C';
		case 'C':
			return 'G';
		case 'G':
			return 'T';
		case 'T':
			return 'A';
		default:
			return '0';
		}

	}

	public static int diffTo4(String a, String b){
		if (a.length() != b.length()){ //shouldn't happen, discard
			return 4;  
		}
		char [] aChar = a.toCharArray();
		char [] bChar = b.toCharArray();
		int diffs = 0;
		for (int i = 0; i < a.length(); i++){
			if (aChar[i] != bChar[i]){
				if (++diffs > 3)
					break;
			}
		}
		return diffs;
	}

	public static int diffToAny(String a, String b){
		if (a.length() != b.length()){ //shouldn't happen, discard
			return a.length();  
		}
		char [] aChar = a.toCharArray();
		char [] bChar = b.toCharArray();
		int diffs = 0;
		for (int i = 0; i < a.length(); i++){
			if (aChar[i] != bChar[i]){
				diffs++;
			}
		}
		return diffs;
	}

	//TODO Recursify these mofos
	public static ArrayList<String> OneSub(String base, int startingFrom){
		ArrayList<String> result = new ArrayList<>();
		if (startingFrom < base.length()){
			result.add(base);
			for (int i = startingFrom; i < base.length(); i++){
				char[] holder = base.toCharArray();
				for (int j = 0; j < alphabetLength-1; j++){
					// only three rotations, because base is already in list
					holder[i] = AdvanceChar(holder[i]);
					result.add(new String(holder));
				}
			}
		}
		return result;
	}

	public static ArrayList<String> TwoSub(String base, int startingFrom){
		ArrayList<String> result = new ArrayList<>();
		for (int i = startingFrom; i < base.length()-1; i++){
			char[] holder = base.toCharArray();
			for (int j = 0; j < alphabetLength - 1; j++){
				holder[i] = AdvanceChar(holder[i]);
				result.addAll(OneSub(new String(holder),i+1));
			}
		}
		return result;
	}

	public static ArrayList<String> ThreeSub(String base, int startingFrom){
		ArrayList<String> result = new ArrayList<>();
		result.add(base);
		for (int i = startingFrom; i < base.length()-2; i++){
			char[] holder = base.toCharArray();
			for (int j = 0; j < alphabetLength-1; j++){
				holder[i] = AdvanceChar(holder[i]);
				result.addAll(TwoSub(new String(holder),i+1));
			}
		}
		return result;
	}

	public static String increment(String Kmer){
		char[] incremented = Kmer.toCharArray();
		for (int i = Kmer.length() - 1; i >= 0; i--){
			incremented[i]= AdvanceChar(incremented[i]);
			if (incremented[i] != 'A'){ // If it didn't roll over, increment the next
				break;
			}
		}
		return new String(incremented);
	}

	/*    public static ArrayList<String> KmerMultiplier(String base, int precision){
	//Note that precision is the number of allowed substitutions not edit distance
	for (int i = 0; i < base.length();i++){

	}
    }*/

	public ArrayList<KmerResult> getBlast(){
		return blastList;
	}
}
