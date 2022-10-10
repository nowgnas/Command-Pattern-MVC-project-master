package member.controller;

import com.google.gson.JsonObject;
import controller.Command;
import member.dto.Member;
import member.service.MemberService;
import member.service.SecurityVOService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterCommand implements Command {
    MemberService memberService;

    public RegisterCommand() {
        memberService = MemberService.getInstance();
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response, JsonObject json, JsonObject reJson) throws ServletException, IOException {
        String name = json.get("registName").getAsString();
        String id = json.get("registId").getAsString();
        String pw = json.get("registPassword").getAsString();
        System.out.println("name: " + name + " " + "skfjls");

        Member member = new Member(id, pw, name);
        int res = memberService.memberInsert(member);
        if (res > 0) {
            reJson.addProperty("state", "register success");
        } else {
            reJson.addProperty("state", "register fail");
        }

    }
}
