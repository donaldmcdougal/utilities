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
import com.maxmind.geoip2.record.Location;
import com.maxmind.geoip2.record.Postal;
import com.maxmind.geoip2.record.Subdivision;
import com.maxmind.geoip2.record.Traits;
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
			CityResponse response = ipLocator.locate("50.26.60.92");
			
			Continent continent = response.getContinent();
			assertEquals("NA", continent.getCode());
			assertEquals("North America", continent.getName());
			
			Country country = response.getCountry();
			assertEquals("US", country.getIsoCode());
			assertEquals("United States", country.getName());
			
			City city = response.getCity();
			assertEquals("Amarillo", city.getName());
			
			Location location = response.getLocation();
			assertNotNull(location.getLongitude());
			assertNotNull(location.getLatitude());
			assertNotNull(location.getTimeZone());
			assertEquals("America/Chicago", location.getTimeZone());
			
			Postal postal = response.getPostal();
			assertNotNull(postal.getCode());
			
			Country registeredCountry = response.getRegisteredCountry();
			assertEquals("US", registeredCountry.getIsoCode());
			assertEquals("United States", registeredCountry.getName());
			
			Subdivision leastSpecificSubdivision = response.getLeastSpecificSubdivision();
			assertEquals("TX", leastSpecificSubdivision.getIsoCode());
			assertEquals("Texas", leastSpecificSubdivision.getName());
			
			Subdivision mostSpecificSubdivision = response.getMostSpecificSubdivision();
			assertEquals("TX", mostSpecificSubdivision.getIsoCode());
			assertEquals("Texas", mostSpecificSubdivision.getName());
			
			Traits traits = response.getTraits();
			assertEquals("50.26.60.92", traits.getIpAddress());
			assertFalse(traits.isAnonymous());
			assertFalse(traits.isAnonymousVpn());
			assertFalse(traits.isHostingProvider());
			assertFalse(traits.isLegitimateProxy());
			assertFalse(traits.isPublicProxy());
			assertFalse(traits.isTorExitNode());
			
		} catch (IOException | GeoIp2Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
