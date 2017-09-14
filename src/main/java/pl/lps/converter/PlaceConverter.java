package pl.lps.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import pl.lps.entity.Place;
import pl.lps.repository.PlaceRepository;

public class PlaceConverter implements Converter<String, Place> {
	@Autowired

	PlaceRepository repoPlace;

	public Place convert(String source) {
		return repoPlace.findOneById(Long.parseLong(source));

	}
}
