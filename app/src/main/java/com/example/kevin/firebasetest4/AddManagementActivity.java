package com.example.kevin.firebasetest4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kevin.firebasetest4.FireBase.Electricity;
import com.example.kevin.firebasetest4.FireBase.Management;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddManagementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_management);

        String input = "2017-09-05";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date t = null;
        try{
            t = formatter.parse(input);

        }catch(ParseException e){

        }

        Management management=new Management("J3455888889","-KzrIWelZvFNuOXWFqKN","H001","tenantID01",t,500);



        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Management");
        mDatabase.push().setValue(management);
    }
}
