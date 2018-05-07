package test.com.schneider.utils.compression;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.schneider.utils.compression.XZCompressor;

public class XZCompressorTest {

	private static Path resourcesDirectory;
	private static XZCompressor compressor;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		resourcesDirectory = Paths.get("src", "test", "resources");
		compressor = new XZCompressor();
	}
	
	@Before
	public void setUp() throws Exception {
		if (!resourcesDirectory.toFile().exists()) {
			fail("Resources directory does not exist!");
		}
	}

	@After
	public void tearDown() throws Exception {
		if (resourcesDirectory.resolve("big_buck_bunny.mp4.xz").toFile().exists()) {
			resourcesDirectory.resolve("big_buck_bunny.mp4.xz").toFile().delete();
		}
		if (resourcesDirectory.resolve("big_buck_bunny_2.mp4").toFile().exists()) {
			resourcesDirectory.resolve("big_buck_bunny_2.mp4").toFile().delete();
		}
	}

	@Test
	public void testCompress() {
		File outFile = resourcesDirectory.resolve("big_buck_bunny.mp4.xz").toFile();
		if (outFile.exists()) {
			fail("Output file already exists.");
		}
		File inFile = resourcesDirectory.resolve("big_buck_bunny.mp4").toFile();
		FileOutputStream outStream;
		FileInputStream inStream;
		try {
			outStream = new FileOutputStream(outFile);
			inStream = new FileInputStream(inFile);
			compressor.compress(inStream, outStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testCompressFile() {
		Path in = resourcesDirectory.resolve("big_buck_bunny.mp4");
		Path out = resourcesDirectory.resolve("big_buck_bunny.mp4.xz");
		try {
			compressor.compressFile(in, out);
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testDecompress() {
		File outFile = resourcesDirectory.resolve("big_buck_bunny.mp4.xz").toFile();
		if (outFile.exists()) {
			fail("Output file already exists.");
		}
		File inFile = resourcesDirectory.resolve("big_buck_bunny.mp4").toFile();
		FileOutputStream outStream;
		FileInputStream inStream;
		try {
			outStream = new FileOutputStream(outFile);
			inStream = new FileInputStream(inFile);
			compressor.compress(inStream, outStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
		try {
			File newOutFile = resourcesDirectory.resolve("big_buck_bunny_2.mp4").toFile();
			outStream = new FileOutputStream(newOutFile);
			inStream = new FileInputStream(outFile);
			compressor.decompress(inStream, outStream);
			if (!resourcesDirectory.resolve("big_buck_bunny_2.mp4").toFile().exists()) {
				fail("Output file does not exist.");
			}
			if (newOutFile.length() != inFile.length()) {
				fail("Decompressed file length does not equal original file length.");
			}
			
			byte[] originalBytes = FileUtils.readFileToByteArray(inFile);
			byte[] decompressedBytes = FileUtils.readFileToByteArray(newOutFile);
			assertArrayEquals(originalBytes, decompressedBytes);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testDecompressFile() {
		Path in = resourcesDirectory.resolve("big_buck_bunny.mp4");
		Path out = resourcesDirectory.resolve("big_buck_bunny.mp4.xz");
		try {
			compressor.compressFile(in, out);
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
		Path inPath = resourcesDirectory.resolve("big_buck_bunny.mp4.xz");
		out = resourcesDirectory.resolve("big_buck_bunny_2.mp4");
		try {
			compressor.decompressFile(inPath, out);
			byte[] originalBytes = FileUtils.readFileToByteArray(in.toFile());
			byte[] decompressedBytes = FileUtils.readFileToByteArray(out.toFile());
			assertArrayEquals(originalBytes, decompressedBytes);
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
