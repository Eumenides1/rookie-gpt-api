package com.rookie.gpt.application;

/**
 * @author Jaguarliu
 * @description 微信公众号验签服务
 */
public interface IWeiXinValidateService {
    /**
     * 验证微信消息
     *
     * @param signature 微信消息签名
     * @param timestamp 微信消息时间戳
     * @param nonce     微信消息随机数
     * @return boolean
     */
    boolean checkSign(String signature, String timestamp, String nonce);
}
