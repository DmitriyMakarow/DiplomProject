package api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CarResponse {

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("engineType")
    @Expose
    public String engineType;

    @SerializedName("mark")
    @Expose
    public String mark;

    @SerializedName("model")
    @Expose
    public String model;

    @SerializedName("price")
    @Expose
    public double price;

    public int getId() {
        return id;
    }
}
