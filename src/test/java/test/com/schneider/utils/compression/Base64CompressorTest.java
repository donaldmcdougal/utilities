/**
 * 
 */
package test.com.schneider.utils.compression;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import com.schneider.utils.compression.Base64Compressor;

/**
 * @author John Schneider
 *
 */
public class Base64CompressorTest {

	private static Base64Compressor compressor;
	private static Path toCompressPath;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		compressor = new Base64Compressor();
		toCompressPath = Paths.get("src", "test", "resources", "kublakhan.txt");
	}

	@Test
	public void testEncodeStringsToStrings() {
		if (!toCompressPath.toFile().exists()) {
			fail("Resources directory does not exist!");
		}
		
		try {
			String toCompress = FileUtils.readFileToString(toCompressPath.toFile());
			String compressed = compressor.encodeToString(toCompress);
			String decompressed = compressor.decodeToString(compressed);
			assertEquals(toCompress, decompressed);
			
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testEncodeBytesToBytes() {
		if (!toCompressPath.toFile().exists()) {
			fail("Resources directory does not exist!");
		}
		
		try {
			byte[] toCompress = FileUtils.readFileToByteArray(toCompressPath.toFile());
			byte[] compressed = compressor.encode(toCompress);
			byte[] decompressed = compressor.decode(compressed);
			assertArrayEquals(toCompress, decompressed);
			
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testEncodeStringToBytes() {
		if (!toCompressPath.toFile().exists()) {
			fail("Resources directory does not exist!");
		}
		
		try {
			String toCompress = FileUtils.readFileToString(toCompressPath.toFile());
			byte[] compressed = compressor.encode(toCompress);
			String decompressed = compressor.decodeToString(compressed);
			assertEquals(toCompress, decompressed);
			
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testEncodeBytesToString() {
		if (!toCompressPath.toFile().exists()) {
			fail("Resources directory does not exist!");
		}
		
		try {
			byte[] toCompress = FileUtils.readFileToByteArray(toCompressPath.toFile());
			String compressed = compressor.encodeToString(toCompress);
			byte[] decompressed = compressor.decode(compressed);
			assertArrayEquals(toCompress, decompressed);
			
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
