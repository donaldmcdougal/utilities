/**
 * 
 */
package com.schneider.utils.geoip;

import java.io.File;
import java.io.IOException;

import com.maxmind.geoip2.DatabaseReader;

/**
 * A factory for generating GeoIP2 database readers.
 * @author John Schneider
 *
 */
public class DatabaseReaderFactory {

	/**
	 * Creates a new GeoIP2 DatabaseReader.
	 * @param file The file to use for the database.  This should be a GeoIP2 City database file.
	 * @return A new DatabaseReader object.
	 * @throws IOException
	 */
	public static DatabaseReader createDatabaseReader(File file) throws IOException {
		DatabaseReader reader = new DatabaseReader.Builder(file).build();
		return reader;
	}
}
