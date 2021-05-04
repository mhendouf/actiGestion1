package org.sid.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.sid.entity.FileInfo;
import org.sid.repository.BenevoleRepository;
import org.sid.service.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@RestController
public class FilesController {

	@Autowired
	FilesStorageService storageService;
	@Autowired
	BenevoleRepository repository;

	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
			@RequestParam("benevole") String idb) {
		String message = "";
		System.out.println ( "POOOOOOOOOOOOOOOOOOOOOOSSSSSSSSSSSSSSSSSSTTTTTTTT : 0" + idb );
		try {
			String root = getRoot ( idb );

			storageService.save ( file , root );
			message = "Uploaded the file successfully: " + file.getOriginalFilename ( );
			return ResponseEntity.status ( HttpStatus.OK ).body ( message );
		} catch (Exception e) {
			message = "Could not upload the file: " + file.getOriginalFilename ( ) + "!";
			return ResponseEntity.status ( HttpStatus.EXPECTATION_FAILED ).body ( message );
		}
	}

	public String getRoot(String id) {
		Long i = Long.parseLong ( id );
		return repository.findById ( i ).get ( ).getRoot ( );
	}

	@GetMapping("/file/{id}")
	public ResponseEntity<List<FileInfo>> getListFiles(@PathVariable(value = "id") String id) {
		System.out.println ( "getttttttttttttttttttttttttttttttttt : " + id );
		String root = getRoot ( id );
		List<FileInfo> fileInfos = storageService.loadAll ( root ).map ( path -> {
			String filename = path.getFileName ( ).toString ( );
			String url = MvcUriComponentsBuilder
					.fromMethodName ( FilesController.class , "getFile" , path.getFileName ( ).toString ( ) ).build ( )
					.toString ( );

			return new FileInfo ( filename , url );
		} ).collect ( Collectors.toList ( ) );

		return ResponseEntity.status ( HttpStatus.OK ).body ( fileInfos );
	}

	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getFile(@PathVariable String filename) throws IOException {

		final String SEPARATEUR = "&%$§/(=";
		String mots[] = filename.split ( SEPARATEUR );
		String id = mots[0];
		filename = mots[1];
		String root = getRoot ( id );
		Resource file = storageService.load ( filename , root );
		System.out.println ( "apres Fooooooooooooooooooooooorrrrrrrrrrrrrr" );

		Path path = file.getFile ( ).toPath ( );

		return ResponseEntity.ok ( ).header ( HttpHeaders.CONTENT_TYPE , Files.probeContentType ( path ) )
				.header ( HttpHeaders.CONTENT_DISPOSITION , "attachment; filename=\"" + file.getFilename ( ) + "\"" )
				.body ( file );
	}
}