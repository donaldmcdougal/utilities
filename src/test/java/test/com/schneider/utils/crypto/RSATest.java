package test.com.schneider.utils.crypto;

import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.SignatureException;

import org.apache.sis.internal.jdk7.StandardCharsets;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.schneider.utils.crypto.RSA;

public class RSATest {
	
	private static File cipherFile;
	private static File plaintextFile;
	private static RSA rsa;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		cipherFile = Paths.get("src", "test", "resources", "cipher.txt").toFile();
		plaintextFile = Paths.get("src", "test", "resources", "plain.txt").toFile();
		rsa = new RSA();
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
	public void testCreateKeyPairGenerator() {
		KeyPairGenerator kpg;
		try {
			kpg = rsa.createKeyPairGenerator();
			assertNotNull(kpg);
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testSignPrivateKeyByteArray() {
		KeyPairGenerator kpg;
		try {
			kpg = rsa.createKeyPairGenerator();
			assertNotNull(kpg);
			KeyPair kp = kpg.generateKeyPair();
			PrivateKey privK = kp.getPrivate();
			byte[] signature = rsa.sign(privK, "somethingtosign".getBytes());
			assertNotNull(signature);
			assertTrue(signature.length > 0);
		} catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException | SignatureException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testVerifyPublicKeyByteArrayByteArray() {
		KeyPairGenerator kpg;
		try {
			kpg = rsa.createKeyPairGenerator();
			assertNotNull(kpg);
			KeyPair kp = kpg.generateKeyPair();
			PrivateKey privK = kp.getPrivate();
			PublicKey pubK = kp.getPublic();
			byte[] toSign = "somethingtosign".getBytes(StandardCharsets.UTF_8);
			byte[] signature = rsa.sign(privK, toSign);
			assertTrue(rsa.verify(pubK, toSign, signature));
		} catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException | SignatureException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
