package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


import java.util.List;
//tsk0
@Data
public class TestTempl {

    private List<JsonData> data;
    private AD ad;
    private Integer page;
    private Integer total;


    @JsonProperty(value = "per_page")
    private Integer perPage;

    @JsonProperty(value = "total_pages")
    private Integer totalPages;


}

