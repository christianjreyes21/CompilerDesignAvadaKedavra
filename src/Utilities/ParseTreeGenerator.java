package Utilities;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;

public class ParseTreeGenerator {
	
	// creates w3c conform redirect HTML page
	public String createRedirectPage(String url){
	    return  "<!DOCTYPE HTML>" +
	            "<meta charset=\"UTF-8\">" +
	            "<meta http-equiv=\"refresh\" content=\"1; url=" + url + "\">" +
	            "<script>" +
	            "window.location.href = \"" + url + "\"" +
	            "</script>" +
	            "<title>Page Redirection</title>" +
	            "<!-- Note: don't tell people to `click` the link, just tell them that it is a link. -->" +
	            "If you are not redirected automatically, follow the <a href='" + url + "'>link</a>";
	}

	// creates temporary file with content of redirect HTML page
	public URI createRedirectTempFile(String url) {        
	    BufferedWriter writer = null;
	    File tmpFile = null;
	    try {
	        // creates temporary file
	        tmpFile = File.createTempFile("pophelp", ".html", null);
	        // deletes file when the virtual machine terminate
	        tmpFile.deleteOnExit(); 
	        // writes redirect page content to file 
	        writer = new BufferedWriter(new FileWriter(tmpFile));
	        writer.write(createRedirectPage(url));
	        writer.close();
	    }
	    catch (IOException e) {
	        return null;
	    }
	    return tmpFile.toURI();
	}
	
	public void generateTree(Node<String> node) throws IOException
	{
		String url = "file:///C:/Users/chris/Desktop/syntree-gh-pages/generate.html?"+URLEncoder.encode(node.toString(), "UTF-8");

		if (Desktop.isDesktopSupported()) {
		    Desktop desktop = Desktop.getDesktop();
		    if (desktop.isSupported(Desktop.Action.BROWSE)) {
		        desktop.browse(createRedirectTempFile(url.replace(" ", "%20")));
		    }
		}
	}

}
