package edu.stevens.cs555.utils;

public class Validate {

	public static boolean noNulls(Object... arguments) {
		for (Object obj : arguments) {
			if (obj == null) {
				return false;
			}
		}

		return true;
	}
	
	public static boolean allNulls(Object... arguments) {
		for (Object obj : arguments) {
			if (obj != null) {
				return false;
			}
		}

		return true;
	}
	
	public static boolean hasNulls(Object... arguments) {
		return !noNulls(arguments);
	}
	
	public static boolean noNullOrEmpty(boolean whiteSpaceisEmpty, String... arguments) {
		for (String str : arguments) {
			if(whiteSpaceisEmpty && str != null)
			{
				str=str.trim(); // force trim whitespace if being strict.
			}
			
			if (str == null || str.isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isNullOrEmpty(boolean whiteSpaceisEmpty, String... arguments) {
		return !noNullOrEmpty(whiteSpaceisEmpty,arguments);
	}
	
	
}
