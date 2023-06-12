package de.fh_zwickau.pti.mobanw.ci_application.model;

import java.util.ArrayList;

public class CIRepository {

    static ArrayList<CI> globalCIList = new ArrayList<>();
    static ArrayList<CI> favCIList = new ArrayList<>();
    static ArrayList<CI> userCIList = new ArrayList<>();

    public static void addCIGlobally(CI ci) {
        globalCIList.add(ci);
    }

    public static ArrayList<CI> getGlobalCIList() {
        return globalCIList;
    }

    public static ArrayList<CI> getFavCIList() {
        return favCIList;
    }

    public static void addUserCI(CI ci) {
        userCIList.add(ci);
        globalCIList.add(ci);
    }

    public static ArrayList<CI> getUserCIList() {
        return userCIList;
    }

    public static void addCIToFavs(int id) {
        CI ci = getCIById(id);
        favCIList.add(ci);
    }

    public static void removeCIFromFavs(int id) {
        CI ci = getCIById(id);
        favCIList.remove(ci);
    }

    public static void removeCIGlobally(int id) {
        CI ci = getCIById(id);
        favCIList.remove(ci);
        globalCIList.remove(ci);
        userCIList.remove(ci);
    }

    public static void removeUserCI(int id) {
        CI ci = getCIById(id);
        if (getUserCIList().contains(ci)) {
            removeCIGlobally(id);
        }
    }

    public static CI getCIById(int id) {
        for (CI ci: globalCIList) {
            if (ci.getId() == id) return ci;
        }
        return null;
    }

    public static boolean isFavorite(int id) {
        for (CI ci: favCIList) {
            if (ci.getId() == id) return true;
        }
        return false;
    }
}
