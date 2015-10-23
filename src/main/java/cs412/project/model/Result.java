package cs412.project.model;

/**
 * Created by Thomas on 10/23/2015.
 */
public class Result {

    String filepath;
    String title;
    String abstractText;

    public Result(String filepath, String title, String abstractText) {
        this.filepath = filepath;
        this.title = title;
        this.abstractText = abstractText;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
