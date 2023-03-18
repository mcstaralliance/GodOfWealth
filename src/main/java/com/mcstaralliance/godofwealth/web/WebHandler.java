package com.mcstaralliance.godofwealth.web;

import com.google.gson.Gson;
import com.mcstaralliance.godofwealth.GodOfWealth;
import org.bukkit.configuration.file.FileConfiguration;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class WebHandler extends AbstractHandler {
    // quoted from BotApi
    private static final Gson GSON = new Gson();

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(200);
        response.setContentType("application/json;charset=utf-8");
        baseRequest.setHandled(true);
        PrintWriter printWriter = response.getWriter();
        switch (target) {
            case "/":
                response.sendRedirect("https://www.mcstaralliance.com");
                return;
            case "/getLuckyPlayer":
                FileConfiguration config = GodOfWealth.getInstance().getConfig();
                GodOfWealth.getInstance().reloadConfig();
                printWriter.print(config.getString("lucky-player-real-name"));
                return;
        }
        printWriter.print(toJson(404, "Invalid action"));
    }

    public String toJson(int code, String message) {
        return GSON.toJson(new Response(code, message));
    }


}

