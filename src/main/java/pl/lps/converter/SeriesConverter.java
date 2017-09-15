package pl.lps.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import pl.lps.entity.Place;
import pl.lps.entity.Series;
import pl.lps.repository.PlaceRepository;
import pl.lps.repository.SeriesRepository;

public class SeriesConverter implements Converter<String, Series> {
	
	@Autowired
	SeriesRepository repoSeries;

	public Series convert(String source) {
		return repoSeries.findOneById(Long.parseLong(source));

	}
}
