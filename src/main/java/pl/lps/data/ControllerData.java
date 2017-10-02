package pl.lps.data;

/**
 * CommonData interface contains definition of final fields for controllers.
 * This interface is implemented by all user action controllers. _VIEW final
 * fields contain application view page names.
 * 
 * @author kaz
 *
 */

public final class ControllerData {

	/**
	 * Contains the name of user sign up view.
	 */
	static final String SIGNUP_VIEW = "signup";

	/**
	 * Contains the name of user login view.
	 */
	static final String LOGIN_VIEW = "login";

	/**
	 * Contains the name of user home page view .
	 */
	static final String USER_PANEL_VIEW = "userPanel";

	/**
	 * Contains the name of edit user view available to admin and user.
	 */
	static final String EDIT_USER_VIEW = "editUser";

	/**
	 * Contains the name of all events view available to admin.
	 */
	static final String EVENTS_VIEW = "events";

	/**
	 * Contains the name of home page of this application.
	 */
	static final String MAIN_VIEW = "main";

	/**
	 * Contains the name of edit event view available to admin and user.
	 */
	static final String EDIT_EVENT_VIEW = "editEvent";

	/**
	 * Contains the name of add event view available to user.
	 */
	static final String ADD_EVENT_VIEW = "addEvent";

	/**
	 * Contains the name of all users view available to admin .
	 */
	static final String USERS_VIEW = "users";

	/**
	 * Contains the name of admin panel / home page view.
	 */
	static final String ADMIN_PANEL_VIEW = "adminPanel";

	/**
	 * Contains the name of all event types view available to admin .
	 */

	static final String EVENT_TYPES_VIEW = "eventTypes";
	/**
	 * Contains the name of edit event type view available to admin .
	 */

	static final String EDIT_EVENT_TYPE_VIEW = "editEventType";
	/**
	 * Contains the name of add event type view available to admin .
	 */

	static final String ADD_EVENT_TYPE_VIEW = "addEventType";

	/**
	 * Contains the name of all rooms view available to admin .
	 */

	static final String ROOMS_VIEW = "rooms";
	/**
	 * Contains the name of edit room view available to admin .
	 */

	static final String EDIT_ROOM_VIEW = "editRoom";
	/**
	 * Contains the name of add room view available to admin .
	 */

	static final String ADD_ROOM_VIEW = "addRoom";

	/**
	 * Contains the name of edit place / site view available to admin .
	 */
	static final String EDIT_PLACE_VIEW = "editPlace";

	/**
	 * Contains the name of add place / site view available to admin .
	 */
	static final String ADD_PLACE_VIEW = "addPlace";

	/**
	 * Contains the name of all places / sites view available to admin .
	 */
	static final String PLACES_VIEW = "places";

	/**
	 * Contains the name of view displaying rooms available to user during
	 * reservation process.
	 */
	static final String ROOMS_FOR_USER_VIEW = "roomsForUser";

	public static String getRoomsForUserView() {
		return ROOMS_FOR_USER_VIEW;
	}

	public static String getSignupView() {
		return SIGNUP_VIEW;
	}

	public static String getLoginView() {
		return LOGIN_VIEW;
	}

	public static String getUserPanelView() {
		return USER_PANEL_VIEW;
	}

	public static String getEditUserView() {
		return EDIT_USER_VIEW;
	}

	public static String getEventsView() {
		return EVENTS_VIEW;
	}

	public static String getMainView() {
		return MAIN_VIEW;
	}

	public static String getEditEventView() {
		return EDIT_EVENT_VIEW;
	}

	public static String getAddEventView() {
		return ADD_EVENT_VIEW;
	}

	public static String getUsersView() {
		return USERS_VIEW;
	}

	public static String getAdminPanelView() {
		return ADMIN_PANEL_VIEW;
	}

	public static String getEventTypesView() {
		return EVENT_TYPES_VIEW;
	}

	public static String getEditEventTypeView() {
		return EDIT_EVENT_TYPE_VIEW;
	}

	public static String getAddEventTypeView() {
		return ADD_EVENT_TYPE_VIEW;
	}

	public static String getRoomsView() {
		return ROOMS_VIEW;
	}

	public static String getEditRoomView() {
		return EDIT_ROOM_VIEW;
	}

	public static String getAddRoomView() {
		return ADD_ROOM_VIEW;
	}

	public static String getEditPlaceView() {
		return EDIT_PLACE_VIEW;
	}

	public static String getAddPlaceView() {
		return ADD_PLACE_VIEW;
	}

	public static String getPlacesView() {
		return PLACES_VIEW;
	}

}
