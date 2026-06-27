package api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserReguest {
    @SerializedName("amount")
    @Expose
    private int amount;
}
