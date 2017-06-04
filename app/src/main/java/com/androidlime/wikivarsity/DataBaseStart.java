package com.androidlime.wikivarsity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Afrina on 04-Jun-17.
 */
public class DataBaseStart extends AppCompatActivity{
    public static DatabaseHelper my_db;
    protected void onCreate(Bundle savedInstanceState) {
        //connectMySQL();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.front_page);
     //   Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
       // myToolbar.setLogo(R.drawable.wikititlelogo);

        //setSupportActionBar(myToolbar);
        my_db= new DatabaseHelper(this);

        ShowMessage("started","database created"+my_db.getDatabaseName());
       addProfessor();
        ShowAllProfessor();
       // setContent();

    }
    public void addProfessor(){



        boolean check=my_db.insertProfessor("Rakin Haider","BUET","CSE","PERT CHART","2.1","www.rh.com");

        if(check){
            System.out.println(" inserted successfully");
            Toast.makeText(DataBaseStart.this,"inserted successfully",Toast.LENGTH_SHORT).show();
        }
        else{
            System.out.println("Not inserted successfully");
            Toast.makeText(DataBaseStart.this,"Not inserted successfully",Toast.LENGTH_SHORT).show();
        }






    }
    public void ShowAllProfessor(){
        Cursor all=my_db.getProfessors();
        if(all.getCount()==0){
            Toast.makeText(DataBaseStart.this,"THERE is no DATA", Toast.LENGTH_LONG).show();
            ShowMessage("ERROR","No data Found");
        }
        else{
            StringBuffer msg= new StringBuffer();
            while(all.moveToNext()){
                msg.append("Name :"+all.getString(0)+"\n");
                msg.append("Uni :"+all.getString(1)+"\n");
                msg.append("Dept :"+all.getString(2)+"\n");
                msg.append("CG :"+all.getString(3)+"\n\n");
                ShowMessage("DATA",msg.toString());
            }
        }
    }
    public  void ShowMessage(String title, String msg){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();
    }
}
