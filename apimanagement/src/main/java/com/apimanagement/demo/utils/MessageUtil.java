package com.apimanagement.demo.utils;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MessageUtil {


    private static final String SMS_Url = "http://sms.webchinese.cn/web_api/";

    private static final Integer TIMEOUT = 5 * 60 * 60;

    private static final Integer RANDOM_NUM = 4;

    /**
     *
     * @param uid SMS用户登录id
     * @param key SMS接口秘钥
     * @param sendPhoneNum  接收验证码的手机号
     * @param desc  短信描述
     * @return
     */
    public static Integer send(String uid, String key, String sendPhoneNum, String desc){

        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(SMS_Url);
        post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");

        NameValuePair[] data = {
                new NameValuePair("Uid", uid),
                new NameValuePair("Key", key),
                new NameValuePair("smsMob", sendPhoneNum),
                new NameValuePair("smsText", desc)
        };

        post.setRequestBody(data);

        try {
            client.executeMethod(post);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Header[] headers = post.getRequestHeaders();
        int statusCode = post.getStatusCode();
        System.out.println("statusCode:" + statusCode);
        for(Header h : headers){
            System.out.println(h.toString());
        }

        String result = "";
        try {
            result = new String(post.getResponseBodyAsString().getBytes("gbk"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        post.releaseConnection();

        return Integer.parseInt(result);
    }

    public static String getMessage(Integer code){
        String message;
        if(code > 0 ) {
            message = "SMS-f发送成功！短信量还有" + code + "条";
        }else if(code == -1){
            message = "SMS-没有该用户账户";
        }else if(code == -2){
            message = "SMS-接口密钥不正确";
        }else if(code == -21){
            message = "SMS-MD5接口密钥加密不正确";
        }else if(code == -3){
            message = "SMS-短信数量不足";
        }else if(code == -11){
            message = "SMS-该用户被禁用";
        }else if(code == -14){
            message = "SMS-短信内容出现非法字符";
        }else if(code == -4){
            message = "SMS-手机号格式不正确";
        }else if(code == -41){
            message = "SMS-手机号码为空";
        }else if(code == -42){
            message = "SMS-短信内容为空";
        }else if(code == -51){
            message = "SMS-短信签名格式不正确接口签名格式为：【签名内容】";
        }else if(code == -6){
            message = "SMS-IP限制";
        }else{
            message = "其他错误";
        }
        return message;
    }

    public static void sendMessage(HttpServletResponse response,
                                   String sendPhoneNum){
        String code = String.valueOf(RandomNumUtil.createRandomNum(RANDOM_NUM));
//        String desc = "尊敬的用户，您好，您正在注册【COOCAA CLUB APIShop】，您的验证码为："+code+"，请于" + TIMEOUT + "分钟内正确输入，如非本人操作，请忽略此短信。";
//        Integer resultCode = send("simplelyg", "d41d8cd98f00b204e980", sendPhoneNum, desc);
//
//        if(resultCode > 0) {
            CookieUtil.set(response, sendPhoneNum, code, TIMEOUT);
//        }
//
//        System.out.println("已发送至：" + sendPhoneNum + "，验证码："+code);
//        System.out.println(getMessage(resultCode));
    }

}
