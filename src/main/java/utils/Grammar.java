package utils;

public class Grammar {
	
	public static String makeSecondPerson(String text){
	    String[] words = text.split(" ");
	    words[0] = words[0] + "s";
	    
	    StringBuilder builder = new StringBuilder();
	    for (String word : words){
	        builder.append(" ");
	        builder.append(word);
	    }
	    
	    return builder.toString().trim();
	}

}
