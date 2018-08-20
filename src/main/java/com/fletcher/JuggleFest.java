package com.fletcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class JuggleFest
{

	/**
	 * Main entry point. To execute on command line
	 * 
	 * > java -jar triangle-max-sum-1.0.jar < triangle.txt
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		String[] fileData = readFile();
		
		toStruct(fileData);

		printInput(fileData);

	}

	/**
	 * Read input file and return String[] where each element is line of input
	 * 
	 * @return String [] each element is line of input
	 * @throws FileNotFoundException
	 */
	public static String[] readFile() throws FileNotFoundException
	{
		final Scanner scanner = new Scanner(new File("jugglers0.txt"));

		StringBuilder sb = new StringBuilder();

		while (scanner.hasNextLine())
		{
			String line = scanner.nextLine();
			if (line.trim().length() > 0)
				sb.append(line).append("###");
		}
		scanner.close();

		return sb.toString().split("###");
	}

	public static void printInput(String[] lines)
	{
		for (int i = 0; i < lines.length; i++)
		{
			System.out.println(lines[i]);
		}
	}

	public static void toStruct(String[] lines)
	{
		for (int i = 0; i < lines.length; i++)
		{
			String[] data = lines[i].split(" ");
			String tok = data[0];

			switch (tok)
			{
			case "C":
				toCircuitStruct(lines[i]);
				break;
			case "J":
				toJugglerStruct(lines[i]);
				break;
			}
		}

	}

	public static Circuit toCircuitStruct(String line)
	{
        Circuit c = new Circuit();
        
        String[] data = line.split(" ");
        c.setName(data[1]);
        String[] hes = data[2].split(":");
        int he = Integer.parseInt(hes[1]);
        c.setHandToEye(he);

        String[] es = data[3].split(":");
        int e = Integer.parseInt(es[1]);
        c.setEndurance(e);

        String[] ps = data[4].split(":");
        int p = Integer.parseInt(ps[1]);
        c.setPizzaz(p);

        return c;
	}

	public static Juggler toJugglerStruct(String line)
	{
		Juggler j = new Juggler();
		
        String[] data = line.split(" ");
        j.setName(data[1]);
        String[] hes = data[2].split(":");
        int he = Integer.parseInt(hes[1]);
        j.setHandEye(he);

        String[] es = data[3].split(":");
        int e = Integer.parseInt(es[1]);
        j.setEndurance(e);

        String[] ps = data[4].split(":");
        int p = Integer.parseInt(ps[1]);
        j.setPizzaz(p);
        
        String[] prefs = data[5].split(",");
        j.setCircuitPrefs(prefs);
        
        

        return j;
	}
}
