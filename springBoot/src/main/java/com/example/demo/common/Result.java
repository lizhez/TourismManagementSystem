package com.example.demo.common;

import lombok.Data;

@Data
public class Result<T>{

    private boolean flag;
    private T data;

    public Result(){}

    public Result(boolean flag){this.flag=flag;}

    public Result(T data){this.data = data;}

    public Result(boolean flag,T data){
        this.flag=flag;
        this.data=data;
    }

    public static Result success(){
        Result result = new Result<>();
        result.setFlag(true);
        return result;
    }

    public static <T> Result<T> success(T data){
        Result result = new Result<>(data);
        result.setFlag(true);
        return result;
    }

    public static Result error(){
        Result result = new Result();
        result.setFlag(false);
        return result;
    }
}