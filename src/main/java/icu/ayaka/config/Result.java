package icu.ayaka.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private Boolean success;
    private String errorMsg;
    private Integer code;

    public static Result fail(String errorMsg){
        return new Result(false, errorMsg, null);
    }

    public static Result fail(String errorMsg,Integer code){
        return new Result(false, errorMsg, code);
    }

}
