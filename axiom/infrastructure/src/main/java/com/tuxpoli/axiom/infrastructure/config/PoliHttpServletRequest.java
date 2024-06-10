package com.tuxpoli.axiom.infrastructure.config;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.util.StreamUtils;

import java.io.*;

public class PoliHttpServletRequest extends HttpServletRequestWrapper {

    private byte[] payload;

    public PoliHttpServletRequest(HttpServletRequest httpServletRequest) throws IOException {
        super(httpServletRequest);
        InputStream inputStream = httpServletRequest.getInputStream();
        this.payload = StreamUtils.copyToByteArray(inputStream);
    }

    @Override
    public ServletInputStream getInputStream() {
        return new PoliServletInputStream(payload);
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(
                new InputStreamReader(
                        new ByteArrayInputStream(payload)
                )
        );
    }
}
