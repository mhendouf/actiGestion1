package org.sid.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {

	private final Path root = Paths.get ( "uploads" );

	@Override
	public void init() {

		try {
			Files.createDirectory ( root );
		} catch (IOException e) {
			throw new RuntimeException ( "Could not initialize folder for upload!" );
		}
	}

	@Override
	public void initRoot(String root) {
		Path rootbenevole = Paths.get ( root );
		try {
			Files.createDirectory ( rootbenevole );
		} catch (IOException e) {
			throw new RuntimeException ( "Could not initialize folder for upload!" );
		}
	}

	@Override
	public void save(MultipartFile file, String root) {
		Path rootbenevole = Paths.get ( root );
		try {
			Files.copy ( file.getInputStream ( ) , rootbenevole.resolve ( file.getOriginalFilename ( ) ) );
		} catch (Exception e) {
			throw new RuntimeException ( "Could not store the file. Error: " + e.getMessage ( ) );
		}
	}

	@Override
	public Resource load(String filename, String rootB) {
		try {
			Path rootbenevole = Paths.get ( rootB );
			Path file = rootbenevole.resolve ( filename );
			Resource resource = new UrlResource ( file.toUri ( ) );

			if (resource.exists ( ) || resource.isReadable ( )) {
				return resource;
			} else {
				throw new RuntimeException ( "Could not read the file!" );
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException ( "Error: " + e.getMessage ( ) );
		}
	}

	@Override
	public void deleteAll(String root) {
		Path rootbenevole = Paths.get ( root );
		FileSystemUtils.deleteRecursively ( rootbenevole.toFile ( ) );
	}

	@Override
	public Stream<Path> loadAll(String root) {
		Path rootbenevole = Paths.get ( root );
		try {
			return Files.walk ( rootbenevole , 1 ).filter ( path -> !path.equals ( rootbenevole ) )
					.map ( rootbenevole::relativize );
		} catch (IOException e) {
			throw new RuntimeException ( "Could not load the files!" );
		}
	}
}