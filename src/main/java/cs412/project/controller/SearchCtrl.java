package cs412.project.controller;

import cs412.project.model.SearchObject;
import cs412.project.response.JSONResponse;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Tom on 21/10/2015.
 */
@RestController
public class SearchCtrl {

    @RequestMapping(value = "/api/search", method = RequestMethod.POST)
    public JSONResponse<String> performSearch(@RequestBody SearchObject searchObject){

        System.out.println(searchObject.getSearchString());
        System.out.println(searchObject.getTest());

        //TODO perform search

        JSONResponse<String> response = new JSONResponse<>();                       //TODO update for return type of list<result> or something

        response.setSuccessful(true);
        response.setResult("You searched for " + searchObject.getSearchString());   //TODO return search results

        return response;
    }
}
