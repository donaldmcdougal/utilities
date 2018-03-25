/**
 * 
 */
package com.schneider.utils.archiving;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

/**
 * Archives things into zip archives.
 * @author Donald McDougal
 *
 */
public class ZipArchiver implements Archiver {
	
	/* (non-Javadoc)
	 * @see com.schneider.utils.archiving.Archiver#archiveDirectory(java.nio.file.Path, java.nio.file.Path)
	 */
	public void archiveDirectory(final Path directory, final Path archived)
			throws IOException, ArchiveException {

		FileOutputStream fout = new FileOutputStream(archived.toFile());
		BufferedOutputStream bout = new BufferedOutputStream(fout);
		final ArchiveOutputStream stream = new ZipArchiveOutputStream(bout);
		
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
		        	ZipArchiveEntry entry = new ZipArchiveEntry(directory.relativize(file).toString());
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
		
		final int BUFFER = 2048;
		BufferedOutputStream bout = null;
        BufferedInputStream bin = null;
        ZipEntry entry;
        ZipFile zipFile = new ZipFile(archived.toFile());
        Enumeration<?> e = zipFile.entries();
        while (e.hasMoreElements()) {
        	entry = (ZipEntry) e.nextElement();
        	bin = new BufferedInputStream(zipFile.getInputStream(entry));
        	int count;
        	byte data[] = new byte[BUFFER];
        	Path extractLocation = extractDirectory.resolve(entry.getName());
        	extractLocation.toFile().getParentFile().mkdirs();
        	FileOutputStream fout = new FileOutputStream(extractLocation.toFile());
        	bout = new BufferedOutputStream(fout, BUFFER);
        	while ((count = bin.read(data, 0, BUFFER)) != -1) {
        		bout.write(data, 0, count);
        	}
        	bout.flush();
        	bout.close();
        	fout.close();
        	bin.close();
        }
        zipFile.close();
	}

}
