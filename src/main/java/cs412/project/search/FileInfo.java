package cs412.project.search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class FileInfo {
	private String filePath;
	
	public FileInfo(String filePath){
		this.filePath = filePath;
	}
	
	private String getAllText(String path){
		try {
			return new Scanner(new File(path)).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error";
		}
	}
	
	public String getHeadline(){
		Document doc = Jsoup.parse(getAllText(this.filePath));
		Elements e=doc.select("HL"); 
		return e.text();
	}
	
	public String getDate(){
		Document doc = Jsoup.parse(getAllText(this.filePath));
		Elements e=doc.select("DATE"); 
		return e.text();
	}
	
	public String getLeadingParagraph(){
		Document doc = Jsoup.parse(getAllText(this.filePath));
		Elements e=doc.select("LP"); 
		return e.text();
	}
	
	public String getArticleText(){
		Document doc = Jsoup.parse(getAllText(this.filePath));
		Elements e=doc.select("TEXT"); 
		return e.text();
	}
	
	public String getDocumentId(){
		Document doc = Jsoup.parse(getAllText(this.filePath));
		Elements e=doc.select("DOCID"); 
		return e.text();
	}
	
	public String getDocumentNumber(){
		Document doc = Jsoup.parse(getAllText(this.filePath));
		Elements e=doc.select("DOCNO"); 
		return e.text();
	}
}
