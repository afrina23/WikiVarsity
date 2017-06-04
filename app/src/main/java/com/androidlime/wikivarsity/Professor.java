package com.androidlime.wikivarsity;

import android.widget.ImageView;

/**
 * Created by Afrina on 02-Jun-17.
 */
public class Professor {
    String Name;
    String University;
    String Dept;
    String ResearchArea;
    String MinimumCGPA;
    String Website;
    int Id;
    //ImageView Photo;
    int photo;
    boolean isFavorite= false;
    Professor(){

    }
    Professor(String name,String university, String dept, String researchArea,String minimumCGPA ,String website){
        Name=name;
        University=university;
        Dept=dept;
        ResearchArea=researchArea;
        MinimumCGPA=minimumCGPA;
        Website=website;
    }

}
