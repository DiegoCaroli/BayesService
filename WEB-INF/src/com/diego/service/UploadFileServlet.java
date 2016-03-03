package com.diego.service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
 
@WebServlet("/UploadFileServlet")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
                 maxFileSize=1024*1024*10,      // 10MB
                 maxRequestSize=1024*1024*50)   // 50MB

public class UploadFileServlet extends HttpServlet {

	private static final String SAVE_DIR = "Documents";
    
    /**
     * handles file upload
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // gets absolute path of the web application
        String appPath = getServletContext().getRealPath("");
        // constructs path of the directory to save uploaded file
        String savePath = appPath + File.separator + SAVE_DIR;
        
        //String savePath = appPath + File.separator + SAVE_DIR;
         
        // creates the save directory if it does not exists
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
        
        for (Part part : request.getParts()) {
            String fileName = extractFileName(part);
            part.write(savePath + File.separator + fileName);
        }
        
        response.setContentType("text/html");

		PrintWriter out = response.getWriter();

		out.println( "<?xml version = \"1.0\"?>" );
		out.println( "<!DOCTYPE html PUBLIC \"-//W3C//DTD " +
				"XHTML 1.0 Strict//EN\" \"http://www.w3.org" +
				"/TR/xhtml1/DTD/xhtml1-strict.dtd\">" ); 

		out.println( 
				"<html xmlns = \"http://www.w3.org/1999/xhtml\">" );

		out.println("<head>" );
		out.println("<title>Upload</title>" );
		out.println("</head>" );
		out.println("<body>" );
		out.println("<h3>");
		out.println("Upload has been done successfully!");
		out.println("</h3");
		out.println("<br>");
		
		out.println("<input type=\"button\" value=\"Menu\" class=\"submit\" onClick=\"location.href='index.html'\">");

		out.println( "</body>" );

		out.println( "</html>" );

		out.close();
    }
 
    /**
     * Extracts file name from HTTP header content-disposition
     */
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }
}
