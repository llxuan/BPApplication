package com.model;

public class NumLog {
	private int count = 0;
	private double probability = 0;

	public int getCount() {
		return count;
	}

	public double getProbability() {
		return probability;
	}

	public void updateNumber(double newData) {
		probability = (probability * count + newData) / (count + 1);
		++count;
	}

	public void clear() {
		count = 0;
		probability = 0;
	}
}
