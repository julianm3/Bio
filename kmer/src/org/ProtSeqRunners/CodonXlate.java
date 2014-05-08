package org.ProtSeqRunners;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.ProtSeq.Codons;

public class CodonXlate
{
    public static void usage(){
	System.out.println("CodonXlate <Codon String> | -f <file>");
	System.out.println("  Translate codon string until stop or non codon");
	System.out.println("  <Codon String>  raw codon string");
	System.out.println("  -f <file>       File containing codon string");
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
	if ((args.length != 1) && (args.length != 2)){
	    usage();
	    return;
	}

	Codons dict = new Codons();
	String result;
	if (args.length == 1){
	    result = dict.XlateCodons(args[0]);
	}
	else {
	    InputStream fis;
	    BufferedReader br;
	    String line = null;
	    String Pattern = new String("");
	    try {
		fis = new FileInputStream(args[1]);
		br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
		while ((line = br.readLine()) != null) {
		    Pattern += line;
		}
		br.close();
	    } catch (Exception e) {
		System.out.println(e.getMessage());
		return;
	    }
	    result = dict.XlateCodons(Pattern);
	}
	System.out.println(result);
    }

}
