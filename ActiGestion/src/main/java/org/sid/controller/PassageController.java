package org.sid.controller;

import java.util.Date;
import java.util.List;

import org.sid.entity.Passage;
import org.sid.exception.RessourceNotFoundException;
import org.sid.repository.PassageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PassageController {
	@Autowired
	PassageRepository passageRepository;

	@GetMapping("/passage/{id}")
	public List<Passage> getAllActi(@PathVariable(value = "id") Long id) {
		System.out.println ( "PASSSAAAAAAAAAAAAAAAAAge : " + id );

		return passageRepository.findAll ( );
	}

	@PostMapping("/passage")
	public Passage savePassage(@RequestParam("id") String id) throws RessourceNotFoundException {

		Date now = new Date ( );
		Long lo = 7L;
		Passage p1 = passageRepository.findById ( lo )
				.orElseThrow ( () -> new RessourceNotFoundException ( "Benevole introuvable" ) );

		Long l = Long.parseLong ( id );
		List<Passage> passageListe = passageRepository.findPassages ( p1.getPassageDate ( ) , l );
		System.out.println ( "PASSSAAAAAAAAAAAAAAAAAge : " + passageListe.size ( ) );

		for (int i = 0; i < passageListe.size ( ); ++i) {
			System.out.println ( "PASSSAAAAAAAAAAAAAAAAAge : " + i );
		}
		Passage p = passageRepository.save ( new Passage ( null , now , l ) );
		return p;
	}
}
