package com.androidlime.wikivarsity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
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

public class MainActivity extends AppCompatActivity {
    Professor professor;
    public String prfessorName;
    public  static Statement statement;
    DatabaseHelper my_db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        my_db=DataBaseStart.my_db;
        Bundle nameContainer=getIntent().getExtras();
        if (nameContainer != null) {
            prfessorName = nameContainer.getString("name");
            //The key argument here must match that used in the other activity
        }
        professor= getProfessorData();
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
        //menu.add("My Profile");
      //  menu.add("Home");
       // menu.add("Notifications");
       // menu.add("log Out");
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);


        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint("Search Professor");
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
        if(item.getItemId()==R.id.profile){
            Intent intent=new Intent(MainActivity.this,StudentActivity.class);
            startActivity(intent);
            return true;
        }
        if(item.getItemId()==R.id.home){
            Intent intent=new Intent(MainActivity.this,DataBaseStart.class);
            startActivity(intent);
            return true;
        }
        return  false;
    }
    public void getProfessorPage(String name){
        Cursor all=my_db.getProfessorByName(name);
       // ShowMessage("number ", String.valueOf(all.getCount()) );
        if(all.getCount()==0){
           // Toast.makeText(MainActivity.this,"THERE is no DATA", Toast.LENGTH_LONG).show();
            ShowMessage("ERROR","No Professor Found of the Name "+name);
        }
        else{
            Intent intent= new Intent(MainActivity.this,MainActivity.class);
            intent.putExtra("name",name);
            startActivity(intent);
        }

    }

    public Professor getProfessorData(){
     /*   professor= new Professor("Thomas Cormen","Harvard University","CE","Algorithm","3.9","www.cormen.com");
        StudentActivity.student= new Student("Mr A","abc@gmail.com","Bangladesh University of Engineering and Technology"
                ,"CSE","3.2",null,"abd","aodbaudfowuer");*/
        Cursor all=my_db.getProfessorByName(prfessorName);
        professor= new Professor();
        while(all.moveToNext()){
           professor.Name=  all.getString(0);
           professor.University=all.getString(1);
            professor.Dept=all.getString(2);
            professor.ResearchArea=all.getString(3);
            professor.MinimumCGPA=all.getString(4);
            professor.Website=all.getString(5);
            //professor.photo=all.getInt(6);
            professor.image=all.getString(6);


        }

       // professor.photo = R.drawable.cormen;
        professor.isFavorite=checkIfFavourite();
       // ShowMessage("Is Favourite",String.valueOf(professor.isFavorite));
        //System.out.println("Picture Id "+R.drawable.cormen);
        return professor;

    }
    public  void setContent(){

        TextView name= (TextView) findViewById(R.id.ProfessorName);
        name.setText(professor.Name);
        TextView varsity= (TextView) findViewById(R.id.professorVarsity);
        varsity.setText(professor.University);
        ImageView image= (ImageView) findViewById(R.id.professorImage);

        String uri = "@drawable/"+professor.image;
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        image.setImageResource(imageResource);

        Button favoriteButton = (Button)findViewById(R.id.favoriteButton);
        if(professor.isFavorite){
            favoriteButton.setBackgroundResource(R.drawable.yellowstar);
        }
        else {
            favoriteButton.setBackgroundResource(R.drawable.whitestar);
        }
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
    public  boolean checkIfFavourite(){
        Cursor result=my_db.getFavouriteByName(professor.Name);
        if(result.getCount()==0){
            return false;
        }
        return true;
    }
    public void addToFavorites(View v){
        String popFavorites;
        Button favoriteButton = (Button)findViewById(R.id.favoriteButton);
        if(professor.isFavorite==false){
            professor.isFavorite=true;
            favoriteButton.setBackgroundResource(R.drawable.yellowstar);
           // StudentActivity.student.FavouriteProfessor.addProfessor(professor.Name);
            my_db.insertFavourite(professor.Name,1);
         //   ShowAllFavourites();
            popFavorites = "Added to Favorites";
        }
        else{
            favoriteButton.setBackgroundResource(R.drawable.whitestar);
            popFavorites = "Removed from Favorites";
            //StudentActivity.student.FavouriteProfessor.remove(professor.Name);
            my_db.DeleteFavourite(professor.Name);
           // ShowAllFavourites();
            professor.isFavorite=false;
        }

        Toast.makeText(MainActivity.this, popFavorites, Toast.LENGTH_SHORT).show();
    }
    public void ShowAllFavourites(){
        Cursor all=my_db.getFavourites();
        if(all.getCount()==0){
            Toast.makeText(MainActivity.this,"THERE is no Professors", Toast.LENGTH_LONG).show();
            ShowMessage("ERROR","No Favourite Found");
        }
        else{
            StringBuffer msg= new StringBuffer();
            while(all.moveToNext()){
                msg.append("Id :"+all.getString(0)+"\n");
                msg.append("Name :"+all.getString(1)+"\n");
                ShowMessage("DATA",msg.toString());
            }
        }
    }
    public void requestForConnection(View v) {
        String connection = "Request Sent";
        Toast.makeText(MainActivity.this, connection, Toast.LENGTH_SHORT).show();

    }
    public  void ShowMessage(String title, String msg){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();
    }


}
