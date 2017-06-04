package com.androidlime.wikivarsity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Afrina on 04-Jun-17.
 */
public class StudentActivity extends AppCompatActivity{
    static  Student student;
    protected void onCreate(Bundle savedInstanceState) {
        //connectMySQL();

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
        return true;
    }
    public  void setContent(){
        //student= new Student("Mr A","abc@gmail.com","Bangladesh University of Engineering and Technology"
          //      ,"CSE","3.2",null,"abd","aodbaudfowuer");
        Favourite favourite= new Favourite();
        favourite.addProfessor("Farbin");
        student.FavouriteProfessor=favourite;
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
}

