package pl.lps.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import pl.lps.entity.Series;
import pl.lps.repository.SeriesRepository;

@Service
public class SeriesDisplayService {

	private final static int PAGESIZE = 3;
	
	@Autowired
	SeriesRepository repoSeries;
	
	public List<Series> getPage(int pageNumber) {
		PageRequest request = new PageRequest (pageNumber - 1, PAGESIZE, Sort.Direction.ASC, "id");
		
		return repoSeries.findAll(request).getContent();
	}
	
}
