package pl.lps.entity;

import java.util.Date;
import java.util.Set;

import org.springframework.stereotype.Component;

/**
 * SeriesDTO class is used to collect series and event data to display series in
 * user series view. !!!!! UNDER CONSTRUCTION !!!!!
 * 
 * @author kaz
 */
@Component
public class SeriesDTO {

	private Series series;
	private Integer numberOfEvents;
	private Date seriesStartDate;
	private Date seriesEndDate;
	private Set<Date> seriesHours;
	private Integer seriesDurations;
	private Integer seriesFrequency;
	private Set<String> seriesPlacesAndRooms;

	public SeriesDTO(Series series, Integer numberOfEvents, Date seriesStartDate, Date seriesEndDate,
			Set<Date> seriesHours, Integer seriesDurations, Integer seriesFrequency,
			Set<String> seriesPlacesAndRooms) {
		super();
		this.series = series;
		this.numberOfEvents = numberOfEvents;
		this.seriesStartDate = seriesStartDate;
		this.seriesEndDate = seriesEndDate;
		this.seriesHours = seriesHours;
		this.seriesDurations = seriesDurations;
		this.seriesFrequency = seriesFrequency;
		this.seriesPlacesAndRooms = seriesPlacesAndRooms;
	}

	public SeriesDTO() {
		super();

	}

	@Override
	public String toString() {
		return "SeriesDTO [series=" + series + ", numberOfEvents=" + numberOfEvents + ", seriesStartDate="
				+ seriesStartDate + ", seriesEndDate=" + seriesEndDate + ", seriesHours=" + seriesHours
				+ ", seriesDurations=" + seriesDurations + ", seriesFrequency=" + seriesFrequency
				+ ", seriesPlacesAndRooms=" + seriesPlacesAndRooms + "]";
	}

	public Series getSeries() {
		return series;
	}

	public void setSeries(Series series) {
		this.series = series;
	}

	public Integer getNumberOfEvents() {
		return numberOfEvents;
	}

	public void setNumberOfEvents(Integer numberOfEvents) {
		this.numberOfEvents = numberOfEvents;
	}

	public Date getSeriesStartDate() {
		return seriesStartDate;
	}

	public void setSeriesStartDate(Date seriesStartDate) {
		this.seriesStartDate = seriesStartDate;
	}

	public Date getSeriesEndDate() {
		return seriesEndDate;
	}

	public void setSeriesEndDate(Date seriesEndDate) {
		this.seriesEndDate = seriesEndDate;
	}

	public Set<Date> getSeriesHours() {
		return seriesHours;
	}

	public void setSeriesHours(Set<Date> seriesHours) {
		this.seriesHours = seriesHours;
	}

	public Integer getSeriesDurations() {
		return seriesDurations;
	}

	public void setSeriesDurations(Integer seriesDurations) {
		this.seriesDurations = seriesDurations;
	}

	public Integer getSeriesFrequency() {
		return seriesFrequency;
	}

	public void setSeriesFrequency(Integer seriesFrequency) {
		this.seriesFrequency = seriesFrequency;
	}

	public Set<String> getSeriesPlacesAndRooms() {
		return seriesPlacesAndRooms;
	}

	public void setSeriesPlacesAndRooms(Set<String> seriesPlacesAndRooms) {
		this.seriesPlacesAndRooms = seriesPlacesAndRooms;
	}

}
