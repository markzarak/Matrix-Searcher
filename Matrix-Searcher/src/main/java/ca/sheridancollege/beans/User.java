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
	private int id;
	private String name;
	private int length = 5; // Minimum size of the 2D matrix
	private int score;
	private char[][] squareMatrix;
	private String squareMatrixString;
	private String wordList;

}
