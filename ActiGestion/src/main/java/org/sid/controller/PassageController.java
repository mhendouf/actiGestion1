package org.sid.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	public Passage savePassage(@RequestParam("id") String id, @RequestParam("date") String date)
			throws RessourceNotFoundException, ParseException {
		Passage p1 = null;
		Long l = Long.parseLong ( id );
		date = date.substring ( 1 , date.length ( ) - 1 );
		Date now = new SimpleDateFormat ( "yyyy/MM/dd" ).parse ( date );
		// Date now = new Date ( );
		try {
			p1 = passageRepository.findPassage ( now , l );
			System.out.println ( "PASSSAAAAAAAAAAAAAAAAAge tryyyyy : " + date );
		} catch (Exception e) {

			p1 = passageRepository.save ( new Passage ( null , now , l ) );
			System.out.println ( "PASSSAAAAAAAAAAAAAAAAAge catch : " + date );
		}

		return p1;
	}

	public boolean PassageExist() {
		return true;
	}
}
