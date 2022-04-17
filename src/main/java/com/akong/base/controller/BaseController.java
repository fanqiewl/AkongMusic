package com.akong.base.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * 基础控制类
 *
 * @author Akong
 * @create 2021/11/25 19:28
 */
public class BaseController {
    //对象转JSON工具
    private ObjectMapper om = new ObjectMapper();

    protected void sendJSON(boolean flag, String message, Object obj){

    }
}
