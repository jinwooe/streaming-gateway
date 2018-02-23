package com.skcc.rtspgw.service;

import com.skcc.rtspgw.utils.BytesUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("prod")
@Service
public class ZookeeperService {
    private static Logger logger = LoggerFactory.getLogger(ZookeeperService.class);

    private CuratorFramework curatorFramework;

    @Value("${zookeeper.znode.gateway}")
    private String znodeGateway;
    @Value("${zookeeper.znode.camera}")
    private String znodeCamera;

    public ZookeeperService(CuratorFramework curatorFramework) {
        this.curatorFramework = curatorFramework;
    }

    /**
     * This method is used when a server is assigned to do transcoding for a specific camera
     * @param cameraId
     * @param serverIpAddress
     */
    public void assignCamera(long cameraId, String serverIpAddress) {
        try {
            String znode = znodeCamera + "/" + cameraId;
            if(curatorFramework.checkExists().forPath(znode) == null) {
                curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath(znode, serverIpAddress.getBytes());
            }
            else {
                throw new RuntimeException(String.format("The camera (%s) was already assigned", cameraId));
            }

            znode = znodeGateway + "/" + serverIpAddress;
            if(curatorFramework.checkExists().forPath(znode) == null) {
                curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath(znode, BytesUtil.intToBytes(1));
                return;
            }

            int numberOfCameras = BytesUtil.bytesToInt(curatorFramework.getData().forPath(znode));
            curatorFramework.setData().forPath(znode, BytesUtil.intToBytes(++numberOfCameras));
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
    }
}
