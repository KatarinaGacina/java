package hr.fer.oprpp1.hw05.crypto;

/**
 * Class that work with hex to byte and byte to hex transitions.
 * @author Katarina Gacina
 *
 */
public class Util {

	/**
	 * Function returns byte array that represents given hex-encoded String value.
	 * It supports both uppercase and lowercase letters.
	 * @param keyText hex value
	 * @return byte[] byte array that represents given hex value
	 * @throws IllegalArgumentException if the given String reference id null 
	 * 									or if its length is even or if it contains invalid characters
	 */
	public static byte[] hextobyte(String keyText) {
		if (keyText == null || keyText.length() % 2 != 0) throw new IllegalArgumentException();
		
		if (keyText.length() == 0) return new byte[0];
		
		byte[] text = new byte[keyText.length() / 2];
		
		int first;
		int second;
		for (int i = 0; i < keyText.length(); i += 2) {
			first = hexCharToInt(keyText.charAt(i));
			second = hexCharToInt(keyText.charAt(i + 1));
			
			text[i/2] = (byte) ((first << 4) + second);
		}

		return text;
	}
	
	/**
	 * Function returns int value of provided hex char.
	 * @param value hex char
	 * @return int value
	 * @throws IllegalArgumentException if char not in intervals: 0-9, a-f, A-F
	 */
	private static int hexCharToInt(char value) {
		if (value >= '0' && value <= '9') {
			return (byte) (value - '0');
		} else if (value >= 'a' && value <= 'f') {
			return (byte) ((value - 'a') + 10);
		} else if (value >= 'A' && value <= 'F') {
			return (byte) ((value - 'A') + 10);
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Function returns hex-encoded String value from the given byte array.
	 * It uses lowercase letters.
	 * @param bytearray given byte array
	 * @return String hex-encoded String value
	 * @throws IllegalArgumentException if the given byte array is null
	 */
	public static String bytetohex(byte[] bytearray) {
		if (bytearray == null) throw new IllegalArgumentException();
		
		if (bytearray.length == 0) return "";		
		
		StringBuilder tekst = new StringBuilder();
		
		for (byte b : bytearray) {
			tekst.append(String.format("%02x", b));
		}
		
		return tekst.toString();
	}

}
