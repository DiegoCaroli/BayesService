package com.diego.BayesANDJess;


import java.util.ArrayList;

import jess.JessException;
import jess.RU;
import jess.Rete;
public class JessEngine {
	private Rete engine;
	private ArrayList<String> templateNames;
	private String result = "";
	
	public JessEngine(){
		this.engine = new Rete();
		templateNames = new ArrayList<String>();
		//engine.executeCommand("(defrule DepositoScelta (Mezzo ?m) => (store \"Mezzo\" ?m))");}
	}
	
	private String checkBlankSpace(String ruleName){
		char [] stringArray = ruleName.toCharArray();
		String toReturn="";
		for(int i=0; i<stringArray.length; i++){
			if(stringArray[i]==' ')
				toReturn  = toReturn+"_";
			else
				toReturn = toReturn+stringArray[i];
		}
		return toReturn;
	}
	
	public void addTemplate(String toInfer){
		String command = "(deftemplate Result_"+checkBlankSpace(toInfer)+" (slot "+checkBlankSpace(toInfer)+") (slot Reliability))";
		try {
			System.out.println("Inviato comando "+command);
			System.out.println(engine.executeCommand(command));
		} catch (JessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.templateNames.add(checkBlankSpace(toInfer));
	}
	
	public void addRule(String ruleName, String lhsRule, String rhsRule){
		//TODO check assert in rhsRule
		String command = "(defrule "+ruleName+" ";
		command = command+lhsRule+" => "+rhsRule; //TODO check parentesis
		System.out.println("Invio comando "+command);
		try {
			System.out.println(engine.executeCommand(command));
		} catch (JessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void defStoreRules(){
		//engine.executeCommand("(defrule DepositoScelta (Mezzo ?m) => (store \"Mezzo\" ?m))");}
		for(int i=0; i<this.templateNames.size(); i++){
			String command = "(defrule DepositoScelta"+i+
					" (Result_"+templateNames.get(i)+" ("+templateNames.get(i)+" ?m) (Reliability ?r)) => (store \""+templateNames.get(i)+"\" ?m))";
			System.out.println("Inviato comando "+command);
			try {
				System.out.println(engine.executeCommand(command));
			} catch (JessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String infer(ArrayList<Observation> observations, ArrayList<String> toFetchs){
		defStoreRules();

		if (toFetchs.isEmpty()) {
			return result;
		} else {
			//Store facts
			for(int i=0; i<observations.size(); i++){
				//engine.store(observations.get(i).getName(), 
				//new Value(observations.get(i).getValue(), RU.ATOM));
				try {
					System.out.println(engine.executeCommand("(assert ("+observations.get(i).getName()+" "+observations.get(i).getValue()+"))"));
				} catch (JessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Aggiunto fatto "+observations.get(i).getName()+" value: "+observations.get(i).getValue());
			}
			//run rules
			try {
				System.out.println(engine.executeCommand("(watch all)"));
				System.out.println(engine.executeCommand("(facts)"));
				System.out.println(engine.executeCommand("(run)"));
			} catch (JessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(engine.fetch(toFetchs.get(0)) != null && toFetchs.size() > 1){
				try {
					result += engine.fetch(toFetchs.get(0)).stringValue(engine.getGlobalContext()) + ", ";
				} catch (JessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (engine.fetch(toFetchs.get(0)) != null) {
				try {
					result += engine.fetch(toFetchs.get(0)).stringValue(engine.getGlobalContext());
				} catch (JessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			toFetchs.remove(0);
			return infer(observations, toFetchs);
		}
	}
	
}
