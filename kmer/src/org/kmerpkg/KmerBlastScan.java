package org.kmerpkg;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.rcpkg.ReverseCompliment;

public class KmerBlastScan
{
    private ArrayList<KmerResult> blastList;
    private int precision;
    private String fileString;	

    public KmerBlastScan(String filepath){
	precision = 0;
	blastList = null;
	try{
	    fileString = readFile(filepath, StandardCharsets.UTF_8);
	}
	catch (Exception e){
	    System.console().writer().println("Couldn't open file:  " + e.getMessage());
	}
    }


    public KmerBlastScan (String filepath, int precision){
	this(filepath);
	this.precision = precision;
    }

    public int scan(int length){
	int result = 0;
	if (blastList == null)
	    blastList = (new KmerBlast(length)).getBlast();
	if (length == fileString.length()){
	    KmerResult temp = new KmerResult(fileString);
	    int index = blastList.indexOf(temp);
	    KmerResult actual = blastList.get(index);
	    actual.addOne(0);
	    //blastList.get(blastList.indexOf(new KmerResult(fileString))).addOne(0);
	    result++;
	}
	else if (length < fileString.length()){
	    String Kmer;
	    if (precision == 0){
		for (int i = 0; i < (fileString.length() - length + 1);i++){
		    Kmer = fileString.substring(i, i+length);
		    int index = blastList.indexOf(new KmerResult(Kmer));
		    if (index >= 0){
			if (blastList.get(index).addOne(i)){
			    result++;
			}
		    }
		}
	    }
	    else {
		ArrayList<String> multi;
		for (int i = 0; i < (fileString.length() - length + 1);i++){
		    Kmer = fileString.substring(i, i+length);
		    multi = KmerBlast.OneSub(Kmer,0);
		    if (precision > 1)
			multi.addAll(KmerBlast.TwoSub(Kmer, 0));
		    if (precision == 3)
			multi.addAll(KmerBlast.ThreeSub(Kmer, 0));
		    for (int j = 0; j< multi.size(); j++){
			int index = blastList.indexOf(new KmerResult(multi.get(j)));
			if (index >= 0){
			    if (blastList.get(index).addOne(i)){
				result++;
			    }
			}
		    }
		}
	    }
	}
	else if (length > fileString.length()){
	    result = 0;
	}
	return result;
    }
    
    public int scan2(int length){
	int result = 0;
	if (blastList == null)
	    blastList = (new KmerBlast(length)).getBlast();
	if (length == fileString.length()){
	    KmerResult temp = new KmerResult(fileString);
	    int index = blastList.indexOf(temp);
	    KmerResult actual = blastList.get(index);
	    actual.addOne(0);
	    //blastList.get(blastList.indexOf(new KmerResult(fileString))).addOne(0);
	    result++;
	}
	else if (length < fileString.length()){
	    String Kmer;
	    if (precision == 0){
		for (int i = 0; i < (fileString.length() - length + 1);i++){
		    Kmer = fileString.substring(i, i+length);
		    int index = blastList.indexOf(new KmerResult(Kmer));
		    if (index >= 0){
			if (blastList.get(index).addOne(i)){
			    result++;
			}
		    }
		}
	    }
	    else {
		for (int i = 0; i < (fileString.length() - length + 1);i++){
		    Kmer = fileString.substring(i, i+length);
		    for (int j = 0; j < blastList.size(); j++){
			KmerResult cursor = blastList.get(j);
			if (KmerBlast.diffTo4(cursor.getStr(), Kmer) <= precision){
			    cursor.addOne(i);
			}
		    }
		}
	    }
	}
	else if (length > fileString.length()){
	    result = 0;
	}
	return result;
    }

    public int scanForSpecific(ArrayList<String> targets){
 	blastList = new KmerBlast(targets).getBlast();
	return scan2(targets.get(0).length());
    }
    

    public ArrayList<KmerResult> scanResults(){
	//TODO brute force for now
	ArrayList<KmerResult> result = new ArrayList<KmerResult>();
	int maxCntr = 0;
	for (int i = 0; i < blastList.size();i++){
	    if (blastList.get(i).getCntr() > maxCntr){
		maxCntr = blastList.get(i).getCntr();
	    }
	}

	if (maxCntr != 0){
	    for (int i = 0; i < blastList.size();i++){
		if (blastList.get(i).getCntr() == maxCntr){
		    result.add(blastList.get(i));
		}
	    }
	}
	return result;
    }

    public ArrayList<KmerResult> scanResultsAnyHit(){
	ArrayList<KmerResult> result = new ArrayList<KmerResult>();
	for (int i = 0; i < blastList.size();i++){
	    if (blastList.get(i).getCntr() > 0){
		result.add(blastList.get(i));
	    }
	}

	return result;
	
    }
    
    
    public ArrayList<KmerResult> scanResultsPlusReverseCompliment(){
	ArrayList<KmerResult> firstPass = scanResults();
	for (int i = 0; i < firstPass.size(); i++){
	    KmerResult current = firstPass.get(i);
	    String rc = ReverseCompliment.revcomp(current.getStr());
	    int rcIndex = blastList.indexOf(new KmerResult(rc));
	    current.combineResults(blastList.get(rcIndex));
	}
	//rescan
	return scanResults();
    }

    private String readFile(String path, Charset encoding) throws IOException 
    {
	byte[] encoded = Files.readAllBytes(Paths.get(path));
	return encoding.decode(ByteBuffer.wrap(encoded)).toString();
    }
}
