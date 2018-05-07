/**
 * 
 */
package com.schneider.utils.archiving;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;

/**
 * Archives things into tar archives.
 * @author Donald McDougal
 *
 */
public class TarArchiver implements Archiver {

	/* (non-Javadoc)
	 * @see com.schneider.utils.archiving.Archiver#archiveDirectory(java.nio.file.Path, java.nio.file.Path)
	 */
	public void archiveDirectory(final Path directory, final Path archived) throws IOException, ArchiveException {
		
		FileOutputStream fout = new FileOutputStream(archived.toFile());
		BufferedOutputStream bout = new BufferedOutputStream(fout);
		final ArchiveOutputStream stream = new TarArchiveOutputStream(bout);
		((TarArchiveOutputStream) stream).setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);
		
		Files.walkFileTree(directory, new SimpleFileVisitor<Path>()
	    {
			/*
			 * (non-Javadoc)
			 * @see java.nio.file.SimpleFileVisitor#visitFile(java.lang.Object, java.nio.file.attribute.BasicFileAttributes)
			 */
	        @Override
	        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
	        {
	        	//make sure not to archive the archive file.
	        	if (!file.toAbsolutePath().toFile().getAbsolutePath().equals(archived.toAbsolutePath().toFile().getAbsolutePath())) {
		        	TarArchiveEntry entry = new TarArchiveEntry(directory.relativize(file).toString());
		        	byte[] fileBytes = Files.readAllBytes(file);
		        	entry.setSize(fileBytes.length);
		        	stream.putArchiveEntry(entry);
		        	stream.write(fileBytes);
		        	stream.closeArchiveEntry();
	        	}
	            return FileVisitResult.CONTINUE;
	        }
	    });
		
		stream.close();
		bout.close();
		fout.close();
	}

	/*
	 * (non-Javadoc)
	 * @see com.schneider.utils.archiving.Archiver#extractArchive(java.nio.file.Path, java.nio.file.Path)
	 */
	public void extractArchive(Path archived, Path extractDirectory)
			throws IOException, ArchiveException {
		FileInputStream fin = new FileInputStream(archived.toFile());
		BufferedInputStream bin = new BufferedInputStream(fin);
		ArchiveInputStream str = new TarArchiveInputStream(bin);
		ArchiveEntry entry = str.getNextEntry();
		while (entry != null) {
			byte[] content = new byte[(int) entry.getSize()];
			str.read(content, 0, (int) entry.getSize());
			Path extractLocation = extractDirectory.resolve(entry.getName());
			extractLocation.toFile().getParentFile().mkdirs();
			Files.write(extractLocation, content);
			entry = str.getNextEntry();
		}
		
		str.close();
		bin.close();
		fin.close();
	}

}
