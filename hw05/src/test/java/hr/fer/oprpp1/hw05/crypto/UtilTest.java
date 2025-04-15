package hr.fer.oprpp1.hw05.crypto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class UtilTest {
	
	@Test
	public void testHexToByteIllegal() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte(null));
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("111"));
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("11*"));
	}
	
	@Test
	public void testHexToByteEmpty() {
		assertArrayEquals(new byte[0], Util.hextobyte(""));
	}
	
	@Test
	public void testHexToByte() {
		assertArrayEquals(new byte[] {1, -82, 34}, Util.hextobyte("01aE22"));
	}
	
	@Test
	public void testByteToHexIllegal() {
		assertThrows(IllegalArgumentException.class, () -> Util.bytetohex(null));
	}
	
	@Test
	public void testByteToHexEmpty() {
		assertEquals("", Util.bytetohex(new byte[0]));
	}
	
	@Test
	public void testByteToHex() {
		assertEquals("01ae22", Util.bytetohex(new byte[] {1, -82, 34}));
	}
}
