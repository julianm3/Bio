package org.kmerpkg;

import java.util.ArrayList;

public class FreqWordsWMismatch
{
    public static void usage(){
	System.out.println("FreqWordsWMismatch <file> <length> <precision> | -rc");
	System.out.println("    <file> containing raw text");
	System.out.println("    <length> kmer length");
	System.out.println("    <precision> Number of allowed errors");
	System.out.println("    -rc include reverse compliment hits");


    }
    /**
     * @param args
     */
    public static void main(String[] args) {
	if ((args.length != 3) && (args.length != 4)){
	    usage();
	    return;
	}
	if ((args.length == 4) && (!args[3].equals("-rc"))){
	    usage();
	    return;
	}
	KmerBlastScan scanner = new KmerBlastScan(args[0], Integer.parseInt(args[2]));
	System.out.println("Starting...");
	int hits = scanner.scan2(Integer.parseInt(args[1]));
	System.out.println("Hits " + hits);
	ArrayList<KmerResult> topKmers;
	if ((args.length == 4) && (args[3].equals("-rc"))){
	    topKmers = scanner.scanResultsPlusReverseCompliment(); 
	}
	else{
	    topKmers = scanner.scanResults();
	}
	for (int i = 0; i < topKmers.size() - 1;i++){
	    System.out.print(topKmers.get(i).getStr() + " ");
	}
	System.out.println(topKmers.get(topKmers.size()-1).getStr());
    }

}
