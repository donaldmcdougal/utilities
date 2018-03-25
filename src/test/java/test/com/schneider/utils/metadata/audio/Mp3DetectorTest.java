package test.com.schneider.utils.metadata.audio;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.tika.exception.TikaException;
import org.apache.tika.parser.mp3.ID3Tags;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import com.schneider.utils.metadata.audio.Mp3Detector;
import com.schneider.utils.metadata.image.ImageFormat;

public class Mp3DetectorTest {

	private static Mp3Detector detector;
	private static Path mp3;
	private static Path artworkPath;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		detector = new Mp3Detector();
		mp3 = Paths.get("src", "test", "resources", "guapo.mp3");
		artworkPath = Paths.get("src", "test", "resources", "cover.png");
	}

	@Test
	public void testParse() {
		try {
			ID3Tags tags = detector.parse(mp3.toFile());
			assertEquals(null, tags.getArtist());
		} catch (IOException | SAXException | TikaException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testWriteArtwork() {
		try {
			detector.writeArtwork(mp3.toFile(), artworkPath.toFile(), ImageFormat.PNG);
			assertTrue(artworkPath.toFile().exists());
			assertTrue(artworkPath.toFile().delete());
		} catch (UnsupportedTagException | InvalidDataException | IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
