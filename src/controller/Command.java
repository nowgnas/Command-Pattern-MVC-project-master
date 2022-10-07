package controller;

import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command {
    public void service(HttpServletRequest request, HttpServletResponse response, JsonObject json, JsonObject reJson) throws ServletException, IOException;
}
