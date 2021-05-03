package org.sid.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {
	public void init();

	public void initRoot(String root);

	public void save(MultipartFile file, String root);

	public Resource load(String filename, String rootB);

	public void deleteAll(String root);

	public Stream<Path> loadAll(String root);
}
