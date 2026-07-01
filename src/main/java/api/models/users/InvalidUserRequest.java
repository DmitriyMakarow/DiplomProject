package api.models.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InvalidUserRequest {
    @SerializedName("firstName")
    @Expose
    private Object firstName;

    @SerializedName("secondName")
    @Expose
    private Object secondName;

    @SerializedName("age")
    @Expose
    private Object age;

    @SerializedName("sex")
    @Expose
    private Object sex;

    @SerializedName("money")
    @Expose
    private Object money;

    private String description;
}
