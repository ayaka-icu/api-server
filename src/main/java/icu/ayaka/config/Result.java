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
    private Object data;
    private String errorMsg;
    private Integer code;

    public static Result ok(){
        return new Result(true,  null, null,null);
    }
    public static Result ok(Object data){
        return new Result(true, data,null,200);
    }
    public static Result ok(List<?> data, Integer code){
        return new Result(true, data,null, code);
    }

    public static Result fail(String errorMsg){
        return new Result(false,null, errorMsg, null);
    }

    public static Result fail(String errorMsg,Integer code){
        return new Result(false,null, errorMsg, code);
    }

}
