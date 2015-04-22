package greg.app.com.hackgameprototype;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MySQLAccess
{
	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	private String host = "jdbc:mysql://sql3.freemysqlhosting.net";
	private String user = "sql374453";
	private String pass = "cQ6!vQ7%";
	private String database = "sql374453"; // Name of DB to use
	private int userID = -1;

	// Constructor: this calls the createConnection() method
	MySQLAccess()
	{
		try
		{
			createConnection();
		}
		catch (Exception e)
		{
		}
	}

	// Constructor: this calls the createConnection() method
	// Takes UserID as argument to apply to all methods
	MySQLAccess(int ID)
	{
		try
		{
			createConnection();
			userID = ID;
		}
		catch (Exception e)
		{
		}
	}

	// Creates the MySQL Connection (must be called before any other methods)
	public void createConnection() throws Exception
	{
		try
		{
			// Load SQL driver
			Class.forName("com.mysql.jdbc.Driver");
			// Connect to DB
			connect = DriverManager.getConnection(host, user, pass);
			// Set the database to be used
			connect.setCatalog(database);
			statement = connect.createStatement();
		}
		catch (Exception e)
		{
            e.printStackTrace();
			System.err.println("Error Connecting! ERROR: " + e);
		}
	}
	
	/*
	 * Getters
	 */

	// Returns the user ID of username (int)
	public int getUserID(String username)
	{
		String sql = String.format("SELECT User_ID FROM User WHERE Username='%s'", username);
		return getInt(sql);
	}

	// Returns username (string)
	public String getUsername()
	{
		String sql = String.format("SELECT Username FROM User WHERE User_ID='%d'", userID);
		return getString(sql);
	}

	// Returns team (int)
	public int getTeam()
	{
		String sql = String.format("SELECT Team FROM User WHERE User_ID='%d'", userID);
		return getInt(sql);
	}

	// Returns cash (int)
	public int getCash()
	{
		String sql = String.format("SELECT Cash FROM User WHERE User_ID='%d'", userID);
		return getInt(sql);
	}

	// Returns wins (int)
	public int getWins()
	{
		String sql = String.format("SELECT Wins FROM User WHERE User_ID='%d'", userID);
		return getInt(sql);
	}

	// Returns losses (int)
	public int getLosses()
	{
		String sql = String.format("SELECT Losses FROM User WHERE User_ID='%d'", userID);
		return getInt(sql);
	}

	// Returns longitude (float)
	public double getLongitude()
	{
		String sql = String.format("SELECT Longitude FROM User WHERE User_ID='%d'", userID);
		return getDouble(sql);
	}

	// Returns latitude (float)
	public double getLatitude()
	{
		String sql = String.format("SELECT Latitude FROM User WHERE User_ID='%d'", userID);
		return getDouble(sql);
	}

	// Returns level (int)
	public int getLevel()
	{
		String sql = String.format("SELECT Level FROM User WHERE User_ID='%d'", userID);
		return getInt(sql);
	}

	// Returns team cash (int)
	public int getTeamCash()
	{
		String sql = String.format("SELECT Team FROM User WHERE User_ID='%d'", userID);
		int team = getInt(sql);
		sql = String.format("SELECT Cash FROM Team WHERE Team='%d'", team);
		return getInt(sql);
	}

	// Returns team cash for specificed team (int)
	public int getTeamCash(int team)
	{
		String sql = String.format("SELECT Cash FROM Team WHERE Team='%d'", team);
		return getInt(sql);
	}

	// Returns number of users (int)
	public int getUserCount()
	{
		String sql = "SELECT COUNT(*) FROM User";
		return getInt(sql);
	}
	
	// Returns sum of team wins (int)
	public int getTotalTeamWins(int team)
	{
		String sql = String.format("SELECT SUM(Wins) FROM User WHERE Team='%d'", team);
		return getInt(sql);
	}
	
	// Returns sum of team losses (int)
	public int getTotalTeamLosses(int team)
	{
		String sql = String.format("SELECT SUM(Losses) FROM User WHERE Team='%d'", team);
		return getInt(sql);
	}
	
	// Returns team members (ArrayList<Integer>)
	public ArrayList<Integer> getTeamMembers(int team)
	{
		ArrayList<Integer> members = new ArrayList<Integer>();
		try
		{
			resultSet = statement.executeQuery(String.format(
					"SELECT User_ID FROM User WHERE Team='%d'", team));

			while (resultSet.next())
			{
				members.add(resultSet.getInt(1));
			}
		}
		catch (Exception e)
		{
			System.out.println("getTeamMembers() error: " + e.getMessage());
		}
		
		return members;
	}
	
	// Returns team member usernames (ArrayList<String>)
	public ArrayList<String> getTeamUsernames(int team)
	{
		ArrayList<String> usernames = new ArrayList<String>();
		try
		{
			resultSet = statement.executeQuery(String.format(
					"SELECT Username FROM User WHERE Team='%d'", team));

			while (resultSet.next())
			{
				usernames.add(resultSet.getString(1));
			}
		}
		catch (Exception e)
		{
			System.out.println("getTeamUsernames() error: " + e.getMessage());
		}
		
		return usernames;
	}
	
	// Returns team member cash amounts (ArrayList<Integer>)
	public ArrayList<Integer> getTeamCashAmounts(int team)
	{
		ArrayList<Integer> amounts = new ArrayList<Integer>();
		try
		{
			resultSet = statement.executeQuery(String.format(
					"SELECT Cash FROM User WHERE Team='%d'", team));

			while (resultSet.next())
			{
				amounts.add(resultSet.getInt(1));
			}
		}
		catch (Exception e)
		{
			System.out.println("getTeamMembers() error: " + e.getMessage());
		}
		
		return amounts;
	}
	
	/*
	 * Setters
	 */
	
	// Sets the UserID to work with
	public void setUserID(int ID)
	{
		userID = ID;
	}

	// Sets the username
	public void setUsername(String username)
	{
		String sql = String.format("UPDATE User SET Username = '%s' WHERE User_ID='%d'", username, userID);
		setValue(sql);
	}

	// Sets longitude
	public void setLongitude(double longitude)
	{
		String sql = String.format("UPDATE User SET Longitude = '%f' WHERE User_ID='%d'", longitude, userID);
		setValue(sql);
	}

	// Sets longitude
	public void setLatitude(double latitude)
	{
		String sql = String.format("UPDATE User SET Latitude = '%f' WHERE User_ID='%d'", latitude, userID);
		setValue(sql);
	}

	// Adds cash
	public void addCash(int cash)
	{
		String sql = String.format("UPDATE User SET Cash = Cash + '%d' WHERE User_ID='%d'", cash, userID);
		setValue(sql);
	}

	// Reduces cash
	public void reduceCash(int cash)
	{
		String sql = String.format("UPDATE User SET Cash = Cash - '%d' WHERE User_ID='%d'", cash, userID);
		setValue(sql);
	}

	// Increments wins by 1
	public void incrementWins()
	{
		String sql = String.format("UPDATE User SET Wins = Wins + '1' WHERE User_ID='%d'", userID);
		setValue(sql);
	}

	// Increments losses by 1
	public void incrementLosses()
	{
		String sql = String.format("UPDATE User SET Losses = Losses + '1' WHERE User_ID='%d'", userID);
		setValue(sql);
	}

	// Increments level by 1
	public void incrementLevel()
	{
		String sql = String.format("UPDATE User SET Level = Level + '1' WHERE User_ID='%d'", userID);
		setValue(sql);
	}

	// Adds to team cash
	public void addTeamCash(int team, int cash)
	{
		String sql = String.format("UPDATE Team SET Cash = Cash + '%d' WHERE Team='%d'", cash, team);
		setValue(sql);
	}

	// Reduces team cash
	public void reduceTeamCash(int team, int cash)
	{
		String sql = String.format("UPDATE Team SET Cash = Cash - '%d' WHERE Team='%d'", cash, team);
		setValue(sql);
	}
	
	// Creates a user with username and team assigned
	public void createUser(String username, int team)
	{
		int count = getUserCount() + 1;
		String sql = String.format("INSERT INTO User VALUES (%d, '%s', %d, 0, 0, 0, 0, 0, 0, 0)", count, username, team);
		setValue(sql);
	}

	/*
	 * Helper Functions
	 */

	// Returns result of query (int)
	public int getInt(String query)
	{
		try
		{
			resultSet = statement.executeQuery(query);
			resultSet.next();
			return resultSet.getInt(1);
		}
		catch (Exception e)
		{
			System.out.println("getInt() error: " + e.getMessage());
			return -1;
		}
	}

	// Returns result of query (double)
	public double getDouble(String query)
	{
		try
		{
			resultSet = statement.executeQuery(query);
			resultSet.next();
			return resultSet.getDouble(1);
		}
		catch (Exception e)
		{
			System.out.println("getDouble() error: " + e.getMessage());
			return -1;
		}
	}

	// Returns result of query (String)
	public String getString(String query)
	{
		try
		{
			resultSet = statement.executeQuery(query);
			resultSet.next();
			return resultSet.getString(1);
		}
		catch (Exception e)
		{
			System.out.println("getString() error: " + e.getMessage());
			return null;
		}
	}
	
	public void setValue(String query)
	{
		try
		{
			statement.execute(query);
		}
		catch (Exception e)
		{
			System.out.println("setValue() error: " + e.getMessage());
		}
	}
}
