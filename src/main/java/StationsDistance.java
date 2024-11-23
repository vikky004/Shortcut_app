import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class StationsDistance {


    public String UpdateInBetweenDistance(Util db, int StationOneId, int StationTwoId, int InBetweenDistance){

        try {
            PreparedStatement query = db.con.prepareStatement("Update StationsDistance set InBetweenDistance = ? where (StationOneId = ? and StationTwoId = ?) or (StationOneId = ? and StationTwoId = ?)");
            query.setInt(1,InBetweenDistance);
            query.setInt(2,StationOneId);
            query.setInt(3,StationTwoId);
            query.setInt(4,StationTwoId);
            query.setInt(5,StationOneId);
            query.executeUpdate();
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
                ResultSet resultrows = query.executeQuery();
                if (resultrows == null) {
                    query = db.con.prepareStatement("Insert Into StationsDistance(StationOneId, StationTwoId, InBetweenDistance) Values (?,?,?)", Statement.RETURN_GENERATED_KEYS);
                    query.setInt(1, StationOneId);
                    query.setInt(2, StationTwoId);
                    query.setInt(3, InBetweenDistance);
                    query.executeUpdate();
                    ResultSet keyset = query.getGeneratedKeys();
                    keyset.next();
                } else {
                    UpdateInBetweenDistance(db, StationOneId, StationTwoId, InBetweenDistance);
                }
                db.con.close();
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
