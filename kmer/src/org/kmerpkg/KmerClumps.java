package org.kmerpkg;

import java.util.ArrayList;

public class KmerClumps
{

	public static void usage(){
		System.out.println("KmerClumps <file> <length> <window> <clumpSize> | -count");
		System.out.println("    <file> containing raw text");
		System.out.println("    <length> kmer length");
		System.out.println("    <window> clump window size");
		System.out.println("    <clumpSize> number of matches in clump");
		System.out.println("    -count only print the number of targets found");
		
	}

    /**
     * @param args
     */
    public static void main(String[] args) {
	if ((args.length != 4) && (args.length != 5))
		usage();
	else{
		KmerScan dut = new KmerScan(args[0]);
		ArrayList<KmerResult> clumps = dut.scanWithLocations(
		    	Integer.parseInt(args[1]), //kmer length
		    	Integer.parseInt(args[2]), //window
		    	Integer.parseInt(args[3]));//clump size
		if (args.length == 4){
        		for(KmerResult i : clumps){
        		    System.out.print(i.getStr() + " ");
        		}		
        	        System.out.println("");
        	        System.out.println("Done");
		}
		else {
		    System.out.println("Targets found: " + clumps.size());
		}
	}

    }

}
