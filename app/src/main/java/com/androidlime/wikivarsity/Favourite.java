package com.androidlime.wikivarsity;



import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Afrina on 02-Jun-17.
 */
public class Favourite {
    String Id;
    ArrayList<String> professorArrayList;
    Favourite(){
        professorArrayList= new ArrayList<>();
    }
    void addProfessor(String name){
        professorArrayList.add(name);
    }
    int getSize(){
        return professorArrayList.size();
    }
    String getProfessorName(int i){
        return professorArrayList.get(i);
    }
    void remove(String name){
        Iterator<String> it = professorArrayList.iterator();
        while (it.hasNext()) {
            if (it.next() == name) {
                it.remove();
                break;
            }
        }
    }
}
