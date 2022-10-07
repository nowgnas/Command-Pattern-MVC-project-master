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
        // login 만 처리
        String id = json.get("id").getAsString();
        HttpSession session = request.getSession(false);
        if (id != null && !id.trim().equals("") && session != null) {
            // 로그인 된 상태
            Member member = (Member) session.getAttribute("member");
            if (member != null && member.getId().equals(id)) {
                reJson.addProperty("loginId", id);
            } else {
                reJson.addProperty("msg", "member 가 null 이 아닐 때  ");
            }
        } else {
            JsonElement pwObject = json.get("pw");
            if (pwObject != null) {
                String pw = pwObject.getAsString();
                System.out.println(id + " : " + pw);

                // login
                Member member = memberService.login(id, pw);
//                Member member = new Member(id, pw);
                if (member != null) {
                    session = request.getSession();
                    session.setAttribute("member", member);
                    reJson.addProperty("loginId", id);
                } else {
                    reJson.addProperty("msg", "login fail");
                }
            } else {
                reJson.addProperty("msg", "session 만료됨 다시 로그인 해주세요");
            }
        }

    }
}
