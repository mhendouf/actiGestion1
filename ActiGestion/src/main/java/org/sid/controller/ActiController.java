package org.sid.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.sid.entity.Acti;
import org.sid.entity.Benevole;
import org.sid.exception.RessourceNotFoundException;
import org.sid.repository.ActiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class ActiController {
	@Autowired
	ActiRepository actiRepository;

	@GetMapping("/acti")
	public List<Acti> getAllBenevole() {
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
	public Acti AjouterActi(@RequestParam("acti") Acti acti) {
		return actiRepository.save ( acti );
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
			/*
			 * Client ar = Clientinfo.get ( ); ar.setCode ( arti.getCode ( ) );
			 * ar.setLibelle ( arti.getLibelle ( ) ); ar.setFodec ( arti.getFodec ( ) );
			 * ar.setId_cat ( arti.getId_cat ( ) ); ar.setId_scat ( arti.getId_scat ( ) );
			 * ar.setPa ( arti.getPa ( ) ); ar.setPv ( arti.getPv ( ) ); ar.setTva (
			 * arti.getTva ( ) ); ar.setDc ( arti.getDc ( ) );
			 */
			return new ResponseEntity<> ( actiRepository.save ( acti ) , HttpStatus.OK );
		} else
			return new ResponseEntity<> ( HttpStatus.NOT_FOUND );
	}
}
