import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/stations/*")
public class Stations extends HttpServlet {
    Util db = new Util();
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        db.init();
        res.setContentType("application/json");
        String json = new Util().request_reader(req);
        PrintWriter out = res.getWriter();
        String requestType = req.getMethod();
        JsonObject responseJson = null;

        //Read data
        Gson gson = new Gson();
        StationsDetails stationsDetails = gson.fromJson(json, StationsDetails.class);
//
        //SuccessMessage and StatusCode
        String message = "Error printing message";
        int statusCode = 404;

        //Filter based on the method
        switch (requestType){
            case "GET":
                responseJson = getShortcut();
                message = "Successfully Retrieved";
                statusCode = 200;
                break;
            case "POST":
                responseJson = CreateStations(db,stationsDetails.StationOneId, stationsDetails.StationTwoId, stationsDetails.InBetweenDistance);
                if(responseJson.get("data")!=null){
                    message = "Successfully Created";
                    statusCode = 201;
                }
                else{
                    message = "StationId does not exist";
                    statusCode = 404;
                }
                break;
            case "UPDATE":
                responseJson = UpdateStations();
                message = "Successfully Updated";
                statusCode = 200;
                break;
            case "DELETE":
                responseJson = DeleteShortcut();
                message = "Successfully Deleted";
                statusCode = 204;
                break;
        }

        ResponseUtil rm = new ResponseUtil(true,message,statusCode);
        rm.sen_response(res,responseJson);
    }

    private JsonObject getShortcut(){
        JsonObject Shortcut = new JsonObject();
        Shortcut.addProperty("data", new ShortcutUtil().getShortcutValue(1,3,db));
        return Shortcut;
    }
    private JsonObject CreateStations(Util db, int StationOneId, int StationTwoId, int InBetweenDistance){
        JsonObject Shortcut = new JsonObject();
        Shortcut.addProperty("data", new StationsDistance().CreateInBetweenDistance(db,StationOneId, StationTwoId, InBetweenDistance));
        return Shortcut;
    }
    private JsonObject UpdateStations(){
        return new JsonObject();
    }
    private JsonObject DeleteShortcut(){
        return new JsonObject();
    }

    private class StationsDetails{
        int StationOneId;
        int StationTwoId;
        int InBetweenDistance;
    }
}