/*
 * Database Access
 * 
 * @author: Mark Zarak, Oct 2020
 */

package ca.sheridancollege.database;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DatabaseAccess {
	@Autowired
	NamedParameterJdbcTemplate jdbc;
	
	// Add player name and length to database
	public void addPlayer(String name, int length) {
		
		// Use 2D array length as difficulty multiplier
		int difficulty = length - 4;
		String query="INSERT INTO `player_scores` VALUES (6, '" + name + "', " + difficulty + ", 0);";
		jdbc.update(query, new HashMap());
	}
	
	// Calculate and add score to database
	public void setScore(int score) {
		
		String id="SELECT MAX(id)";
		String query="UPDATE player_scores SET score=" + score + " WHERE `id` = " + id + ";";
		jdbc.update(query, new HashMap());
	}
	
//	public void getScore() {
//		String id="SELECT MAX(id)";
//		String query="SELECT score FROM player_scores WHERE `id` = " + id + ";";
//
//	}
//	
//	public void getName() {
//		String id="SELECT MAX(id)";
//		
//	}
}