package org.kmerpkg;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class ApproxPatternMatch
{

    public static void usage(){
	System.out.println("ApproxPatternMatch <file>");
	System.out.println("    <file> containing pattern text and precision");
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
	if (args.length != 1){
	    usage();
	    return;
	}

	InputStream fis;
	BufferedReader br;
	String line;
	int lineCntr = 1;
	String Pattern = null;
	String Text = null;
	int precision = 0;
	try {
	    fis = new FileInputStream(args[0]);
	    br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
	    while ((line = br.readLine()) != null) {
		switch(lineCntr){
		    case 1:
			Pattern = new String(line);
			break;
		    case 2:
			Text = new String(line);
			break;
		    case 3:
			precision = Integer.parseInt(line);
			break;
		    default:
			break;

		}
		lineCntr++;
	    }
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	}
	if (lineCntr < 3)
	    System.out.println("Invalid input file, not enough lines");
	
	int hitCntr = 0;
	for (int i = 0; i < Text.length() - Pattern.length() + 1; i++){
	    if (precision >= KmerBlast.diffToAny(Pattern, Text.substring(i, i+Pattern.length()))){
		System.out.print(i + " ");
		hitCntr++;
	    }
	}
	if (hitCntr == 0){
	    System.out.println("No hits found");
	}
	else{
	    System.out.println("");
	}

    }

}
