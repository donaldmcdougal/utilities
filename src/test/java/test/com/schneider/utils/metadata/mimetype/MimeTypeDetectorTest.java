/**
 * 
 */
package test.com.schneider.utils.metadata.mimetype;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.Test;

import com.schneider.utils.metadata.mimetype.MimeTypeDetector;

/**
 * @author John Schneider
 *
 */
public class MimeTypeDetectorTest {

	/**
	 * Test method for {@link com.schneider.utils.metadata.mimetype.MimeTypeDetector#detectMimeType(java.io.File)}.
	 */
	@Test
	public void testDetectMimeType() {
		MimeTypeDetector detector = new MimeTypeDetector();
		try {
			assertEquals("audio/mpeg", detector.detectMimeType(Paths.get("src", "test", "resources", "guapo.mp3").toFile()));
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
