package com.blog.content.controller;

import com.alibaba.fastjson.JSON;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import javafx.scene.Camera;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: lxk
 * @date 2022/6/8 20:34
 * @description: 文章服务接口
 */

@Slf4j
@RestController
@RequestMapping("/usp")
public class TestController {

    @PostMapping("/video/cameraList")
    public Result testApi(@RequestBody Param param) {
        log.info(">>>> param: {}", JSON.toJSONString(param));
        Result result = new Result();
        result.setCount(5);
        result.setResult(0);
        List<Result.Camera> cameraList = new ArrayList<>();
        String deviceCode = "000E01331910000";
        for (int i=0; i<result.getCount(); i++) {
            Result.Camera camera = new Result.Camera();
            camera.setFlag(0);
            camera.setDevice_code(deviceCode + i);
            camera.setDevice_name("摄像头" + i);
            cameraList.add(camera);
        }
        result.setCameraList(cameraList);
        return result;
    }

    private static class Param {

        private String device_type;

        private Integer action_flag;

        public String getDevice_type() {
            return device_type;
        }

        public void setDevice_type(String device_type) {
            this.device_type = device_type;
        }

        public Integer getAction_flag() {
            return action_flag;
        }

        public void setAction_flag(Integer action_flag) {
            this.action_flag = action_flag;
        }
    }

    private static class Result {

        private Integer result;

        private Integer count;

        private List<Camera> cameraList;

        public Integer getResult() {
            return result;
        }

        public void setResult(Integer result) {
            this.result = result;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public List<Camera> getCameraList() {
            return cameraList;
        }

        public void setCameraList(List<Camera> cameraList) {
            this.cameraList = cameraList;
        }

        private static class Camera {

            private String device_code;

            private Integer flag;

            private String device_name;

            public String getDevice_code() {
                return device_code;
            }

            public void setDevice_code(String device_code) {
                this.device_code = device_code;
            }

            public Integer getFlag() {
                return flag;
            }

            public void setFlag(Integer flag) {
                this.flag = flag;
            }

            public String getDevice_name() {
                return device_name;
            }

            public void setDevice_name(String device_name) {
                this.device_name = device_name;
            }
        }
    }

}
