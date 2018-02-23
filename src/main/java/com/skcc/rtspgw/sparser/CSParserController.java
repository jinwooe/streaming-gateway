package com.skcc.rtspgw.sparser;

public class CSParserController {
    public CSParserController(long cameraId, String rtspUrl) {
    }

    public void setDataCallback(OnDataCallback onDataCallback) {
    }

    public void setEnableRgb(boolean rgb) {
    }

    public void setEnableJpeg(boolean jpeg, int i, int i1) {
    }

    public void start() {
    }

    public boolean isEnabledRgb() {
        return true;
    }

    public boolean isEnabledJpeg() {
        return true;
    }

    public interface OnDataCallback {
        void onRgbData(long cameraId, byte[] buf, int bufSize, int width, int height);
        void onJpegData(long cameraId, byte[] buf, int bufSize, int width, int height);
    }
}
