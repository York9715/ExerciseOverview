package de.hybris.platform.customerreview.exception;

public lass InCorrectRatingExcetion extends Exception{  
	public InCorrectRatingExcetion() { 
		super("Rating can not be minus Exception"); 
	}
	
	public InCorrectRatingExcetion(String s){  
		  super(s);  
	}	
}
