package house.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import controller.Command;
import house.dto.House;
import house.service.HouseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetHouseDealInfoCommand implements Command {
    HouseService houseService;

    public GetHouseDealInfoCommand() {
        houseService = HouseService.getInstance();
    }


    @Override
    public void service(HttpServletRequest request, HttpServletResponse response,
                        JsonObject json, JsonObject reJson)
            throws ServletException, IOException {

        String sido = json.get("sido").getAsString();
        String gugun = json.get("gugun").getAsString();
        String dongName = json.get("dong").getAsString();
        String year = json.get("year").getAsString();
        String month = json.get("month").getAsString();
        List<House> list = houseService.getHouseDealInfo(sido, gugun, dongName, year, month);
        if (list != null) {
            JsonArray array = new JsonArray();
            for (House house : list) {
                array.add(house.toString());
            }
            reJson.add("houseDealInfoList", array);
        } else {
            reJson.addProperty("msg", "getHouseDealInfo fail");
        }

    }

}

