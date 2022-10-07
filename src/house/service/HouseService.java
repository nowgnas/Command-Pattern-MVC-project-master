package house.service;

import house.dao.HouseDao;

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
}
