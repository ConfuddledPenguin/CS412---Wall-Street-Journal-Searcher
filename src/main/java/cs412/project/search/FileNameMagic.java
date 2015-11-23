package cs412.project.search;

import cs412.project.config.Config;

/**
 * Created by Tom on 23/11/2015.
 */
public class FileNameMagic {

	public String FileNamePoint(String path){

		return path.replace(Config.filesPath, "articles/").replace("\\", "/") + ".html";

	}

}
