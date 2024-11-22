import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseUtil {
    boolean success;
    String message;
    int code;

    public ResponseUtil(boolean success, String message, int error_code) {
        this.success = success;
        this.message = message;
        this.code = error_code;
    }
    public void sen_response(HttpServletResponse response,JsonArray data) throws IOException {
        response.setStatus(this.code);
        PrintWriter out = response.getWriter();
        String r_obj = new Gson().toJson(this,ResponseUtil.class);
        JsonObject out_object = new Gson().fromJson(r_obj,JsonObject.class);
        out_object.add("data",data);
        out.println(out_object);
    }
    public void sen_response(HttpServletResponse response, JsonElement data) throws IOException {
        response.setStatus(this.code);
        PrintWriter out = response.getWriter();
        String r_obj = new Gson().toJson(this,ResponseUtil.class);
        JsonObject out_object = new Gson().fromJson(r_obj,JsonObject.class);
        out_object.add("data",data);
        out.println(out_object);
    }
    public void sen_response(HttpServletResponse response, JsonObject data) throws IOException {
        response.setStatus(this.code);
        PrintWriter out = response.getWriter();
        String r_obj = new Gson().toJson(this,ResponseUtil.class);
        JsonObject out_object = new Gson().fromJson(r_obj,JsonObject.class);
        out_object.add("data",data);
        out.println(out_object);
    }
}