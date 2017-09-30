package pl.lps.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import pl.lps.entity.Place;
import pl.lps.entity.Room;
import pl.lps.repository.PlaceRepository;
import pl.lps.repository.RoomRepository;

/**
 * String to Room class object converter
 * 
 * @author kaz
 *
 */

public class RoomConverter implements Converter<String, Room> {

	/**
	 * Jpa repository reference to the Room class
	 */
	@Autowired

	RoomRepository repoRoom;

	/**
	 * Method converting string representing a Room class object's id into a Room
	 * class object
	 */
	public Room convert(String source) {
		return repoRoom.findOneById(Long.parseLong(source));

	}
}
