package com.learn.zookeeper.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static org.apache.zookeeper.Watcher.Event.EventType.*;

@Component
@Slf4j
public class Schedule {

    @Autowired
    private ZooKeeper zooKeeper;

    @Scheduled(fixedRate = 1000000)
    public void watchNode() throws InterruptedException, KeeperException {
        System.out.println("sout");
        // 动态监控
        zooKeeper.addWatch("/node7",  event -> {
            switch (event.getType()) {
                case NodeCreated:
                    log.info("{}节点创建了", event.getPath());
                    break;
                case NodeDataChanged:
                    log.info("{}节点数据被修改了", event.getPath());
                    break;
                case NodeDeleted:
                    log.info("{}节点被删除了", event.getPath());
                    break;
            }
        }, AddWatchMode.PERSISTENT);
        Thread.sleep(1000000);
    }
}
