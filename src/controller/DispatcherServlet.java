package controller;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/main")
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    HashMap<String, Command> beans;

    public void init(ServletConfig config) throws ServletException {
        String path = config.getServletContext().getRealPath("/WEB-INF/beans.xml");
        try {
            XmlBeanFactory xmlBeanFactory = new XmlBeanFactory(path);
            beans = xmlBeanFactory.getBeans();
            System.out.println(beans);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");

        PrintWriter out = response.getWriter();
        JsonObject reJson = new JsonObject();

        JsonObject json = (JsonObject) JsonParser.parseReader(request.getReader());
        String sign = json.get("sign").getAsString();
        System.out.println("---------sign: " + sign + "---------");

        if (sign != null) {
            // command pattern
            beans.get(sign).service(request, response, json, reJson);
        } else {
            reJson.addProperty("msg", "해킹 모니터링 중");
        }
        out.append(reJson.toString());
    }
}
