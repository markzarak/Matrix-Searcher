/*
 * Database Access
 * 
 * @author: Mark Zarak, Oct 2020
 */

package ca.sheridancollege.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.beans.User;
import ca.sheridancollege.squarematrix.SquareMatrixCreator;

@Repository
public class DatabaseAccess {

	@Autowired
	NamedParameterJdbcTemplate jdbc;

	// Add player info to database
	public void addPlayer(User user) {

		// Convert squareMatrix 2D array to squareMatrixString for database storage
		String squareMatrixString = SquareMatrixCreator.doSquareMatrixString(user);

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "INSERT INTO `player_scores` (name, length, squareMatrixString) VALUES (:name, :length, :squareMatrixString)";
		parameters.addValue("name", user.getName());
		parameters.addValue("length", user.getLength());
		parameters.addValue("squareMatrixString", squareMatrixString);

		jdbc.update(query, parameters);
	}

	// Update score
	public void setScore(int totalWordsFound) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "Update player_scores Set score=:score WHERE id=SELECT MAX(id)";
		parameters.addValue("score", totalWordsFound);

		jdbc.update(query, parameters);
	}

	// Returns all data from database as an ArrayList of User objects
	public ArrayList<User> getPlayers() {

		ArrayList<User> players = new ArrayList<User>();
		String query = "SELECT * FROM player_scores";
		List<Map<String, Object>> rows = jdbc.queryForList(query, new HashMap<String, Object>());

		// Loop through database and add data to User object
		for (Map<String, Object> row : rows) {
			User user = new User();
			user.setName((String) (row.get("name")));
			user.setLength((Integer) (row.get("length")));
			user.setScore((Integer) (row.get("score")));
			user.setSquareMatrixString((String) (row.get("squareMatrixString")));

			players.add(user);
		}

		return players;
	}

	// Return squareMatrixString
	public String getSquareMatrixString() {

		String query = "SELECT squareMatrixString FROM player_scores WHERE id=SELECT MAX(id)";

		return jdbc.queryForObject(query, EmptySqlParameterSource.INSTANCE, String.class);
	}

	// Return length
	public int getLength() {

		String query = "SELECT length FROM player_scores WHERE id=SELECT MAX(id)";
		int length = Integer.valueOf(jdbc.queryForObject(query, EmptySqlParameterSource.INSTANCE, String.class));

		return length;
	}

}