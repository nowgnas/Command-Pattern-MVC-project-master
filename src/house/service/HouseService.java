package house.service;

import house.dao.HouseDao;
import house.dto.House;

import java.util.List;

public class HouseService {
    HouseDao houseDao;
    private static HouseService instance;

    private HouseService() {
        houseDao = HouseDao.getInstance();
    }

    public static HouseService getInstance() {
        if (instance == null) instance = new HouseService();
        return instance;
    }

    public List<String> getGugun(String sido) {
        return houseDao.getGugun(sido);
    }

    public List<String> getDong(String sido, String gugun) {
        return houseDao.getDong(sido, gugun);
    }

    public List<House> getHouseDealInfo(String sido, String gugun, String dongName, String year, String month) {
        return houseDao.getHouseDealInfo(sido, gugun, dongName, year, month);
    }

}
