/**
 * 
 */
package com.schneider.utils.compression;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Compresses and decompresses using Base64 encoding.
 * @author Donald McDougal
 *
 */
public class Base64Compressor {
	
	/**
	 * Decodes a base 64 encoded input.
	 * @param input The input to decode.
	 * @return The decoded input.
	 */
	public byte[] decode(byte[] input) {
		return Base64.getDecoder().decode(input);
	}
	
	/**
	 * Decodes a base 64 encoded input.
	 * @param input The input to decode.
	 * @return The decoded input.
	 */
	public byte[] decode(String input) {
		return Base64.getDecoder().decode(input);
	}
	
	/**
	 * Decodes a base 64 encoded input.
	 * @param input The input to decode.
	 * @return The decoded input.
	 */
	public String decodeToString(byte[] input) {
		return new String(this.decode(input), StandardCharsets.UTF_8);
	}
	
	/**
	 * Decodes a base 64 encoded input.
	 * @param input The input to decode.
	 * @return The decoded input.
	 */
	public String decodeToString(String input) {
		return new String(this.decode(input), StandardCharsets.UTF_8);
	}

	/**
	 * Encodes to base 64.
	 * @param input The input to encode.
	 * @return The encoded input.
	 */
	public byte[] encode(byte[] input) {
		return Base64.getEncoder().encode(input);
	}

	/**
	 * Encodes to base 64.
	 * @param input The input to encode.
	 * @return The encoded input.
	 */
	public byte[] encode(String input) {
		return this.encode(input.getBytes(StandardCharsets.UTF_8));
	}
	
	/**
	 * Encodes to base 64.
	 * @param input The input to encode.
	 * @return The encoded input.
	 */
	public String encodeToString(byte[] input) {
		return Base64.getEncoder().encodeToString(input);
	}
	
	/**
	 * Encodes to base 64.
	 * @param input The input to encode.
	 * @return The encoded input.
	 */
	public String encodeToString(String input) {
		return this.encodeToString(input.getBytes(StandardCharsets.UTF_8));
	}
}
