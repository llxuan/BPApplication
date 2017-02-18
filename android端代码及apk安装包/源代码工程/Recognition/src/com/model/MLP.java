package com.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MLP implements Serializable {
	private static final long serialVersionUID = 2L;
	public static double STEP = 0.5;
	public static final double Momentum = 0.5;
	public static final int[] LAYER_NUM = new int[] { 784, 256, 10 };
	private List<Neuron[]> net = new ArrayList<Neuron[]>();

	public MLP() {
		Random random = new Random();
		Neuron[] in_layer = new Neuron[LAYER_NUM[0]];
		for (int i = 0; i < LAYER_NUM[0]; ++i) {
			in_layer[i] = new Neuron(null, null, 0);
		}
		net.add(in_layer);
		for (int i = 1; i < LAYER_NUM.length; ++i) {
			Neuron[] layer = new Neuron[LAYER_NUM[i]];
			for (int k = 0; k < LAYER_NUM[i]; ++k) {
				double[] weights = new double[LAYER_NUM[i - 1]];
				double[] oldWeights = new double[LAYER_NUM[i - 1]];
				for (int j = 0; j < LAYER_NUM[i - 1]; ++j) {
					weights[j] = (random.nextDouble() - 0.5) * 4.8 / LAYER_NUM[i - 1];
					oldWeights[j] = weights[j];
				}
				layer[k] = new Neuron(oldWeights, weights, 0);
			}
			net.add(layer);
		}
	}

	public double[] foreword(int[] data) {
		for (int i = 0; i < LAYER_NUM[0]; ++i) {
			net.get(0)[i].setOutput(data[i]);
		}
		for (int i = 1; i < LAYER_NUM.length; ++i) {
			for (int k = 0; k < LAYER_NUM[i]; ++k) {
				double[] weights = net.get(i)[k].getWeights();
				double sum = 0;
				for (int j = 0; j < weights.length; ++j) {
					sum += weights[j] * net.get(i - 1)[j].getOutput();
				}
				net.get(i)[k].setOutput(sigmoid(sum - net.get(i)[k].getThreshold()));
			}
		}
		double[] output = new double[LAYER_NUM[LAYER_NUM.length - 1]];
		for (int i = 0; i < output.length; ++i) {
			output[i] = net.get(LAYER_NUM.length - 1)[i].getOutput();
		}

		return output;
	}

	public void back(double[] data, double[] output) {
		for (int i = 0; i < LAYER_NUM[LAYER_NUM.length - 1]; ++i) {
			double[] weights = net.get(LAYER_NUM.length - 1)[i].getWeights();
			double[] oldWeights = net.get(LAYER_NUM.length - 1)[i].getOldWeights();
			double o = net.get(LAYER_NUM.length - 1)[i].getOutput();
			double delta = (-1) * (data[i] - output[i]) * o * (1 - o);
			net.get(LAYER_NUM.length - 1)[i].setDelta(delta);
			for (int k = 0; k < weights.length; ++k) {
				double newWeight = weights[k] - Momentum * (weights[k] - oldWeights[k])
						- (1 - Momentum) * STEP * (delta * net.get(LAYER_NUM.length - 2)[k].getOutput());
				oldWeights[k] = weights[k];
				weights[k] = newWeight;
			}
		}
		for (int i = LAYER_NUM.length - 2; i > 0; --i) {
			for (int k = 0; k < LAYER_NUM[i]; ++k) {
				double[] weights = net.get(i)[k].getWeights();
				double[] oldWeights = net.get(i)[k].getOldWeights();
				double o = net.get(i)[k].getOutput();
				double sum = 0;
				for (int t = 0; t < net.get(i + 1).length; ++t) {
					sum += net.get(i + 1)[t].getWeights()[k] * net.get(i + 1)[t].getDelta();
				}
				double delta = sum * o * (1 - o);
				net.get(i)[k].setDelta(delta);
				for (int j = 0; j < weights.length; ++j) {
					double newWeight = weights[j] - Momentum * (weights[j] - oldWeights[j])
							- (1 - Momentum) * STEP * (delta * net.get(i - 1)[j].getOutput());
					oldWeights[j] = weights[j];
					weights[j] = newWeight;
				}
			}
		}
	}

	private double sigmoid(double input) {
		return 1 / (1 + Math.pow(Math.E, (-1) * input));
	}
}