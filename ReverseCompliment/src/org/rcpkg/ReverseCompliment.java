package org.rcpkg;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReverseCompliment {
	//TODO rewrite this whole thing
    	
	
	public static String reverse(String str){
		String result = new String("");
		for (int i = str.length() -1 ;i >= 0; i--){
			result = result.concat(str.substring(i,i+1));
		}
		return result;
	}
	
	public static String revcomp(String str){
		String reversed = reverse(str);
		String result = new String("");
		for (int i = 0; i < reversed.length(); i++){
			if (reversed.substring(i, i+1).contains("A"))
				result = result.concat("T");
			else if (reversed.substring(i, i+1).contains("C"))
				result = result.concat("G");
			else if (reversed.substring(i, i+1).contains("G"))
				result = result.concat("C");
			else if (reversed.substring(i, i+1).contains("T"))
				result = result.concat("A");
			else{
				result = result.concat(reversed.substring(i, i+1));
			}
		}
		return result;
	}
	
	private static String readFile(String path, Charset encoding) throws IOException 
	{
		  byte[] encoded = Files.readAllBytes(Paths.get(path));
		  return encoding.decode(ByteBuffer.wrap(encoded)).toString();
	}
	public static void usage(){
		System.out.println("ReverseCompliment <in file> <out file>");
		System.out.println("    <in file> containing raw text (only ACGT will be complimented)");
		System.out.println("    <out file> destination file");
	}
	
	public static void main(String [ ] args)
	{
		if (args.length != 2){
			usage();
			return;
		}
		String fileString;
		try{
			fileString = readFile(args[0], StandardCharsets.UTF_8);
		}
		catch (Exception e){
			System.console().writer().println("Couldn't open file:  " + e.getMessage());
			return;
		}
		
		try {
			PrintWriter out = new PrintWriter(args[1]);
			out.println(ReverseCompliment.revcomp(fileString));
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		
	}

}
