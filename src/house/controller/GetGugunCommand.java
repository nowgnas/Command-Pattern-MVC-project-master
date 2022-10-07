package house.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import controller.Command;
import house.service.HouseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetGugunCommand implements Command {
    HouseService houseService;

    public GetGugunCommand() {
        houseService = HouseService.getInstance();
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response, JsonObject json, JsonObject reJson) throws ServletException, IOException {
        String sido = json.get("sido").getAsString();
        List<String> gugun = houseService.getGugun(sido);
        if (gugun != null) {
            JsonArray array = new JsonArray();
            for (String str : gugun) {
                array.add(str);
            }
            reJson.add("gugunList", array); // json으로 응답
        }else {
            reJson.addProperty("msg", "get gugun fail");
        }
    }
}
