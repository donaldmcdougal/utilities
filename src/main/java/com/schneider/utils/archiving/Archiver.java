/**
 * 
 */
package com.schneider.utils.archiving;

import java.io.IOException;
import java.nio.file.Path;

import org.apache.commons.compress.archivers.ArchiveException;

/**
 * An interface for archiving things.
 * @author Donald McDougal
 *
 */
public interface Archiver {

	/**
	 * Archives a directory.
	 * @param directory The directory to archive.
	 * @param archived The archived file.
	 * @throws IOException
	 * @throws ArchiveException
	 */
	public void archiveDirectory(Path directory, Path archived) throws IOException, ArchiveException;
	
	/**
	 * Extracts an archive to the given directory.
	 * @param archived The archived file.
	 * @param extractDirectory The directory to which we will extracts the contents of the archive.
	 * @throws IOException
	 * @throws ArchiveException
	 */
	public void extractArchive(Path archived, Path extractDirectory) throws IOException, ArchiveException;
}
