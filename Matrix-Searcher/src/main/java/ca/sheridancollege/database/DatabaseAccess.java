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
	
	public void addPlayer(String name, int length) {
		int difficulty = length - 4;
		String query="INSERT INTO `player_scores` VALUES (6, '" + name + "', " + difficulty + ", 0);";
		jdbc.update(query, new HashMap());
	}
	
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