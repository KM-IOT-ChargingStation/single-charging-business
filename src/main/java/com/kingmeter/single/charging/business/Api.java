package com.kingmeter.single.charging.business;

import com.alibaba.fastjson.JSONObject;
import com.kingmeter.dto.charging.v2.rest.business.*;
import com.kingmeter.utils.HardWareUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestController
public class Api {

    @Value("${kingmeter.default.timezone}")
    private int timezone;

    @Value("${kingmeter.default.companyCode}")
    private String companyCode;

    @GetMapping("/updateMemory")
    public void updateMemory(int acm,int expense,
                             int ret,int rtm,
                             int ast,int minbsoc,
                             String userId){
        BSMemory.acm = acm;
        BSMemory.expense = expense;
        BSMemory.ret = ret;
        BSMemory.rtm = rtm;
        BSMemory.ast = ast;
        BSMemory.minbsoc = minbsoc;
        BSMemory.userId = userId;
    }

    @GetMapping("/getMemory")
    public Map<String,Object> getMemory(){

        Map<String,Object> map = new HashMap<>();
        map.put("acm",BSMemory.acm);
        map.put("expense",BSMemory.expense);
        map.put("ret",BSMemory.ret);
        map.put("rtm",BSMemory.rtm);
        map.put("ast",BSMemory.ast);
        map.put("minbsoc",BSMemory.minbsoc);
        map.put("userId",BSMemory.userId);

        return map;
    }



    @GetMapping("/loginPermission/site/{siteId}")
    public LoginPermissionBSDto queryLoginPermission(
            @PathVariable("siteId") long siteId,
            @RequestParam String msv,
            @RequestParam String mhv) {

        log.info("Login {},{},{}", siteId, msv, mhv);

        return new LoginPermissionBSDto(0,
                "100419", "", 0,
                companyCode, timezone);
    }

    @PostMapping("/malfunction")
    public void malfuctionUpload(@RequestBody MalfunctionDataUploadBSDto rest) {

        log.info("malfunction {}", JSONObject.toJSONString(rest));

    }

    @PostMapping("/bikeInDock")
    public BikeInDockResponseBSDto bikeInDock(@RequestBody BikeInDockBSDto requestDto) {
        log.info("bikeInDock {},{},{},{}", requestDto.getSiteId(),
                requestDto.getDockId(),
                requestDto.getBikeId(),
                HardWareUtils.getInstance().getLocalTimeByHardWareTimeStamp(requestDto.getCurrentTime()));
        BikeInDockResponseBSDto result = new BikeInDockResponseBSDto();
        result.setAcm(BSMemory.acm);
        result.setExpense(BSMemory.expense);
        result.setRet(BSMemory.ret);
        result.setRtm(BSMemory.rtm);
        return result;
    }

    @DeleteMapping("/offline/site/{siteId}")
    public void offline(@PathVariable("siteId") long siteId) {

        log.info("offline {}", siteId);

    }

    @GetMapping("/swipeCard/permission/{card}")
    public SwipeCardPermissionBSDto swipeCardPermission(@PathVariable("card") String card,
                                    @RequestParam long siteId,
                                    @RequestParam long dockId,
                                    @RequestParam long bikeId,
                                    @RequestParam long currentTime) {

        log.info("swipeCard permission {},{},{},{},{}", card, siteId,
                dockId, bikeId, currentTime);

        return new SwipeCardPermissionBSDto(BSMemory.ast,
                BSMemory.acm,BSMemory.minbsoc,BSMemory.userId);

    }


    @PostMapping("/swipeCard/confirm")
    public void swipeCardConfirm(@RequestBody SwipeCardConfirmBSDto
                                         requestDto) {

        log.info("swipeCard confirm {},{},{},{},{},{},{}",
                requestDto.getSiteId(), requestDto.getDockId(),
                requestDto.getBikeId(), requestDto.getUserId(),
                requestDto.getSls(), requestDto.getCurrentTime(),
                requestDto.getCard());

    }

    @PostMapping("/allDockInfo")
    public void allDockInfo(@RequestBody SiteHeartDataUploadBSDto
                                    requestDto) {

        log.info("allDockInfo  {},{},{}",
                requestDto.getSiteId(), requestDto.getRpow(),
                JSONObject.toJSONString(requestDto.getDockArray()));

    }

    @PostMapping("/allDockMonitorData")
    public void allDockMonitorData(@RequestBody DockDataUploadBSDto
                                           requestDto) {

        log.info("allDockMonitorData {},{}",
                requestDto.getSiteId(),
                JSONObject.toJSONString(requestDto.getDockArray()));

    }

    @PostMapping("/otaTrackerData")
    public void otaTrackerData(@RequestBody Map<String,Object> trackResult){
        log.info("otaTrackerData {}",
                JSONObject.toJSONString(trackResult));
    }
}
