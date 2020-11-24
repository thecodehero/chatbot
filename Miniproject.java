/* ***************************************
   AUTHOR: Timothy Luckcock
		  Programming Miniproject:This program is a chatbot program 
		  that can have realistic conversations with people.
   ****************************************/
import java.util.Scanner; //import scanner library
import java.util.Random; //import random library
import java.util.Arrays; //import array library
import java.io.*; //imports standard java I/O library
class chatbotminiproject //class name is same as the name of the file
{
    public static void main (String[] param) throws IOException
    {
		final String endConversation = "bedtime";		

    		Scanner scanner = new Scanner(System.in); //creates scanner object

		details s1 = new details();	
		s1 = setSubject(s1, "Mathematics");
		s1 = setResponse(s1, "I also love Mathematics. I am very talented at it."); 

		details s2 = new details();
		s2 = setSubject(s2,"English");
		s2 = setResponse(s2,"Okay but I personally find English a boring subject");

		details s3 = new details();
		s3 = setSubject(s3,"Art"); 
		s3 = setResponse(s3,"Wonderful. I love art too.");

		String subject1 = getSubject(s1); //calls functions for returning subjects from records. 
		String subject2 = getSubject(s2);
		String subject3 = getSubject(s3); 

		String response1 = getResponse(s1);
		String response2 = getResponse(s2);
		String response3 = getResponse(s3);	

		TriggerResponseDatabase t = createEmptyReplies(4);
		t = setTriggerReply(t,"That very interesting. Thank you.");
		t = setTriggerReply(t,"That is cool. I like Computer Science because it is fun.");
		t = setTriggerReply(t,"Wow. That's really cool. My favourite sport is basketball.");
		t = setTriggerReply(t,"Delicious, my favourite food is pizza.");		
				
		String BestTopic = "0";
		String[] TriggerGroup = TriggerChoice();
		String[] TriggerMessage = ReadFile(TriggerGroup);

		while (!BestTopic.equals(endConversation)) 
		{ //while loop. Will stop loop when user enter's "bedtime"
		String sortChoice = sortOption(scanner); //calls method sortOption to ask user if they want to enter admin mode and sort record
			if (sortChoice.equals("y")) 
			{				
				SortTriggerReplies(t); //call method for sorting 
				for(int i=0;i <= 3;i++) 
				{
					System.out.println(getReply(t,i) + " Used " + getCount(t,i) + " times");				
				}				
			}
			else if (sortChoice.equals("Write"))
			{
				WriteFile(scanner,TriggerMessage);
			}			
			else 
			{			
				helloMessage(scanner);  //Calls method helloMessage()				
				String favouriteSubject = favSubject(scanner,subject1,subject2,subject3,response1,response2,response3,t,TriggerMessage);  // 
				BestTopic = favTopic(scanner,favouriteSubject); //calls function with arguments favourite subject. 	
			}	
		}	
			
		
		//record needs to be defined in main class.

		scanner.close(); //closes scanner
		System.exit(0);
		
    } // END main	
	
    public static String helloMessage(Scanner scanner) //function for greeting the user and asking how they are.               
    {        
	String wellbeing; //declares variable for storing wellbeing of user	S
    	System.out.println("Hello. How are you doing?"); /* Program introduces itself and
    	ask the person how they are.*/   	
	wellbeing = scanner.nextLine(); //reads input entered by user. 
    	System.out.println("Nice to meet you"); //program prints that it is nice to meet them    	
    	return wellbeing; //returns value      	
	
    } // END helloMessage         
    public static String favSubject (Scanner scanner, String subject1, String subject2, String subject3, String response1, String response2, String response3, TriggerResponseDatabase t, String[] TriggerMessage)  //initialisation method	                             
    {       
	System.out.println("I really enjoy learning Computer Science. "   
    	+ "It is my favourite subject. What is your favourite subject?");
    	String subject = scanner.nextLine(); //reads favourite subject entered by user. 
	if (subject.equals(subject1)) 
	{
		System.out.println(response1); //prints response
	}
	else if (subject.equals(subject2)) 
	{
		System.out.println(response2); //prints response
	}
	else if (subject.equals(subject3)) 
	{
		System.out.println(response3); //prints response
	}
	else //when user enters a subject that the program does not know
	{	
    	
		//loop within loops
		for (int i=0;i<=3;i++) //will iterate through array 'triggers'
		{
			int dicenumber = dicethrow(); //calls function for rolling virtual dice					
			String chosenResponse = TriggerResponse(dicenumber,TriggerMessage); //calls functions triggerresponse. 
			System.out.println(chosenResponse);
			String userResponse = scanner.nextLine(); //reads user's response to trigger message
			for(int j=0;j<=3;j++) //will iterate through array 'replies'
			{
				if (j == dicenumber) 
				{
					System.out.println(getReply(t,dicenumber)); //prints reply
					editCount(t,dicenumber); //calls function to count how often replies are used.
				}			
			}
		}
	} 
	return subject;      
    } // END favSubject   
     	public static String favTopic (Scanner scanner, String subject) 
		{ //method for asking user for favourite topic.
    		String topic; //declares variable for storing favourite topic 
			System.out.println("What is your favourite topic in " + subject); //asks user for their favourite topic
			topic = scanner.nextLine();
			return topic; 	
		} //END favTopic

     	public static String[] TriggerChoice() //method for initialising array
		{
			String[] triggers = new String[4]; //declares new array triggers
			triggers[0] = "Never heard of that subject before. Tell me something interesting."; //fills each index with values. 
			triggers[1] = "Okay. Why do you like it?";
			triggers[2] = "That is an interesting subject! What is your favourite sport?";
			triggers[3] = "Cool! What is your favourite food?";
			return triggers;	
		}		
	
		/* public static TriggerResponseDatabase[] TriggerReplyArray() //method for initialising array for multiple trigger-response records
		{
			TriggerResponseDatabase[] replies = new TriggerResponseDatabase[4]; //declares new array replies
			replies[0] = "That very interesting. Thank you.";
			replies[1] = "That is cool. I like Computer Science because it is fun.";
			replies[2] = "Wow. That's really cool. My favourite sport is basketball.";
			replies[3] = "Delicious, my favourite food is pizza.";
			return replies;
		} */

	public static int dicethrow() 
	{ //function for rolling virtual dice and picking random number. 
		int dice = (int)(Math.random()*3+0); //generate random number between 0 and 3 inclusive. 
		return dice; //returns value of dice. 
	} //END dicethrow	
	
	public static details setSubject(details s, String chosenSubject) //accessor method for storing subject
	{
		s.subject = chosenSubject;
		return s;
	} //END  setSubject

	public static details setResponse(details s, String chosenResponse) //accessor method for storing response
	{
		s.response = chosenResponse;
		return s;
	} //END setResponse

	//accessor methods to extract information 	

    public static String getSubject(details s)
	{		//accessor method for extracting subject from record
		return s.subject;
	} //END getResponse
	public static String getResponse(details s) //accessor method for extracting response from record. 
	{ 
		return s.response;
	} //END getResponse	

	public static String TriggerResponse(int diceresult, String[] choices)  //method for accessing array with number chosen by dice as the index. 
	{ 
		String response; //declare local variable for storing chosen trigger response.
		response = choices[diceresult]; //assigns value in array index to variable response. 
		return response; 
	}	
	
	public static TriggerResponseDatabase setTriggerReply(TriggerResponseDatabase t, String newReply) //accessor method for storing reply to trigger
	{
		if(t.numberreplies < t.replies.length) // Still space
		{
			t.replies[t.numberreplies] = newReply;
			t.countreplies[t.numberreplies] = 0;
			t.numberreplies = t.numberreplies + 1;			
		}		
		return t;		
	}
	
	public static String getReply(TriggerResponseDatabase t,int chosenindex) //method for accessing trigger response record.
	{
		String chosenReply = t.replies[chosenindex];
		return chosenReply;
	}
	
	public static TriggerResponseDatabase createEmptyReplies(int size) //method for accessing trigger response record 
	{
		TriggerResponseDatabase t = new TriggerResponseDatabase ();
		String[] a = new String[size];
		int[] b = new int[size];
		t.replies = a;
		t.countreplies = b;
		t.numberreplies = 0;		
		return t;		
	}
	
	public static TriggerResponseDatabase SortTriggerReplies(TriggerResponseDatabase t) //method for sorting trigger replies by how often they are used. 
	{
		for (int pass=1; pass <= t.countreplies.length-1; pass++)
		{
			for (int i =0; i < t.countreplies.length-pass; i++)
				if (t.countreplies[i] < t.countreplies[i+1])
				{
					swap(t, i);					
				}
		}
		return t;
	}
	
	public static TriggerResponseDatabase swap(TriggerResponseDatabase t, int i) //method for swapping entries in an array
	{
		int tmp1 = t.countreplies[i+1];
		t.countreplies[i+1] = t.countreplies[i];
		t.countreplies[i] = tmp1;
		
		String tmp2 = t.replies[i+1];
		t.replies[i+1] = t.replies[i];
		t.replies[i] = tmp2;
		return t;
	}			
	
	public static int getCount(TriggerResponseDatabase t, int entry) //accessor method to extract count of how often replies are used from record
	{
		int chosenEntry = t.countreplies[entry];
		return chosenEntry;
	}
	
	public static TriggerResponseDatabase editCount(TriggerResponseDatabase t, int entryNumber) //method for editing count for array storing count of how often replies are used. 
	{	
		t.countreplies[entryNumber] = t.countreplies[entryNumber]+1; //increases count by 1 for entry containg reply chosen.
		return t;
	}
	
	public static String sortOption(Scanner scanner) /* method with option for new person to start conversation, input data or enter and admin mode and sort
	trigger records in order of most used and print them in this order, giving number of times used.*/
	{
		System.out.println("Would you like to enter admin and sort records, input data or have conversations, or write to a file? Type 'y' for yes, 'Write' to write to a file anything else for 	  conversations?");
		String choice = scanner.nextLine(); //read input of user
		return choice;		
	}	

	public static String[] ReadFile(String[] TriggerCollection) throws IOException
	{
		BufferedReader inputStream = new BufferedReader(new FileReader("triggerfile.txt"));
		for (int i = 0; i <= 3; i++)
		{
			String readtrigger = inputStream.readLine();
			TriggerCollection[i] = readtrigger;
		}						
		inputStream.close();
		return TriggerCollection;	
	}	

	public static void WriteFile(Scanner scanner, String[] TriggerCollection) throws IOException //method for writing to a text file.
	{		
		PrintWriter outputStream = new PrintWriter(new FileWriter("triggerfile.txt")); //OutputStream created. new FileWriter opens the file name and creates new one if it doesn't exist.
		String inputTrigger;
		int j = 0;
		for (int i = 0; i <= 3; i++) 
		{
			
			System.out.println("Please enter new trigger. Type 'Stop' to stop entering triggers"); 
			inputTrigger = scanner.nextLine(); //reads user input and stores in variable inputTrigger
			if (inputTrigger.equals("Stop")) 
			{
				j = i;
				break;
			}	
			else 
			{
				outputStream.println(inputTrigger); //used to write to file
			}	 
			
		}		
		TriggerCollection = TriggerChoice();		
		for (int i = j; i <= 3; i++)
		{	 			
			outputStream.println(TriggerCollection[i]);
		}		
		outputStream.close(); //close a file				
	}		
	
	
} // END class chatbotminiproject 
class details {	//record containing subjects along with their responses.
	String subject;
	String response;
}
class TriggerResponseDatabase //trigger response record stored in an array. 
{
	 String[] replies; 
	 int numberreplies;
	 int[] countreplies;
}

