package org.kmerpkg;

import java.util.ArrayList;

public class KmerLocations {
	
	public static void usage(){
		System.out.println("KmerLocations <file> <target kmer>");
		System.out.println("    <file> containing raw text");
		System.out.println("    <target kmer> kmer string");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 2)
			usage();
		else{
			KmerScan dut = new KmerScan(args[0]);
			KmerResult result = dut.KmerLocations(args[1]);
			ArrayList<Integer> locations = result.getLocations();
			for (int i = 0; i < result.getCntr(); i++){
				if (i < result.getCntr() - 1)
					System.out.print(locations.get(i) + " ");
				else
					System.out.println(locations.get(i));
			}
			
		}

	}

}
