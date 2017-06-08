package com.androidlime.wikivarsity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Afrina on 29-Sep-16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DataBaseName="database.db";
    public static final String professorTable="ProfessorInfo";
    public static final String favouriteTable="favourite_table";
    public static final String C_1="NAME";
    public static final String C_2="VARSITY";
    public static final String C_3="DEPT";
    public static final String C_4="RESEARCH_AREA";
    public static final String C_5="MINIMUM_CGPA";
    public static final String C_6="WEBSITE";
    public static final String C_7="PHOTO";
    public DatabaseHelper(Context context) {
        super(context, DataBaseName, null, 1);


}


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table "+professorTable+
                    "(NAME TEXT PRIMARY KEY , VARSITY TEXT ,DEPT TEXT,RESEARCH_AREA TEXT,MINIMUM_CGPA TEXT,WEBSITE TEXT,PHOTO TEXT);");
            sqLiteDatabase.execSQL("create table "+favouriteTable+"(ID INT ,PROFESSOR_NAME TEXT);");
        System.out.println("************************table created:"+professorTable+"***************************************");


    }

    public  boolean insertFavourite(String name,int Id){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("PROFESSOR_NAME",name);
        contentValues.put("ID",Id);
       // contentValues.put(C_4,mark);
        long result=sqLiteDatabase.insert(favouriteTable,null,contentValues);
        if(result==-1){
            return  false;
        }
        return true;
    }

    public  boolean insertProfessor(String name, String university, String dept, String researchArea,
                                    String minimumCGPA , String website, String photo){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(C_1,name);
        contentValues.put(C_2,university);
        contentValues.put(C_3,dept);
        contentValues.put(C_4,researchArea);
        contentValues.put(C_5,minimumCGPA);
        contentValues.put(C_6,website);
        contentValues.put(C_7,photo);
        // contentValues.put(C_4,mark);
        long result=sqLiteDatabase.insert(professorTable,null,contentValues);
        if(result==-1){
            return  false;
        }
        return true;
    }
    public Cursor getFavourites(){
        SQLiteDatabase sqLiteDatabase= this.getReadableDatabase();
        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM "+favouriteTable,null);
        return  cursor;
    }
    public Cursor getFavouriteByName(String name){
        SQLiteDatabase sqLiteDatabase= this.getReadableDatabase();
        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM "+favouriteTable+ " WHERE PROFESSOR_NAME LIKE \""+name+"\"",null);
        return  cursor;
    }
    public Cursor getProfessors(){
        SQLiteDatabase sqLiteDatabase= this.getReadableDatabase();
        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM "+professorTable,null);
        return  cursor;
    }
    public Cursor getProfessorByName(String name){
        SQLiteDatabase sqLiteDatabase= this.getReadableDatabase();
       Cursor cursor ;
        try{
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+professorTable+ " WHERE NAME LIKE \""+name+"\""
                    ,null);

        }catch (Exception e){
            return null;
        }

        return  cursor;
    }
    /*public boolean updateData(String id,String name,String surname, int Marks){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(C_1,id);
        contentValues.put(C_2,name);
        contentValues.put(C_3,surname);
        contentValues.put(C_4,Marks);
        sqLiteDatabase.update(StudentTable,contentValues,"ID=?",new String[]{id});
        return true;
    }*/
    public Integer DeleteFavourite(String name){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        return sqLiteDatabase.delete(favouriteTable,"PROFESSOR_NAME=?",new String[]{name});
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DataBaseName);
        onCreate(sqLiteDatabase);
    }
}
