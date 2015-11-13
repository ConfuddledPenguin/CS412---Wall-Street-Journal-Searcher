package cs412.project.search;

import java.util.List;

import org.apache.lucene.demo.SearchFiles;

import cs412.project.model.Result;
import cs412.project.model.SearchObject;

/**
 * Created by Thomas on 10/23/2015.
 */
public class Searcher implements SearchI{


    @Override
    public List<Result> performSearch(SearchObject searchObject) {

            SearchFiles sf = new SearchFiles();
            
            try {
				sf.search(searchObject);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            return sf.getResultsList();
    }
}