package ProtSeq;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.ProtSeq.Aminos;
import org.junit.Test;

public class AminoTest
{

    Aminos am = new Aminos();
    
    @Test
    public void Amino_MA() {
	ArrayList<String> MA_list = am.XlateAminos("MA");
	assertEquals("Size check",MA_list.size(),8);
	assertEquals("First", "ATGGCT", MA_list.get(0));
	assertEquals("Last", "GGCCAT", MA_list.get(5));
	for (String matching : MA_list){
	    System.out.println(matching);
	}
	
    }    
    
    @Test
    public void Amino_KEVFEPHYY() {
	ArrayList<String> list = am.XlateAminos("KEVFEPHYY");
	assertEquals("Size check",4096,list.size());
	assertEquals("First", "AAAGAAGTTTTTGAACCTCATTATTAT", list.get(0));
	assertEquals("Last", "AAGGAAGTCTTTGAACCTCATTATTAT", list.get(5));
	for (String matching : list){
	    System.out.println(matching);
	}
	
    }

}
