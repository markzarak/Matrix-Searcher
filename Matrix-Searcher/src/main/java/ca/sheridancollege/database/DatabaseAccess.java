/*
 * A class that models a Database to perform CRUD operations.
 * 
 * @author: Mark Zarak, Nov 2020
 */

package ca.sheridancollege.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.beans.User;
import ca.sheridancollege.squarematrix.SquareMatrixCreator;

@Repository
public class DatabaseAccess {

	@Autowired
	NamedParameterJdbcTemplate jdbc;

	// Add new player info
	public void addPlayer(User user) {

		// Convert 2D array to String for database storage
		String squareMatrixString = SquareMatrixCreator.doSquareMatrixString(user);

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "INSERT INTO `player_scores` (name, length, squareMatrixString) VALUES (:name, :length, :squareMatrixString)";
		parameters.addValue("name", user.getName());
		parameters.addValue("length", user.getLength());
		parameters.addValue("squareMatrixString", squareMatrixString);

		jdbc.update(query, parameters);
	}
	
	// Update current player info
	public void updatePlayer(User user) {

		// Convert 2D array to String for database storage
		String squareMatrixString = SquareMatrixCreator.doSquareMatrixString(user);

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "Update player_scores Set length=:length, squareMatrixString=:squareMatrixString WHERE id=SELECT MAX(id)";
		parameters.addValue("length", user.getLength());
		parameters.addValue("squareMatrixString", squareMatrixString);

		jdbc.update(query, parameters);
	}

	// Update current player score
	public void setScore(int totalWordsFound, int difficulty) {
		
		// Calculate score based on words found and grid length 
		difficulty += 5; // A 5x5 grid produces a score multiplier of 10, a 6x6 grid = 11, etc...
		int score = totalWordsFound * difficulty;
		
		// Add to previous score
		int oldScore = getPlayer().getScore();
		score += oldScore;

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "Update player_scores Set score=:score WHERE id=SELECT MAX(id)";
		parameters.addValue("score", score);

		jdbc.update(query, parameters);
	}

	// Returns an ArrayList of the top 5 players
	public ArrayList<User> getTopPlayers() {

		ArrayList<User> players = new ArrayList<User>();
		String query = "SELECT * FROM player_scores ORDER BY score DESC LIMIT 5";
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
	
	// Returns the current player
	public User getPlayer() {

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
			user.setContinuingGame((boolean) (row.get("continuingGame")));

			players.add(user);
		}
		
		return players.get(players.size()-1);
	}
	
	// Set continuingGame
	public void setContinuingGame(Boolean decision) {
	
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "Update player_scores Set continuingGame=:continuingGame WHERE id=SELECT MAX(id)";
		parameters.addValue("continuingGame", decision);

		jdbc.update(query, parameters);
	}

}