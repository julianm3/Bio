package org.kmerpkg;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class KmerResultTest {
	
	@Test
	public void testKmerResult() {
		KmerResult dut = new KmerResult("ABCD");
		assertEquals("String value test", "ABCD", dut.getStr());
		assertEquals("Kmer counter test", 0, dut.getCntr());
	}

	@Test
	public void testAddOne() {
		KmerResult dut = new KmerResult("ABCD");
		dut.addOne();
		assertEquals("Kmer Result AddOne test", 1, dut.getCntr());
	}
	
	@Test
	public void testLocations() {
		KmerResult dut = new KmerResult("ABCD");
		for (int i = 0; i < 401;i++){
			dut.addOne(i*2);
		}
		assertEquals("Kmer Result AddOne test",401, dut.getCntr());
		ArrayList<Integer> result = dut.getLocations();
		assertEquals("Location 0", (Integer)0, result.get(0));
		assertEquals("Location 217",(Integer)(2*217), result.get(217));
		assertEquals("Location 399",(Integer)(2*399), result.get(399));
	}

	@Test
	public void testInAClump(){
		KmerResult dut1 = new KmerResult("ABCD");
		dut1.addOne(5);
		dut1.addOne(10);
		dut1.addOne(15);
		assertTrue("In clump 1", dut1.inAClump(10 + 4, 3)); 
		assertTrue("In clump 1", dut1.inAClump(5 + 4, 2)); 
		assertFalse("Not in clump 1", dut1.inAClump(9, 3));
		KmerResult dut2 = new KmerResult("ABCD");
		dut2.addOne(0);
		dut2.addOne(100);
		dut2.addOne(150);
		dut2.addOne(175);
		dut2.addOne(188);
		assertTrue("In clump 2", dut2.inAClump(13 + 4, 2)); 
		assertTrue("In clump 2", dut2.inAClump(38 + 4, 3)); 
		assertFalse("Not in clump 2", dut2.inAClump(87 + 4, 4));
	}
	@Test
	public void testArrayList(){
	    ArrayList<KmerResult> list = new ArrayList<KmerResult>();
	    KmerResult sig = new KmerResult("three", 50);
	    assertEquals(1, sig.getCntr());
	    list.add(new KmerResult("one"));
	    list.add(new KmerResult("two"));
	    list.add(sig);
	    assertTrue(list.contains(new KmerResult("one")));
	    KmerResult isItSig = list.get(list.indexOf(new KmerResult("three")));
	    assertEquals(1, isItSig.getCntr());
	    KmerResult empty = new KmerResult("empty");
	    assertEquals(0, empty.getCntr());
	}

}
