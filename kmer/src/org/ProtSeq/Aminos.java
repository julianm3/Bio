package org.ProtSeq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.rcpkg.ReverseCompliment;

public class Aminos
{
    Map <String, ArrayList<String>> map;

    public Aminos(){
	map = new HashMap<String, ArrayList<String>>();
	ArrayList<String> Histidine = new ArrayList<String>();
	Histidine.add("CAT");
	Histidine.add("CAC");
	map.put("H",Histidine);
	ArrayList<String> Glutamine = new ArrayList<String>();
	Glutamine.add("CAA");
	Glutamine.add("CAG");
	map.put("Q",Glutamine);
	ArrayList<String> Proline = new ArrayList<String>();
	Proline.add("CCT");
	Proline.add("CCC");
	Proline.add("CCA");
	Proline.add("CCG");
	map.put("P",Proline);
	ArrayList<String> Arginine = new ArrayList<String>();
	Arginine.add("CGT");
	Arginine.add("CGC");
	Arginine.add("CGA");
	Arginine.add("CGG");
	Arginine.add("AGA");
	Arginine.add("AGG");
	map.put("R",Arginine);
	ArrayList<String> Leucine = new ArrayList<String>();
	Leucine.add("CTT");
	Leucine.add("CTC");
	Leucine.add("CTA");
	Leucine.add("CTG");
	Leucine.add("TTA");
	Leucine.add("TTG");
	map.put("L",Leucine);
	ArrayList<String> AsparticAcid = new ArrayList<String>();
	AsparticAcid.add("GAT");
	AsparticAcid.add("GAC");
	map.put("D",AsparticAcid);
	ArrayList<String> GlutamicAcid = new ArrayList<String>();
	GlutamicAcid.add("GAA");
	GlutamicAcid.add("GAG");
	map.put("E",GlutamicAcid);
	ArrayList<String> Alanine = new ArrayList<String>();
	Alanine.add("GCT");
	Alanine.add("GCC");
	Alanine.add("GCA");
	Alanine.add("GCG");
	map.put("A",Alanine);
	ArrayList<String> Glycine = new ArrayList<String>();
	Glycine.add("GGT");
	Glycine.add("GGC");
	Glycine.add("GGA");
	Glycine.add("GGG");
	map.put("G",Glycine);
	ArrayList<String> Valine = new ArrayList<String>();
	Valine.add("GTT");
	Valine.add("GTC");
	Valine.add("GTA");
	Valine.add("GTG");
	map.put("V",Valine);
	ArrayList<String> Tyrosine = new ArrayList<String>();
	Tyrosine.add("TAT");
	Tyrosine.add("TAC");
	map.put("Y",Tyrosine);
	ArrayList<String> Serine = new ArrayList<String>();
	Serine.add("TCT");
	Serine.add("TCC");
	Serine.add("TCA");
	Serine.add("TCG");
	Serine.add("AGT");
	Serine.add("AGC");
	map.put("S",Serine);
	ArrayList<String> Cysteine = new ArrayList<String>();
	Cysteine.add("TGT");
	Cysteine.add("TGC");
	map.put("C",Cysteine);
	ArrayList<String> Tryptophan = new ArrayList<String>();
	Tryptophan.add("TGG");
	map.put("W",Tryptophan);
	ArrayList<String> Phenylalanine = new ArrayList<String>();
	Phenylalanine.add("TTT");
	Phenylalanine.add("TTC");
	map.put("F",Phenylalanine);
	ArrayList<String> Asparagine = new ArrayList<String>();
	Asparagine.add("TTT");
	Asparagine.add("TTC");
	map.put("N",Asparagine);
	ArrayList<String> Lysine = new ArrayList<String>();
	Lysine.add("AAA");
	Lysine.add("AAG");
	map.put("K",Lysine);
	ArrayList<String> Threonine = new ArrayList<String>();
	Threonine.add("ACT");
	Threonine.add("ACC");
	Threonine.add("ACA");
	Threonine.add("ACG");
	map.put("T",Threonine);
	ArrayList<String> Isoleucine = new ArrayList<String>();
	Isoleucine.add("ATT");
	Isoleucine.add("ATC");
	Isoleucine.add("ATA");
	map.put("I",Isoleucine);
	ArrayList<String> Methionine = new ArrayList<String>();
	Methionine.add("ATG");
	map.put("M",Methionine);

    }

    public ArrayList<String> XlateAminos(String aminoString){
	ArrayList<String> aggregator = new ArrayList<String>();
	ArrayList<String> aminoList;
	for (int i = 0; i < aminoString.length(); i++){
	    aminoList = map.get(aminoString.substring(i, i+1));
	    if (aminoList == null){
		//TODO better handling for unrecognized Amino letter
		return null;
	    }
	    if (aggregator.size() == 0){
		for (String codon : aminoList){
		    aggregator.add(new String(codon));
		}
	    }
	    else{
		ArrayList<String> builder = new ArrayList<String>();
		for (int j = 0; j < aminoList.size(); j++){ 
		    for (String partial : aggregator){
			builder.add(new String(partial.concat(aminoList.get(j))));
		    }
		}
		aggregator = builder;
	    }
	}
	ArrayList<String> revComp = new ArrayList<>();
	for (int i = 0; i < aggregator.size(); i++){
	    revComp.add(ReverseCompliment.revcomp(aggregator.get(i)));
	}
	aggregator.addAll(revComp);
	return aggregator;
    }

}
