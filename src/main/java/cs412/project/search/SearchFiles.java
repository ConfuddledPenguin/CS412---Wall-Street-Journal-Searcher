
package cs412.project.search;

import cs412.project.config.Config;
import cs412.project.model.Result;
import cs412.project.model.SearchObject;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Simple command-line based search demo.
 */
public class SearchFiles {

	List<Result> resultsList = new ArrayList<Result>();
	List<Result> authorResultsList = new ArrayList<Result>();
	List<Result> titleResultsList = new ArrayList<Result>();
	List<Result> dateResultsList = new ArrayList<Result>();
	List<Result> dateRangeList = new ArrayList<Result>();

	private SearchObject searchObject;

	/**
	 * Simple command-line based search demo.
	 */
	public void search(SearchObject so) throws Exception {


		searchObject = so;
		String index = Config.indexPath;
		String field = "contents";


		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(index).toPath()));
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer();

		BufferedReader in;
		in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		QueryParser parser = new QueryParser(field, analyzer);

		//Before this we will need to check if the search objects Date, Author field etc have been set
		Query query = parser.parse(so.getSearchString());

		if(Config.debug) System.out.println("Searching: " + query.toString());

		doPagingSearch(in, searcher, query);

		reader.close();
	}

	/**
	 * This demonstrates a typical paging search scenario, where the search engine presents
	 * pages of size n to the user. The user can then go to the next page if interested in
	 * the next hits.
	 * <p>
	 * When the query is executed for the first time, then only enough results are collected
	 * to fill 5 result pages. If the user wants to page beyond this limit, then the query
	 * is executed another time and all hits are collected.
	 */
	public void doPagingSearch(BufferedReader in, IndexSearcher searcher, Query query) throws Exception {

		// Collect enough docs to show 5 pages
		TopDocs results = searcher.search(query, searchObject.getStartAt() * searchObject.getPerPage(), Sort.RELEVANCE);
		ScoreDoc[] hits = results.scoreDocs;

		int startAt = (searchObject.getStartAt() - 1 ) * searchObject.getPerPage();

		for (int i = startAt; i < hits.length; i++) {
			Document doc = searcher.doc(hits[i].doc);

			String path = doc.get("path");
			if (path != null) {

				FileInfo fi = new FileInfo(path);

                addToResults(fi, path);
			}
		}
		if(!resultsList.isEmpty()){
			constructAuthorList();
			constructTitleList();
			constructDateList();
			constructDateRangeList();
		}
	}

	public void addToResults(FileInfo fi, String path) {

		FileNameMagic magic = new FileNameMagic();

		String lp = fi.getLeadingParagraph();
		if(lp == null)
			lp = fi.getArticleText();

		if(lp.length() > 400)
			lp = lp.substring(0, 397) + "...";

		String headline = "";

		if(fi.getHeadline().contains("----")){
			headline = fi.getHeadline().split("----")[0];
		}else
			headline = fi.getHeadline();

		Result r = new Result(magic.FileNamePoint(path), headline, lp);

		String author = fi.getAuthor();

		author = (author == null) ? "Author Unknown" : author;

		r.setDate(fi.getDate());
		r.setAuthor(author);
		resultsList.add(r);
	}

	private void constructAuthorList(){
		if ((searchObject.getAuthor() != null)){
			for (Result r : resultsList) {
				if (r.getAuthor().toLowerCase().trim().equals(searchObject.getAuthor().toLowerCase().trim())) {
					authorResultsList.add(r);
				}
				System.out.println(r.getAuthor().toLowerCase() + " 0 " + searchObject.getAuthor().toLowerCase());
			}
		}
	}

	private void constructTitleList(){
		if ((searchObject.getTitle() != null)){
			for (Result r : resultsList) {
				if (r.getAbstractText().toLowerCase().contains(searchObject.getTitle().toLowerCase())) {
					titleResultsList.add(r);
				}
			}
		}
	}

	private void constructDateList(){
		if ((searchObject.getDate() != null)) {
			for (Result r : resultsList) {
				if (r.getDate().equals(searchObject.getDate())) {
					dateResultsList.add(r);
				}
			}
		}
	}

	private void constructDateRangeList() throws Exception{
		if ((searchObject.getStartDate() != null && searchObject.getEndDate() != null)) {
			String startDate = searchObject.getStartDate().trim();
			String endDate = searchObject.getEndDate().trim();
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			Date startDateMaker, endDateMaker;


			startDateMaker = df.parse(startDate);
			endDateMaker = df.parse(endDate);


			for (Result r : resultsList) {
				Date resultDate = df.parse(r.getDate().trim());
				if (resultDate.after(startDateMaker) && resultDate.before(endDateMaker)) {
					dateRangeList.add(r);
				}
			}
		}

	}

	public List<Result> getResultsList() {
		return resultsList;
	}

	public List<Result> getAuthorResultsList() {
		return authorResultsList;
	}

	public List<Result> getDateResultsList() {
		return dateResultsList;
	}

	public List<Result> getDateRangeList() {
		return dateRangeList;
	}

	public List<Result> getTitleList() {
		return titleResultsList;
	}
}