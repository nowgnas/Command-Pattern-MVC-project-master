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

public class LoginCommand implements Command {
    MemberService memberService;

    public LoginCommand() {
        memberService = MemberService.getInstance();
    }


    @Override
    public void service(HttpServletRequest request, HttpServletResponse response, JsonObject json, JsonObject reJson) throws ServletException, IOException {
        String id = json.get("id").getAsString();
        String pw = json.get("pw").getAsString();
        System.out.println(id);

        Member member = memberService.login(id, pw);
        if (member != null) {
            // login 성공
            reJson.addProperty("state", member.getName());
        } else {
            reJson.addProperty("state", "login fail");
        }
    }
}
