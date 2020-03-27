package com.example.smartstickapp3;

import java.util.ArrayList;

public class NavigationDrawerItem {

    String baslik ;
    int resimID;

    public String getBaslik() {
        return baslik;
    }

    public int getResimID() {
        return resimID;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public void setResimID(int resimID) {
        this.resimID = resimID;
    }

    public static ArrayList<NavigationDrawerItem> getData(){

        ArrayList<NavigationDrawerItem> dataList = new ArrayList<NavigationDrawerItem>();
        int[] resimID = getImages();
        String[] basliklar = getBasliklar();

        for (int i=0 ; i<basliklar.length ; i++){
            NavigationDrawerItem navItem = new NavigationDrawerItem();
            navItem.setBaslik(basliklar[i]);
            navItem.setResimID(resimID[i]);

            dataList.add(navItem);
        }
        
        return dataList;
    }

    private static int[] getImages() {
        return new int[]{
                R.drawable.tree_01,R.drawable.tree_02,R.drawable.tree_03,
                R.drawable.tree_04};
    }

    private static String[] getBasliklar() {

        return new String[]{
                "Bitki Ekle","Bitki Göster","Bitki Ara","Hakkında"
        };
    }



}
