package hr.fer.oprpp1.hw05.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class that can encrypt, decrypt or checksum.
 * @author Katarina Gacina
 *
 */
public class Crypto {
	
	/**
	 * buffer size
	 */
	public static final int BLEN = 4 * 1024;
	
	/**
	 * Main program used for file checksum if args[0] equals "checksum", for file encryption if args[0] equals "encrypt", 
	 * for file decryption if args[0] equals "decrypt".
	 * Checksum uses SHA-256 algorithm. Encryption and decryption is based on symmetric crypto-algorithm AES.
	 * Checksum accepts one argument args[1], file path, and user is expected to provide expected sha-256 digest through console.
	 * Encryption or decryption accepts two arguments args[1], source file path, and args[2], destination file path, and 
	 * user is expected to provide password as hex-encoded text and initialization vector as hex-encoded text through console.
	 * @param args input arguments
	 * @throws IOException if problem occurs with reading from the file or writing to the file
	 * @throws NoSuchAlgorithmException if checksum algorithm does not exist
	 * @throws NoSuchPaddingException if requested padding mechanism is not available in the environment
	 * @throws InvalidKeyException if provided password for encryption or decryption is invalid
	 * @throws InvalidAlgorithmParameterException if provided initialization vector for encryption or decryption is invalid
	 * @throws IllegalBlockSizeException if the length of data provided to a block cipher is incorrect, i.e., does not match the block size of the cipher
	 * @throws BadPaddingException if the input data is not padded properly
	 * @throws IllegalArgumentException if provided hex-encoded arguments contain invalid characters
	 */
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException  {
		
		Scanner scanner = new Scanner(System.in);
		
		if (args[0].equals("checksha")) {
			System.out.println("Please provide expected sha-256 digest for " + args[1] + ":");
			System.out.print("> ");
			
			String expectedSha = null;
			if (scanner.hasNextLine()) { 
				expectedSha = scanner.nextLine();
			}
			
			Path p = Paths.get("C:\\Users\\User\\Documents\\5.semestar\\JAVA\\Labosi\\DZ5\\"+args[1]);
			try (InputStream ios = Files.newInputStream(p)) {
				
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				
				byte[] dataOneRead = new byte[BLEN];
				
				int num = 0;
				while ((num = ios.read(dataOneRead)) > 0) {
					md.update(dataOneRead, 0, num);
				}

				byte[] hash = md.digest();
				
				System.out.print("Digesting complete. ");
				
				if ((Util.bytetohex(hash)).equals(expectedSha)) {
					System.out.println("Digest of " + args[1] + " matches expected digest.");
				} else {
					System.out.println("Digest of " + args[1] + " does not match the expected digest. Digest was: " + Util.bytetohex(hash));
				}
			}
			
			
		} else if (args[0].equals("encrypt") || args[0].equals("decrypt")) {
			
			System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
			System.out.print("> ");
			
			String keyText = null;
			if (scanner.hasNextLine()) { 
				keyText = scanner.nextLine();
			}
			
			System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
			System.out.print("> ");
			
			String ivText = null;
			if (scanner.hasNextLine()) { 
				ivText = scanner.nextLine();
			}
			
			boolean encrypt = args[0].equals("encrypt");
			
			SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
			
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
			
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			{
				cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);

			}
			
			Path p_in = Paths.get("C:\\Users\\User\\Documents\\5.semestar\\JAVA\\Labosi\\DZ5\\"+args[1]);
			Path p_out = Paths.get("C:\\Users\\User\\Documents\\5.semestar\\JAVA\\Labosi\\DZ5\\"+args[2]);
			try (InputStream input = Files.newInputStream(p_in); OutputStream output = Files.newOutputStream(p_out)) {
				
				byte[] dataOneRead = new byte[BLEN];
				
				int num = 0;
				while ((num = input.read(dataOneRead)) != -1) {
					output.write(cipher.update(dataOneRead, 0, num));
					
				}
				
				output.write(cipher.doFinal());
				
			}
			
			if (encrypt) {
				System.out.println("Encryption completed. Generated file " + args[2] + " based on file " + args[1] + ".");
				
			} else {
				System.out.println("Decryption completed. Generated file " + args[2] + " based on file " + args[1] + ".");
				
			}
			
		}
		
		scanner.close();
		
	}
}
