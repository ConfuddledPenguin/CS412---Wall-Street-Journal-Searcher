package cs412.project.spelling;

import cs412.project.config.WebConfig;
import org.languagetool.JLanguageTool;
import org.languagetool.MultiThreadedJLanguageTool;
import org.languagetool.language.BritishEnglish;
import org.languagetool.rules.RuleMatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 20/11/2015.
 */
@Component
public class SpellChecker {

    @Autowired
    private BritishEnglish britishEnglish;


    public List<String> spellCheck(String string){

        MultiThreadedJLanguageTool spellChecker = new MultiThreadedJLanguageTool(britishEnglish);
        List<RuleMatch> matches = new ArrayList<>();
        try{
            matches = spellChecker.check(string);
	        spellChecker.shutdown();
        }catch(IOException e){
			System.err.println(e.getStackTrace());
        }

        List<String> corrections = new ArrayList<>();
        for(RuleMatch match: matches){
            corrections.add(string.substring(0, match.getFromPos()) + match.getSuggestedReplacements().get(0) + string.substring(match.getToPos(), string.length()));
        }


        return corrections;

    }


}
