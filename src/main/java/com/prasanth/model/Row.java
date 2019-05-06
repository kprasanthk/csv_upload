package com.prasanth.model;

import java.util.List;

public class Row {

	private int id;
	private int decision;
	private List<Integer> variables;
	
	public Row() {
		
	}
	
	public Row(int id, int decision, List<Integer> variables) {
		this.id=id;
		this.decision=decision;
		this.variables=variables;
	}
	
	public Row(List<Integer> variables) {
		this.variables=variables;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDecision() {
		return decision;
	}
	public void setDecision(int decision) {
		this.decision = decision;
	}
	public List<Integer> getVariables() {
		return variables;
	}
	public void setVariables(List<Integer> variables) {
		this.variables = variables;
	}
	
	
}
