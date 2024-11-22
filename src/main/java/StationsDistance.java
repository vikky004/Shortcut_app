import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StationsDistance {


    public String UpdateInBetweenDistance(Util db, int StationOneId, int StationTwoId, int InBetweenDistance){

        try {
            PreparedStatement query = db.con.prepareStatement("Update StationsDistance from StationsDistance where (StationOneId = ? and StationTwoId = ?) Or (StationOneId = ? and StationTwoId = ?) set InBetweenDistance = ?");
            query.setInt(1,StationOneId);
            query.setInt(2,StationTwoId);
            query.setInt(3,StationTwoId);
            query.setInt(4,StationOneId);
            query.setInt(5,InBetweenDistance);
            ResultSet resultSet = query.executeQuery();
        } catch (Exception e) {
            System.out.println(e);
        }
        return "null";
    }

    public String CreateInBetweenDistance(Util db, int StationOneId, int StationTwoId, int InBetweenDistance){

        try {
            //check for Station's Existence
            if(checkStationsExistence(StationOneId, StationTwoId, db)) {
                //Check for existence in StationsDistance
                PreparedStatement query = db.con.prepareStatement("Select StationsDistanceId from StationsDistance where (StationOneId = ? and StationTwoId = ?) Or (StationOneId = ? and StationTwoId = ?)");
                query.setInt(1, StationOneId);
                query.setInt(2, StationTwoId);
                query.setInt(3, StationTwoId);
                query.setInt(4, StationOneId);
                ResultSet resultSet = query.executeQuery();
                if (resultSet == null) {
                    query = db.con.prepareStatement("Insert Into StationsDistance Values (?,?,?)");
                    query.setInt(1, StationOneId);
                    query.setInt(2, StationTwoId);
                    query.setInt(3, InBetweenDistance);
                    resultSet = query.executeQuery();
                } else {
                    UpdateInBetweenDistance(db, StationOneId, StationTwoId, InBetweenDistance);
                }
            }
            else{
                return null;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return "Success";
    }

    public boolean checkStationsExistence(int StationOneId, int StationTwoId, Util db){
        try {
            //Check for existence
            PreparedStatement query = db.con.prepareStatement("Select station_Id from Stations where (station_Id = ?)");
            query.setInt(1,StationOneId);
            ResultSet resultSet1 = query.executeQuery();
            query = db.con.prepareStatement("Select station_Id from Stations where (station_Id = ?)");
            query.setInt(1,StationTwoId);
            ResultSet resultSet2 = query.executeQuery();

            if( resultSet1.next() && resultSet2.next()){
                return true;
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}
