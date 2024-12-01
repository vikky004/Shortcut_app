import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StationsDistanceHandler implements Handler{
    @Override
    public void handleProcess(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.getWriter().println("StationsDistanceHandler");
        ResponseUtil rm = new ResponseUtil(true,"StationsDistanceHandler",200);
        rm.sen_response(res,new JsonObject());
    }
}
