package org.rcpkg;

import static org.junit.Assert.*;

import org.junit.Test;

public class ReverseComplimentClassTest {

    @Test
    public void testReverseCompliment1() {
	assertEquals("DCBA", ReverseCompliment.reverse("ABCD"));
    }

    @Test
    public void testReverseCompliment2() {
	assertEquals("ACGT", ReverseCompliment.revcomp("ACGT"));
    }

    @Test
    public void testReverseCompliment3() {
	assertEquals("ACCGGGTTTT", ReverseCompliment.revcomp("AAAACCCGGT"));
    }	

}
