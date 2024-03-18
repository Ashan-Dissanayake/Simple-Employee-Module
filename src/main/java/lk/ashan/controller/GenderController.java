package lk.ashan.controller;

import lk.ashan.dao.GenderDao;
import lk.ashan.entity.Gender;

import java.util.List;

public class GenderController {

    public static List<Gender> get() {
        return GenderDao.getAll();

    }
}
