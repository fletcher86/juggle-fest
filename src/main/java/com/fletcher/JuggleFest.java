package com.fletcher;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class JuggleFest
{

	static List<Juggler> jugglerList = new ArrayList<>();

	/**
	 * Main entry point. To execute on command line
	 * 
	 * > java -jar juggle-fest-1.0.jar < jugglers.txt
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		String[] fileData = readFile();

		toStruct(fileData);

		assignJugglers();

		printOutput();

	}

	/**
	 * Read input file and return String[] where each element is line of input
	 * 
	 * @return String [] each element is line of input
	 * @throws FileNotFoundException
	 */
	public static String[] readFile() throws FileNotFoundException
	{
		final Scanner scanner = new Scanner(System.in);

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

	/**
	 * Populate data structures
	 * 
	 * @param lines
	 *            String[] represents the input files as array of strings where each
	 *            element is a line of input
	 */
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
					Juggler j = toJugglerStruct(lines[i]);
					jugglerList.add(j);
					break;
			}
		}

	}

	/**
	 * Populate the circuit data
	 * 
	 * @param line
	 *            String is the input line consisting of circuit data
	 */
	public static void toCircuitStruct(String line)
	{

		String[] data = line.split(" ");
		String name = data[1];

		Circuit c = Circuit.getCircuitInstance(name);
		c.setName(name);
		String[] hes = data[2].split(":");
		int he = Integer.parseInt(hes[1]);
		c.setHandToEye(he);

		String[] es = data[3].split(":");
		int e = Integer.parseInt(es[1]);
		c.setEndurance(e);

		String[] ps = data[4].split(":");
		int p = Integer.parseInt(ps[1]);
		c.setPizzaz(p);
	}

	/**
	 * Populate the juggler data
	 * 
	 * @param line
	 *            String represents the input for a juggler
	 * @return Juggler instance of
	 */
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

		j.setAssigned(false);

		String[] prefs = data[5].split(",");
		j.setCircuitPrefs(prefs);
		for (String pref : prefs)
		{
			Circuit c = Circuit.getCircuitInstance(pref);
			j.getCircuitPrefsMap().put(pref, c);
		}

		return j;
	}

	/**
	 * Assign jugglers to circuits
	 */
	public static void assignJugglers()
	{

		final int maxJugglersPerCircuit = jugglerList.size() / Circuit.getAllCircuits().size();

		while (!jugglerList.isEmpty())
		{
			int randomJugglerIndex = new Random().nextInt(jugglerList.size());
			Juggler nextJuggler = jugglerList.get(randomJugglerIndex);

			/*
			 * First try to assign juggler to circuit based on preferences
			 */
			for (Circuit c : nextJuggler.getCircuitPrefsMap().values())
			{
				/*
				 * if circuit is not full, add juggler to preferred circuit in order of jugglers
				 * preference
				 */
				if (c.getAssignedJugglers().size() < maxJugglersPerCircuit)
				{
					c.addJuggler(nextJuggler);
					jugglerList.remove(nextJuggler);
					nextJuggler.setAssigned(true);
					nextJuggler.setAssignedCircuit(c);
					break;
				}
				/*
				 * otherwise this circuit is already full, so lets see if this juggler may be a
				 * better fit than the lowest scoring juggler for this circuit. This means a
				 * juggler may lose his spot to someone more qualified.
				 */
				else
				{
					Juggler lastJuggler = c.getJugglerWithLowestScore();
					/*
					 * See if this juggler could be a better fit for this circuit.  If his score is
					 * higher than the lowest scoring juggler, then replace the low scoring juggler
					 */
					if (nextJuggler.getCircuitScore(c) > lastJuggler.getCircuitScore(c))
					{
						/*
						 * unseat the lowest scoring juggler
						 */

						c.removeJuggler(lastJuggler);
						lastJuggler.setAssigned(false);
						lastJuggler.setAssignedCircuit(null);
						jugglerList.add(lastJuggler);

						/*
						 * add the better scoring juggler to the circuit
						 */
						c.addJuggler(nextJuggler);
						jugglerList.remove(nextJuggler);
						nextJuggler.setAssigned(true);
						nextJuggler.setAssignedCircuit(c);
						break;
					}
				}
			}

			/*
			 * If juggler didn't get assigned based on their prefs, then assign for them
			 */

			if (!nextJuggler.isAssigned())
			{
				Circuit c = Circuit.getAlternateCircuit(nextJuggler, maxJugglersPerCircuit);
				c.addJuggler(nextJuggler);
				jugglerList.remove(nextJuggler);
				nextJuggler.setAssigned(true);
				nextJuggler.setAssignedCircuit(c);
			}
		}

	}

	/**
	 * Print the output
	 */
	public static void printOutput()
	{
		for (Circuit c : Circuit.getAllCircuits().values())
		{
			int ctr = 0;

			System.out.printf("%s", c.getName());
			for (Juggler j : c.getAssignedJugglers())
			{
				System.out.printf(" %s", j.getName());
				for (Circuit c1 : j.getCircuitPrefsMap().values())
				{
					System.out.printf(" %s:%d", c1.getName(), j.getCircuitScore(c1));
				}
				if (ctr < c.getAssignedJugglers().size() - 1)
					System.out.print(",");
				ctr++;
			}
			System.out.println();
		}
	}

}
