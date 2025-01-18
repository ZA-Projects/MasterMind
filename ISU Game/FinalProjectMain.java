//                              ICS 3U1
//                           FinalProjectMain
//                       Written by: Zahi Ahmed
//                      Written for: Mrs. Ganesan
//                       Due date: January 17, 2025
//                      Submission date: January 17, 2025
//
//"MasterMind"
// This program allows the user to play a MasterMind game
// where they attempt to guess a 5-digit code generated
// by the program. The user is given feedback on how many
// digits are correct and in the correct position after
// each guess. The program tracks the number of games played,
// // total guesses, and the best performance (least guesses).
//
// playAgain (boolean) - Tracks whether the player wants to play another game. It controls the main game loop.
//
// totalGames (int) - Counts the total number of games played.
//
// totalCorrect (int) - Keeps track of how many times the player has correctly guessed the code.
//
// totalGuesses (int) - Tracks the total number of guesses made across all games.
//
// bestGame (int) - Records the least number of guesses in a single game. Starts with a high value (99999) and updates if the player guesses the code in fewer guesses than the previous best.
//
// pwdverify (boolean) - Determines if the password verification is successful. It is used to control login functionality.
//
// name (String) - Holds the player's name.
//
// password (String) - Stores the player's password for login. Retrieved from a file if the player has played before.
//
// input (String) - Stores user input during various prompts.
//
// br (BufferedReader) - Used to read input from the user (console).
//
// code[] (int[]) - The randomly generated 5-digit code that the player needs to guess.
//
// board[][] (int[][]) - An 8x5 2D array that keeps track of the user's guesses. Each row represents a guess, and each column represents one of the 5 digits.
//
// guessCount (int) - Tracks the number of guesses the player has made in a game.
//
// gameWon (boolean) - Indicates whether the player has guessed the correct code.
//
// rightnum (int) - Tracks how many digits are correct, regardless of position.
//
// rightnumplace (int) - Tracks how many digits are correct and in the correct position.
//
// matched[] (boolean[]) - Keeps track of which digits in the player's guess have already been matched with the code.
//
// UserGuess[] (int[]) - Stores the player's guess as an array of integers representing the 5 digits they entered.
//
// average (double) - The average number of guesses per game, calculated at the end when the player opts not to play again.




import java.io.*;
// Main method that controls the overall game flow
class FinalProjectMain
{
    public static void main (String args[]) throws java.io.IOException
    {
	BufferedReader br = new BufferedReader (new InputStreamReader (System.in)); // Reading input from the user
	MasterMindMethods mmm = new MasterMindMethods (); // Creating an object to handle MasterMind game logic

	boolean playAgain = true; // Boolean to track whether the player wants to play again
	int totalGames = 0; // Tracks total games played
	int totalCorrect = 0; // Tracks how many times the player correctly guessed the code
	int totalGuesses = 0; // Tracks the total number of guesses made across all games
	int bestGame = 99999; // Tracks the best game (fewest guesses)

	// Main game loop, runs while the player wants to play
	while (playAgain)
	{
	    boolean pwdverify = mmm.Login (); // Calls the login method
	    if (pwdverify)
	    {
		System.out.println ("Password Accepted");
	    }
	    else
	    {
		System.out.println ("Password Incorrect");
	    }

	    while (pwdverify)
	    { // Runs the game loop if login is successful
		int code[] = mmm.CodeGeneration (); // Generates the secret code to guess
		// System.out.println("Code generated (for debugging purposes):");
		// for (int count = 0; count < 5; count++) {
		//     System.out.print(code[count] + " ");
		// }
		// System.out.println();

		int[] [] board = new int [8] [5]; // 8x5 board to track user guesses
		for (int count = 0 ; count < 8 ; count++)
		{
		    for (int count2 = 0 ; count2 < 5 ; count2++)
		    {
			board [count] [count2] = -1; // Initializes the board with -1 (empty)
		    }
		}

		int guessCount = 0; // Keeps track of the number of guesses made in a game
		boolean gameWon = false; // Boolean to track if the player won the game

		// Guessing loop, runs until the player wins or reaches the maximum guesses
		while (guessCount < 8 && !gameWon)
		{
		    int[] UserGuess = mmm.UserInput (); // Prompts user for input and stores guess

		    // Records user's guess on the board
		    for (int count = 0 ; count < 5 ; count++)
		    {
			board [guessCount] [count] = UserGuess [count];
		    }

		    int rightnum = mmm.UserInputVerify (UserGuess, code); // Verifies the user's guess

		    mmm.displayBoard (board, guessCount); // Displays the board with all guesses

		    System.out.println ("You got " + rightnum + " numbers right."); // Displays feedback on how many digits are correct
		    System.out.println ("Guesses left: " + (8 - (guessCount + 1))); // Displays how many guesses are remaining
		    totalGuesses++; // Increments total guesses made

		    if (rightnum == 5)
		    { // If the user guessed the correct code
			System.out.println ("Congratulations! You've guessed the correct code.");
			totalCorrect++; // Increments total correct guesses
			gameWon = true; // Marks the game as won

			if (guessCount + 1 < bestGame)
			{ // If this game was the best (fewest guesses)
			    bestGame = guessCount + 1; // Updates best game performance
			}
		    }
		    else
		    {
			guessCount++; // Increments the guess count if the code was not guessed
		    }
		}

		// If the game is over and the player didn't win
		if (!gameWon)
		{
		    System.out.println ("Game Over! You've used all guesses.");
		    System.out.print ("The code was: ");
		    for (int count = 0 ; count < code.length ; count++)
		    {
			System.out.print (code [count] + " "); // Reveals the code to the player
		    }
		    System.out.println ();
		}

		totalGames++; // Increments the total games played
		System.out.print ("Do you want to play again? (yes/no): ");
		String input = br.readLine (); // Gets the player's response

		// Handles invalid input (other than "yes" or "no")
		while (!input.equals ("yes") && !input.equals ("no"))
		{
		    System.out.println ("Invalid input. Please enter 'yes' or 'no'.");
		    System.out.print ("Do you want to play again? (yes/no): ");
		    input = br.readLine ().toLowerCase ();
		}


		if (input.equals ("no"))
		{ // If the player chooses not to play again
		    playAgain = false; // Exits the loop
		    System.out.println ("Thanks for playing!");

		    double average = (double) totalGuesses / totalGames; // Calculates average guesses per game
		    System.out.println ("Total guesses: " + totalGuesses); // Displays total guesses made
		    System.out.println ("Games played: " + totalGames); // Displays total games played

		    // If there were any games played, display the average guesses per game
		    if (totalGames > 0)
		    {
			System.out.println ("Average guesses per game: " + average);
		    }
		    else
		    {
			System.out.println ("Average guesses per game: 0");
		    }
		    System.out.println ("Best game: " + bestGame + " guesses"); // Displays the best game performance
		    
		    // FileReader fr = new FileReader (mmm.name + ".txt");
		    // BufferedReader bfr = new BufferedReader (fr);
		    //
		    // String empty = bfr.readLine();
		    //
		    // String bfrline = bfr.readLine ();
		    // int oldtotalGuesses = Integer.parseInt (bfrline);
		    //
		    // bfrline = bfr.readLine ();
		    // int oldtotalGames = Integer.parseInt (bfrline);
		    //
		    // bfrline = bfr.readLine ();
		    // int oldaverage = Integer.parseInt (bfrline);
		    //
		    // bfrline = bfr.readLine ();
		    // int oldbestGame = Integer.parseInt (bfrline);
		    //
		    //
		    // int updatedTotalGuesses = totalGuesses + oldtotalGuesses;
		    // int updatedTotalGames = totalGames + oldtotalGames;
		    // double updatedAverage = (double) updatedTotalGuesses / updatedTotalGames;
		    //
		    //
		    // if (bestGame > oldbestGame)
		    // {
		    //     bestGame = bestGame;
		    // }
		    // else
		    // {
		    //     bestGame = oldbestGame;
		    // }
		    //
		    //
		    // System.out.println ("Total guesses: " + updatedTotalGuesses);
		    // System.out.println ("Games played: " + updatedTotalGames);
		    //
		    // if (updatedTotalGames > 0)
		    // {
		    //     System.out.println ("Average guesses per game: " + updatedAverage);
		    // }
		    // else
		    // {
		    //     System.out.println ("Average guesses per game: 0");
		    // }
		    // System.out.println ("Best game: " + bestGame + " guesses");
		    //
		    //
		    // FileWriter fw = new FileWriter (mmm.name + ".txt", true); // Use 'false' to overwrite the file
		    // fw.write (updatedTotalGuesses + "\r\n");
		    // fw.write (updatedTotalGames + "\r\n");
		    // fw.write (updatedAverage + "\r\n");
		    // fw.write (bestGame + "\r\n");
		    // fw.close ();

		      // Save the game data to a file for future use (track total guesses, games, average, and best game)
		    FileWriter fw = new FileWriter(mmm.name + ".txt", true);
		    fw.write("\r\n");
		    fw.write(totalGuesses + "\r\n");
		    fw.write(totalGames + "\r\n");
		    fw.write(average + "\r\n");
		    fw.write(bestGame + "\r\n");
		    fw.close();
		    break; // Breaks the loop when the player doesn't want to play again
		}
	    }
	}
    }
}

class MasterMindMethods
{
    boolean pwdverify = false; // Boolean to track if login was successful
    String name, password, input; // Variables to store player name, password, and input
    BufferedReader br = new BufferedReader (new InputStreamReader (System.in)); // Used for reading input from the user

    // Login method to handle player login and password verification
    boolean Login () throws java.io.IOException
    {
	System.out.print ("What is your name?: ");
	name = br.readLine (); // Reads player's name

	System.out.print ("Have you played before(yes/no)?: ");
	input = br.readLine (); // Asks if the player has played before

	if (input.equalsIgnoreCase ("yes"))
	{ // If player has played before
	    FileReader fr = new FileReader (name + ".txt"); // Read stored password file
	    BufferedReader bfr = new BufferedReader (fr);
	    password = bfr.readLine (); // Read the password from file
	    System.out.println ("Please enter your password: ");
	    input = br.readLine (); // Reads input password

	    // Verifies the entered password
	    if (!input.equals (password))
	    {
		System.out.print ("Password was Incorrect, please enter your password: ");
		input = br.readLine ();

		if (!input.equals (password))
		{
		    System.out.print ("Password was Incorrect, please enter your password: ");
		    input = br.readLine ();

		    if (!input.equals (password))
		    {
			pwdverify = false; // If password is incorrect 3 times, fails login
		    }
		    else
		    {
			pwdverify = true; // Successful login
		    }
		}
		else
		{
		    pwdverify = true; // Successful login
		}
	    }
	    else
	    {
		pwdverify = true; // Successful login
	    }
	}
	if (input.equalsIgnoreCase ("no"))
	{ // If player has not played before
	    do
	    {
		FileWriter fw = new FileWriter (name + ".txt"); // Create a new password file
		System.out.println ("Create a password with the following conditions: ");
		System.out.println ("- between 6 and 12 characters long");
		System.out.println ("- at least one upper case letter");
		System.out.println ("- at least one lower case letter");
		System.out.println ("- at least one number");
		System.out.println ("- no special characters or spaces");
		System.out.print (":");
		password = br.readLine (); // Get new password from the player

		// Password validation
		if (password.length () >= 6 && password.length () <= 12)
		{
		    boolean verification = false;
		    boolean LowerCaseverify = false;
		    boolean UpperCaseverify = false;
		    boolean Digitverify = false;
		    boolean Whitespaceverify = false;
		    boolean specialcharacterverify = false;
		    char ch;
		    // Validate the password based on the conditions
		    for (int count = 0 ; count < password.length () ; count++)
		    {
			ch = password.charAt (count);
			if (Character.isLowerCase (ch))
			{
			    LowerCaseverify = true;
			}
			if (Character.isUpperCase (ch))
			{
			    UpperCaseverify = true;
			}
			if (Character.isDigit (ch))
			{
			    Digitverify = true;
			}
			if (Character.isWhitespace (ch))
			{
			    Whitespaceverify = true;
			}
			if (ch == '#' || ch == '@' || ch == '?' || ch == '$' || ch == '%' || ch == '!' || ch == '&' || ch == '^' || ch == '+' || ch == '=' || ch == '*' || ch == '-' || ch == '_')
			{
			    specialcharacterverify = true;
			}
		    }
		    // If password meets all conditions
		    if (LowerCaseverify && UpperCaseverify && Digitverify && !Whitespaceverify && !specialcharacterverify)
		    {
			fw.write (password);
			fw.close (); // Save the new password to file
			pwdverify = true; // Login successful
		    }
		}
	    }
	    while (pwdverify == false);   // Repeats if password does not meet criteria
	}
	return pwdverify; // Returns whether the login was successful
    }


    // Code generation method that creates a 5-digit secret code for the player to guess
    int[] CodeGeneration ()
    {
	int[] Code = new int [5]; // Array to hold the 5-digit secret code
	for (int count = 0 ; count < 5 ; count++)
	{
	    Code [count] = (int) (Math.random () * 9) + 1; // Generates a random digit between 1 and 9
	}
	return Code; // Returns the generated code
    }


    // Method that allows the player to input their guess
    int[] UserInput () throws java.io.IOException
    {
	int[] UserGuess = new int [5]; // Array to hold the player's guess
	System.out.print ("Enter 5 digits (1-9) for your guess: ");
	input = br.readLine (); // Reads the input

	// Validates that the input has exactly 5 digits
	while (input.length () != 5)
	{
	    System.out.print ("Invalid input. Please enter exactly 5 digits (1-9): ");
	    input = br.readLine ();
	}

	// Converts each character of the input to an integer and stores it in the guess array
	for (int count = 0 ; count < input.length () ; count++)
	{
	    UserGuess [count] = Character.getNumericValue (input.charAt (count));
	}

	return UserGuess; // Returns the player's guess as an array
    }


    // Method that verifies how many digits in the guess are correct
    int UserInputVerify (int[] UserGuess, int[] Code)
    {
	int correct = 0; // Tracks how many digits are in the correct position
	for (int count = 0 ; count < 5 ; count++)
	{
	    if (UserGuess [count] == Code [count])
	    {
		correct++; // Increments the counter for correct digits in the right position
	    }
	}
	return correct; // Returns the number of correct digits
    }


    // Method that displays the current state of the board
    void displayBoard (int[] [] board, int guessCount)
    {
	System.out.println ("Board so far:");
	for (int count = 0 ; count <= guessCount ; count++)
	{
	    for (int count2 = 0 ; count2 < 5 ; count2++)
	    {
		System.out.print (board [count] [count2] + " "); // Displays each guess made
	    }
	    System.out.println ();
	}
    }
}
