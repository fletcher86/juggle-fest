package com.fletcher;

import java.util.LinkedHashMap;
import java.util.Map;

public class Juggler
{
	private String name;
	private Integer handEye;
	private Integer endurance;
	private Integer pizzaz;
	private boolean isAssigned;
	private Circuit assignedCircuit;
	private String[] circuitPrefs;
	private Map<String, Circuit> circuitPrefsMap;
	private Map<String, Integer> circuitScoreMap;

	public Juggler()
	{
		circuitPrefsMap = new LinkedHashMap<>();
		circuitScoreMap = new LinkedHashMap<>();
		isAssigned = false;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Integer getHandEye()
	{
		return handEye;
	}

	public void setHandEye(Integer handEye)
	{
		this.handEye = handEye;
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

	public String[] getCircuitPrefs()
	{
		return circuitPrefs;
	}

	public void setCircuitPrefs(String[] circuitPrefs)
	{
		this.circuitPrefs = circuitPrefs;
	}

	public Integer getCircuitScore(Circuit c)
	{
		Integer score = circuitScoreMap.get(c.getName());

		if (score == null)
		{
			score = c.getHandToEye() * this.getHandEye() + c.getEndurance() * this.getEndurance()
					+ c.getPizzaz() * this.getPizzaz();

			circuitScoreMap.put(c.getName(), score);
			
		}
		return score;
	}

	public Map<String, Integer> getCircuitScoreMap()
	{
		return circuitScoreMap;
	}

	public void setCircuitScoreMap(Map<String, Integer> circuitScore)
	{
		this.circuitScoreMap = circuitScore;
	}

	public Map<String, Circuit> getCircuitPrefsMap()
	{
		return circuitPrefsMap;
	}

	public void setCircuitPrefsMap(Map<String, Circuit> circuitPrefsMap)
	{
		this.circuitPrefsMap = circuitPrefsMap;
	}

	public boolean isAssigned()
	{
		return isAssigned;
	}

	public void setAssigned(boolean isAssigned)
	{
		this.isAssigned = isAssigned;
	}

	public Circuit getAssignedCircuit()
	{
		return assignedCircuit;
	}

	public void setAssignedCircuit(Circuit assignedCircuit)
	{
		this.assignedCircuit = assignedCircuit;
	}

}
