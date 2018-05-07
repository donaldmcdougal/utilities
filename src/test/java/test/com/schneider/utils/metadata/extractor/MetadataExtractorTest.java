package test.com.schneider.utils.metadata.extractor;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.tika.exception.TikaException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.schneider.utils.metadata.extractor.MetadataExtractor;

public class MetadataExtractorTest {

	private static MetadataExtractor extractor;
	private static Path mp3;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		extractor = new MetadataExtractor();
		mp3 = Paths.get("src", "test", "resources", "guapo.mp3");
	}

	@Test
	public void testParseToHTML() {
		try {
			String html = extractor.parseToHTML(mp3.toFile());
			System.out.println(html);
		} catch (IOException | SAXException | TikaException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testParseToJSON() {
		try {
			String html = extractor.parseToJSON(mp3.toFile());
			System.out.println(html);
		} catch (IOException | SAXException | TikaException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
