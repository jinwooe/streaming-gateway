package com.skcc.rtspgw.controller;

import com.skcc.rtspgw.service.TokenValidationService;
import com.skcc.rtspgw.stream.HttpHandlerContext;
import com.skcc.rtspgw.stream.HttpHandlerContextImpl;
import com.skcc.rtspgw.stream.StreamManagerActivator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Controller
public class MJpegController {
    private static Logger logger = LoggerFactory.getLogger(MJpegController.class);

    @Autowired
    private TokenValidationService tokenValidationService;

    @Autowired
    private StreamManagerActivator streamManagerActivator;

    @GetMapping("jpeg")
    public StreamingResponseBody handleRequest(@RequestParam("cameraId") long cameraId,
                                               @RequestParam("token") String token, HttpServletResponse response) {
        if(!tokenValidationService.validate(token)) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return os -> {
                os.write("Invalid token".getBytes());
            };
        }

        response.setContentType("multipart/x-mixed-replace; boundary=--mjpeg");

        HttpHandlerContextImpl context = new HttpHandlerContextImpl(cameraId);
        streamManagerActivator.activateStreamManagerForJpeg(cameraId, context);

        return context;
    }
}
