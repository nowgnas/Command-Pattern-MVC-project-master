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

public class GetDongCommnad implements Command {
    HouseService houseService;

    public GetDongCommnad() {
        houseService = HouseService.getInstance();
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response, JsonObject json, JsonObject reJson) throws ServletException, IOException {
        String gugun = json.get("gugun").getAsString();
        String sido = json.get("sido").getAsString();
        System.out.println(gugun);
        List<String> list = houseService.getDong(sido, gugun);
        if (list != null) {
            JsonArray jsonArray = new JsonArray();
            for (String item :
                    list) {
                jsonArray.add(item);
            }
            reJson.add("dong", jsonArray);
        } else {
            reJson.addProperty("msg", "get dong fail");
        }
    }
}
