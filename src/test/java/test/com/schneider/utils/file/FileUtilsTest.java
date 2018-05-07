/**
 * 
 */
package test.com.schneider.utils.file;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.schneider.utils.file.FileUtils;

/**
 * @author John Schneider
 *
 */
public class FileUtilsTest {
	
	private static FileUtils utils;
	private static Path dirToCopy;
	private static Path copiedDir;
	private static Path moviePath;
	private static Path poemPath;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		utils = new FileUtils();
		dirToCopy = Paths.get("src", "test", "resources");
		copiedDir = Paths.get("src", "test", "resources_copy");
		moviePath = Paths.get("src", "test", "resources", "big_buck_bunny.mp4");
		poemPath = Paths.get("src", "test", "resources", "kublakhan.txt");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		if (copiedDir.toFile().exists()) {
			utils.deleteRecursively(copiedDir);
		}
	}

	/**
	 * Test method for {@link com.schneider.utils.file.FileUtils#copyDirectory(java.nio.file.Path, java.nio.file.Path)}.
	 */
	@Test
	public void testCopyDirectory() {
		assertTrue(utils.copyDirectory(dirToCopy, copiedDir));
		assertTrue(copiedDir.toFile().exists());
		try {
			assertEquals(4, Files.list(copiedDir).collect(Collectors.toList()).size());
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link com.schneider.utils.file.FileUtils#deleteRecursively(java.nio.file.Path)}.
	 */
	@Test
	public void testDeleteRecursively() {
		utils.copyDirectory(dirToCopy, copiedDir);
		assertTrue(copiedDir.toFile().exists());
		try {
			utils.deleteRecursively(copiedDir);
			assertFalse(copiedDir.toFile().exists());
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link com.schneider.utils.file.FileUtils#getFileChunkCount(long, int)}.
	 */
	@Test
	public void testGetFileChunkCount() {
		int chunks = utils.getFileChunkCount(moviePath.toFile().length(), 50);
		assertEquals(110218, chunks);
	}

	/**
	 * Test method for {@link com.schneider.utils.file.FileUtils#getFileExtension(java.nio.file.Path)}.
	 */
	@Test
	public void testGetFileExtension() {
		String ext = utils.getFileExtension(moviePath);
		assertEquals("mp4", ext);
	}

	/**
	 * Test method for {@link com.schneider.utils.file.FileUtils#hideFile(java.nio.file.Path)}.
	 */
	@Test
	public void testHideAndUnhideFile() {
		utils.hideFile(moviePath);
		assertFalse(moviePath.toFile().exists());
		utils.unhideFile(dirToCopy.resolve(".big_buck_bunny.mp4"));
		assertTrue(moviePath.toFile().exists());
	}

	/**
	 * Test method for {@link com.schneider.utils.file.FileUtils#joinFile(java.nio.file.Path, java.nio.file.Path, java.lang.String, boolean)}.
	 */
	@Test
	public void testSplitAndJoinFile() {
		try {
			utils.splitFile(poemPath, 100, dirToCopy, "poem");
			utils.joinFile(dirToCopy.resolve("poem.txt"), dirToCopy, "poem", true);
			assertEquals(org.apache.commons.io.FileUtils.readFileToString(poemPath.toFile(), StandardCharsets.UTF_8), org.apache.commons.io.FileUtils.readFileToString(dirToCopy.resolve("poem.txt").toFile(), StandardCharsets.UTF_8));
			assertTrue(poemPath.toFile().delete());
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link com.schneider.utils.file.FileUtils#listFilesRecursively(java.nio.file.Path)}.
	 */
	@Test
	public void testListFilesRecursively() {
		try {
			List<Path> files = utils.listFilesRecursively(dirToCopy);
			assertEquals(4, files.size());
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link com.schneider.utils.file.FileUtils#writeStringToFile(java.lang.String, java.nio.file.Path)}.
	 */
	@Test
	public void testWriteStringToFile() {
		Path fileToWrite = dirToCopy.resolve("temp.txt");
		assertTrue(utils.writeStringToFile("test", fileToWrite));
		try {
			assertEquals("test", org.apache.commons.io.FileUtils.readFileToString(fileToWrite.toFile(), StandardCharsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		assertTrue(fileToWrite.toFile().delete());
	}

}
