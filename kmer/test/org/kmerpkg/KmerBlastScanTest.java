package org.kmerpkg;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class KmerBlastScanTest
{

    @Test
    public void testKmerBlastScan1() {
	KmerBlastScan dut = new KmerBlastScan("test/ACGT.txt");
	int result = dut.scan(4);
	assertEquals("Single", 1, result);
	ArrayList<KmerResult> list = dut.scanResults();
	assertEquals("Single result count", 1, list.size());
	KmerResult single = list.get(0);
	assertEquals("Single Kmer", "ACGT", single.getStr());
	assertEquals("Single Kmer hit", 1, single.getCntr());
    }
    
    @Test
    public void testKmerBlastScan2(){
	KmerBlastScan dut = new KmerBlastScan("test/ACGT.txt");
	int result = dut.scan(2);
	assertEquals("Kmer count", 3 , result);
	ArrayList<KmerResult> list = dut.scanResults();
	KmerResult cursor = list.get(0);
	assertEquals("First", "AC", cursor.getStr());
	assertEquals("First hit", 1, cursor.getCntr());
	cursor = list.get(1);
	assertEquals("Second", "CG", cursor.getStr());
	assertEquals("Second hit", 1, cursor.getCntr());
	cursor = list.get(2);
	assertEquals("Third", "GT", cursor.getStr());
	assertEquals("Third hit", 1, cursor.getCntr());
    }
    
    @Test
    public void testKmerBlastScan3(){
	KmerBlastScan dut = new KmerBlastScan("test/kmerSample1.txt");
	// Should find CATG GCAT
	int result = dut.scan(4);
	assertEquals("Kmer count", 21 , result);
	ArrayList<KmerResult> list = dut.scanResults();
	for (int i = 0; i < list.size(); i++){
	    System.out.println(list.get(i).getStr() + " " + list.get(i).getCntr());
	}
    }

}
