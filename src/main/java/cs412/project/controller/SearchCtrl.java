package cs412.project.controller;

import cs412.project.model.Result;
import cs412.project.model.SearchObject;
import cs412.project.response.JSONResponse;
import cs412.project.search.SearchI;
import cs412.project.search.Searcher;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tom on 21/10/2015.
 */
@RestController
public class SearchCtrl {

    @RequestMapping(value = "/api/search", method = RequestMethod.POST)
    public JSONResponse<List<Result>> performSearch(@RequestBody SearchObject searchObject){

        System.out.println(searchObject.getSearchString());
        System.out.println(searchObject.getTest());

        SearchI searcher = new Searcher();

        List<Result> results = searcher.performSearch(searchObject);

        JSONResponse<List<Result>> response = new JSONResponse<>();

        response.setSuccessful(true);
        response.setResult(results);

        return response;
    }

    @RequestMapping(value = "/api/autoSearch", method = RequestMethod.POST)
    public JSONResponse<List<String>> autoSearch(@RequestBody SearchObject searchObject){

        System.out.println(searchObject.getSearchString());
        System.out.println(searchObject.getTest());

        SearchI searcher = new Searcher();

        List<Result> results = searcher.performSearch(searchObject);

        JSONResponse<List<String>> response = new JSONResponse<>();

	    List<String> out = new ArrayList<>();
	    out.add("Not done yet, go away");

        response.setSuccessful(false);
        response.setResult(out);

        return response;
    }

}
