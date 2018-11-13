package org.eclipse.basyx.aas.impl.provider.filesystem.filesystem.impl;

import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.basyx.aas.impl.provider.filesystem.filesystem.File;
import org.eclipse.basyx.aas.impl.provider.filesystem.filesystem.FileSystem;
import org.eclipse.basyx.aas.impl.provider.filesystem.filesystem.FileType;

/**
 * Implements a generic file system
 * 
 * @author schnicke
 *
 */
public class GenericFileSystem implements FileSystem {

	@Override
	public String readFile(String path) throws Exception {
		return new String(Files.readAllBytes(getPath(path)), StandardCharsets.UTF_8);
	}

	@Override
	public void writeFile(String path, String content) throws Exception {
		FileWriter f = new FileWriter(path, false);
		f.write(content);
		f.close();
	}

	@Override
	public void deleteFile(String path) throws Exception {
		Files.deleteIfExists(getPath(path));
	}

	@Override
	public void createDirectory(String path) throws Exception {
		Files.createDirectories(getPath(path));
	}

	@Override
	public List<File> readDirectory(String path) throws Exception {
		List<File> files = new ArrayList<>();
		files.addAll(Files.list(getPath(path)).filter(Files::isRegularFile).map(p -> new File(p.toString().replace("\\", "/"), FileType.DATA)).collect(Collectors.toList()));

		files.addAll(Files.list(getPath(path)).filter(Files::isDirectory).map(p -> new File(p.toString().replace("\\", "/"), FileType.DIRECTORY)).collect(Collectors.toList()));

		return files;
	}

	@Override
	public void deleteDirectory(String path) throws Exception {
		try {
			Files.walk(getPath(path)).sorted(Comparator.reverseOrder()).map(p -> p.toFile()).forEach(f -> f.delete());
		} catch (NoSuchFileException e) {
		}
	}

	private Path getPath(String path) {
		return FileSystems.getDefault().getPath(path);
	}

}
