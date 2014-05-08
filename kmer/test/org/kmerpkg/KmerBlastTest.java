package org.kmerpkg;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class KmerBlastTest
{

    @Test
    public void testKmerBlast() {
	KmerBlast blast = new KmerBlast(2);
	ArrayList<KmerResult> list = blast.getBlast();
	assertEquals("Blast 0", "AA", list.get(0).getStr());
	assertEquals("Blast 1", "AC", list.get(1).getStr());
	assertEquals("Blast 2", "AG", list.get(2).getStr());
	assertEquals("Blast 3", "AT", list.get(3).getStr());
	assertEquals("Blast 4", "CA", list.get(4).getStr());
	assertEquals("Blast 5", "CC", list.get(5).getStr());
	assertEquals("Blast 6", "CG", list.get(6).getStr());
	assertEquals("Blast 7", "CT", list.get(7).getStr());
	assertEquals("Blast 8", "GA", list.get(8).getStr());
	assertEquals("Blast 9", "GC", list.get(9).getStr());
	assertEquals("Blast 10", "GG", list.get(10).getStr());
	assertEquals("Blast 11", "GT", list.get(11).getStr());
	assertEquals("Blast 12", "TA", list.get(12).getStr());
	assertEquals("Blast 13", "TC", list.get(13).getStr());
	assertEquals("Blast 14", "TG", list.get(14).getStr());
	assertEquals("Blast 15", "TT", list.get(15).getStr());
    }

    @Test
    public void testIncrement() {
	String start = new String("AA");
	assertEquals("Incr 1", "AC", KmerBlast.increment(start));
	assertEquals("Incr 2", "AG", KmerBlast.increment("AC"));
	assertEquals("Incr 3", "AT", KmerBlast.increment("AG"));
	assertEquals("Incr 4", "CA", KmerBlast.increment("AT"));
	assertEquals("Incr 5", "CC", KmerBlast.increment("CA"));
	assertEquals("Incr 6", "CG", KmerBlast.increment("CC"));
	assertEquals("Incr 7", "CT", KmerBlast.increment("CG"));
	assertEquals("Incr 8", "GA", KmerBlast.increment("CT"));
	assertEquals("Incr 9", "GC", KmerBlast.increment("GA"));
	assertEquals("Incr 10", "GG", KmerBlast.increment("GC"));
	assertEquals("Incr 11", "GT", KmerBlast.increment("GG"));
	assertEquals("Incr 12", "TA", KmerBlast.increment("GT"));
	assertEquals("Incr 13", "TC", KmerBlast.increment("TA"));
	assertEquals("Incr 14", "TG", KmerBlast.increment("TC"));
	assertEquals("Incr 15", "TT", KmerBlast.increment("TG"));
	assertEquals("Incr Rollover", "AA", KmerBlast.increment("TT"));
	String last = new String("TT");
	assertEquals("Incr ", "AA", KmerBlast.increment(last));
	String lastLong = new String("TTTTTTTTTT");
	assertEquals("Incr ", "AAAAAAAAAA", KmerBlast.increment(lastLong));
	
    }
    
    @Test
    public void testSpeed(){
	System.out.println("starting length = 10");
	@SuppressWarnings("unused")
	KmerBlast blast = new KmerBlast(10);
	System.out.println("Done");
    }
    
    @Test
    public void TestStringMultiplier1(){
	String base = new String("AT");
	// should have 7 total strings;
	ArrayList<String> mult = KmerBlast.OneSub(base, 0);
	for (int i = 0; i < mult.size();i++){
	    System.out.println(mult.get(i));
	}
    }
    
    @Test
    public void TestStringMultiplier2(){
	String base = new String("AA");
	// should have 16 total strings;
	ArrayList<String> mult = KmerBlast.TwoSub(base, 0);
	for (int i = 0; i < mult.size();i++){
	    System.out.println(mult.get(i));
	}
    
	base = new String("AAA");
	// should have 48 total strings;
	mult = KmerBlast.TwoSub(base, 0);
	for (int i = 0; i < mult.size();i++){
	    System.out.println(mult.get(i));
	}
    }
    
    @Test
    public void TestStringMultiplier3(){
	String base = new String("AAAA");
	// should have 16 total strings;
	ArrayList<String> mult = KmerBlast.ThreeSub(base, 0);
	for (int i = 0; i < mult.size();i++){
	    System.out.println(mult.get(i));
	}
    
    }

}
