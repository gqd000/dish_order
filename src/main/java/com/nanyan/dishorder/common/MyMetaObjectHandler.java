package com.nanyan.dishorder.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    //自动注入httpsession来获取当前登录用户的id
    //也可以用 ThreadLocal 来传递ID
    @Autowired
    HttpSession httpSession;

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段填充「insert」");
        log.info(metaObject.toString());
        metaObject.setValue("createTime", LocalDateTime.now());
//        metaObject.setValue("createUser",httpSession.getAttribute("employee"));
        metaObject.setValue("createUser",BaseContext.getCurrentId());
        metaObject.setValue("updateTime",LocalDateTime.now());
//        metaObject.setValue("updateUser",httpSession.getAttribute("employee"));
        metaObject.setValue("updateUser",BaseContext.getCurrentId());

//        BaseContext.RemoveCurrentId();
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段填充「update」");
        log.info(metaObject.toString());
        metaObject.setValue("updateTime",LocalDateTime.now());
//        metaObject.setValue("updateUser",httpSession.getAttribute("employee"));
        metaObject.setValue("updateUser",BaseContext.getCurrentId());

//        BaseContext.RemoveCurrentId();
    }
}
