package pl.lps.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import pl.lps.entity.Place;
import pl.lps.entity.Series;
import pl.lps.repository.PlaceRepository;
import pl.lps.repository.SeriesRepository;

/**
 * String to Place class object converter
 * @author kaz
 *
 */

public class SeriesConverter implements Converter<String, Series> {
	
	
	/**
	 * Jpa repository reference to the Converter class
	 */
	@Autowired
	SeriesRepository repoSeries;

	/**
	 * Method converting string representing a series' id into a Series class object 
	 */
	public Series convert(String source) {
		return repoSeries.findOneById(Long.parseLong(source));

	}
}
