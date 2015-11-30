package cs412.project.model;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Tom on 21/10/2015.
 */
public class SearchObject {

    private String searchString = null;
    private String author = null;
	private String title = null;
	private String date = null;
	private String startDate = null;
	private String endDate = null;
	private int startAt = 0;
	private int perPage  = 0;

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String date) {
		this.startDate = date;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String date) {
		this.endDate = date;
	}

	public int getStartAt() {
		return startAt;
	}

	public void setStartAt(int startAt) {
		this.startAt = startAt;
	}

	public int getPerPage() {
		return perPage;
	}

	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}

	@Override
	public String toString() {

		ObjectMapper mapper = new ObjectMapper();

		String object;
		try{
			object = mapper.writeValueAsString(this);
		}catch(Exception e){
			object = null;
			e.printStackTrace();
		}


		return object;
	}
}
