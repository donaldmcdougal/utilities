package test.com.schneider.utils.crypto;

import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;

import org.apache.sis.internal.jdk7.StandardCharsets;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.schneider.utils.crypto.EC;

public class ECTest {

	private static File cipherFile;
	private static File plaintextFile;
	private static EC ecc;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		cipherFile = Paths.get("src", "test", "resources", "cipher.txt").toFile();
		plaintextFile = Paths.get("src", "test", "resources", "plain.txt").toFile();
		ecc = new EC();
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
			kpg = ecc.createKeyPairGenerator();
			assertNotNull(kpg);
		} catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testSignPrivateKeyByteArray() {
		KeyPairGenerator kpg;
		try {
			kpg = ecc.createKeyPairGenerator();
			assertNotNull(kpg);
			KeyPair kp = kpg.generateKeyPair();
			PrivateKey privK = kp.getPrivate();
			byte[] signature = ecc.sign(privK, "somethingtosign".getBytes());
			assertNotNull(signature);
			assertTrue(signature.length > 0);
		} catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException | InvalidKeyException | SignatureException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testVerifyPublicKeyByteArrayByteArray() {
		KeyPairGenerator kpg;
		try {
			kpg = ecc.createKeyPairGenerator();
			assertNotNull(kpg);
			KeyPair kp = kpg.generateKeyPair();
			PrivateKey privK = kp.getPrivate();
			PublicKey pubK = kp.getPublic();
			byte[] toSign = "somethingtosign".getBytes(StandardCharsets.UTF_8);
			byte[] signature = ecc.sign(privK, toSign);
			assertTrue(ecc.verify(pubK, toSign, signature));
		} catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException | InvalidKeyException | SignatureException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testCreateKeyAgreement() {
		KeyPairGenerator kpg;
		try {
			kpg = ecc.createKeyPairGenerator();
	        KeyPair kpa = kpg.generateKeyPair();
	        PrivateKey privka = kpa.getPrivate();
	        PublicKey pubka = kpa.getPublic();
	        KeyPair kpb = kpg.generateKeyPair();
	        PrivateKey privkb = kpb.getPrivate();
	        PublicKey pubkb = kpb.getPublic();
	        KeyAgreement kaa = ecc.createKeyAgreement(privka, pubkb);
	        SecretKey ska = ecc.generateSecret(kaa);
	        KeyAgreement kab = ecc.createKeyAgreement(privkb, pubka);
	        SecretKey skb = ecc.generateSecret(kab);
	        assertArrayEquals(ska.getEncoded(), skb.getEncoded());
		} catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException | InvalidKeyException | InvalidKeySpecException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}   
	}

}
