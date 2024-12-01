import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StationsHandler implements Handler{
    @Override
    public void handleProcess(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.getWriter().println("StationsHandler");
        ResponseUtil rm = new ResponseUtil(true,"StationsHandler",200);
        rm.sen_response(res,new JsonObject());
    }
}
