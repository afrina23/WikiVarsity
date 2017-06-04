package com.androidlime.wikivarsity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

//        ShowMessage("started","database created"+my_db.getDatabaseName());
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setLogo(R.drawable.wikititlelogo);

        setSupportActionBar(myToolbar);


         addProfessor();
        ShowAllProfessor();
       // setContent();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        menu.add("My Profile");
        menu.add("Notifications");
        menu.add("log Out");
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);


        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //ShowMessage("Msg",query);
                getProfessorPage(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
      //  searchView.setSearchableInfo(
        //        searchManager.getSearchableInfo(getComponentName()));
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if(item.getTitle()=="My Profile"){
            Intent intent=new Intent(DataBaseStart.this,StudentActivity.class);
            startActivity(intent);
            return true;
        }
        return  false;
    }
    public void addProfessor(){



        boolean check=my_db.insertProfessor("Thomas Cormen","Harvard University","CS","Algorithms","3.7"
                ,"www.cormen.com",R.drawable.cormen);
        boolean second=my_db.insertProfessor("Md Kaykobad","BUET","CS","Algorithms","3.7"
                ,"www.kaykobad.com",R.drawable.kykobad);

        if(check){
            System.out.println(" inserted successfully");
            Toast.makeText(DataBaseStart.this,"inserted successfully",Toast.LENGTH_SHORT).show();
        }
        else{
            System.out.println("Not inserted successfully");
            Toast.makeText(DataBaseStart.this,"Not inserted successfully",Toast.LENGTH_SHORT).show();
        }






    }
    public void getProfessorPage(String name){
        Cursor all=my_db.getProfessorByName(name);
        ShowMessage("number ", String.valueOf(all.getCount()) );
        if(all.getCount()==0){
            Toast.makeText(DataBaseStart.this,"THERE is no DATA", Toast.LENGTH_LONG).show();
            ShowMessage("ERROR","No Professor Found of this Name"+name);
        }
        else{
            Intent intent= new Intent(DataBaseStart.this,MainActivity.class);
            intent.putExtra("name",name);
            startActivity(intent);
        }

    }
    public void ShowAllProfessor(){
        Cursor all=my_db.getProfessors();
        if(all.getCount()==0){
            Toast.makeText(DataBaseStart.this,"THERE is no Professors", Toast.LENGTH_LONG).show();
            ShowMessage("ERROR","No Professor Found");
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
