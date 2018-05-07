/**
 * 
 */
package com.schneider.utils.metadata.mimetype;

import java.io.File;
import java.io.IOException;

import org.apache.tika.Tika;

/**
 * Detects MIME types.
 * @author Donald McDougal
 *
 */
public class MimeTypeDetector {

	private Tika tika;
	
	/**
	 * Creates a new MimeTypeDetector.
	 */
	public MimeTypeDetector() {
		this.tika = new Tika();
	}
	
	/**
	 * Detects the MIME type of the given file.
	 * @param file The file whose MIME type to detect.
	 * @return The detected MIME type.
	 * @throws IOException
	 */
	public String detectMimeType(File file) throws IOException {
		return this.tika.detect(file);
	}
}
