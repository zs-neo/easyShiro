/*
 * Author github: https://github.com/zs-neo
 * Author Email: 2931622851@qq.com
 */
package com.example.demo.support.wrapper;

import com.example.demo.support.utils.RequestUtils;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * 1.预防xss攻击 2.增强HttpServletRequestWrapper
 * 其实效率偏低,从流中读取前台传过来的内容转为String，进行过滤，再转回输入流写到request中
 *
 * @author zhousheng
 * @version 1.0
 * @since 2020/5/28 13:47
 */
public class RequestWraper extends HttpServletRequestWrapper {
	
	private final byte[] body;
	
	public RequestWraper(HttpServletRequest request) {
		super(request);
		this.body = RequestUtils.getByteBody(request);
	}
	
	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream inputStream = new ByteArrayInputStream(body);
		return getServletInputStream(inputStream);
	}
	
	private ServletInputStream getServletInputStream(ByteArrayInputStream inputStream) {
		return new ServletInputStream() {
			@Override
			public boolean isFinished() {
				return false;
			}
			
			@Override
			public boolean isReady() {
				return false;
			}
			
			@Override
			public void setReadListener(ReadListener readListener) {
			
			}
			
			@Override
			public int read() throws IOException {
				return inputStream.read();
			}
		};
	}
	
	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);
		if (values == null) {
			return null;
		}
		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			encodedValues[i] = xssExcape(values[i]);
		}
		return encodedValues;
	}
	
	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);
		if (value == null) {
			return null;
		}
		return xssExcape(value);
	}
	
	public String getHeader(String name) {
		String value = super.getHeader(name);
		if (value == null) {
			return null;
		}
		return xssExcape(value);
	}
	
	public String getQueryString() {
		String value = super.getQueryString();
		if (value == null) {
			return null;
		}
		return xssExcape(value);
	}
	
	public String xssExcape(String param) {
		return HtmlUtils.htmlEscape(param);
	}
}
