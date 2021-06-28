package com.pavliuk.spring.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.web.util.HtmlUtils;

public class XSSRequestWrapper extends HttpServletRequestWrapper {
    public XSSRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = htmlEscape(values[i]);
        }

        return encodedValues;
    }

    @Override
    public String getParameter(String name) {
        return htmlEscape(super.getParameter(name));
    }

    public String htmlEscape(String value) {
        if (value == null) {
            return null;
        }

        return HtmlUtils.htmlEscape(value);
    }
}
