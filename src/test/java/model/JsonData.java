package model;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
//tsk0
@Data
public class JsonData {

    private Integer id;
    private String email;
    private String avatar;

    @JsonProperty(value = "first_name")
    private String firstName;

    @JsonProperty(value = "last_name")
    private String lastName;


}
