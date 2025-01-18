import java.io.*;

class MasterMindMain
{
    public static void main (String args[]) throws java.io.IOException
    {
	BufferedReader br = new BufferedReader (new InputStreamReader (System.in));
	MasterMindMethods mmm = new MasterMindMethods ();

	boolean playAgain = true;
	int totalGames = 0;
	int totalCorrect = 0;
	int totalGuesses = 0;
	int bestGame = 99999;

	while (playAgain)
	{
	    boolean pwdverify = mmm.Login ();
	    if (pwdverify)
	    {
		System.out.println ("Password Accepted");
	    }
	    else
	    {
		System.out.println ("Password Incorrect");
	    }

	    while (pwdverify)
	    {
		int code[] = mmm.CodeGeneration ();
		// System.out.println ("Code generated (for debugging purposes):");
		// for (int count = 0 ; count < 5 ; count++)
		// {
		//     System.out.print (code [count] + " ");
		// }
		// System.out.println ();

		int[] [] board = new int [8] [5];
		for (int count = 0 ; count < 8 ; count++)
		{
		    for (int count2 = 0 ; count2 < 5 ; count2++)
		    {
			board [count] [count2] = -1;
		    }
		}

		int guessCount = 0;
		boolean gameWon = false;
		while (guessCount < 8 && !gameWon)
		{
		    int[] UserGuess = mmm.UserInput ();

		    for (int count = 0 ; count < 5 ; count++)
		    {
			board [guessCount] [count] = UserGuess [count];
		    }

		    int rightnum = mmm.UserInputVerify (UserGuess, code);

		    mmm.displayBoard (board, guessCount);

		    System.out.println ("You got " + rightnum + " numbers right.");
		    System.out.println ("Guesses left: " + (8 - (guessCount + 1)));
		    totalGuesses++;

		    if (rightnum == 5)
		    {
			System.out.println ("Congratulations! You've guessed the correct code.");
			totalCorrect++;
			gameWon = true;

			if (guessCount + 1 < bestGame)
			{
			    bestGame = guessCount + 1; 
			}
		    }
		    else
		    {
			guessCount++;
		    }
		}

		if (!gameWon)
		{
		    System.out.println ("Game Over! You've used all guesses.");
		    System.out.print ("The code was: ");
		    for (int count = 0 ; count < code.length ; count++)
		    {
			System.out.print (code [count] + " ");
		    }
		    System.out.println ();
		}

		totalGames++;
		System.out.print ("Do you want to play again? (yes/no): ");
		String input = br.readLine ();
		input = input.toLowerCase ();

		while (!input.equals ("yes") && !input.equals ("no"))
		{
		    System.out.println ("Invalid input. Please enter 'yes' or 'no'.");
		    System.out.print ("Do you want to play again? (yes/no): ");
		    input = br.readLine ().toLowerCase ();
		}

		if (input.equals ("no"))
		{
		    playAgain = false;
		    System.out.println ("Thanks for playing!");

		    double average = (double) totalGuesses / totalGames;
		    System.out.println ("Total guesses: " + totalGuesses);
		    System.out.println ("Games played: " + totalGames);

		    if (totalGames > 0)
		    {
			System.out.println ("Average guesses per game: " + average);
		    }
		    else
		    {
			System.out.println ("Average guesses per game: 0");
		    }
		    System.out.println ("Best game: " + bestGame + " guesses");
		    
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
		    
		    FileWriter fw = new FileWriter (mmm.name + ".txt", true);
		    fw.write("\r\n");
		    fw.write (totalGuesses + "\r\n");
		    fw.write (totalGames + "\r\n");
		    fw.write (average + "\r\n");
		    fw.write (bestGame + "\r\n");
		    fw.close ();
		    break;
		}
	    }
	}
    }
}

class MasterMindMethods
{
    boolean pwdverify = false;
    String name, password, input;
    BufferedReader br = new BufferedReader (new InputStreamReader (System.in));

    boolean Login () throws java.io.IOException
    {
	System.out.print ("What is your name?: ");
	name = br.readLine ();

	System.out.print ("Have you played before(yes/no)?: ");
	input = br.readLine ();
	if (input.equalsIgnoreCase ("yes"))
	{
	    FileReader fr = new FileReader (name + ".txt");
	    BufferedReader bfr = new BufferedReader (fr);
	    password = bfr.readLine ();
	    System.out.println ("Please enter your password: ");
	    input = br.readLine ();
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
			pwdverify = false;
		    }
		    else
		    {
			pwdverify = true;
		    }
		}
		else
		{
		    pwdverify = true;
		}
	    }
	    else
	    {
		pwdverify = true;
	    }
	}
	if (input.equalsIgnoreCase ("no"))
	{
	    do
	    {
		FileWriter fw = new FileWriter (name + ".txt");
		System.out.println ("Create a password with the following conditions: ");
		System.out.println ("- between 6 and 12 characters long");
		System.out.println ("- at least one upper case letter");
		System.out.println ("- at least one lower case letter");
		System.out.println ("- at least one number");
		System.out.println ("- no special characters or spaces");
		System.out.print (":");
		password = br.readLine ();

		if (password.length () >= 6 && password.length () <= 12)
		{
		    boolean verification = false;
		    boolean LowerCaseverify = false;
		    boolean UpperCaseverify = false;
		    boolean Digitverify = false;
		    boolean Whitespaceverify = false;
		    boolean specialcharacterverify = false;
		    char ch;
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
			if (!Character.isLowerCase (ch) && !Character.isUpperCase (ch) && !Character.isDigit (ch) && !Character.isWhitespace (ch))
			{
			    specialcharacterverify = true;
			}
		    }
		    if (LowerCaseverify && UpperCaseverify && Digitverify && !Whitespaceverify && !specialcharacterverify)
		    {
			fw.write (password + "\r\n");
			fw.close ();
			pwdverify = true;
		    }
		    else
		    {
			pwdverify = false;
		    }
		}
		else
		{
		    pwdverify = false;
		}
	    }
	    while (!pwdverify);
	}

	return pwdverify;
    }


    int[] CodeGeneration ()
    {
	int[] code = new int [5];
	for (int count = 0 ; count < 5 ; count++)
	{
	    code [count] = (int) (Math.random () * 9) + 1;
	}

	return code;
    }


    int[] UserInput () throws java.io.IOException
    {
	int[] UserGuess = new int [5];
	System.out.println ("Guess the 5 digit code (Hint = its between 1-9):");

	char ch = ' ';
	for (int count = 0 ; count < 5 ; count++)
	{
	    System.out.print ("Digit " + (count + 1) + ": ");
	    String input = br.readLine ();

	    if (input.length () == 0)
	    {
		System.out.println ("Input cannot be empty. Please enter a valid number.");
		count--;
	    }
	    else if (input.length () == 1 && Character.isDigit (input.charAt (0)))
	    {
		UserGuess [count] = Integer.parseInt (input);
	    }
	    else
	    {
		System.out.println ("Invalid input, enter a number between 1 and 9.");
		count--;
	    }
	}

	return UserGuess;
    }


    int UserInputVerify (int[] UserGuess, int[] code)
    {
	int rightnum = 0;
	int rightnumplace = 0;

	boolean[] matched = new boolean [5];

	for (int count = 0 ; count < 5 ; count++)
	{
	    if (UserGuess [count] == code [count])
	    {
		rightnumplace++;
		rightnum++;
		matched [count] = true;
	    }
	}

	for (int count = 0 ; count < 5 ; count++)
	{
	    if (UserGuess [count] != code [count])
	    {
		for (int count2 = 0 ; count2 < 5 ; count2++)
		{
		    if (!matched [count2] && UserGuess [count] == code [count2])
		    {
			rightnum++;
			matched [count2] = true;
			break;
		    }
		}
	    }
	}

	System.out.println ("You got " + rightnumplace + " numbers in the right place.");
	return rightnum;
    }


    void displayBoard (int[] [] board, int guessCount)
    {
	System.out.println ("\nBoard so far:");
	for (int count = 0 ; count <= guessCount ; count++)
	{
	    for (int count2 = 0 ; count2 < 5 ; count2++)
	    {
		if (board [count] [count2] == -1)
		{
		    System.out.print ("_ ");
		}
		else
		{
		    System.out.print (board [count] [count2] + " ");
		}
	    }
	    System.out.println ();
	}
    }
}
