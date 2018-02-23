package com.skcc.rtspgw.stream;

import com.skcc.rtspgw.entity.CameraInfo;
import com.skcc.rtspgw.repository.CameraInfoRepository;
import com.skcc.rtspgw.service.ZookeeperService;
import com.skcc.rtspgw.sparser.CSParserController;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class StreamManagerActivator {

    @Autowired
    private ZookeeperService zookeeperService;

    @Autowired
    private CameraInfoRepository cameraInfoRepository;

    private Map<Long, StreamManager> streamManagerMap = new ConcurrentHashMap<>();

    public StreamManager activateStreamManagerForRgb(long cameraId, ChannelHandlerContext channelHandlerContext) {
        if(streamManagerMap.containsKey(cameraId)) {
            StreamManager streamManager = streamManagerMap.get(cameraId);
            CSParserController csParserController = streamManager.getCsParserController();
            if(!csParserController.isEnabledRgb()) {
                csParserController.setEnableRgb(true);
            }

            streamManager.getTcpChannelHandlerContexts().add(channelHandlerContext);

            return streamManager;
        }

        String rtspUrl = findRtspUrl(cameraId);
        StreamManager streamManager = new StreamManager(cameraId, rtspUrl, true, false);
        streamManager.getTcpChannelHandlerContexts().add(channelHandlerContext);

        streamManagerMap.put(cameraId, streamManager);

        assignCamera(cameraId);

        return streamManager;
    }

    public StreamManager activateStreamManagerForJpeg(long cameraId, HttpHandlerContext httpHandlerContext) {
        if(streamManagerMap.containsKey(cameraId)) {
            StreamManager streamManager = streamManagerMap.get(cameraId);
            CSParserController csParserController = streamManager.getCsParserController();
            if(!csParserController.isEnabledJpeg()) {
                csParserController.setEnableJpeg(true, 640, 480);
            }

            streamManager.getHttpHandlerContexts().add(httpHandlerContext);

            return streamManager;
        }

        String rtspUrl = findRtspUrl(cameraId);
        StreamManager streamManager = new StreamManager(cameraId, rtspUrl, false, true);
        streamManager.getHttpHandlerContexts().add(httpHandlerContext);

        streamManagerMap.put(cameraId, streamManager);

        assignCamera(cameraId);

        return streamManager;
    }

    private String findRtspUrl(long cameraId) {
        CameraInfo cameraInfo = cameraInfoRepository.findOne(cameraId);
        return "rtsp://root:pass@10.250.170.141/axis-media/media.amp";
        //TODO
        //
//        return cameraInfo.getRtspUrl();
    }

    private void assignCamera(long cameraId) {
        String localhost;
        try {
            localhost = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        zookeeperService.assignCamera(cameraId, localhost);
    }
}
