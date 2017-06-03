package com.androidlime.wikivarsity;

/**
 * Created by Afrina on 02-Jun-17.
 */
public class Student {
    String Name;
    String Email;
    String University;
    String Dept;
    String CGPA;
    String Id;
    Favourite FavouriteProfessor;
    String CV;
    String About;
    Student(){

    }
    Student(String name,String email,String university,String dept,String cgpa,Favourite favourite,String cv,String about){
        Name=name;
        Email=email;
        University=university;
        Dept=dept;
        CGPA=cgpa;
        FavouriteProfessor=favourite;
        CV= cv;
        About=about;
    }
}
