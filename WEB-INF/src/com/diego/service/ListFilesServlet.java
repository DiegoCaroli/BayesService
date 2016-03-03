package com.diego.service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@WebServlet("/ListFileServlet")
public class ListFilesServlet extends HttpServlet {
	
	private static final String SAVE_DIR = "Documents";
	private List<String> files;
	
	protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
		// gets absolute path of the web application
        String appPath = request.getServletContext().getRealPath("");
        // constructs path of the directory to save uploaded file
        String savePath = appPath + File.separator + SAVE_DIR;
		
        files = new ArrayList();
		File directory = new File(savePath);
		
        //get all the files from a directory
        File[] fileList = directory.listFiles();
        for (File file : fileList){
            if (file.isFile()){
            	if (file.getName().endsWith(".csv")) {
            		files.add(file.getName());
            	}
            }
        }
        
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        
        out.write(createJSON(files).toString());
        out.close();

	}
	
	private JSONObject createJSON(List<String> files){
		
		JSONArray list = new JSONArray();
		for (String file : files) {
			JSONObject name = new JSONObject();
			name.put("name", file);
			list.add(name);
		}
		 //create Json Object
        JSONObject json = new JSONObject();
        
        json.put("files", list);
        
		return json;
	}
}
