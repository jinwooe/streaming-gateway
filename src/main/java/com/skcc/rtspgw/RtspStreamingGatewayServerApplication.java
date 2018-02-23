package com.skcc.rtspgw;

import com.skcc.rtspgw.utils.BytesUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;

@SpringBootApplication
public class RtspStreamingGatewayServerApplication {
	private static final Logger logger = LoggerFactory.getLogger(RtspStreamingGatewayServerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RtspStreamingGatewayServerApplication.class, args);
	}

	/**
	 * while bootstrapping, register the server information of itself to zookeeper
	 */
	@Bean(destroyMethod = "close")
	@Profile("prod")
	public CuratorFramework curatorFramework(Environment env) {
		CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(env.getProperty("zookeeper.connectString"),
				new RetryNTimes(5, 1000));
		curatorFramework.start();

		String znodeGateway = env.getProperty("zookeeper.znode.gateway");
		String znodeCamera = env.getProperty("zookeeper.znode.camera");

		try {
			if (curatorFramework.checkExists().forPath(znodeGateway) == null) {
				curatorFramework.create().creatingParentsIfNeeded().forPath(znodeGateway);
			}

			if (curatorFramework.checkExists().forPath(znodeCamera) == null) {
				curatorFramework.create().creatingParentsIfNeeded().forPath(znodeCamera);
			}

			String localhost = InetAddress.getLocalHost().getHostAddress();
			curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath(znodeGateway + "/" + localhost, BytesUtil.intToBytes(0));
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}

		return curatorFramework;
	}

	@Bean
    public RestTemplate restTemplate() {
	    return new RestTemplate();
    }
}
