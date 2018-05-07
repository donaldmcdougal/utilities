package test.com.schneider.utils.geoip;

import static org.junit.Assert.*;

import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

import com.maxmind.geoip2.DatabaseReader;
import com.schneider.utils.geoip.DatabaseReaderFactory;
import com.schneider.utils.geoip.IPLocator;

public class IPLocatorTest {

	private static IPLocator ipLocator;
	private static DatabaseReader reader;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		reader = DatabaseReaderFactory.createDatabaseReader(Paths.get("src", "test", "resources", "GeoLite2-City.mmdb").toFile());
		ipLocator = new IPLocator(reader);
	}

	@Test
	public void testLocateInetAddress() {
		fail("Not yet implemented");
	}

	@Test
	public void testLocateString() {
		fail("Not yet implemented");
	}

}