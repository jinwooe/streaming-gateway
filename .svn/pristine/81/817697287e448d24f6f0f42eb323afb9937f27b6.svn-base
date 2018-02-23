package com.skcc.rtspgw;

import com.skcc.rtspgw.service.ZookeeperService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Profile("prod")
@Component("applicationInitializer")
@Order(1)
public class ApplicationInitializer implements CommandLineRunner{
    private ZookeeperService zookeeperService;

    public ApplicationInitializer(ZookeeperService zookeeperService) {
        this.zookeeperService = zookeeperService;
    }

    @Override
    public void run(String... args) throws Exception {
        zookeeperService.assignCamera(1L, "127.0.0.1");
        zookeeperService.assignCamera(2L, "127.0.0.2");
        zookeeperService.assignCamera(3L, "127.0.0.3");
        zookeeperService.assignCamera(4L, "127.0.0.1");
        zookeeperService.assignCamera(5L, "127.0.0.2");
    }
}
