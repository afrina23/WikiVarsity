package com.androidlime.wikivarsity;

import android.content.Intent;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import  android.support.v7.app.*;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    Professor professor= getProfessor();
    String url="jdbc:mysql://192.168.0.104/test";
    String user="app";
    String password="";
    Connection connection;
    public  static Statement statement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //connectMySQL();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.professor);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setLogo(R.drawable.wikititlelogo);

        setSupportActionBar(myToolbar);

        setContent();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        menu.add("My Profile");
        menu.add("Notifications");
        menu.add("log Out");
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
       if(item.getTitle()=="My Profile"){
           Intent intent=new Intent(MainActivity.this,StudentActivity.class);
           startActivity(intent);
           return true;
       }
        return  false;
    }
    public Professor getProfessor(){
        Professor professor= new Professor("Thomas Cormen","Harvard University","CE","Algorithm","3.9","www.cormen.com");
        StudentActivity.student= new Student("Mr A","abc@gmail.com","Bangladesh University of Engineering and Technology"
                ,"CSE","3.2",null,"abd","aodbaudfowuer");

        /*
        String query="Select * from Professor";
        ResultSet resultSet;
        try {
            resultSet= statement.executeQuery(query);
            resultSet.next();
            professor.Name=resultSet.getString(1);
            professor.University=resultSet.getString(2);
            professor.Dept=resultSet.getString(3);
            professor.ResearchArea=resultSet.getString(4);
            professor.MinimumCGPA=resultSet.getString(5);
            professor.Website=resultSet.getString(6);
            Toast.makeText(MainActivity.this, "Professor Added", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            System.out.println("query is not executed");

        }*/
        //professor.Photo= new ImageView();
        professor.photo = R.drawable.cormen;
        System.out.println("Picture Id "+R.drawable.cormen);
        return professor;

    }
    public  void setContent(){

        TextView name= (TextView) findViewById(R.id.ProfessorName);
        name.setText(professor.Name);
        TextView varsity= (TextView) findViewById(R.id.professorVarsity);
        varsity.setText(professor.University);
        ImageView image= (ImageView) findViewById(R.id.professorImage);
        image.setImageResource(professor.photo);
        //listview
        String[] professorInfo = new String[4];
        professorInfo[0]="Department : "+professor.Dept;
        professorInfo[1]="Research Area : "+professor.ResearchArea;
        professorInfo[2]="Minimum Required CGPA : "+professor.MinimumCGPA;
        professorInfo[3]="Website : "+professor.Website;

        ListAdapter theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                professorInfo);

        // ListViews display data in a scrollable list
        ListView theListView = (ListView) findViewById(R.id.theListView);
        theListView.setDivider(null);
        // Tells the ListView what data to use
        theListView.setAdapter(theAdapter);

        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String tvShowPicked = "You selected " +
                        String.valueOf(adapterView.getItemAtPosition(i));

                Toast.makeText(MainActivity.this, tvShowPicked, Toast.LENGTH_SHORT).show();

            }
        });


    }
    public void addToFavorites(View v){
        String popFavorites;
        Button favoriteButton = (Button)findViewById(R.id.favoriteButton);
        if(professor.isFavorite==false){
            professor.isFavorite=true;
            favoriteButton.setBackgroundResource(R.drawable.goldstar);
            StudentActivity.student.FavouriteProfessor.addProfessor(professor.Name);
            popFavorites = "Added to Favorites";
        }
        else{
            favoriteButton.setBackgroundResource(R.drawable.whitestar);
            popFavorites = "Removed from Favorites";
            StudentActivity.student.FavouriteProfessor.remove(professor.Name);
            professor.isFavorite=false;
        }

        Toast.makeText(MainActivity.this, popFavorites, Toast.LENGTH_SHORT).show();
    }
    public void requestForConnection(View v) {
        String connection = "Request Sent";
        Toast.makeText(MainActivity.this, connection, Toast.LENGTH_SHORT).show();

    }
    public  void connectMySQL(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection=DriverManager.getConnection(url,user,password);
            statement=connection.createStatement();
        } catch (Exception e) {
            System.out.println("error connecting driver");

        }
    }

}
