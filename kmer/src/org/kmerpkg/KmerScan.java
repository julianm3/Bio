package org.kmerpkg;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

//Needed for Levenshtein distance
//import org.apache.commons.lang3.StringUtils;

public class KmerScan {

    private String fileString;	
    TreeSet<KmerResult> kmerResults;
    private int precision;

    public KmerScan(String filepath){
	kmerResults = new TreeSet<KmerResult>(new Comparator<KmerResult>() {
	    public int compare(KmerResult a, KmerResult b){
		int compareResult = a.getStr().compareTo(b.getStr());
		//int dist = StringUtils.getLevenshteinDistance(a.getStr(),b.getStr());
		//int dist = getSimpleDistance(a.getStr(), b.getStr(), precision);
		if (getSimpleDistance(a.getStr(), b.getStr(), precision)){
		    KmerResult.CombineKmerResults(a,b);
		    compareResult = 0;
		}
		return compareResult;
	    }
	}
		);
	precision = 0;
	try{
	    fileString = readFile(filepath, StandardCharsets.UTF_8);
	}
	catch (Exception e){
	    System.console().writer().println("Couldn't open file:  " + e.getMessage());
	}
    }
    
    public KmerScan (String filepath, int precision){
	this(filepath);
	this.precision = precision;
    }
    
    public static Boolean getSimpleDistance(String a, String b, int precision){
	if (a.length() != b.length())
	    return false;
	int diffCntr = 0;
	for(int i = 0; i < a.length();i++){
	    if (!a.substring(i,i+1).equals(b.subSequence(i, i+1))){
		if (++diffCntr > precision){
		    return false;
		}
	    }
	}
	return true;
    }

    // Now obsolete
    public KmerResult KmerLocations(String targetKmer){
	KmerResult result = new KmerResult(targetKmer);
	int index = 0;
	int cursor = 0;
	while ((index = fileString.substring(cursor).indexOf(targetKmer)) >= 0){
	    result.addOne(cursor + index);
	    cursor = cursor + index + 1;
	    //TODO handle case where targetKmer is one character long
	}
	return result;
    }

    public int scan(int length){
	int result = 0;
	if (length == fileString.length()){
	    if (kmerResults.add(new KmerResult(fileString,0)))
		result++;
	}
	else if (length < fileString.length()){
	    for (int i = 0; i < (fileString.length() - length + 1);i++){
		if (kmerResults.add(new KmerResult(fileString.substring(i, i+length),i)))
		    result++;
	    }
	}
	else{
	    result = 0;  //redundant but all cases covered.
	}
	return result;
    }

    public ArrayList<KmerResult> scanWithLocations(int length, int window, int clumpSize){
	scan(length);
	ArrayList<KmerResult> result = new ArrayList<KmerResult>();
	for (Iterator<KmerResult> it = this.iterator();it.hasNext();){
	    KmerResult current = it.next();
	    if (current.getCntr() >= clumpSize){
		if (current.inAClump(window, clumpSize)){
		    result.add(current);
		}
	    }
	}

	return result;
    }
    public Iterator<KmerResult> iterator(){
	return kmerResults.iterator();
    }

    private String readFile(String path, Charset encoding) throws IOException 
    {
	byte[] encoded = Files.readAllBytes(Paths.get(path));
	return encoding.decode(ByteBuffer.wrap(encoded)).toString();
    }

    public static void usage(){
	System.out.println("KmerScan <file> <length>");
	System.out.println("    <file> containing raw text");
	System.out.println("    <length> kmer length");
    }

    public static void main(String [ ] args)
    {
	if (args.length != 2)
	    usage();
	else{
	    KmerScan dut = new KmerScan(args[0]);
	    int length = Integer.parseInt(args[1]);
	    int result = dut.scan(length);
	    if (result ==0){
		System.out.println("Error, no results found");
		return;
	    }
	    int maxCnt = 0;
	    for (Iterator<KmerResult> it = dut.iterator();it.hasNext();){
		KmerResult kmerRes = it.next();
		if (kmerRes.getCntr()>maxCnt){
		    maxCnt = kmerRes.getCntr();
		}
	    }
	    boolean first = true;
	    for (Iterator<KmerResult> it = dut.iterator();it.hasNext();){
		KmerResult kmerRes = it.next();

		if (kmerRes.getCntr() == maxCnt){
		    if (first){
			System.out.print(kmerRes.getStr());
			first = false;
		    }
		    else
			System.out.print(" " + kmerRes.getStr());
		}
	    }
	}
    }
}
