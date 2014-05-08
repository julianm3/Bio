package org.kmerpkg;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CGDiff
{
    String fileString;
    int diff;
    int index;
    Boolean first;
    ArrayList<Integer> skewValues;
    ArrayList<Integer> minimumPrefix;
    int minimum;

    public CGDiff(String filepath){
	diff = 0;
	index = 0;
	first = true;
	skewValues = new ArrayList<Integer>();
	skewValues.add(0);  // Skew for prefix 0
	minimumPrefix = new ArrayList<Integer>();
	minimum = 0;
	try{
	    fileString = readFile(filepath, StandardCharsets.UTF_8);
	}
	catch (Exception e){
	    System.console().writer().println("Couldn't open file:  " + e.getMessage());
	}
    }

    private int differ(){
	if (fileString.substring(index, index+1).equals("C"))
	    return -1;
	if (fileString.substring(index, index+1).equals("G"))
	    return 1;
	return 0;
    }

    public int scanNext(){

	if (first){  // Weird requirement in the class example
	    first = false;
	    return 0;
	}
	diff += differ(); 
	index++;
	return diff;
    }

    public void skewCalc(){
	for (int i = 0; i < fileString.length(); i++){
	    int currentSkew = skewValues.get(index) + differ();
	    if (currentSkew < minimum)
		minimum = currentSkew;
	    skewValues.add(currentSkew);
	    index++;
	}
	for (int i = 0; i < fileString.length() + 1; i++){
	    if (skewValues.get(i) == minimum)
		minimumPrefix.add(i);
	}
    }

    public ArrayList<Integer> getMinimumPrefixes(){
	return minimumPrefix;
    }


    public int totalSkew(int prefix){
	index = 0;
	diff = 0;
	if (prefix > fileString.length())
	    prefix = fileString.length();
	for (int i = 0; i <= prefix; i++ ){
	    diff += differ();
	    index++;
	}
	return diff;
    }

    public Boolean theresMore(){
	return index < fileString.length();
    }

    private String readFile(String path, Charset encoding) throws IOException 
    {
	byte[] encoded = Files.readAllBytes(Paths.get(path));
	return encoding.decode(ByteBuffer.wrap(encoded)).toString();
    }

    public static void usage(){
	System.out.println("CGDiff <file Name> | (-all|<max prefix>)");
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
	if ((args.length != 1) && (args.length != 2)){
	    usage();
	    return;
	}
	CGDiff scanner = new CGDiff(args[0]);
	if (args.length == 1){
	    while (scanner.theresMore()){
		System.out.print(scanner.scanNext() + " ");
	    }
	    System.out.println("");
	}
	else{
	    if (args[1].equals("-all")){
		scanner.skewCalc();
		ArrayList<Integer> results = scanner.getMinimumPrefixes();
		for(int i = 0; i < results.size(); i++){
		    System.out.print(results.get(i) + " ");
		}
		System.out.println("");
	    }
	    else{
		for (int i = 0; i < Integer.parseInt(args[1]);i++){
		    System.out.print(scanner.totalSkew(i) + " ");
		}
		System.out.println("");
	    }
	}
    }

}
