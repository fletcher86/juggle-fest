package com.fletcher;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Circuit
{
	private String name;
	private Integer handToEye;
	private Integer endurance;
	private Integer pizzaz;
	private List<Juggler> assignedJugglers = new ArrayList<>();

	private static Map<String, Circuit> circuits = new LinkedHashMap<>();

	private Circuit()
	{
	}

	public static Circuit getCircuitInstance(String name)
	{
		Circuit c = circuits.get(name);
		if (c == null)
		{
			c = new Circuit();
			circuits.put(name, c);
		}

		return c;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Integer getHandToEye()
	{
		return handToEye;
	}

	public void setHandToEye(Integer handToEye)
	{
		this.handToEye = handToEye;
	}

	public Integer getEndurance()
	{
		return endurance;
	}

	public void setEndurance(Integer endurance)
	{
		this.endurance = endurance;
	}

	public Integer getPizzaz()
	{
		return pizzaz;
	}

	public void setPizzaz(Integer pizzaz)
	{
		this.pizzaz = pizzaz;
	}

	public List<Juggler> getAssignedJugglers()
	{
		return assignedJugglers;
	}

	public void addJuggler(Juggler j)
	{
		this.assignedJugglers.add(j);
	}

	public void removeJuggler(Juggler j)
	{
		this.assignedJugglers.remove(j);
	}

	public Juggler getJugglerWithLowestScore()
	{
		Juggler rtnJuggler = null;
		int lowval = 9999;
		for (Juggler j : assignedJugglers)
		{
			int score = j.getCircuitScore(this);
			if (score < lowval)
			{
				lowval = score;
				rtnJuggler = j;
			}
		}

		return rtnJuggler;
	}

	public static Circuit getAlternateCircuit(Juggler j, int maxPerCircuit)
	{
		Circuit optCircuit = null;
		int bestscore = -999;

		for (Circuit c : circuits.values())
		{
			/*
			 * If circuit is already maxed out, skip
			 */
			if (c.getAssignedJugglers().size() >= maxPerCircuit)
			{
				continue;
			}
			/*
			 * if circuit is already a preference we have to exclude it because juggler wasn't
			 * qualified or already got booted out because of a better qualified juggler.
			 */
			if (j.getCircuitPrefsMap().containsKey(c.getName()))
			{
				continue;
			}
			/*
			 * only include circuits the juggler may be able to compete in
			 */
			if (j.getCircuitScore(c) > bestscore)
			{
				bestscore = j.getCircuitScore(c);
				optCircuit = c;
			}
		}

		if (optCircuit == null)
		{
			System.out.print("");
		}
		return optCircuit;
	}

	public static Map<String, Circuit> getAllCircuits()
	{
		return circuits;
	}

}
