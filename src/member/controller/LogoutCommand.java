package member.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import controller.Command;
import member.dto.Member;
import member.service.MemberService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutCommand implements Command {
    MemberService memberService;

    public LogoutCommand() {
        memberService = MemberService.getInstance();
    }


    @Override
    public void service(HttpServletRequest request, HttpServletResponse response, JsonObject json, JsonObject reJson) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
