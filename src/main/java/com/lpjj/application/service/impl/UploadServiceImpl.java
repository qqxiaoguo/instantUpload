package com.lpjj.application.service.impl;

import com.alibaba.fastjson.JSON;
import com.lpjj.application.annotation.Timer;
import com.lpjj.application.base.CodeCache;
import com.lpjj.application.cache.RedisService;
import com.lpjj.application.dao.HistoryDao;
import com.lpjj.application.dao.UploadDao;
import com.lpjj.application.entity.Base64Image;
import com.lpjj.application.entity.FDS_faceDB.FDS_Facedb;
import com.lpjj.application.entity.FDS_faceDB.FDS_Picture;
import com.lpjj.application.entity.History;
import com.lpjj.application.entity.User;
import com.lpjj.application.entity.faceDB.FaceDB;
import com.lpjj.application.entity.faceDB.Results;
import com.lpjj.application.entity.login.Login;
import com.lpjj.application.entity.parameters.Face_Results;
import com.lpjj.application.entity.parameters.JsonRootBean;
import com.lpjj.application.entity.picturesync.Picturesync;
import com.lpjj.application.entity.result.IdentifyResults;
import com.lpjj.application.entity.result.Result;
import com.lpjj.application.entity.retrieval.*;
import com.lpjj.application.service.UploadService;
import com.lpjj.application.service.threads.MyThread;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import javax.annotation.PostConstruct;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.concurrent.*;


/**
 * com.lpjj.application.service.impl
 * 黄新乐
 * 2019/6/21
 */
@Service
@Slf4j
public class UploadServiceImpl implements UploadService, CommandLineRunner {

    public long sessionID;

    private List<Integer> repository_ids = new ArrayList<Integer>();

    /**
     * 优先执行该方法
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        this.sessionID = (long) CodeCache.codeMap.get("session_ID");
        this.repository_ids = (List<Integer>) CodeCache.codeMap.get("repository_ids");
    }


    @Autowired
    private RestTemplate restTemplate;


    @Value("${fds.ip}")
    private String ip;

    @Value("${fds.threshold}")
    private int threshold;

    @Autowired
    private UploadService uploadService;


    @Autowired
    private ExecutorService executorService;

    @Autowired
    private RedisService redisService;

    /**
     * 构建对象
     *
     * @param picturesync
     * @return
     */
    public RootBean getRootBean(Picturesync picturesync) {
        RootBean rootBean = new RootBean();
        if (picturesync.getResults().get(0).getFace_image_id_str() != null) {
            log.error("face image id  is {}", picturesync.getResults().get(0).getFace_image_id_str());
            Retrieval retrieval = new Retrieval();
            retrieval.setFace_image_id_str(picturesync.getResults().get(0).getFace_image_id_str());
            rootBean.setRetrieval(retrieval);
            Timestamp timestamp = new Timestamp();
            Condition condition = new Condition();
            condition.setTimestamp(timestamp);
            rootBean.setCondition(condition);
            Order order = new Order();
            rootBean.setOrder(order);
            List<Integer> creamIDs = new ArrayList<>();
            for (Integer repository_id : repository_ids) {
                creamIDs.add(repository_id);
            }
            rootBean.getRetrieval().setRepository_ids(creamIDs);
            rootBean.getRetrieval().setThreshold(threshold);
            return rootBean;
        }
        return null;
    }


    @Override
    @Timer
    public Picturesync picturesync(String image_base64) {
        Base64Image base64Image = new Base64Image(image_base64);
        HttpEntity<Object> entity = getStringHttpEntity(base64Image);
        String message = restTemplate.postForObject("http://" + ip + ":22000/fds/register/facedb/picturesync", entity, String.class);
        log.info("同步注册 ----------{}", message);
        Picturesync picturesync = JSON.parseObject(message, Picturesync.class);
        return picturesync;
    }

    @Override
    @Timer
    public JsonRootBean retrieval(RootBean rootBean) {
        HttpEntity<Object> entity = getStringHttpEntity(rootBean);
        String message = restTemplate.postForObject("http://" + ip + ":22000/fds/config/retrieval", entity, String.class);
        log.info("比对结果返回值----------{}", message);
        JsonRootBean jsonRootBean = JSON.parseObject(message, JsonRootBean.class);
        return jsonRootBean;
    }


    /**
     * 组装人脸请求
     *
     * @param base64_image 编辑后的
     * @return
     */
    @Override
    @Timer
    public Result ImageRecognition(String base64_image, String device_id) throws NullPointerException {
        Result result = null;
        MyThread myThread = new MyThread(base64_image, device_id, ip, uploadService,redisService);
        Future<Result> fal = executorService.submit(myThread);
        try {
            result = (Result) fal.get();
            return result;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            log.info("图片未处理");
        }
        return null;
    }

    @Override
    public String facedb(FDS_Facedb fds_facedb) {
        String faceDBUrl = "http://"+ip+":22000/fds/config/facedb";
        HttpEntity<Object> httpEntity = getStringHttpEntity(fds_facedb);
        return   restTemplate.postForObject(faceDBUrl,httpEntity,String.class);
    }



    @Override
    public String picture(FDS_Picture fds_picture) {
        String pictureUrl="http://"+ip+":22000/fds/register/facedb/picture";
        HttpEntity<Object> httpEntity = getStringHttpEntity(fds_picture);
        return restTemplate.postForObject(pictureUrl,httpEntity,String.class);
    }
//只能
    @Override
    public  void     delete(int id) {
        String pictureUrl="http://"+ip+":22000/fds/config/camera?id="+id;
        System.out.println(pictureUrl);
           HttpEntity<Object> httpEntity = getStringHttpEntity(null);
       // return restTemplate.postForObject(pictureUrl,httpEntity,String.class);

        ResponseEntity<String> exchange = restTemplate.exchange(pictureUrl, HttpMethod.DELETE, httpEntity, String.class);
        log.info("body is {}",exchange.getBody());
    }



    /**
     * 构建请求头
     *
     * @param param JSON串
     * @return
     */
    private HttpEntity<Object> getStringHttpEntity(Object param) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Cookie", "session_id=" + this.sessionID + ";isGlobal=0;face_platform_session_id=" + this.sessionID + ";");
        if (!"".equals(param) && param != null) {
            String jsonString = JSON.toJSONString(param);
            return new HttpEntity<>(jsonString, headers);
        }
        return new HttpEntity<>(null, headers);
    }


}
