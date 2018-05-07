/**
 * 
 */
package com.schneider.utils.geoip;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;

/**
 * Locates an IP address's geography.
 * 
 * @author John Schneider
 *
 */
public class IPLocator {

	private DatabaseReader reader;
	
	/**
	 * Creates a new IPLocator using the DatabaseReader object.
	 * @param reader The DatabaseReader object.
	 */
	public IPLocator(DatabaseReader reader) {
		this.reader = reader;
	}
	
	/**
	 * Gets the geographical location of an InetAddress.
	 * @param address The InetAddress to locate.
	 * @return The CityResponse.
	 * @throws GeoIp2Exception 
	 * @throws IOException 
	 */
	public CityResponse locate(InetAddress address) throws IOException, GeoIp2Exception {
		return this.reader.city(address);
	}
	
	/**
	 * Gets the geographical location of a string IP address.
	 * @param address The IP address to locate.
	 * @return The CityResponse.
	 * @throws UnknownHostException
	 * @throws IOException
	 * @throws GeoIp2Exception
	 */
	public CityResponse locate(String address) throws UnknownHostException, IOException, GeoIp2Exception {
		return this.locate(InetAddress.getByName(address));
	}
}
