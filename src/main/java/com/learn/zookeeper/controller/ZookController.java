package com.learn.zookeeper.controller;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ZookController {

    @Autowired
    private ZooKeeper zkClient;

    @GetMapping("/zookeeper")
    public String getData() throws KeeperException, InterruptedException {
        String path = "/zookeeper";
        boolean watch = true;
        byte[] data = zkClient.getData(path, watch, null);
        return new String(data);
    }

    @GetMapping("/addNode/{nodename}/{data}")
    public String addNode(@PathVariable("nodename") String nodename, @PathVariable("data") String data1) {
        // 创建节点的路径
        String path = "/" + nodename;
        // 节点数据
        String data = data1;
        // 权限控制
        List<ACL> aclList = ZooDefs.Ids.OPEN_ACL_UNSAFE;
        // 创建节点的类型
        CreateMode createMode = CreateMode.PERSISTENT;

        String result = null;
        try {
            result = zkClient.create(path, data.getBytes(), aclList, createMode);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @GetMapping("/getData/{nodename}")
    public String getData(@PathVariable("nodename") String nodename) {
        //数据的描述信息，包括版本号，ACL权限，子节点信息等等
        Stat stat = new Stat();
        //返回结果是byte[]数据，getData()方法底层会把描述信息复制到stat对象中
        byte[] bytes;
        String path = "/" + nodename;
        try {
            bytes = zkClient.getData(path, false, stat);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //打印结果
        System.out.println("ZNode的数据data:" + new String(bytes));//Hello World
        System.out.println("获取到dataVersion版本号:" + stat.getVersion());//默认数据版本号是0
        return new String(bytes);
    }

    @GetMapping("/setData/{nodename}/{data}")
    public String setData(@PathVariable("nodename")String nodename, @PathVariable("data") String data){
        String path = "/"+nodename;
        int version = 0;
        Stat stat = null;
        try {
            stat = zkClient.setData(path, data.getBytes(), version);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return stat.toString();
    }

}