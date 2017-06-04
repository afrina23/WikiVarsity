package com.androidlime.wikivarsity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Afrina on 04-Jun-17.
 */
public class StudentActivity extends AppCompatActivity{
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
        Student student= new Student("Mr A","abc@gmail.com","Bangladesh University of Engineering and Technology","CSE","3.2",null,"abd","aodbaudfowuer");
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





    }
}

