package kkm;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Properties;

import kkm.model.Event;
import kkm.model.EventSignup;
import kkm.model.League;
import kkm.model.User;

public class DB {
	private Connection conn = null;
	private static DB db = new DB();

	private DB() {
		try (InputStream input = new FileInputStream("config.properties")) {
			Properties prop = new Properties();

			// load a properties file
			prop.load(input);

			Properties connectionProps = new Properties();
			connectionProps.put("user", prop.getProperty("db.user"));
			connectionProps.put("password", prop.getProperty("db.password"));

			String serverName = prop.getProperty("db.host");
			String port = prop.getProperty("db.port");
			String db = prop.getProperty("db.instance");

			conn = DriverManager.getConnection("jdbc:mysql://" + serverName + ":" + port + "/" + db, connectionProps);

			System.out.println("Connected to database");
		} catch (Exception ex) {
			System.err.println(ex);
			ex.printStackTrace(System.err);
		}
	}

	// Returns an array list of the events in the database.
	public static ArrayList<Event> loadEvents() {
		ArrayList<Event> list = new ArrayList<>();

		String queryString = "SELECT event_id, event_name, event_location, event_start, event_end, " +
				"event_volunteers, event_description " +
				"FROM event " +
				"ORDER BY event_start";

		try (
				PreparedStatement queryStmt = db.conn.prepareStatement(queryString);
				ResultSet rs = queryStmt.executeQuery();) {
			while (rs.next()) {
				int eventId = rs.getInt("event_id");
				String eventName = rs.getString("event_name");
				String eventLocation = rs.getString("event_location");

				// Assuming event_start and event_end are DATETIME in DB
				LocalDateTime eventStart = rs.getTimestamp("event_start").toLocalDateTime();
				LocalDateTime eventEnd = rs.getTimestamp("event_end").toLocalDateTime();

				int eventVolunteers = rs.getInt("event_volunteers");
				String eventDescription = rs.getString("event_description");

				// Build Event object
				Event event = new Event(eventId, eventName, eventLocation, eventStart, eventEnd, eventVolunteers,
						eventDescription);
				list.add(event);
			}

		} catch (Exception ex) {
			System.err.println("Error loading events: " + ex.getMessage());
			ex.printStackTrace(System.err);
		}

		return list;
	}

	// Loads a single league given an id.
	public static League loadLeague(int leagueId) {
		String queryString = " select league.league_id, league_name " +
				" from league  " +
				" where league_id = ? ";

		try (
				PreparedStatement queryStmt = db.conn.prepareStatement(queryString)) {
			queryStmt.setInt(1, leagueId);

			try (ResultSet rs = queryStmt.executeQuery()) {

				if (rs.next()) {
					String leagueName = rs.getString("league_name");

					return new League(leagueId, leagueName, false);
				}
			}
		} catch (Exception ex) {
			System.err.println(ex);
			ex.printStackTrace(System.err);
		}

		return null;
	}

	// Adds a new league to the database.
	public static void insertLeague(String leagueName) {
		String query = "insert into league(league_name) values (?)";

		try (PreparedStatement insertStmt = db.conn.prepareStatement(query)) {

			insertStmt.setString(1, leagueName);
			insertStmt.executeUpdate();
		} catch (Exception ex) {
			System.err.println(ex);
			ex.printStackTrace(System.err);
		}
	}

	/** Updates the name of a league in the database. */
	public static void updateLeague(League league) {
		String query = "update league set league_name = ? where league_id = ?";

		try (PreparedStatement updateStmt = db.conn.prepareStatement(query)) {

			updateStmt.setString(1, league.getLeagueName());
			updateStmt.setInt(2, league.getLeagueId());

			updateStmt.executeUpdate();
		} catch (Exception ex) {
			System.err.println(ex);
			ex.printStackTrace(System.err);
		}
	}

	// Deletes the given league from the database. //
	public static void deleteLeague(int leagueId) {
		String query = "delete from league where league_id = ?";

		try (PreparedStatement updateStmt = db.conn.prepareStatement(query)) {

			updateStmt.setInt(1, leagueId);
			updateStmt.executeUpdate();
		} catch (Exception ex) {
			System.err.println(ex);
			ex.printStackTrace(System.err);
		}
	}

	// Method to insert a new event into the event table
	public static void insertEvent(String eventName, String eventLocation, String eventStart, String eventEnd,
			int eventVolunteers, String eventDescription) {
		String query = "INSERT INTO event (event_name, event_location, event_start, event_end, event_volunteers, event_description) VALUES (?, ?, ?, ?, ?, ?)";

		try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
			stmt.setString(1, eventName);
			stmt.setString(2, eventLocation);
			stmt.setString(3, eventStart);
			stmt.setString(4, eventEnd);
			stmt.setInt(5, eventVolunteers);
			stmt.setString(6, eventDescription);
			stmt.executeUpdate();
		} catch (Exception ex) {
			System.err.println(ex);
			ex.printStackTrace(System.err);
		}
	}

	// Method to insert a new user into the user table
	public static void insertUser(String userName, String userPassword, String userStatus, String userType) {
		String query = "INSERT INTO user (user_name, user_password, user_status, user_type) VALUES (?, ?, ?, ?)";

		try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
			stmt.setString(1, userName);
			stmt.setString(2, userPassword);
			stmt.setString(3, userStatus);
			stmt.setString(4, userType);
			stmt.executeUpdate();
		} catch (Exception ex) {
			System.err.println(ex);
			ex.printStackTrace(System.err);
		}
	}

	// Load a single user by id.
	public static User loadUser(int userId) {
		String sql = "SELECT user_id, full_name, email, phone, is_admin " +
				"FROM user " + // <-- change to `users` if that's your table name
				"WHERE user_id = ?";

		try (PreparedStatement ps = db.conn.prepareStatement(sql)) {
			ps.setInt(1, userId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					int id = rs.getInt("user_id");
					String fullName = rs.getString("full_name");
					String email = rs.getString("email");
					String phone = rs.getString("phone");
					boolean isAdmin = rs.getBoolean("is_admin");

					return new User(id, fullName, email, phone, isAdmin);
				}
			}
		} catch (Exception ex) {
			System.err.println("Error loading user " + userId + ": " + ex.getMessage());
			ex.printStackTrace(System.err);
		}
		return null;
	}

	// Load the past 5 most recent events attended by a user (events whose end time
	// is before now).
	public static ArrayList<Event> loadPastEventsForUser(int userId) {
		ArrayList<Event> list = new ArrayList<>();

		String sql = "SELECT e.event_id, e.event_name, e.event_location, e.event_start, e.event_end, " +
				"       e.event_volunteers, e.event_description " +
				"FROM event e " +
				"JOIN event_signup s ON s.event_id = e.event_id " +
				"WHERE s.volunteer_id = ? " +
				"  AND e.event_end < NOW() " +
				"GROUP BY e.event_id " +
				"ORDER BY e.event_start DESC " +
				"LIMIT 5";

		try (PreparedStatement ps = db.conn.prepareStatement(sql)) {
			ps.setInt(1, userId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {

					int eventId = rs.getInt("event_id");
					String eventName = rs.getString("event_name");
					String eventLocation = rs.getString("event_location");

					LocalDateTime start = null;
					var t1 = rs.getTimestamp("event_start");
					if (t1 != null)
						start = t1.toLocalDateTime();

					LocalDateTime end = null;
					var t2 = rs.getTimestamp("event_end");
					if (t2 != null)
						end = t2.toLocalDateTime();

					int vols = rs.getInt("event_volunteers");
					String desc = rs.getString("event_description");

					list.add(new Event(eventId, eventName, eventLocation, start, end, vols, desc));
				}
			}
		} catch (Exception ex) {
			System.err.println("Error loading past events: " + ex.getMessage());
			ex.printStackTrace(System.err);
		}

		return list;
	}

	public static boolean usernameExists(String username) {
		String sql = "SELECT COUNT(*) FROM `user` WHERE `user_name` = ?";
		try (PreparedStatement ps = db.conn.prepareStatement(sql)) {
			ps.setString(1, username);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next())
					return rs.getInt(1) > 0;
			}
		} catch (Exception ex) {
			System.err.println("Error checking username: " + ex.getMessage());
			ex.printStackTrace(System.err);
		}
		return false;
	}

	public static int insertUser(String username, String password, String userType) {
		String sql = "INSERT INTO `user` (`user_name`, `user_password`, `user_type`, `user_status`) VALUES (?, ?, ?, ?)";
		try (PreparedStatement ps = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, userType);
			ps.setInt(4, 1); // default status, or adjust as you like

			int updated = ps.executeUpdate();
			if (updated > 0) {
				try (ResultSet keys = ps.getGeneratedKeys()) {
					if (keys.next())
						return keys.getInt(1);
				}
			}
		} catch (Exception ex) {
			System.err.println("Error inserting user: " + ex.getMessage());
			ex.printStackTrace(System.err);
		}
		return -1;
	}

	public static int verifyUser(String username, String password) {
		String sql = "SELECT `user_id` FROM `user` WHERE `user_name` = ? AND `user_password` = ?";
		try (PreparedStatement ps = db.conn.prepareStatement(sql)) {
			ps.setString(1, username);
			ps.setString(2, password);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next())
					return rs.getInt("user_id");
			}
		} catch (Exception ex) {
			System.err.println("Error verifying user: " + ex.getMessage());
			ex.printStackTrace(System.err);
		}
		return -1;
	}

	public static String getUserTypeByUserId(int userId) {
		String sql = "SELECT user_type FROM `user` WHERE user_id = ?";
		try (PreparedStatement ps = db.conn.prepareStatement(sql)) {
			ps.setInt(1, userId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getString("user_type");
				}
			}
		} catch (Exception ex) {
			System.err.println("Error getting user type: " + ex.getMessage());
			ex.printStackTrace(System.err);
		}
	
		return null; 
	}

	public static int getUserIdByUsername(String username) {
		String sql = "SELECT user_id FROM `user` WHERE `user_name` = ?";
		try (PreparedStatement ps = db.conn.prepareStatement(sql)) {
			ps.setString(1, username);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next())
					return rs.getInt("user_id");
			}
		} catch (Exception ex) {
			System.err.println("Error looking up userId: " + ex.getMessage());
			ex.printStackTrace(System.err);
		}
		return -1;
	}

	public static String getUserNameByUserId(int userId) {
		String sql = "SELECT user_name FROM user WHERE user_id = ?";
	
		try (PreparedStatement ps = db.conn.prepareStatement(sql)) {
			ps.setInt(1, userId);
	
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getString("user_name");
				}
			}
		} catch (Exception ex) {
			System.err.println("Error getting username: " + ex.getMessage());
			ex.printStackTrace(System.err);
		}
		return null;
	}
	

	// Method to insert a new event signup into the event_signup table
	public static void insertEventSignup(int volunteerId, int eventId, String signupStartTime, String signupEndTime,
			String signupStatus) {
		String query = "INSERT INTO event_signup (volunteer_id, event_id, event_signup_start_time, event_signup_end_time, event_signup_status) VALUES (?, ?, ?, ?, ?)";

		try (PreparedStatement stmt = db.conn.prepareStatement(query)) {
			stmt.setInt(1, volunteerId);
			stmt.setInt(2, eventId);
			stmt.setString(3, signupStartTime);
			stmt.setString(4, signupEndTime);
			stmt.setString(5, signupStatus);
			stmt.executeUpdate();
		} catch (Exception ex) {
			System.err.println(ex);
			ex.printStackTrace(System.err);
		}
	}

	public static void addUserToEvent(int volunteerId, int eventId) {
		String queryString = "INSERT INTO event_signup (volunteer_id, event_id, event_signup_start_time, event_signup_status) VALUES (?, ?, NOW(), 1)";
		try (PreparedStatement ps = db.conn.prepareStatement(queryString)) {
			ps.setInt(1, volunteerId);
			ps.setInt(2, eventId);
			ps.executeUpdate();
			System.out.println("User " + volunteerId + " signed up for event " + eventId);
		} catch (SQLException ex) {
			System.err.println("Error adding user to event: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public static void removeUserFromEvent(int volunteerId, int eventId) {
		String queryString = "UPDATE event_signup SET event_signup_end_time = NOW(), event_signup_status = 0 WHERE volunteer_id = ? AND event_id = ?";
		try (PreparedStatement ps = db.conn.prepareStatement(queryString)) {
			ps.setInt(1, volunteerId);
			ps.setInt(2, eventId);
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("User " + volunteerId + " removed from event " + eventId);
			} else {
				System.out.println("User " + volunteerId + " was not signed up for event " + eventId);
			}
		} catch (SQLException ex) {
			System.err.println("Error removing user from event: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public static boolean isUserSignedUpForEvent(int volunteerId, int eventId) {
		String queryString = "SELECT COUNT(*) FROM event_signup WHERE volunteer_id = ? AND event_id = ? AND event_signup_status = 1";
		try (PreparedStatement ps = db.conn.prepareStatement(queryString)) {
			ps.setInt(1, volunteerId);
			ps.setInt(2, eventId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0; 
				}
			}
		} catch (SQLException ex) {
			System.err.println("Error checking if user is signed up: " + ex.getMessage());
			ex.printStackTrace();
		}
		return false;
	}

	public static void updateUserStatusForEvent(int volunteerId, int eventId, int status) {
		String queryString = "UPDATE event_signup SET event_signup_status = ? WHERE volunteer_id = ? AND event_id = ?";
		try (PreparedStatement ps = db.conn.prepareStatement(queryString)) {
			ps.setInt(1, status); 
			ps.setInt(2, volunteerId);
			ps.setInt(3, eventId);
			ps.executeUpdate();
			System.out.println("User " + volunteerId + " status updated for event " + eventId);
		} catch (SQLException ex) {
			System.err.println("Error updating user status: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public static ArrayList<EventSignup> loadPastEventSignupsForUser(int userId) {
		ArrayList<EventSignup> list = new ArrayList<>();
	
		// SQL query to fetch past event signups for the user
		String sql = "SELECT DISTINCT es.volunteer_id, es.event_id, es.event_signup_start_time, es.event_signup_end_time, " +
					 "e.event_name, e.event_location, DATE(es.event_signup_start_time) AS event_date " +
					 "FROM event_signup es " +
					 "JOIN event e ON es.event_id = e.event_id " +
					 "WHERE es.volunteer_id = ? " +
					 "AND es.event_signup_end_time < CURRENT_TIMESTAMP " +
					 "ORDER BY es.event_signup_start_time DESC"; 
	
		try (PreparedStatement ps = db.conn.prepareStatement(sql)) {
			ps.setInt(1, userId);  
	
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					int volunteerId = rs.getInt("volunteer_id");
					int eventId = rs.getInt("event_id");
					LocalDateTime signupStart = rs.getTimestamp("event_signup_start_time").toLocalDateTime();
					LocalDateTime signupEnd = rs.getTimestamp("event_signup_end_time").toLocalDateTime();
					String eventName = rs.getString("event_name");
					String eventLocation = rs.getString("event_location");
					LocalDateTime eventDate = signupStart.toLocalDate().atStartOfDay(); 
	
					EventSignup eventSignup = new EventSignup(volunteerId, eventId, signupStart, signupEnd);
					eventSignup.setEventName(eventName);
					eventSignup.setEventLocation(eventLocation);
	
					if (!isDuplicate(list, eventId, eventDate)) {
						list.add(eventSignup);
					}
				}
			}
		} catch (SQLException ex) {
			System.err.println("Error loading past event signups for user " + userId + ": " + ex.getMessage());
			ex.printStackTrace(System.err);
		}
	
		return list;  
	}
	
	private static boolean isDuplicate(ArrayList<EventSignup> list, int eventId, LocalDateTime eventDate) {
		for (EventSignup es : list) {
			if (es.getEventId() == eventId && es.getEventSignupStartTime().toLocalDate().isEqual(eventDate.toLocalDate())) {
				return true;  
			}
		}
		return false;  
	}

	public static ArrayList<EventSignup> loadEventSignupsForEvent(int eventId) {
    ArrayList<EventSignup> list = new ArrayList<>();

    String sql =
        "SELECT es.volunteer_id, es.event_id, " +
        "       es.event_signup_start_time, es.event_signup_end_time, " +
        "       u.user_name " +
        "FROM event_signup es " +
        "JOIN user u ON es.volunteer_id = u.user_id " +
        "WHERE es.event_id = ? " +
        "  AND es.event_signup_end_time IS NOT NULL " +
        "ORDER BY es.event_signup_start_time";

    try (PreparedStatement ps = db.conn.prepareStatement(sql)) {
        ps.setInt(1, eventId);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int volunteerId = rs.getInt("volunteer_id");
                LocalDateTime start = null;
                LocalDateTime end = null;

                var tsStart = rs.getTimestamp("event_signup_start_time");
                if (tsStart != null) {
                    start = tsStart.toLocalDateTime();
                }

                var tsEnd = rs.getTimestamp("event_signup_end_time");
                if (tsEnd != null) {
                    end = tsEnd.toLocalDateTime();
                }

                EventSignup signup = new EventSignup(volunteerId, eventId, start, end);

                list.add(signup);
            }
        }
    } catch (SQLException ex) {
        System.err.println("Error loading signups for event " + eventId + ": " + ex.getMessage());
        ex.printStackTrace(System.err);
    }

    return list;
}

public static ArrayList<Integer> loadVolunteerIds() {
    ArrayList<Integer> list = new ArrayList<>();

    String sql = "SELECT user_id FROM `user` WHERE `user_type` = 'volun' AND `user_status` = 1";

    try (PreparedStatement ps = db.conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            list.add(rs.getInt("user_id"));
        }
    } catch (Exception ex) {
        System.err.println("Error loading volunteer ids: " + ex.getMessage());
        ex.printStackTrace(System.err);
    }

    return list;
}

}
