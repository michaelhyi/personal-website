package com.michaelyi.personalwebsite;

import org.springframework.mock.web.MockHttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.michaelyi.personalwebsite.util.HttpResponse;

public abstract class IntegrationTest extends TestContainers {
    protected static final ObjectMapper MAPPER = new ObjectMapper();
    protected static final ObjectWriter WRITER = MAPPER.writer();

    protected String getError(MockHttpServletResponse servletResponse) throws Exception {
        String json = servletResponse.getContentAsString();
        HttpResponse res = MAPPER.readValue(json, HttpResponse.class);
        return res.getError();
    }
}
