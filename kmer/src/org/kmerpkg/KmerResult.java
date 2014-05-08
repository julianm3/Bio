package org.kmerpkg;

import java.util.ArrayList;

public class KmerResult {
	private String str;
	private int cntr;
	private ArrayList<Integer> locations;
	private int currentLocation;
	
	public static void CombineKmerResults(KmerResult a, KmerResult b){
	    if (a.cntr > b.cntr){
		a.addOne(b.currentLocation);
	    }
	    else if (b.cntr > a.cntr){
		b.addOne(a.currentLocation);
	    }
	    else if (a.cntr == b.cntr){
		if(a != b){
		    if (a.currentLocation < b.currentLocation){
			a.addOne(b.currentLocation);
		    }
		    else if (b.currentLocation < a.currentLocation){
			b.addOne(a.currentLocation);
		    }
		    else{
			//TODO This should never happen
		    }
		}
	    }
	}
	
	public KmerResult(String str){
		this.str = str;
		cntr = 0;
		locations = new ArrayList<Integer>(); 
		currentLocation = 0;
	}	

	public KmerResult(String str, int currentLocation){
	    this(str);
		cntr++;
		this.currentLocation = currentLocation;
		locations.add(currentLocation);
	}
	
	public void combineResults(KmerResult other){  
	    int total = cntr + other.cntr;
	    cntr =total;
	    other.cntr = total;
	    //TODO combine location information as well
	}
	
	public Boolean addOne(){
		cntr++;
		if (cntr > 1)
		    return false;
		else
		    return true;
	}
	
	public Boolean addOne(int dex){
	    locations.add(dex);
	    return addOne();
	}
	
	int getCntr(){
		return cntr;
	}
	
	public String getStr(){
		return str;
	}
	
	ArrayList<Integer> getLocations(){
		return locations;
	}
	
	public boolean equals (Object o){
	    KmerResult r = (KmerResult) o;
	    return r.getStr().equals(str);
	}
	
	boolean inAClump(int window, int clumpSize){
		boolean result = false;
		int clumpStart = 0;
		int clumpEnd = 0;
		while (clumpEnd < (cntr-1)){
			if ((locations.get(++clumpEnd) - locations.get(clumpStart)) > (window - str.length())){
				clumpStart++;
			}
			else if ((clumpEnd - clumpStart + 1) >= clumpSize){
				result = true;
				break;
			}
		}
		return result;
	}

}
