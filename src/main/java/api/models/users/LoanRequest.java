package api.models.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoanRequest {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("amount")
    @Expose
    private Integer amount;
}
