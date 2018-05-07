/**
 * 
 */
package test.com.schneider.utils.archiving;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import com.schneider.utils.archiving.ZipArchiver;

/**
 * @author John Schneider
 *
 */
public class ZipArchiverTest {
	
	private static ZipArchiver archiver;
	private static Path resourcesPath;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		archiver = new ZipArchiver();
		resourcesPath = Paths.get("src", "test", "resources");
	}

	@Test
	public void testArchiveDirectory() {
		if (!resourcesPath.toFile().exists()) {
			fail("Resources directory does not exist!");
		}
		
		Path outPath = resourcesPath.resolve("out.zip");
		
		try {
			archiver.archiveDirectory(resourcesPath, outPath);
		} catch (IOException | ArchiveException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
		assertTrue(outPath.toFile().exists());
		try {
			Files.delete(outPath);
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testExtractArchive() {
		if (!resourcesPath.toFile().exists()) {
			fail("Resources directory does not exist!");
		}
		
		Path outPath = resourcesPath.resolve("out.zip");
		
		try {
			archiver.archiveDirectory(resourcesPath, outPath);
		} catch (IOException | ArchiveException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
		Path outDir = resourcesPath.resolve("out");
		try {
			archiver.extractArchive(outPath, outDir);
		} catch (IOException | ArchiveException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
		assertTrue(outDir.toFile().exists());
		
		try {
			FileUtils.deleteDirectory(outDir.toFile());
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
