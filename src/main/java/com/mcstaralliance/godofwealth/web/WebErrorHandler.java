package com.mcstaralliance.godofwealth.web;


import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.ErrorHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class WebErrorHandler extends ErrorHandler {
    // quoted from BotApi
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(500);
        response.setContentType("text/html;charset=utf-8");
        baseRequest.setHandled(true);
        PrintWriter printWriter = response.getWriter();
        printWriter.print("<!DOCTYPE html><html><head><title>Error</title><style>body{width:35em;margin:0 auto;font-family:Tahoma,Verdana,Arial,sans-serif}</style></head><body><h1>An error occurred.</h1><p>Sorry, the page you are looking for is currently unavailable.<br/>Please try again later.</p><p>If you are the system administrator of this resource then you should checkthe error log for details.</p><p><em>Faithfully yours, jetty.</em></p></body></html>");
    }
}