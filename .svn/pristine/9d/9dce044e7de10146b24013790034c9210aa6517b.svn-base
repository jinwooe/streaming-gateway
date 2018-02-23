package com.skcc.rtspgw.stream;

import com.skcc.rtspgw.sparser.CSParserController;
import com.skcc.rtspgw.tcp.ResponseData;
import com.skcc.rtspgw.utils.CircularQueue;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class StreamManager implements CSParserController.OnDataCallback {
    private static Logger logger = LoggerFactory.getLogger(StreamManager.class);

    private final long cameraId;
    private final String rtspUrl;

    private List<ChannelHandlerContext> tcpChannelHandlerContexts = new CopyOnWriteArrayList<>();
    private List<HttpHandlerContext> httpHandlerContexts = new CopyOnWriteArrayList<>();

    private CircularQueue<byte[]> rgbQueue = new CircularQueue<>(50);
    private CircularQueue<byte[]> jpegQueue = new CircularQueue<>(50);

    private final CSParserController csParserController;

    ExecutorService executorService =  Executors.newFixedThreadPool(3);


    public StreamManager(long cameraId, String rtspUrl, boolean rgb, boolean jpeg) {
        this.cameraId = cameraId;
        this.rtspUrl = rtspUrl;
        csParserController = new CSParserController(cameraId, rtspUrl);
        csParserController.setDataCallback(this);
        csParserController.setEnableRgb(rgb);
        csParserController.setEnableJpeg(jpeg, 640, 480);
        csParserController.start();

        executorService.submit(new RetrievalRgbTask(rgbQueue));
        executorService.submit(new RetrievalJpegTask(jpegQueue));
    }

    public CSParserController getCsParserController() {
        return csParserController;
    }

    public List<ChannelHandlerContext> getTcpChannelHandlerContexts() {
        return tcpChannelHandlerContexts;
    }

    public List<HttpHandlerContext> getHttpHandlerContexts() {
        return httpHandlerContexts;
    }

    @Override
    public void onRgbData(long cameraId, byte[] buf, int bufSize, int width, int height) {
        rgbQueue.add(buf);
    }

    @Override
    public void onJpegData(long cameraId, byte[] buf, int bufSize, int width, int height) {
        jpegQueue.add(buf);
    }

    class RetrievalRgbTask implements Runnable {

        private CircularQueue<byte[]> queue;

        public RetrievalRgbTask(CircularQueue<byte[]> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while(true) {
                byte[] bytes = queue.poll();
                if (bytes != null) {
                    for (ChannelHandlerContext ctx : tcpChannelHandlerContexts) {
                        ResponseData responseData = new ResponseData(bytes.length, bytes);
                        ctx.writeAndFlush(responseData);
                    }
                }

                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    logger.warn(e.getMessage());
                }
            }
        }
    }

    class RetrievalJpegTask implements Runnable {
        private CircularQueue<byte[]> queue;

        public RetrievalJpegTask(CircularQueue<byte[]> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while(true) {
                byte[] bytes = queue.poll(1000, TimeUnit.SECONDS);
                if (bytes != null) {
                    for (HttpHandlerContext ctx : httpHandlerContexts) {
                        ctx.write(bytes);
                    }
                }
            }
        }
    }
}
