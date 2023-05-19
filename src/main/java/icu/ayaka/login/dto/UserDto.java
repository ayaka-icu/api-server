package icu.ayaka.login.dto;

import lombok.Data;

@Data
public class UserDTO {

    public static final String USER_DTO_ID = "id";
    public static final String USER_DTO_USERNAME = "username";
    public static final String USER_DTO_NICKNAME = "nickName";


    private Long id;
    private String username;
    private String nickName;
}
