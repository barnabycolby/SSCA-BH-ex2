package ex2.Interface;

public class CommonFunctions {
	public static String repeatedCharacterString(String string, int count) {
		String returnString = "";
		for (int i = 0; i < count; i++) {
			returnString += string;
		}
		return returnString;
	}
	
	public static String padString(String theString, int paddingLength) {
		int paddingPrefixLength = paddingLength / 2;
		int paddingSuffixLength = paddingLength - paddingPrefixLength;
		String paddingPrefix = repeatedCharacterString(" ", paddingPrefixLength);
		String paddingSuffix = repeatedCharacterString(" ", paddingSuffixLength);
		return paddingPrefix + theString + paddingSuffix;
	}
}
