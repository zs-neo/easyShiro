package com.example.demo.support.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Result<T> {
  
  private int code;
  private String msg;
  private T data;
  
  public Result(T data) {
    this.code = 0;
    this.msg = "success";
    this.data = data;
  }
  
  public Result(CodeMsg codeMsg) {
    if (codeMsg == null) {
      return;
    }
    this.code = codeMsg.getCode();
    this.msg = codeMsg.getMsg();
  }
  
  public static <T> Result<T> success(T data) {
    return new Result<T>(data);
  }
  
  public static <T> Result<T> error(CodeMsg codeMsg) {
    return new Result<T>(codeMsg);
  }
  
}
