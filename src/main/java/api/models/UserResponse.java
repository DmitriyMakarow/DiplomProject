package api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserResponse {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("firstName")
        @Expose
        public String firstName;
        @SerializedName("secondName")
        @Expose
        public String secondName;
        @SerializedName("age")
        @Expose
        public Integer age;
        @SerializedName("sex")
        @Expose
        public String sex;
        @SerializedName("money")
        @Expose
        public Integer money;
    }
