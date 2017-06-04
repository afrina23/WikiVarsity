package com.androidlime.wikivarsity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Afrina on 29-Sep-16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DataBaseName="student.db";
    public static final String StudentTable="student_table";
    public static final String C_1="ID";
    public static final String C_2="NAME";
    public static final String C_3="SURNAME";
    public static final String C_4="MARKS";

    public DatabaseHelper(Context context) {
        super(context, DataBaseName, null, 1);

}

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table "+StudentTable+"(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT ,SURNAME TEXT,MARKS INTEGER);");

    }

    public  boolean insertData(String name,String sur,int mark){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(C_2,name);
        contentValues.put(C_3,sur);
        contentValues.put(C_4,mark);
        long result=sqLiteDatabase.insert(StudentTable,null,contentValues);
        if(result==-1){
            return  false;
        }
        return true;
    }
    public Cursor getData(){
        SQLiteDatabase sqLiteDatabase= this.getReadableDatabase();
        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM "+StudentTable,null);
        return  cursor;
    }
    public boolean updateData(String id,String name,String surname, int Marks){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(C_1,id);
        contentValues.put(C_2,name);
        contentValues.put(C_3,surname);
        contentValues.put(C_4,Marks);
        sqLiteDatabase.update(StudentTable,contentValues,"ID=?",new String[]{id});
        return true;
    }
    public Integer DeleteData(String id){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        return sqLiteDatabase.delete(StudentTable,"ID=?",new String[]{id});
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DataBaseName);
        onCreate(sqLiteDatabase);
    }
}
