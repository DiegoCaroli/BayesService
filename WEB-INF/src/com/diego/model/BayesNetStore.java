package com.diego.model;

import java.util.HashMap;
import java.util.Map;

import com.diego.BayesANDJess.BayesNet;

public class BayesNetStore {
	
	private static BayesNetStore instance;
	private Map<String, BayesNet> bayesNets;

	private BayesNetStore() {
		bayesNets = new HashMap<>();
	}

	public static BayesNetStore getInstance() {
		if (instance == null) {
			instance = new BayesNetStore();
		}
		return instance;
	}
	
	public Map<String, BayesNet> getBayesNets() {
        return bayesNets;
    }
	
	public void addNet(String nameNet, BayesNet bayesNet) {
		bayesNets.put(nameNet, bayesNet);
	}
	
	public BayesNet getNet(String nameNet) {
		return bayesNets.get(nameNet);
	}

}


