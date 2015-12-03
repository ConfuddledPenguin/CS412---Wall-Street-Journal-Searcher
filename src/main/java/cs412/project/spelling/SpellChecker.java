package cs412.project.spelling;

import com.sun.xml.internal.bind.v2.util.CollisionCheckStack;
import cs412.project.config.WebConfig;
import org.languagetool.JLanguageTool;
import org.languagetool.MultiThreadedJLanguageTool;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.language.BritishEnglish;
import org.languagetool.rules.Rule;
import org.languagetool.rules.RuleMatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Thomas on 20/11/2015.
 */
@Component
public class SpellChecker {

    @Autowired
    private AmericanEnglish americanEnglish;

    public List<String> spellCheck(String string){

        MultiThreadedJLanguageTool spellChecker = new MultiThreadedJLanguageTool(americanEnglish);
        List<RuleMatch> matches = new ArrayList<>();
        try{
			spellChecker.disableRule("UPPERCASE_SENTENCE_START");
            matches = spellChecker.check(string);
	        spellChecker.shutdown();
        }catch(IOException e){
			e.printStackTrace();
	        return Collections.emptyList();
        }

        List<String> corrections = new ArrayList<>();
        for(RuleMatch match: matches){

	        int i = 0;
			for(String suggestion: match.getSuggestedReplacements()){
				if(i == 3) break; else i++;
				corrections.add(string.substring(0, match.getFromPos()) + "<span class=\"bold\">" + suggestion + "</span>" + string.substring(match.getToPos(), string.length()));
			}
        }

        return corrections;
    }
}
