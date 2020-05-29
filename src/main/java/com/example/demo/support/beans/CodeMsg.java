package com.example.demo.support.beans;

public class CodeMsg {
	
	private int code;
	private String msg;
	
	public CodeMsg(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	/**
	 * 通用异常
	 */
	public static CodeMsg SUCCESS = new CodeMsg(0, "success");
	public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
	public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常：%s");
	
	/**
	 * 登录模块 5002XX
	 */
	public static CodeMsg USERNAME_EMPTY = new CodeMsg(500210, "用户名不能为空");
	public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500211, "登录密码不能为空");
	public static CodeMsg PASSWORD_ERROR = new CodeMsg(500212, "密码错误");
	public static CodeMsg UNKONWN_ACCOUNT_ERROR = new CodeMsg(500213, "未知账户");
	public static CodeMsg LOCKED_ACCOUNT_ERROR = new CodeMsg(500214, "账户已锁定");
	public static CodeMsg ATTEMPT_ACCOUNT_ERROR = new CodeMsg(500215, "用户名或密码错误次数太多");
	public static CodeMsg ACCOUNT_ERROR = new CodeMsg(500216, "验证未通过");
	public static CodeMsg NO_PERMISSIONS = new CodeMsg(500217, "无此权限");
	
	public int getCode() {
		return code;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public CodeMsg fillArgs(Object... args) {
		int code = this.code;
		String message = String.format(this.msg, args);
		return new CodeMsg(code, message);
	}
	
	@Override
	public String toString() {
		return "CodeMsg{" +
				"code=" + code +
				", msg='" + msg + '\'' +
				'}';
	}
}
