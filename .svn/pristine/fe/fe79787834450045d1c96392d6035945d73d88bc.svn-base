package com.skcc.rtspgw.tcp;

public class FailedResponse {
    private final String tag = "FAIL";
    private final int messageLength;
    private final String message;

    public FailedResponse(String message) {
        this.messageLength = message.getBytes().length;
        this.message = message;
    }

    public String getTag() {
        return tag;
    }

    public int getMessageLength() {
        return messageLength;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "FailedResponse{" +
                "tag='" + tag + '\'' +
                ", messageLength=" + messageLength +
                ", message='" + message + '\'' +
                '}';
    }
}
