package pl.lps.model;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.lps.entity.Room;
import pl.lps.repository.PlaceRepository;
import pl.lps.repository.RoomRepository;

@Component
public class RoomList {

	private ArrayList<Room> rooms;
	
	
	public RoomList() {
		super();
	}

	public RoomList(ArrayList<Room> rooms) {
		super();
		this.rooms = rooms;
	}


	public ArrayList<Room> getRooms() {
		return rooms;
	}


	public void setRooms(ArrayList<Room> rooms) {
		this.rooms = rooms;
	}


	@Override
	public String toString() {
		return "RoomList [rooms=" + rooms + "]";
	}


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

}
