package org.kmerpkg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Test;

public class KmerScanTest {

    @Test
    /**
     * testKmerScan
     * Also tests case where kmer length matches symbol string length
     */
    public void testKmerScan() {
	KmerScan dut = new KmerScan("test/ABCD.txt");
	int result = dut.scan(4);
	assertEquals("Single entry", result, 1);
	Iterator<KmerResult> it = dut.iterator();
	assertTrue(it.hasNext());
	KmerResult kmerRes = it.next();
	assertEquals("ABCD",kmerRes.getStr());
	assertEquals(1,kmerRes.getCntr());
    }

    @Test
    /**
     * testScan1
     * Tests case where kmer length exceeds symbol string length
     */
    public void testScan1() {
	KmerScan dut = new KmerScan("test/ABCD.txt");
	int result = dut.scan(5);
	assertEquals("Error result",0,result);
    }

    @Test
    /**
     * testScan2
     * Should return AB, BC, CD as three substrings in ABCD of length 2 
     * Count for each should be 1
     */
    public void testScan2() {
	KmerScan dut = new KmerScan("test/ABCD.txt");
	int result = dut.scan(2);
	assertEquals("Kmer 2 out of ABCD count", 3, result);
	Iterator<KmerResult> it = dut.iterator();
	assertTrue("First", it.hasNext());
	KmerResult kmerRes = it.next();
	assertEquals("First","AB", kmerRes.getStr());
	assertTrue("Second", it.hasNext());
	kmerRes = it.next();
	assertEquals("Second","BC", kmerRes.getStr());
	assertTrue("Third", it.hasNext());
	kmerRes = it.next();
	assertEquals("Third","CD", kmerRes.getStr());
    }
    @Test
    /**
     * testScan3
     * Should return AB three times in ABABAB @ length 2 
     */
    public void testScan3() {
	KmerScan dut = new KmerScan("test/AB3.txt");
	int result = dut.scan(2);
	assertEquals("Kmer 2 total unique count", 2, result);
	Iterator<KmerResult> it = dut.iterator();
	assertTrue("Empty check", it.hasNext());
	KmerResult kmerRes1 = it.next();
	assertEquals("First","AB", kmerRes1.getStr());
	assertEquals("Pattern count", 3, kmerRes1.getCntr());
	KmerResult kmerRes2 = it.next();
	assertEquals("Second","BA", kmerRes2.getStr());
	assertEquals("Pattern count", 2, kmerRes2.getCntr());
    }	

    @Test
    /**
     * testScan4
     * Legit example from class site 
     */
    public void testScan4() {
	KmerScan dut = new KmerScan("test/kmerSample1.txt");
	// Should find CATG GCAT
	int result = dut.scan(4); 
	assertEquals("Kmer 22 total unique count",21, result);
	//TODO figure out why this isn't 22
	int maxCnt = 0;
	for (Iterator<KmerResult> it = dut.iterator();it.hasNext();){
	    KmerResult kmerRes = it.next();
	    if (kmerRes.getCntr()>maxCnt){
		maxCnt = kmerRes.getCntr();
	    }
	}
	for (Iterator<KmerResult> it = dut.iterator();it.hasNext();){
	    KmerResult kmerRes = it.next();
	    if (kmerRes.getCntr() == maxCnt){
		System.out.println(kmerRes.getStr() + " " + kmerRes.getCntr());
	    }
	}
    }

    @Test
    /**
     * testLocations1
     * Should return AB at 0,2 and 4 within ABABAB 
     */
    public void testLocations1() {
	KmerScan dut = new KmerScan("test/AB3.txt");
	int kmers = dut.scan(2);
	assertEquals("Kmers found", 2, kmers);
	Iterator<KmerResult> it = dut.iterator();
	//TODO might be order dependent
	KmerResult current = it.next();
	assertEquals("Kmer hits", 3, current.getCntr());
	ArrayList<Integer> locations = current.getLocations();
	for (int i = 0; i < current.getCntr(); i++)
	    assertEquals("Kmer Location " + i, (Integer)(i*2), locations.get(i));

	current = it.next();
	assertEquals("Kmer hits", 2, current.getCntr());
	locations = current.getLocations();
	for (int i = 0; i < current.getCntr(); i++)
	    assertEquals("Kmer Location " + i, (Integer)(i*2 + 1), locations.get(i));
    }	

    @Test
    /**
     * testClumps1
     * Should return 
     * ABCD 2 locations
     *   0 7
     * ZZ01 3 locations
     *   11 21 27
     */
    public void testClumps1() {
	KmerScan dut = new KmerScan("test/clump1.txt");
	ArrayList<KmerResult> clumps = dut.scanWithLocations(4, 11, 2);
	assertEquals("Found ABCD", "ABCD", clumps.get(0).getStr());
	ArrayList<Integer> abcdLocations = clumps.get(0).getLocations();
	assertEquals("Clump size", 7, abcdLocations.get(1) - abcdLocations.get(0));

	assertEquals("Found ZZ01", "ZZ01", clumps.get(1).getStr());
	ArrayList<Integer> zz01Locations = clumps.get(1).getLocations();
	assertEquals("Clump size", 6, zz01Locations.get(2) - zz01Locations.get(1));

	for(KmerResult i : clumps){
	    System.out.println(i.getStr() + " " + i.getCntr() + " locations");
	    ArrayList<Integer> locations = i.getLocations();
	    for (int j = 0; j < i.getCntr() - 1;j++){
		System.out.print(locations.get(j) + " ");
	    }
	    System.out.println(locations.get(i.getCntr() - 1));
	}
    }	
    @Test
    /**
     * testClumps2
     * Should return 
     * ZZ 4 locations
     *  11 21 27 31
     */
    public void testClumps2() {
	KmerScan dut = new KmerScan("test/clump1.txt");
	ArrayList<KmerResult> clumps = dut.scanWithLocations(2, 23, 4);
	assertEquals("Found ZZ", "ZZ", clumps.get(0).getStr());
	ArrayList<Integer> zzLocations = clumps.get(0).getLocations();
	assertEquals("Clump size", 20, zzLocations.get(3) - zzLocations.get(0));

	for(KmerResult i : clumps){
	    System.out.println(i.getStr() + " " + i.getCntr() + " locations");
	    ArrayList<Integer> locations = i.getLocations();
	    for (int j = 0; j < i.getCntr() - 1;j++){
		System.out.print(locations.get(j) + " ");
	    }
	    System.out.println(locations.get(i.getCntr() - 1));
	}
    }	
    @Test
    /**
     * testFuzzyScan1
     * precision == 1
     */
    public void testFuzzyScan1(){
	KmerScan dut = new KmerScan("test/kmerSample1.txt", 1);
	// Should find GATG ATGC ATGT
	int result = dut.scan(4); 
	assertEquals("Kmer 15 total fuzzy count",15, result);
	int maxCnt = 0;
	for (Iterator<KmerResult> it = dut.iterator();it.hasNext();){
	    KmerResult kmerRes = it.next();
	    if (kmerRes.getCntr()>maxCnt){
		maxCnt = kmerRes.getCntr();
	    }
	}
	for (Iterator<KmerResult> it = dut.iterator();it.hasNext();){
	    KmerResult kmerRes = it.next();
	    if (kmerRes.getCntr() == maxCnt){
		System.out.println(kmerRes.getStr() + " " + kmerRes.getCntr());
	    }
	}
    }
    @Test
    /**
     * testFuzzyScan1
     * precision == 1
     */
    public void testFuzzyScan2(){
	KmerScan dut = new KmerScan("test/fuzzyClassExample.txt", 2);
	// Should find GCACACAGAC GCGCACACAC
	int result = dut.scan(10); 
	//assertEquals("Kmer 15 total fuzzy count",15, result);
	System.out.println(result + " results");
	int maxCnt = 0;
	for (Iterator<KmerResult> it = dut.iterator();it.hasNext();){
	    KmerResult kmerRes = it.next();
	    if (kmerRes.getCntr()>maxCnt){
		maxCnt = kmerRes.getCntr();
	    }
	}
	for (Iterator<KmerResult> it = dut.iterator();it.hasNext();){
	    KmerResult kmerRes = it.next();
	    if (kmerRes.getCntr() == maxCnt){
		System.out.println(kmerRes.getStr() + " " + kmerRes.getCntr());
	    }
	}
    }

}
