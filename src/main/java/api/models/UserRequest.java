package api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {

    @SerializedName("firstName")
    @Expose
    private String firstName;

    @SerializedName("secondName")
    @Expose
    private String secondName;

    @SerializedName("age")
    @Expose
    private long age;

    @SerializedName("sex")
    @Expose
    private String sex;

    @SerializedName("money")
    @Expose
    private double money;

    @SerializedName("amount")
    @Expose
    private int amount;
}
