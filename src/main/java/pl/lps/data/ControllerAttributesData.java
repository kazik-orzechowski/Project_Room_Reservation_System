package pl.lps.data;

public class ControllerAttributesData {

	/**
	 * Empty controller throws exception to prevent this utility class to be
	 * instantiated.
	 */

	private ControllerAttributesData() {
		throw new IllegalStateException("Utility Class");
	}

	/**
	 * Name of model attribute passing an information to the user panel view
	 * regarding the current series (all or name} being displayed
	 */
	private static final String SERIES_DISPLAYED_INFO_ATTRIBUTE = "displayedSeries";

	/**
	 * Name of model attribute passing selected user to event related views.
	 */
	private static final String USER_ATTRIBUTE = "user";
	/**
	 * Name of model attribute passing selected event to add event and edit event
	 * views.
	 */
	private static final String EVENT_ATTRIBUTE = "event";
	/**
	 * Name of model attribute passing list of all or selected events to particular
	 * views.
	 */
	private static final String ALL_EVENTS_ATTRIBUTE = "allEvents";
	/**
	 * Name of model attribute passing event type attribute of selected event to
	 * event views.
	 */
	private static final String EVENT_TYPE_ATTRIBUTE = "eventType";
	/**
	 * Name of model attribute passing a list all places to event views.
	 */
	private static final String ALL_PLACES_ATTRIBUTE = "allPlaces";

	/**
	 * Name of model attribute passing a list all places to event views.
	 */
	private static final String REQUESTED_EVENT_ATTRIBUTE = "requestedEvent";
	/**
	 * Name of model attribute passing result of add / edit event to event views.
	 */
	private static final String ADD_EVENT_INFO_ATTRIBUTE = "addEventInfo";

	/**
	 * Id of the series that should be displayed (0 for all series)
	 */
	private static final String SERIES_DISPLAYED_ATTRIBUTE = "displayedSeriesId";

	/**
	 * Name of model attribute passing information regarding displayed series to
	 * user event view.
	 */

	private static final String ADD_SERIES_INFO_ATTRIBUTE = "addSeriesInfo";

	/**
	 * Name of model attribute passing list of series to be displayed in series
	 * view.
	 */
	private static final String ALL_SERIES = "allSeries";

	/**
	 * Name of place attribute used in room list view to show current place that
	 * list refers to.
	 */
	private static final String PLACE_ATTRIBUTE = "place";

	/**
	 * Name of room attribute used in add place and edit room views.
	 */
	private static final String ROOM_ATTRIBUTE = "room";

	/**
	 * Name of place attribute used in place list view.
	 */
	private static final String ALL_ROOMS_ATTRIBUTE = "allRooms";

	/**
	 * Name of model attribute passing a list of all or selected events to user
	 * panel view.
	 */
	private static final String USER_PANEL_EVENTS_ATTRIBUTE = "allEvents";
	/**
	 * Name of model attribute passing user name to login view.
	 */
	private static final String LOGIN_USERNAME_ATTRIBUTE = "username";
	/**
	 * Name of model attribute passing login message to login view.
	 */
	private static final String LOGIN_MESSAGE_ATTRIBUTE = "message";
	/**
	 * Name of model attribute passing user as User class object to login view.
	 */
	private static final String LOGIN_USER_ATTRIBUTE = "user";

	/**
	 * Name of String class message attribute used in the home page view.
	 */
	private static final String MAIN_MESSAGE_ATTRIBUTE = "message";
	/**
	 * Name of User class user attribute used in session.
	 */
	private static final String SESSION_USER_ATTRIBUTE = "user";
	/**
	 * Name of the String class attribute used in this application's home page.
	 */
	private static final String MAIN_USERNAME_ATTRIBUTE = "username";

	/**
	 * Name of model attribute passing list of all event types to particular views.
	 */
	private static final String ALL_EVENT_TYPES_ATTRIBUTE = "allEventTypes";

	/**
	 * Gets the name of place attribute used in room list view to show current place
	 * that list refers to.
	 */
	public static String getPlaceAttribute() {
		return PLACE_ATTRIBUTE;
	}

	/**
	 * Gets the name of room attribute used in add place and edit room views.
	 */

	public static String getRoomAttribute() {
		return ROOM_ATTRIBUTE;
	}

	/**
	 * Gets the name of place attribute used in place list view.
	 */

	public static String getAllRoomsAttribute() {
		return ALL_ROOMS_ATTRIBUTE;
	}

	/**
	 * Gets the name of model attribute passing a list of all or selected events to
	 * user panel view.
	 */

	public static String getUserPanelEventsAttribute() {
		return USER_PANEL_EVENTS_ATTRIBUTE;
	}

	/**
	 * Gets the name of model attribute passing user name to login view.
	 */

	public static String getLoginUsernameAttribute() {
		return LOGIN_USERNAME_ATTRIBUTE;
	}

	/**
	 * Gets the name of model attribute passing login message to login view.
	 */

	public static String getLoginMessageAttribute() {
		return LOGIN_MESSAGE_ATTRIBUTE;
	}

	/**
	 * Gets the name of model attribute passing user as User class object to login
	 * view.
	 */

	public static String getLoginUserAttribute() {
		return LOGIN_USER_ATTRIBUTE;
	}

	/**
	 * Gets the name of String class message attribute used in the home page view.
	 */

	public static String getMainMessageAttribute() {
		return MAIN_MESSAGE_ATTRIBUTE;
	}

	/**
	 * Gets the name of User class user attribute used in session.
	 */

	public static String getSessionUserAttribute() {
		return SESSION_USER_ATTRIBUTE;
	}

	/**
	 * Gets the name of the String class attribute used in this application's home
	 * page.
	 */

	public static String getMainUsernameAttribute() {
		return MAIN_USERNAME_ATTRIBUTE;
	}

	/**
	 * Gets the name of model attribute passing list of series to be displayed in
	 * series view.
	 */

	public static String getAllSeries() {
		return ALL_SERIES;
	}

	/**
	 * Gets the name of model attribute passing information regarding displayed
	 * series to user event view.
	 */

	public static String getAddSeriesInfoAttribute() {
		return ADD_SERIES_INFO_ATTRIBUTE;
	}

	/**
	 * Gets the name of model attribute passing an information to the user panel
	 * view regarding the current series (all or name} being displayed
	 */

	public static String getSeriesDisplayedInfoAttribute() {
		return SERIES_DISPLAYED_INFO_ATTRIBUTE;
	}

	/**
	 * Gets the name of model attribute passing selected user to event related
	 * views.
	 */

	public static String getUserAttribute() {
		return USER_ATTRIBUTE;
	}

	/**
	 * Gets the name of model attribute passing selected event to add event and edit
	 * event views.
	 */

	public static String getEventAttribute() {
		return EVENT_ATTRIBUTE;
	}

	/**
	 * Gets the name of model attribute passing list of all or selected events to
	 * particular views.
	 */

	public static String getAllEventsAttribute() {
		return ALL_EVENTS_ATTRIBUTE;
	}

	/**
	 * Gets the name of model attribute passing event type attribute of selected
	 * event to event views.
	 */

	public static String getEventTypeAttribute() {
		return EVENT_TYPE_ATTRIBUTE;
	}

	/**
	 * Gets the name of model attribute passing a list all places to event views.
	 */

	public static String getAllPlacesAttribute() {
		return ALL_PLACES_ATTRIBUTE;
	}

	/**
	 * Gets the name of model attribute passing a list all places to event views.
	 */

	public static String getRequestedEventAttribute() {
		return REQUESTED_EVENT_ATTRIBUTE;
	}

	/**
	 * Gets the name of model attribute passing an information to the user panel
	 * view regarding the current series (all or name} being displayed
	 */

	public static String getAddEventInfoAttribute() {
		return ADD_EVENT_INFO_ATTRIBUTE;
	}

	/**
	 * Gets the name of id of the series that should be displayed (0 for all series)
	 */

	public static String getSeriesDisplayedAttribute() {
		return SERIES_DISPLAYED_ATTRIBUTE;
	}

}
