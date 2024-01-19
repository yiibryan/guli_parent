package com.atguigu.servicebase.exceptionHandler;

import com.atgugui.commonutils.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody // 为了返回数据
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行了全局异常");
    }

    //特定异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e) {
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException异常处理..");
    }

    //自定义异常
    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R error(GuliException e) {
        // log.error(e.getMessage());
        e.printStackTrace();

        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
