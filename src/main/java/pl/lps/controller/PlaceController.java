package pl.lps.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.lps.data.ControllerAttributesData;
import pl.lps.data.ControllerData;
import pl.lps.entity.Place;
import pl.lps.repository.PlaceRepository;

/**
 * PlaceController is a class used to map and process all admin requests
 * regarding places, i.e. sites in which rooms are located. All methods are
 * mapped at base "/places" browser path. This controller returns and feeds
 * place related views: places.html (admin's view responsible for display of all
 * places), addPlace.html and editPlace.html responsible for adding and editing
 * places.
 * 
 * @author kaz
 *
 */

@Controller
@RequestMapping("/places")
public class PlaceController extends SessionedController {

	/**
	 * Defines the name of place attribute used in add place and edit place views.
	 */
	private static final String PLACE_ATTRIBUTE = ControllerAttributesData.getPlaceAttribute();

	/**
	 * Defines the name of all places attribute used in place list view.
	 */
	private static final String ALL_PLACES_ATTRIBUTE = ControllerAttributesData.getAllPlacesAttribute();

	/**
	 * Passes the name of home page of this application.
	 */
	private static final String MAIN_VIEW = ControllerData.getMainView();
	/**
	 * Passes the name of all places view.
	 */
	private static final String PLACES_VIEW = ControllerData.getPlacesView();
	/**
	 * Passes the name of add place view.
	 */
	private static final String ADD_PLACE_VIEW = ControllerData.getAddPlaceView();
	/**
	 * Passes the name of edit place view.
	 */
	private static final String EDIT_PLACE_VIEW = ControllerData.getEditPlaceView();

	@Autowired
	PlaceRepository repoPlace;

	/**
	 * Maps admin's request to display all the places.
	 * 
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * @return places.html view
	 */

	@RequestMapping("")
	public String allPlaces(Model model) {

		if (!SessionValidation.isSessionValid()) {
			return MAIN_VIEW;
		}

		model.addAttribute(ALL_PLACES_ATTRIBUTE, repoPlace.findAll());
		System.out.println(repoPlace.findAll().toString());
		return PLACES_VIEW;
	}

	/**
	 * Maps admin's request to add new place.
	 * 
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * @return addPlace.html view with a list of all the places
	 */

	@GetMapping("/add")
	public String addPlace(Model model) {

		if (!SessionValidation.isSessionAdmin()) {
			return MAIN_VIEW;
		}

		Place place = new Place();
		model.addAttribute("addOrEdit", "add");
		model.addAttribute(PLACE_ATTRIBUTE, place);
		return EDIT_PLACE_VIEW;
	}

	/**
	 * Maps post request concerning adding a new place made by admin via input form
	 * on addPlace.html view
	 * 
	 * @param addedPlace
	 *            - place instance parameterized in the input form
	 * @param result
	 *            - binding result errors
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * @return addPlace.html if there are errors in the input form, places.html view with the updated list of all the places
	 */

	@PostMapping("/add")
	public String addPlacePost(@Valid Place addedPlace, BindingResult result, Model model) {
		if (result.hasErrors()) {
			System.err.println(result);
			return EDIT_PLACE_VIEW;
		}
		repoPlace.save(addedPlace);
		model.addAttribute(ALL_PLACES_ATTRIBUTE, repoPlace.findAll());
		return PLACES_VIEW;

	}

	/**
	 * Maps admin request to delete a place with the selected id.
	 * 
	 * @param id
	 *            - id of the place to be deleted
	 * 
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * @return places.html view with the list of all the places
	 */

	@GetMapping("/{id}/delete")
	public String delPlace(@PathVariable Long id, Model model) {

		if (!SessionValidation.isSessionAdmin()) {
			return MAIN_VIEW;
		}

		repoPlace.deleteById(id);
		return "redirect: /" + PLACES_VIEW;
	}

	/**
	 * Maps admin request to edit a place with the selected id.
	 * 
	 * @param id
	 *            - id of the place to be edited
	 * 
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * @return places.html view with the list of all the places
	 */

	@GetMapping("/{id}/edit")
	public String editPlace(@PathVariable Long id, Model model) {

		if (!SessionValidation.isSessionAdmin()) {
			return MAIN_VIEW;
		}
		model.addAttribute("addOrEdit", "edit");
		model.addAttribute(PLACE_ATTRIBUTE, repoPlace.findOneById(id));
		return EDIT_PLACE_VIEW;
	}

	/**
	 * Maps admin's post request concerning editing a selected place via input form
	 * on editPlace.html view
	 * 
	 * @param editedPlace
	 *            - place instance parameterized in the input form
	 * @param result
	 *            - binding result errors
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * @return editPlace.html if there are errors in the input form, places.html
	 *         view with the updated list of all the places
	 */

	@PostMapping("/{id}/edit")
	public String editPlacePost(@Valid Place editedPlace, BindingResult result, Model model) {
		if (result.hasErrors()) {
			System.err.println(result);
			return EDIT_PLACE_VIEW;
		}
		repoPlace.save(editedPlace);
		model.addAttribute(ALL_PLACES_ATTRIBUTE, repoPlace.findAll());
		return PLACES_VIEW;

	}

}
