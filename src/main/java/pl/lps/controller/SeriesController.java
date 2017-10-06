package pl.lps.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.lps.data.ControllerAttributesData;
import pl.lps.data.ControllerData;
import pl.lps.repository.EventRepository;
import pl.lps.repository.EventTypeRepository;
import pl.lps.repository.SeriesRepository;
import pl.lps.repository.UserRepository;
import pl.lps.service.SeriesService;

/**
 * SeriesController is a class used to map and process users and admins requests
 * regarding series of events, especially displaying, editing and deleting. All
 * methods are mapped at base "/series" browser path. SeriesController returns
 * and feeds series related views: userSeriesPanel (user's view responsible for
 * display of all user's series). Series editing is performed on userPanel.html
 * view, by deleting or editing particular event of series.
 * 
 * @author kaz
 *
 */

@Controller
@RequestMapping("/series")
public class SeriesController extends SessionedController {

	/**
	 * Name of series view of this application.
	 */
	private static final String SERIES_VIEW = ControllerData.getSeriesView();
	/**
	 * Name of user view, containing list of user's series.
	 */
	private static final String USER_SERIES_PANEL_VIEW = ControllerData.getUserSeriesPanelView();
	/**
	 * Name of model attribute passing information regarding displayed series to
	 * user event view.
	 */

	private static final String ADD_SERIES_INFO_ATTRIBUTE = ControllerAttributesData.getAddSeriesInfoAttribute();

	/**
	 * Name of model attribute passing result of add / edit event to event views.
	 */
	private static final String ADD_EVENT_INFO_ATTRIBUTE = ControllerAttributesData.getAddEventInfoAttribute();
	/**
	 * Name of home page of this application.
	 */
	private static final String MAIN_VIEW = ControllerData.getMainView();

	@Autowired
	EventRepository repoEvent;

	@Autowired
	EventTypeRepository repoEventType;

	@Autowired
	UserRepository repoUser;

	@Autowired
	SeriesRepository repoSeries;

	@Autowired
	SessionValidation sv;

	/**
	 * Maps the request of admin or user to display a list of this user's series of
	 * events.
	 * 
	 * @param id
	 *            - user id
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * @return for user userPanel.html view containing all of this user series. For
	 *         admin returns series.html view containing series of events of all
	 *         users.
	 */
	@Transactional
	@RequestMapping("/{id}")
	public String getSeries(@PathVariable Long id, Model model) {

		if (!SessionValidation.isSessionUser(id)) {
			return MAIN_VIEW;
		}

		SeriesService seriesServiceView = new SeriesService();

		seriesServiceView.prepareSeriesView(id, model);

		return adminVsUserSeriesViewRedirect(id);

	}

	/**
	 * Maps the request of admin or user to delete the selected series of events.
	 * 
	 * @param id
	 *            - user id
	 * @param ids
	 *            - id of the series to be deleted
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * @return for user userPanel.html view containing all of this user series. For
	 *         admin returns series.html view containing series of events of all
	 *         users.
	 */
	@GetMapping("/{id}/delete/{ids}")
	public String delSeries(@PathVariable Long id, @PathVariable Long ids, Model model) {

		if (!SessionValidation.isSessionUser(id)) {
			return MAIN_VIEW;
		}

		repoSeries.deleteById(ids);

		SeriesService seriesServiceView = new SeriesService();

		seriesServiceView.prepareSeriesView(id, model);

		return adminVsUserSeriesViewRedirect(id);

	}

	/**
	 * Selects admin or user view based on passed id
	 * 
	 * @param id
	 *            - user id
	 * @return for user userPanel.html view, for admin return series.html view.
	 */

	String adminVsUserSeriesViewRedirect(Long id) {
		if (repoUser.findOneById(id).getUserName().equals("admin")) {
			return SERIES_VIEW;
		} else {
			return USER_SERIES_PANEL_VIEW;

		}
	}

}
