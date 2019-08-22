package com.lpjj.application.service.threads;

import com.alibaba.fastjson.JSON;
import com.lpjj.application.cache.RedisService;
import com.lpjj.application.dao.HistoryDao;
import com.lpjj.application.dao.UploadDao;
import com.lpjj.application.entity.History;
import com.lpjj.application.entity.faceDB.FaceDB;
import com.lpjj.application.entity.parameters.Face_Results;
import com.lpjj.application.entity.parameters.JsonRootBean;
import com.lpjj.application.entity.picturesync.Picturesync;
import com.lpjj.application.entity.result.IdentifyResults;
import com.lpjj.application.entity.result.Result;
import com.lpjj.application.entity.retrieval.RootBean;
import com.lpjj.application.service.UploadService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * com.lpjj.application.service.threads
 * 黄新乐
 * 2019/7/19
 * 组装请求的线程类
 */
@Slf4j
public class MyThread implements Callable<Result> {

    private String base64_image;

    private String device_id;

    private String ip;

    private UploadService uploadService;


    private RedisService redisService;

    public MyThread(String base64_image, String device_id, String ip, UploadService uploadService, RedisService redisService) {
        this.base64_image = base64_image;
        this.device_id = device_id;
        this.ip = ip;
        this.uploadService = uploadService;
        this.redisService = redisService;
    }
//线程任务
    public Result call() throws Exception {
        //页面返回对象
        Result result = new Result();
        //识别成功与否的集合
        ArrayList<IdentifyResults> lists = new ArrayList<>();
        String[] split = base64_image.split(";");
        for (String s : split) {
            Picturesync picturesync = uploadService.picturesync(s);
            if (!"OK".equals(picturesync.getMessage())) {
                result.setMessage(picturesync.getMessage());
                log.info("人脸检索结果 --- {}", picturesync.getMessage());
                long d = System.currentTimeMillis();
                saveToDB(result, s);
                long d1 = System.currentTimeMillis();
                log.info("插入库时间 :{}", d1 - d);
                continue;
            }
            RootBean rootBean = uploadService.getRootBean(picturesync);
            JsonRootBean jsonRootBean = uploadService.retrieval(rootBean);
            //用于页面img标签显示图片
            jsonRootBean.setImage_base64("data:image/jpeg;base64," + s);
            jsonRootBean.setDevice_id(device_id);
            String results = jsonRootBean.getResults();
            //识别失败进入
            if ("".equals(results) || results == null) {
                long d = System.currentTimeMillis();
                saveToDB(result, s);
                long d1 = System.currentTimeMillis();
                log.info("插入库时间 :{}", d1 - d);
                result.setMessage("To deal with failure");
                lists.add(new IdentifyResults());
                continue;
            }
            log.info("识别成功");
            jsonRootBean.setTime(new Date());
            long d = System.currentTimeMillis();
            redisService.setMap("myThread_ok", JSON.toJSONString(jsonRootBean));
            long d1 = System.currentTimeMillis();
            log.info("插入库时间 :{}", d1 - d);
            result.setCode(picturesync.getRtn());
            result.setMessage("Handle a successful");
            IdentifyResults identifyResults = new IdentifyResults();
            List<Face_Results> face_results = JSON.parseArray(results, Face_Results.class);
            String uri = "http://" + ip + ":22000/fds/query/image?uri_base64=" + Base64Utils.encodeToString(face_results.get(0).getFace_image_uri().getBytes());
            long t0 = System.currentTimeMillis();
            String urlToBase64 = getImgUrlToBase64(uri);
            long t1 = System.currentTimeMillis();
            log.info("图片下载时间 {} ", t1 - t0);
            identifyResults.setFace_src(urlToBase64);
            identifyResults.setFace_reg(s);
            identifyResults.setScore(face_results.get(0).getSimilarity());
            identifyResults.setFace_name(face_results.get(0).getName());
            lists.add(identifyResults);
        }
        ;
        result.setData(lists);
        //识别成功
        return result;
    }

    /**
     * 存入数据库并更改状态
     *
     * @param result
     * @param s
     */
    private void saveToDB(Result result, String s) {
        History history = new History();
        history.setBase64_image("data:image/jpeg;base64," + s);
        history.setTime(new Date());
        history.setDevice_id(device_id);
        redisService.setMap("myThread_error",  JSON.toJSONString(history));
        log.info("识别失败");
        result.setCode(-1);
    }

    /**
     * 将网络图片转换成Base64编码字符串
     *
     * @param imgUrl 网络图片Url
     * @return
     */
    public static String getImgUrlToBase64(String imgUrl) {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;
        byte[] buffer = null;
        try {
            // 创建URL
            URL url = new URL(imgUrl);
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            inputStream = conn.getInputStream();
            outputStream = new ByteArrayOutputStream();
            // 将内容读取内存中
            buffer = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            buffer = outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    // 关闭inputStream流
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    // 关闭outputStream流
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // 对字节数组Base64编码
        return Base64Utils.encodeToString(buffer);
    }
}
