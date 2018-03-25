package com.schneider.utils.metadata.audio;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.tika.exception.TikaException;
import org.apache.tika.parser.mp3.ID3Tags;
import org.apache.tika.parser.mp3.ID3v1Handler;
import org.apache.tika.parser.mp3.ID3v22Handler;
import org.apache.tika.parser.mp3.ID3v23Handler;
import org.apache.tika.parser.mp3.ID3v24Handler;
import org.apache.tika.parser.mp3.ID3v2Frame;
import org.apache.tika.parser.mp3.MP3Frame;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import com.schneider.utils.metadata.image.ImageFormat;

/**
 * Gets metadata from Mp3s.
 * @author Donald McDougal
 *
 */
public class Mp3Detector {
	
	private ContentHandler handler;
	
	/**
	 * Creates a new Mp3Detector.
	 */
	public Mp3Detector() {
		this.handler = new DefaultHandler();
	}
	
	/**
	 * Parses an Mp3 file and gets its ID3 tags.
	 * @param file The file to parse.
	 * @return The file's ID3 tags.
	 * @throws TikaException 
	 * @throws SAXException 
	 * @throws IOException 
	 */
	public ID3Tags parse(File file) throws IOException, SAXException, TikaException {
		InputStream input = new FileInputStream(file);
		MP3Frame frame = ID3v2Frame.createFrameIfPresent(input);
		if (frame instanceof ID3v2Frame) {
			ID3v2Frame id3F = (ID3v2Frame)frame;
			if (id3F.getMajorVersion() == 4) {
				input.close();
				return new ID3v24Handler(id3F);
            } else if(id3F.getMajorVersion() == 3) {
        		input.close();
            	return new ID3v23Handler(id3F);
            } else if(id3F.getMajorVersion() == 2) {
        		input.close();
            	return new ID3v22Handler(id3F);
            } else {
            	input.close();
            	return null;
            }
		}
		else {
			ID3v1Handler v1 = new ID3v1Handler(input, this.handler);
			input.close();
			return v1;
		}
	}
	
	/**
	 * Gets the artwork for a file as a byte array.
	 * @param file The file whose artwork to retrieve.
	 * @return The byte array representing the image.
	 * @throws UnsupportedTagException
	 * @throws InvalidDataException
	 * @throws IOException
	 */
	public byte[] getArtwork(File file) throws UnsupportedTagException, InvalidDataException, IOException {
		Mp3File song = new Mp3File(file.getAbsolutePath());
		if (song.hasId3v2Tag()) {
			ID3v2 tag = song.getId3v2Tag();
			return tag.getAlbumImage();
		}
		else {
			return null;
		}
	}
	
	/**
	 * Writes artwork to a file.
	 * @param inputFile The MP3 file whose artwork we want to retrieve.
	 * @param outputFile The artwork output file.
	 * @param format The format of the image.
	 * @throws IOException
	 * @throws UnsupportedTagException
	 * @throws InvalidDataException
	 */
	public void writeArtwork(File inputFile, File outputFile, ImageFormat format) throws IOException, UnsupportedTagException, InvalidDataException {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(this.getArtwork(inputFile));
        BufferedImage img = ImageIO.read(inputStream);
        inputStream.close();
        switch (format) {
        	case PNG:
                ImageIO.write(img, "PNG", outputFile);
                break;
        	case JPEG:
                ImageIO.write(img, "JPEG", outputFile);
                break;
        	case GIF:
                ImageIO.write(img, "GIF", outputFile);
                break;
        	case BMP:
                ImageIO.write(img, "BMP", outputFile);
                break;
        	default:
        		throw new IOException("Unrecognized image format.");
        }
	}
}
