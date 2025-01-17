// gamesplayed

//     gameslost
//     bestgame
//     totalguesses

import java.io.*;

class MasterMindMain
{
    public static void main (String args[]) throws java.io.IOException
    {
	BufferedReader br = new BufferedReader (new InputStreamReader (System.in));
	MasterMindMethods mmm = new MasterMindMethods ();

	boolean pwdverify = mmm.Login ();
	if (pwdverify == true)
	{
	    System.out.println ("Password Accepted");
	}
	else
	{
	    System.out.println ("Password Incorrect");

	}

	while (pwdverify == true)
	{
	    int code[] = mmm.CodeGeneration ();
	    System.out.println ("Code generated (for debugging purposes):");
	    for (int count = 0 ; count < 5 ; count++)
	    {
		System.out.print (code [count] + " ");
	    }
	    System.out.println ();

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
		if (rightnum == 5)
		{
		    System.out.println ("Congratulations! You've guessed the correct code.");
		    gameWon = true;
		}
		else
		{
		    guessCount++;
		}
	    }

	    if (gameWon == false)
	    {
		System.out.println ("Game Over! You've used all guesses.");
	    }
	    else
	    {
		break;
	    }
	}
    }
}

class MasterMindMethods
{
    boolean pwdverify = false;
    String name, password, input;
    BufferedReader br = new BufferedReader (new InputStreamReader (System.in));

    boolean Login ()
	throws java.io.IOException
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
			if (Character.isLowerCase (ch) == true)
			{
			    LowerCaseverify = true;
			}
			if (Character.isUpperCase (ch) == true)
			{
			    UpperCaseverify = true;
			}
			if (Character.isDigit (ch) == true)
			{
			    Digitverify = true;
			}
			if (Character.isWhitespace (ch) == true)
			{
			    Whitespaceverify = true;
			}
			if (Character.isLowerCase (ch) == false && Character.isUpperCase (ch) == false && Character.isDigit (ch) == false && Character.isWhitespace (ch) == false)
			{
			    specialcharacterverify = true;
			}

		    }
		    if (LowerCaseverify == true && UpperCaseverify == true && Digitverify == true && Whitespaceverify == false && specialcharacterverify == false)
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
	    while (pwdverify == false)
		;

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

	    ch = input.charAt (0);
	    if (Character.isDigit (ch) == false)
	    {
		System.out.println ("Invalid input, enter a number");
		count--;
	    }
	    else
	    {
		UserGuess [count] = Integer.parseInt (ch + "");

	    }


	}


	return UserGuess;
    }


    int UserInputVerify (int[] UserGuess, int[] code)
    {
	int rightnum = 0;
	int rightnumplace = 0;
	for (int count = 0 ; count < 5 ; count++)
	{
	    if (UserGuess [count] == code [count])
	    {
		rightnumplace++;
		rightnum++;
	    }
	    else
	    {
		for (int count2 = 0 ; count2 < 5 ; count2++)
		{
		    if (UserGuess [count] == code [count2])
		    {
			rightnum++;
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
