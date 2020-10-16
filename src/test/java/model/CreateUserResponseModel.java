package model;

import lombok.Data;
//tsk0
@Data
public class CreateUserResponseModel {
    private String name;
    private String job;
    private Integer id;
    private String createdAt;
//    private Instant createdAt;

}
