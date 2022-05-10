package com.social.trade;

import android.widget.ImageView;

public class Rank {

    private ImageView nationImage;
    private String nationName;
    private String lv;



    private String teamname;

    public Rank(){}

    public ImageView getNationImage() {
        return nationImage;
    }

    public void setNationImage(ImageView nationImage) {
        this.nationImage = nationImage;
    }



    public String getNationName() {
        return nationName;
    }

    public void setNationName(String nationName) {
        this.nationName = nationName;
    }



    public String getLv() {
        return lv;
    }

    public void setLv(String lv) {
        this.lv = lv;
    }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }



}
