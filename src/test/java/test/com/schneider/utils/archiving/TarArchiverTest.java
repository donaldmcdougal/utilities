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
import org.junit.Test;

import com.schneider.utils.archiving.TarArchiver;

/**
 * @author John Schneider
 *
 */
public class TarArchiverTest {

	@Test
	public void testArchiveDirectory() {
		Path resourcesDirectory = Paths.get("src", "test", "resources");
		if (!resourcesDirectory.toFile().exists()) {
			fail("Resources directory does not exist!");
		}
		
		Path outPath = Paths.get("src", "test", "resources", "out.tar");
		
		TarArchiver archiver = new TarArchiver();
		
		try {
			archiver.archiveDirectory(resourcesDirectory, outPath);
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
		Path resourcesDirectory = Paths.get("src", "test", "resources");
		if (!resourcesDirectory.toFile().exists()) {
			fail("Resources directory does not exist!");
		}
		
		Path outPath = Paths.get("src", "test", "resources", "out.tar");
		
		TarArchiver archiver = new TarArchiver();
		
		try {
			archiver.archiveDirectory(resourcesDirectory, outPath);
		} catch (IOException | ArchiveException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
		Path outDir = Paths.get("src", "test", "resources", "out");
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
