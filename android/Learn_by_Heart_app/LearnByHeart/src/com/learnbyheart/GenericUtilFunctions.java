package com.learnbyheart;

import java.util.List;

public class GenericUtilFunctions<T> {
	
	/**
	 * @param list
	 * @return String[] with toString elements of each element of the list
	 */
	public String[] getListToString(List<T> list){
		String[] names = new String[list.size()];
		for(int i=0; i<list.size(); i++){
			names[i] = list.get(i).toString();
		}
		return names;
	}
}
