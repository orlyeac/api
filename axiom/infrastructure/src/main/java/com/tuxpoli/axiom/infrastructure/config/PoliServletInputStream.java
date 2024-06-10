package com.tuxpoli.axiom.infrastructure.config;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PoliServletInputStream extends ServletInputStream {

    private InputStream inputStream;

    public PoliServletInputStream(byte[] payload) {
        this.inputStream = new ByteArrayInputStream(payload);
    }

    @Override
    public boolean isFinished() {
        try {
            return inputStream.available() == 0;
        }
        catch (IOException iOException) {
            return true;
        }
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public int read() throws IOException {
        return inputStream.read();
    }

    public void setReadListener(ReadListener readListener) {
    }

}
