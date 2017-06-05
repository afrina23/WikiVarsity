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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Afrina on 04-Jun-17.
 */
public class StudentActivity extends AppCompatActivity{
    static  Student student;
    DatabaseHelper my_db;
    protected void onCreate(Bundle savedInstanceState) {
        //connectMySQL();
        my_db=DataBaseStart.my_db;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student);
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
            Intent intent=new Intent(StudentActivity.this,StudentActivity.class);
            startActivity(intent);
            return true;
        }
        return  false;
    }
    public void getProfessorPage(String name){
        Cursor all=my_db.getProfessorByName(name);
        ShowMessage("number ", String.valueOf(all.getCount()) );
        if(all.getCount()==0){
            Toast.makeText(StudentActivity.this,"THERE is no DATA", Toast.LENGTH_LONG).show();
            ShowMessage("ERROR","No Professor Found of this Name"+name);
        }
        else{
            Intent intent= new Intent(StudentActivity.this,MainActivity.class);
            intent.putExtra("name",name);
            startActivity(intent);
        }

    }

    public  void setContent(){
        student= new Student("Mr A","abc@gmail.com","Bangladesh University of Engineering and Technology"
                ,"CSE","3.2",null,"abd","aodbaudfowuer");
        //Favourite favourite= new Favourite();
       // favourite.addProfessor("Farbin");
        student.FavouriteProfessor=getAllFavourites();
        TextView studentName= (TextView) findViewById(R.id.studentName);
        TextView university= (TextView) findViewById(R.id.StudentVarsity);
        ImageView imageView= (ImageView) findViewById(R.id.studentImage);
        TextView email= (TextView) findViewById(R.id.studentEmail);
        TextView dept= (TextView) findViewById(R.id.studentDept);
        TextView cgpa= (TextView) findViewById(R.id.studentCGPA);
        TextView cv= (TextView) findViewById(R.id.studentCV);
        TextView about= (TextView) findViewById(R.id.studentAbout);
        studentName.setText(student.Name);
        university.setText(student.University);
        imageView.setImageResource(R.drawable.dubu);
        email.setText(student.Email);
        dept.setText(student.Dept);
        cgpa.setText(student.CGPA);
        cv.setText(student.CV);
        about.setText(student.About);


        //list view

        ArrayList<String> list = new ArrayList<String>();
        for(int i=0;i<student.FavouriteProfessor.getSize();i++){
                list.add(student.FavouriteProfessor.getProfessorName(i));
        }


        //instantiate custom adapter
        MyCustomAdapter adapter = new MyCustomAdapter(list, this);

        //handle listview and assign adapter
        ListView lView = (ListView)findViewById(R.id.favouriteList);
        lView.setAdapter(adapter);





    }
    public Favourite getAllFavourites(){
        Cursor all=my_db.getFavourites();
        Favourite result= new Favourite();
        if(all.getCount()==0){
            Toast.makeText(StudentActivity.this,"THERE is no Professors", Toast.LENGTH_LONG).show();
            ShowMessage("ERROR","No Favourite Found");
        }
        else{
            StringBuffer msg= new StringBuffer();
            while(all.moveToNext()){
                result.addProfessor(all.getString(1));
               /* msg.append("Id :"+all.getString(0)+"\n");
                msg.append("Name :"+all.getString(1)+"\n");
                ShowMessage("DATA",msg.toString());*/
            }
        }
        return result;
    }
    public  void ShowMessage(String title, String msg){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();
    }

}

