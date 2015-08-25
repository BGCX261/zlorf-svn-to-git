package org.zlorf.controller;

/**
 *
 * @author JaggardM
 */
public class InvalidOptionException extends Exception
{

	public InvalidOptionException(String reason)
	{
		super(reason);
	}

	public InvalidOptionException()
	{
		super();
	}

	public InvalidOptionException(Throwable cause)
	{
		super(cause);
	}
}
