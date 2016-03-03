package com.diego.service;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.diego.BayesANDJess.BayesNet;
import com.diego.model.BayesNetStore;

@WebServlet("/CreateNetServlet")
public class CreateNetServlet extends HttpServlet {
	
	private static final String DIR_FILE = "Documents";

	
	protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
		
		String fileName = request.getParameter("filename");
		
		String appPath = getServletContext().getRealPath("");
		String csvFilePath = appPath + File.separator + DIR_FILE + File.separator + fileName;
		
		BayesNet bayesNet = new BayesNet(csvFilePath);
		
		BayesNetStore.getInstance().addNet(fileName, bayesNet);
		
	}
	
}
