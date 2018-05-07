/**
 * 
 */
package com.schneider.utils.compression;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

/**
 * An interface for compressing things.
 * @author Donald McDougal
 *
 */
public interface Compressor {

	/**
	 * Compresses an input stream.
	 * @param input The input stream to compress.
	 * @param output The output stream to compress.
	 * @throws IOException
	 */
	public void compress(InputStream input, OutputStream output) throws IOException;
	
	/**
	 * Compresses a file.
	 * @param toCompress The file to compress.
	 * @param compressed The compressed file.
	 * @throws IOException
	 */
	public void compressFile(Path toCompress, Path compressed) throws IOException;
	
	/**
	 * Decompresses an input stream.
	 * @param input The input stream to decompress.
	 * @param output The output stream to decompress.
	 * @throws IOException
	 */
	public void decompress(InputStream input, OutputStream output) throws IOException;
	
	/**
	 * Decompresses a file.
	 * @param toDecompress The file to decompress.
	 * @param decompressed The decompressed file.
	 * @throws IOException
	 */
	public void decompressFile(Path toDecompress, Path decompressed) throws IOException;
}
