/*
 * Model
 * 
 * @author: Mark Zarak, Oct 2020
 */

package ca.sheridancollege.beans;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements Serializable {
	
	private static final long serialVersionUID = 7773031754142054607L;
	private String name;
	private int score;
	private int length = 5; //Value set to default minimum
	
}
