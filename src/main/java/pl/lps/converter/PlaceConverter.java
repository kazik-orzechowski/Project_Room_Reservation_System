package pl.lps.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import pl.lps.entity.Place;
import pl.lps.repository.PlaceRepository;

/**
 * String to Place class object converter
 * 
 * @author kaz
 *
 */

public class PlaceConverter implements Converter<String, Place> {

	/**
	 * Jpa repository reference to the Place class
	 */
	@Autowired

	PlaceRepository repoPlace;

	/**
	 * Method converting string representing a Place class object's id into a Place
	 * class object
	 */
	public Place convert(String source) {
		return repoPlace.findOneById(Long.parseLong(source));

	}
}
