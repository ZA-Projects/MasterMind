import java.io.*;
class MasterMindMain
{
    public static void main (String args[])
	throws java.io.IOException
    {
	BufferedReader br = new BufferedReader (new InputStreamReader (System.in));

	MasterMindMethods mmm = new MasterMindMethods ();

	// boolean pwdverify = mmm.Login ();
	// if (pwdverify == true)
	// {
	//     System.out.println ("Password Accepted");
	// }
	// else
	// {
	//     System.out.println ("Password Incorrect");
	// }
	int code[] = mmm.CodeGeneration ();

	int UserGuess[] = mmm.UserInput (code);

    }
}
class MasterMindMethods
{
    boolean pwdverify = true;
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
		System.out.print ("Please enter your password: ");
		input = br.readLine ();

		if (!input.equals (password))
		{
		    System.out.print ("Please enter your password: ");
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
	    FileWriter fw = new FileWriter (name + ".txt");

	    System.out.print ("Create a password: ");
	    password = br.readLine ();
	    fw.write (password + "\r\n");
	    fw.close ();

	    pwdverify = true;
	}
	return pwdverify;
    }


    int[] CodeGeneration ()
    {
	int[] code = new int [5];
	for (int count = 0 ; count < 5 ; count++)
	{
	    code [count] = (int) (Math.random () * 10) + 1;
	}
	return code;
    }


    int[] UserInput (int code[])
	throws java.io.IOException
    {
	BufferedReader br = new BufferedReader (new InputStreamReader (System.in));

	String input;
	int[] UserGuess = new int [5];
	System.out.println ("Guess the 5 digit code (Hint = its between 1-10)");
	for (int count = 0 ; count < 5 ; count++)
	{
	    System.out.print ("Digit " + (count+1) + ":");
	    input = br.readLine ();
	    UserGuess [count] = Integer.parseInt (input);
	    System.out.println();
	}
	return UserGuess;
    }
}


