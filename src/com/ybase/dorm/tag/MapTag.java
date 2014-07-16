package com.ybase.dorm.tag;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class MapTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	private String key;
	private Map<?, ?> items;

	public void setKey(String key) {
		this.key = key;
	}

	public void setItems(Map<?, ?> items) {
		this.items = items;
	}

	@Override
	public int doStartTag() throws JspException {
		if (items == null || items.size() <= 0 || key == null || "".equals(key.trim())) {
			return SKIP_BODY;
		} else {
			JspWriter jw = this.pageContext.getOut();
			try {
				jw.println(items.get(key));
				return EVAL_BODY_INCLUDE;
			} catch (IOException e) {
				throw new JspException(e.getMessage());
			}
		}
	}

	@Override
	public int doEndTag() {
		return EVAL_PAGE;
	}
}
