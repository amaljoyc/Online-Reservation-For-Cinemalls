package com.cinemall.Accessories;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {
	public static boolean validPhoneNo(String phn) {
		boolean val =false;
		if((phn.length()==10)){
			  try{
			        Long.parseLong(phn);
			        val=true;
			    } catch(NumberFormatException nfe) {
			        
			    }
		}
		return val;
	}

	public static boolean validEmailId(String email) {
		boolean val=true;
		//Set the email pattern string
	      Pattern p = Pattern.compile(".+@.+\\.[a-z]+");

	      //Match the given string with the pattern
	      Matcher m = p.matcher(email);

	      //check whether match is found 
	      boolean matchFound = m.matches();

	      if (!matchFound){
	       val=false;
	      }
		return val;
	}
	
	public static int[] splitRowAndColumn(int tot){
		int[] rac={0,0};
		rac[0]=(int) Math.sqrt(tot);
		rac[1]=rac[0];
		while(rac[1]>0){
			int pro = rac[1]*rac[0];
			if(pro==tot){
				break;
				}
			else if(pro<tot){
				rac[0]++;
			}else{
				rac[1]--;
			}
		}
		
		return rac; 
	}
}
