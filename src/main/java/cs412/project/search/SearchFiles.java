
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
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Simple command-line based search demo.
 */
public class SearchFiles {

	List<Result> resultsList = new ArrayList<Result>();

	/**
	 * Simple command-line based search demo.
	 */
	public void search(SearchObject so) throws Exception {


		String index = Config.indexPath;
		String field = "contents";
		boolean raw = false;
		int hitsPerPage = 10;


		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(index).toPath()));
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer();

		BufferedReader in;
		in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		QueryParser parser = new QueryParser(field, analyzer);

		Query query = parser.parse(so.getSearchString());

		doPagingSearch(in, searcher, query, hitsPerPage, raw, true);

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
	public void doPagingSearch(BufferedReader in, IndexSearcher searcher, Query query,
	                           int hitsPerPage, boolean raw, boolean interactive) throws IOException {

		// Collect enough docs to show 5 pages
		TopDocs results = searcher.search(query, 5 * hitsPerPage);
		ScoreDoc[] hits = results.scoreDocs;

		for (int i = 0; i < hits.length; i++) {
			Document doc = searcher.doc(hits[i].doc);
			String path = doc.get("path");
			if (path != null) {
				printArticleInfo(path);
			}
		}
	}

	public void printArticleInfo(String path) {
		FileInfo fi = new FileInfo(path);

		FileNameMagic magic = new FileNameMagic();

		Result r = new Result(magic.FileNamePoint(path), fi.getHeadline(), fi.getHeadline());
		r.setDate(fi.getDate());
		r.setAuthor(fi.getAuthor());
		resultsList.add(r);
	}

	public List<Result> getResultsList() {
		return resultsList;
	}
}




