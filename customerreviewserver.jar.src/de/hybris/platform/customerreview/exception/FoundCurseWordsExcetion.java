package de.hybris.platform.customerreview.exception;


public class FoundCurseWordsExcetion extends Exception{  
	public FooException() { 
		super("Found Curise Words Exception"); 
	}
	
	public FoundCurseWordsExcetion(String s){  
		  super("Found Curise Words Exception:" + s);  
	}	
	
	public FoundCurseWordsExcetion(List<String> s){  
		  super(super("Found Curise Words Exception:" + s.toString());
	}	
	
}
