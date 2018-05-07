package test.com.schneider.utils.crypto;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.apache.cxf.helpers.FileUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.schneider.utils.crypto.AES;

public class AESTest {

	private static String dataString;
	private static byte[] data;
	private static File dataFile;
	private static File cipherFile;
	private static File plaintextFile;
	private static AES aes;
	private static char[] password;
    
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		dataString = "Here is some decrypted text.";
		data = dataString.getBytes(StandardCharsets.UTF_8);
		dataFile = Paths.get("src", "test", "resources", "kublakhan.txt").toFile();
		cipherFile = Paths.get("src", "test", "resources", "cipher.txt").toFile();
		plaintextFile = Paths.get("src", "test", "resources", "plain.txt").toFile();
		aes = new AES();
		password = "supersecretpassword".toCharArray();
	}

	@Before
	public void setUp() throws Exception {
		if (!Paths.get("src", "test", "resources").toFile().exists()) {
			fail("The test resources folder does not exist.");
		}
	}

	@After
	public void tearDown() throws Exception {
		if (cipherFile.exists()) {
			cipherFile.delete();
		}
		if (plaintextFile.exists()) {
			plaintextFile.delete();
		}
	}

	@Test
	public void testGenerateSecretKey() {
		SecretKey sk2;
		try {
			sk2 = aes.generateSecretKey(password);
			assertNotNull(sk2);
			assertEquals(32, sk2.getEncoded().length);
	    } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testDecrypt() {
		SecretKey sk2;
		try {
			sk2 = aes.generateSecretKey(password);
	        byte[] cipherText2 = aes.encrypt(sk2, data);
	        byte[] plainText2 = aes.decrypt(sk2, cipherText2);
	        String plainTextString2 = new String(plainText2, StandardCharsets.UTF_8);
	        assertEquals(dataString, plainTextString2);
		} catch (InvalidKeySpecException | NoSuchAlgorithmException | InvalidKeyException | NoSuchProviderException | 
				NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException | 
				BadPaddingException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testDecryptFile() {
		SecretKey sk2;
		try {
			sk2 = aes.generateSecretKey(password);
	        aes.encryptFile(sk2, dataFile, cipherFile);
	        aes.decryptFile(sk2, cipherFile, plaintextFile);
	        assertEquals(FileUtils.getStringFromFile(dataFile), FileUtils.getStringFromFile(plaintextFile));
		} catch (InvalidKeySpecException | NoSuchAlgorithmException | InvalidKeyException | NoSuchProviderException | 
				NoSuchPaddingException | InvalidAlgorithmParameterException | IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testEncrypt() {
		SecretKey sk2;
		try {
			sk2 = aes.generateSecretKey(password);
	        byte[] cipherText2 = aes.encrypt(sk2, data);
	        byte[] plainText2 = aes.decrypt(sk2, cipherText2);
	        String plainTextString2 = new String(plainText2, StandardCharsets.UTF_8);
	        assertEquals(dataString, plainTextString2);
		} catch (InvalidKeySpecException | NoSuchAlgorithmException | InvalidKeyException | NoSuchProviderException | 
				NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException | 
				BadPaddingException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testEncryptFile() {
		SecretKey sk2;
		try {
			sk2 = aes.generateSecretKey(password);
	        aes.encryptFile(sk2, dataFile, cipherFile);
	        aes.decryptFile(sk2, cipherFile, plaintextFile);
	        assertEquals(FileUtils.getStringFromFile(dataFile), FileUtils.getStringFromFile(plaintextFile));
		} catch (InvalidKeySpecException | NoSuchAlgorithmException | InvalidKeyException | NoSuchProviderException | 
				NoSuchPaddingException | InvalidAlgorithmParameterException | IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
