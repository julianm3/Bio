package org.ProtSeq;

import java.util.ArrayList;

import org.kmerpkg.KmerBlastScan;
import org.kmerpkg.KmerResult;

public class SeqScanner
{

    Aminos am = new Aminos();
    KmerBlastScan scanner;

    public SeqScanner (String sequence, String filePath){
	ArrayList<String> targets  = am.XlateAminos(sequence);
	scanner = new KmerBlastScan(filePath);
	scanner.scanForSpecific(targets);
    }

    ArrayList<String> results(){
	ArrayList<KmerResult> kmerList  = scanner.scanResultsAnyHit();
	ArrayList<String> result = new ArrayList<String>();
	for (KmerResult cursor : kmerList){
	    result.add(cursor.getStr());
	}
	return result;
    }

    public static void usage(){
	System.out.println("SeqScanner <Protein sequence> <DNA file>");
	System.out.println("  Return any hits that could encode the sequence ");
	System.out.println("  <Protein sequence>  Sequence of amino acids in the protein");
	System.out.println("  <file>              File containing sample DNA");
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
	if (args.length != 2){
	    usage();
	    return;
	}

	SeqScanner scan = new SeqScanner(args[0], args[1]);

	ArrayList<String> matches = scan.results();

	for (String line:matches){
	    System.out.println(line);
	}
    }
}
