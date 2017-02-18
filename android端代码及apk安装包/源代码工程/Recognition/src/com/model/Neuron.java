package com.model;

import java.io.Serializable;

public class Neuron implements Serializable {
	private static final long serialVersionUID = 1L;
	private double threshold;
	private double[] weights;
	private double output;
	private double delta;
	private double[] oldWeights;

	public Neuron(double[] oldWeights, double[] weights, double threshold) {
		this.oldWeights = oldWeights;
		this.weights = weights;
		this.threshold = threshold;
	}

	public double getOutput() {
		return output;
	}

	public void setOutput(double output) {
		this.output = output;
	}

	public double[] getWeights() {
		return weights;
	}

	public double getDelta() {
		return delta;
	}

	public void setDelta(double delta) {
		this.delta = delta;
	}

	public double getThreshold() {
		return threshold;
	}

	public double[] getOldWeights() {
		return oldWeights;
	}

}