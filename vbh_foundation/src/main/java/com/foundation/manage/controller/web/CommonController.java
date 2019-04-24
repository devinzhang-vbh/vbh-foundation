package com.foundation.manage.controller.web;

import cn.stylefeng.roses.core.base.controller.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.foundation.manage.util.HttpClientUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/common")
public class CommonController extends BaseController {


    @RequestMapping("/getLang")
    public String getLang(String ip) {
        if (ip == null || ip.trim().equals("")) {
            System.out.println("ip is null, set the default language to ZN.");
            return "CN";
        }

        //getIpArea(ip);

        String url = "https://api.ip.sb/geoip/" + ip;
        String response = HttpClientUtil.getHtmlByUrl(url);
        if (response != null && !response.equals("")) {
            System.out.println(response);
            JSONObject res = JSON.parseObject(response);
            res = (res == null ? new JSONObject() : res);

            String country = res.getString("country_code");
            System.out.println("country:" + country);
            if (country == null || country.equals("")) {
                return "CN";
            }
            if (country.equalsIgnoreCase("CN") || country.equalsIgnoreCase("HK")
                    || country.equalsIgnoreCase("TW") || country.equalsIgnoreCase("MO")) {
                return "CN";
            }
            return "US";
        }
        System.out.println("not get the ip from the service ,set default language to CN");
        return "CN";

    }


}
