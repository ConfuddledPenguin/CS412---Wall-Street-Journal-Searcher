package cs412.project.controller;

import cs412.project.model.Result;
import cs412.project.model.SearchObject;
import cs412.project.response.JSONResponse;
import cs412.project.search.SearchI;
import cs412.project.search.Searcher;
import cs412.project.spelling.SpellChecker;
import org.languagetool.JLanguageTool;
import org.languagetool.language.BritishEnglish;
import org.languagetool.rules.RuleMatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tom on 21/10/2015.
 */
@RestController
public class SearchCtrl {

    @Autowired
    SpellChecker checker;

    @RequestMapping(value = "/api/search", method = RequestMethod.POST)
    public JSONResponse<Map<String, Object>> performSearch(@RequestBody SearchObject searchObject){


        //checking spelling
//        List<String> corrections = checker.spellCheck(searchObject.getSearchString());

        //perform search
        SearchI searcher = new Searcher();
        List<Result> results = searcher.performSearch(searchObject);

        //build response map
        Map<String, Object> r = new HashMap<>();
//        r.put("corrections", corrections);
        r.put("results", results);

        //build response object
        JSONResponse<Map<String, Object>> response = new JSONResponse<>();
        response.setSuccessful(true);
        response.setResult(r);

        return response;
    }

    @RequestMapping(value = "/api/autoSearch", method = RequestMethod.POST)
    public JSONResponse<List<String>> autoSearch(@RequestBody SearchObject searchObject){

        System.out.println(searchObject.getSearchString());

        SearchI searcher = new Searcher();

        JSONResponse<List<String>> response = new JSONResponse<>();

	    List<String> out = new ArrayList<>();
	    out.add("Not done yet, go away");

        response.setSuccessful(false);
        response.setResult(out);

        return response;
    }

}
