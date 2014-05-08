package org.ProtSeq;

import java.util.HashMap;
import java.util.Map;

public class Codons
{

    Map<String, String> map;

    public Codons(){
	map = new HashMap<String, String>();
	map.put("AAA", "K");
	map.put("AAC", "N");
	map.put("AAG", "K");
	map.put("AAU", "N");
	map.put("ACA", "T");
	map.put("ACC", "T");
	map.put("ACG", "T");
	map.put("ACU", "T");
	map.put("AGA", "R");
	map.put("AGC", "S");
	map.put("AGG", "R");
	map.put("AGU", "S");
	map.put("AUA", "I");
	map.put("AUC", "I");
	map.put("AUG", "M");
	map.put("AUU", "I");
	map.put("CAA", "Q");
	map.put("CAC", "H");
	map.put("CAG", "Q");
	map.put("CAU", "H");
	map.put("CCA", "P");
	map.put("CCC", "P");
	map.put("CCG", "P");
	map.put("CCU", "P");
	map.put("CGA", "R");
	map.put("CGC", "R");
	map.put("CGG", "R");
	map.put("CGU", "R");
	map.put("CUA", "L");
	map.put("CUC", "L");
	map.put("CUG", "L");
	map.put("CUU", "L");
	map.put("GAA", "E");
	map.put("GAC", "D");
	map.put("GAG", "E");
	map.put("GAU", "D");
	map.put("GCA", "A");
	map.put("GCC", "A");
	map.put("GCG", "A");
	map.put("GCU", "A");
	map.put("GGA", "G");
	map.put("GGC", "G");
	map.put("GGG", "G");
	map.put("GGU", "G");
	map.put("GUA", "V");
	map.put("GUC", "V");
	map.put("GUG", "V");
	map.put("GUU", "V");
	map.put("UAA", "");
	map.put("UAC", "Y");
	map.put("UAG", "");
	map.put("UAU", "Y");
	map.put("UCA", "S");
	map.put("UCC", "S");
	map.put("UCG", "S");
	map.put("UCU", "S");
	map.put("UGA", "");
	map.put("UGC", "C");
	map.put("UGG", "W");
	map.put("UGU", "C");
	map.put("UUA", "L");
	map.put("UUC", "F");
	map.put("UUG", "L");
	map.put("UUU", "F");
    }
    
    public String XlateCodons(String codonString){
	String result = new String();
	for (int i = 0; i < codonString.length(); i+= 3){
	    if ((i+3) <= codonString.length()){
		String protein = map.get(codonString.substring(i, i + 3));
		if ((protein == null) || (protein.length() == 0)) // Non codon string, or stop codon
		    break;
		result += protein;
	    }
	}
	return result;
    }



}
