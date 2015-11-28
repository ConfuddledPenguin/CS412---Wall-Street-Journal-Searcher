package cs412.project.response;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tom on 21/10/2015.
 */
public class JSONResponse<T> {

    private boolean successful = false;
    private String message = null;
    private Map<String, Object> meta = new HashMap<>();
    private T result = null;

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getMeta() {
        return meta;
    }

    public void setMeta(Map<String, Object> meta) {
        this.meta = meta;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
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
