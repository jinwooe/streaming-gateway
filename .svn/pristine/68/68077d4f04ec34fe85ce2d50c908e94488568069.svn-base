package com.skcc.rtspgw.tcp;

public class ResponseData {
    private final String tag = "RESP";
    private final int width;
    private final int height;
    private final int length;
    private final byte[] bytes;

    public ResponseData(int width, int height, int length, byte[] bytes) {
        this.width = width;
        this.height = height;
        this.length = length;
        this.bytes = bytes;
    }

    public ResponseData(int length, byte[] bytes) {
        this(1920, 1080, length, bytes);
    }

    public String getTag() {
        return tag;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getLength() {
        return length;
    }

    public byte[] getBytes() {
        return bytes;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "tag='" + tag + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", length=" + length +
                ", bytes=<not displayable>" +
                '}';
    }
}
