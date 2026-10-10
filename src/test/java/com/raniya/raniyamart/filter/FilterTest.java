package com.raniya.raniyamart.filter;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class FilterTest {

    @Test
    void testSecurityHeadersFilterAppendsHeaders() throws IOException, ServletException {
        SecurityHeadersFilter filter = new SecurityHeadersFilter();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        filter.doFilter(req, resp, chain);

        verify(resp).setHeader("X-Content-Type-Options", "nosniff");
        verify(resp).setHeader("X-Frame-Options", "SAMEORIGIN");
        verify(resp).setHeader("X-XSS-Protection", "1; mode=block");
        verify(chain).doFilter(req, resp);
    }
}
