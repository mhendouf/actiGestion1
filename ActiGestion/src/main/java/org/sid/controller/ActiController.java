package org.sid.controller;

import java.io.ByteArrayInputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.sid.entity.Acti;
import org.sid.entity.Benevole;
import org.sid.exception.RessourceNotFoundException;
import org.sid.repository.ActiRepository;
import org.sid.service.ExportActiPdf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class ActiController {
	@Autowired
	ActiRepository actiRepository;
	@Autowired
	ExportActiPdf exportPdfService;

	@GetMapping("/acti")
	public List<Acti> getAllActis() {
		System.out.println ( "Actiiiiiiiiiiiiiiiii" );
		return actiRepository.findAll ( );
	}

	@GetMapping("/acti/{id}")
	public ResponseEntity<Acti> getActiById(@PathVariable(value = "id") Long actiId) throws RessourceNotFoundException {
		Acti acti = actiRepository.findById ( actiId )
				.orElseThrow ( () -> new RessourceNotFoundException ( "Acti introuvable" ) );
		return ResponseEntity.ok ( ).body ( acti );
	}

	@GetMapping("/acti/search/{titre}")
	public List<Benevole> getSearch(@PathVariable(value = "titre") String titre) throws RessourceNotFoundException {
		// System.out.println ( actiRepository.findByTitre ( titre ) );
		List acti = actiRepository.findByTitre ( titre );
		return acti;
	}

	@PostMapping("/acti")
	public Acti AjouterActi(@RequestBody Acti info) {
		System.out.println ( "ajouter" );
		return actiRepository.save ( (Acti) info );
	}

	@DeleteMapping("/acti/{id}")
	public Map<String, Boolean> SupprimerActi(@PathVariable(value = "id") Long actiId)
			throws RessourceNotFoundException {

		Acti acti = actiRepository.findById ( actiId )
				.orElseThrow ( () -> new RessourceNotFoundException ( "Acti introuvable" ) );
		actiRepository.delete ( acti );
		Map<String, Boolean> map = new HashMap<> ( );
		map.put ( "Acti Supprimé" , Boolean.TRUE );
		return map;
	}

	@DeleteMapping("/acti/delete")
	public ResponseEntity<String> SupprimerBenevole() {
		System.out.println ( "Supprimer tous les actis" );

		actiRepository.deleteAll ( );
		return new ResponseEntity<> ( "Actis supprimés" , HttpStatus.OK );
	}

	@PutMapping("/acti/{id}")
	public ResponseEntity<Acti> ModifierActi(@PathVariable(value = "id") long id, @RequestParam("acti") String ac)
			throws JsonMappingException, JsonProcessingException {
		Acti acti = new ObjectMapper ( ).readValue ( ac , Acti.class );

		Optional<Acti> actiInfo = actiRepository.findById ( id );
		if (actiInfo.isPresent ( )) {

			return new ResponseEntity<> ( actiRepository.save ( acti ) , HttpStatus.OK );
		} else
			return new ResponseEntity<> ( HttpStatus.NOT_FOUND );
	}

	@PutMapping("/acti/pdf")
	public ResponseEntity<InputStreamResource> exportPdf(@RequestBody Collection<Acti> actis) {
		ByteArrayInputStream bais = exportPdfService.actiPDFreport ( getAllActis ( ) );
		HttpHeaders headers = new HttpHeaders ( );
		headers.add ( "Content-Disposition" , "filename=Actis.pdf" );
		return ResponseEntity.ok ( ).headers ( headers ).contentType ( MediaType.APPLICATION_PDF )
				.body ( new InputStreamResource ( bais ) );
	}
}
