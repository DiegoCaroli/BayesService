package com.diego.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.diego.BayesANDJess.BayesNet;
import com.diego.model.BayesNetStore;

@WebServlet("/ListNetsServlet")
public class ListNetsServlet extends HttpServlet {
	
	private Map<String, BayesNet> bayesNets;
	
	protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
		bayesNets = BayesNetStore.getInstance().getBayesNets();
		
		response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        
        out.write(createJSON().toString());
        out.close();
	}
	
	private JSONObject createJSON(){
		JSONArray list = new JSONArray();
		
		for (String key : bayesNets.keySet()) {
			JSONObject net = new JSONObject();
			
			net.put("name", key);
			net.put("id", bayesNets.get(key).toString());
			
			list.add(net);
		}

		 //create Json Object
        JSONObject json = new JSONObject();
        
        json.put("nets", list);
        
		return json;
	}
}
