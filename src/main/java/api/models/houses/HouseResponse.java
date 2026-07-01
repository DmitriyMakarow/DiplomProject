package api.models.houses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import java.util.List;

@Data
public class HouseResponse {

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("floorCount")
    @Expose
    public int floorCount;

    @SerializedName("price")
    @Expose
    public double price;

    @SerializedName("parkingPlaces")
    @Expose
    public List<ParkingPlace> parkingPlaces;

    @SerializedName("lodgers")
    @Expose
    public List<Lodger> lodgers;

    @Data
    public static class ParkingPlace {
        @SerializedName("id")
        @Expose
        public int id;

        @SerializedName("isWarm")
        @Expose
        public boolean isWarm;

        @SerializedName("isCovered")
        @Expose
        public boolean isCovered;

        @SerializedName("placesCount")
        @Expose
        public int placesCount;
    }

    @Data
    public static class Lodger {
        @SerializedName("id")
        @Expose
        public int id;

        @SerializedName("firstName")
        @Expose
        public String firstName;

        @SerializedName("secondName")
        @Expose
        public String secondName;

        @SerializedName("age")
        @Expose
        public int age;

        @SerializedName("sex")
        @Expose
        public String sex;

        @SerializedName("money")
        @Expose
        public double money;
    }
}
