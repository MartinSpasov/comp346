import common.Semaphore;

/**
 * Class BlockStack
 * Implements character block stack and operations upon it.
 *
 * $Revision: 1.4 $
 * $Last Revision Date: 2018/02/04 $
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca;
 * Inspired by an earlier code by Prof. D. Probst

 */
class BlockStack
{
	/**
	 * # of letters in the English alphabet + 2
	 */
	public static final int MAX_SIZE = 28;

	/**
	 * Default stack size
	 */
	public static final int DEFAULT_SIZE = 6;

	/**
	 * Current size of the stack
	 */
	private int iSize = DEFAULT_SIZE;

	/**
	 * Current top of the stack
	 */
	private int iTop  = 3;

	/**
	 * stack[0:5] with four defined values
	 */
	private char acStack[] = new char[] {'a', 'b', 'c', 'd', '$', '$'};







	//The access counter variable
	private int accessCounter = 0;
	//The semaphore that guards the critical section, set as signaled
	private Semaphore accessCounterMutex = new Semaphore(1);
	//Helper function to safely increment the access counter
	private void incrementAccessCouter()
	{
		accessCounterMutex.Wait();
		++accessCounter;
		accessCounterMutex.Signal();
	}

	public int getAccessCounter()
	{
		return accessCounter;
	}

	//Basic implementation to get the code compiling for task 1
	public int getTop()
	{
		return iTop;
	}

	public int getSize()
	{
		return iSize;
	}

	public boolean isEmpty()
	{
		return iTop == -1;
	}




	/**
	 * Default constructor
	 */
	public BlockStack()
	{
	}

	/**
	 * Supplied size
	 */
	public BlockStack(final int piSize)
	{


                if(piSize != DEFAULT_SIZE)
		{
			this.acStack = new char[piSize];

			// Fill in with letters of the alphabet and keep
			// 2 free blocks
			for(int i = 0; i < piSize - 2; i++)
				this.acStack[i] = (char)('a' + i);

			this.acStack[piSize - 2] = this.acStack[piSize - 1] = '$';

			this.iTop = piSize - 3;
                        this.iSize = piSize;
		}
	}

	/**
	 * Picks a value from the top without modifying the stack
	 * @return top element of the stack, char
	 */
	public char pick()
	{
		incrementAccessCouter();
		return this.acStack[this.iTop];
	}

	/**
	 * Returns arbitrary value from the stack array
	 * @return the element, char
	 */
	public char getAt(final int piPosition) throws Exception
	{
	    if (piPosition < 0)
        {
            throw new Exception("getAt was called with an invalid parameter: " + (piPosition));
        }
		incrementAccessCouter();
		return this.acStack[piPosition];
	}

	/**
	 * Standard push operation
	 */
	public void push(final char pcBlock) throws PushException
	{
		//Handle case when stack is empty
		if (iTop == 0)
		{
			acStack[++iTop] = 'a';
			return;
		}

		//Throw of stack is full
		int newIndex = ++this.iTop;
		if (newIndex > iSize)
        {
            throw new PushException();
        }

		this.acStack[newIndex] = pcBlock;
		incrementAccessCouter();
	}

	/**
	 * Standard pop operation
	 * @return ex-top element of the stack, char
	 */
	public char pop() throws PopException
	{
	    //Throw if stack is empty
	    if (iTop < 0)
        {
            throw new PopException();
        }

		char cBlock = this.acStack[this.iTop];
		this.acStack[this.iTop--] = '$'; // Leave prev. value undefined
		incrementAccessCouter();
		return cBlock;
	}
}

// EOF
