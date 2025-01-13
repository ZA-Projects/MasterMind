import java.io.*;

class MasterMindMain
{
    public static void main (String args[]) throws java.io.IOException
    {
	BufferedReader br = new BufferedReader (new InputStreamReader (System.in));
	MasterMindMethods mmm = new MasterMindMethods ();

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

	if (!gameWon)
	{
	    System.out.println ("Game Over! You've used all guesses.");
	}
    }
}

class MasterMindMethods
{
    BufferedReader br = new BufferedReader (new InputStreamReader (System.in));

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
	for (int count = 0 ; count < 5 ; count++)
	{
	    System.out.print ("Digit " + (count + 1) + ": ");
	    String input = br.readLine ();
	    UserGuess [count] = Integer.parseInt (input);
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
