/**
 * 
 */
package com.schneider.utils.metadata.extractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.serialization.JsonMetadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.ToXMLContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/**
 * For extracting the metadata from files.
 * @author Donald McDougal
 *
 */
public class MetadataExtractor {

	/**
	 * Parses the file to XHTML
	 * @param file The file to parse to XHTML.
	 * @return The string of XHTML extracted from the file.
	 * @throws IOException
	 * @throws SAXException
	 * @throws TikaException
	 */
	public String parseToHTML(File file) throws IOException, SAXException, TikaException {
	    ContentHandler handler = new ToXMLContentHandler();
	    InputStream stream = new FileInputStream(file);
	    AutoDetectParser parser = new AutoDetectParser();
	    Metadata metadata = new Metadata();
	    try {
	        parser.parse(stream, handler, metadata);
	        return handler.toString();
	    } finally {
	        stream.close();
	    }
	}
	
	/**
	 * Parses the file to JSON
	 * @param file The file to parse to JSON.
	 * @return The string of JSON extracted from the file.
	 * @throws IOException
	 * @throws SAXException
	 * @throws TikaException
	 */
	public String parseToJSON(File file) throws IOException, SAXException, TikaException {
	    ContentHandler handler = new ToXMLContentHandler();
	    InputStream stream = new FileInputStream(file);
	    AutoDetectParser parser = new AutoDetectParser();
	    Metadata metadata = new Metadata();
        StringWriter writer = new StringWriter();
	    try {
	        parser.parse(stream, handler, metadata);
	        JsonMetadata.toJson(metadata, writer);
	        return writer.toString();
	    } finally {
	        stream.close();
	        writer.close();
	    }
	}
}
