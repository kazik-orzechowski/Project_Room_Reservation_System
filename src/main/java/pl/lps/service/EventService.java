package pl.lps.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import pl.lps.entity.Room;
import pl.lps.repository.PlaceRepository;
import pl.lps.repository.RoomRepository;

@Service
public class EventService {

	private ArrayList<Room> eventRooms;

	
	
	@Autowired
	PlaceRepository repoPlace;
	
	@Autowired
	RoomRepository repoRoom;
	
	/**
	 * Preparation of array list of rooms, that will be used to narrow free room
	 * search to rooms meeting place and number of seats criteria
	 */

	public ArrayList<Room> roomLongListing(Long placeCurrentId, Long seatsRequired) {

		ArrayList<Room> rooms = new ArrayList<Room>();
		if (placeCurrentId == repoPlace.findOneByName("Dowolne").getId()) {
			rooms = (ArrayList<Room>) repoRoom.findAllByRoomSize(seatsRequired);
		} else {
			rooms = (ArrayList<Room>) repoRoom.findAllByPlaceAndRoomSize(placeCurrentId, seatsRequired);
		}
		return rooms;
	}
	
	public ArrayList<Room> getEventRooms() {
		return eventRooms;
	}

	public void setEventRooms(ArrayList<Room> eventRooms) {
		this.eventRooms = eventRooms;
	}

	public EventService() {
		super();
	}

	
}
