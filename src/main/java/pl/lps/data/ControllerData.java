package pl.lps.data;

/**
 * CommonData class contains definition of final fields for controllers. Views
 * names in this class are used in controllers. _VIEW final fields contain the
 * names of this application's view html files (without .html extension).
 * 
 * 
 * @author kaz
 *
 */

public final class ControllerData {

	/**
	 * Name of user sign up view.
	 */
	static final String SIGNUP_VIEW = "signup";

	/**
	 * Name of user login view.
	 */
	static final String LOGIN_VIEW = "login";

	/**
	 * Name of user home page view .
	 */
	static final String USER_PANEL_VIEW = "userPanel";

	/**
	 * Name of series view of this application.
	 */
	private static final String SERIES_VIEW = "series";
	
	public static String getSeriesView() {
		return SERIES_VIEW;
	}

	/**
	 * Name of edit user view available to admin and user.
	 */
	static final String EDIT_USER_VIEW = "editUser";

	/**
	 * Name of all events view available to admin.
	 */
	static final String EVENTS_VIEW = "events";

	/**
	 * Name of home page of this application.
	 */
	static final String MAIN_VIEW = "main";

	/**
	 * Name of edit event view available to admin and user.
	 */
	static final String EDIT_EVENT_VIEW = "editEvent";

	/**
	 * Name of add event view available to user.
	 */
	static final String ADD_EVENT_VIEW = "addEvent";

	/**
	 * Name of all users view available to admin .
	 */
	static final String USERS_VIEW = "users";

	/**
	 * Name of admin panel / home page view.
	 */
	static final String ADMIN_PANEL_VIEW = "adminPanel";

	/**
	 * Name of all event types view available to admin .
	 */
	static final String EVENT_TYPES_VIEW = "eventTypes";

	/**
	 * Name of edit event type view available to admin .
	 */
	static final String EDIT_EVENT_TYPE_VIEW = "editEventType";

	/**
	 * Name of add event type view available to admin .
	 */
	static final String ADD_EVENT_TYPE_VIEW = "addEventType";

	/**
	 * Name of all rooms view available to admin .
	 */
	static final String ROOMS_VIEW = "rooms";

	
	/**
	 * Name of edit room view available to admin .
	 */
	static final String EDIT_ROOM_VIEW = "editRoom";

	/**
	 * Name of add room view available to admin .
	 */
	static final String ADD_ROOM_VIEW = "addRoom";

	/**
	 * Name of edit place / site view available to admin .
	 */
	static final String EDIT_PLACE_VIEW = "editPlace";

	/**
	 * Name of add place / site view available to admin .
	 */
	static final String ADD_PLACE_VIEW = "addPlace";

	/**
	 * Name of all places / sites view available to admin .
	 */
	static final String PLACES_VIEW = "places";

	/**
	 * Name of the view displaying rooms available to user during
	 * reservation process.
	 */
	static final String ROOMS_FOR_USER_VIEW = "roomsForUser";

	/**
	 * Name of the view displaying list of user's series.
	 */
	private static final String USER_SERIES_PANEL_VIEW = "userSeriesPanel";
	
	
	/**
	 * Gets the name of view containing the list of series of user
	 * 
	 * @return the name of a view displaying a list of series of user
	 */
	
	public static String getUserSeriesPanelView() {
		return USER_SERIES_PANEL_VIEW;
	}

	/**
	 * Gets the name of view containing the list of rooms available for user
	 * 
	 * @return the name of a view displaying a list of rooms available to user
	 */
	public static String getRoomsForUserView() {
		return ROOMS_FOR_USER_VIEW;
	}

	/**
	 * Gets the name of user's and admin's sign up view
	 * 
	 * @return the name of the sign up view
	 */
	public static String getSignupView() {
		return SIGNUP_VIEW;
	}

	/**
	 * Gets the name of user's and admin's login view
	 * 
	 * @return the name of the login view
	 */
	public static String getLoginView() {
		return LOGIN_VIEW;
	}

	/**
	 * Gets the name of user's home page view
	 * 
	 * @return the name of user's home page view
	 */
	public static String getUserPanelView() {
		return USER_PANEL_VIEW;
	}

	/**
	 * Gets the name of admin's home page view
	 * 
	 * @return the name of admin's home page view
	 */
	public static String getAdminPanelView() {
		return ADMIN_PANEL_VIEW;
	}

	/**
	 * Gets the name of the view concerning edition of user
	 * 
	 * @return the name of the user edition view
	 */
	public static String getEditUserView() {
		return EDIT_USER_VIEW;
	}

	/**
	 * Gets the name of all events view available to admin
	 * 
	 * @return the name of all events view
	 */
	public static String getEventsView() {
		return EVENTS_VIEW;
	}

	/**
	 * Gets the name of the home page view
	 * 
	 * @return the name of the home page view
	 */
	public static String getMainView() {
		return MAIN_VIEW;
	}

	/**
	 * Gets the name of the view concerning event details edition
	 * 
	 * @return the name of the event edition view
	 */
	public static String getEditEventView() {
		return EDIT_EVENT_VIEW;
	}

	/**
	 * Gets the name of the view concerning new event creation and definition
	 * 
	 * @return the name of the event creation view
	 */
	public static String getAddEventView() {
		return ADD_EVENT_VIEW;
	}

	/**
	 * Gets the name of all users view available to admin
	 * 
	 * @return the name of all users view
	 */
	public static String getUsersView() {
		return USERS_VIEW;
	}

	/**
	 * Gets the name of all event types view available to admin
	 * 
	 * @return the name of all events types view
	 */
	public static String getEventTypesView() {
		return EVENT_TYPES_VIEW;
	}

	/**
	 * Gets the name of the view concerning event type details edition
	 * 
	 * @return the name of event type edition view
	 */
	public static String getEditEventTypeView() {
		return EDIT_EVENT_TYPE_VIEW;
	}

	/**
	 * Gets the name of the view concerning new event type creation and definition
	 * 
	 * @return the name of the event type creation view
	 */
	public static String getAddEventTypeView() {
		return ADD_EVENT_TYPE_VIEW;
	}

	/**
	 * Gets the name of all rooms view available to admin
	 * 
	 * @return the name of all rooms view
	 */
	public static String getRoomsView() {
		return ROOMS_VIEW;
	}

	/**
	 * Gets the name of the view concerning room details edition
	 * 
	 * @return the name of the room edition view
	 */
	public static String getEditRoomView() {
		return EDIT_ROOM_VIEW;
	}

	/**
	 * Gets the name of the view concerning new room creation and definition
	 * 
	 * @return the name of the room creation view
	 */
	public static String getAddRoomView() {
		return ADD_ROOM_VIEW;
	}

	/**
	 * Gets the name of the view concerning event details edition
	 * 
	 * @return the name of the place edition view
	 */
	public static String getEditPlaceView() {
		return EDIT_PLACE_VIEW;
	}

	/**
	 * Gets the name of the view concerning new place creation and definition
	 * 
	 * @return the name of the place creation view
	 */
	public static String getAddPlaceView() {
		return ADD_PLACE_VIEW;
	}

	/**
	 * Gets the name of all places view available to admin
	 * 
	 * @return the name of all places view
	 */
	public static String getPlacesView() {
		return PLACES_VIEW;
	}

	
	
}
