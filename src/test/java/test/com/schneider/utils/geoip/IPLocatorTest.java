package test.com.schneider.utils.geoip;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Continent;
import com.maxmind.geoip2.record.Country;
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
	public void testLocateString() {
		try {
			CityResponse response = ipLocator.locate("50.26.60.91");
			Continent continent = response.getContinent();
			Country country = response.getCountry();
			City city = response.getCity();
		} catch (IOException | GeoIp2Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
