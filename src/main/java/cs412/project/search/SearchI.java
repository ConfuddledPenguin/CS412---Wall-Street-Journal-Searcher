package cs412.project.search;

import cs412.project.model.Result;
import cs412.project.model.SearchObject;

import java.util.List;

/**
 * Created by Thomas on 10/23/2015.
 */
public interface SearchI {

    List<Result> performSearch(SearchObject searchObject);
}
