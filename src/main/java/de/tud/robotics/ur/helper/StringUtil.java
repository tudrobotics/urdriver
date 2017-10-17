package de.tud.robotics.ur.helper;

public class StringUtil {

	public static String decapitalize(String string) {
		char c[] = string.toCharArray();
		c[0] = Character.toLowerCase(c[0]);
		return new String(c);
	}
}
