package com.spaceplorer.spaceplorerweb.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Logs {

    public static void info(Object obj,String method, String data){
        log.info("[OBJECT] {}, [METHOD] {}, [DATA] {}",getClassName(obj), method,data);
    }

    public static void info(Object obj,String method){
        log.info("[OBJECT] {}, [METHOD] {}",getClassName(obj), method);
    }

    public static void error(Object obj,String method){
        log.error("[OBJECT] {}, [METHOD] {}",getClassName(obj), method);
    }

    private static String getClassName(Object obj) {
        String simpleName_ = obj.getClass().getSimpleName();
        int index = simpleName_.indexOf("$");
        //CGLIB등의 프록시 객체인경우 문자열 자르기위해
        if(index != 0){
            return simpleName_.substring(0, index);
        }
        return simpleName_;
    }
}
