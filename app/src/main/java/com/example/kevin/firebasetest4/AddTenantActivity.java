package com.example.kevin.firebasetest4;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kevin.firebasetest4.FireBase.HouseData;
import com.example.kevin.firebasetest4.FireBase.Tenant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.kevin.firebasetest4.R.id.etName;
import static com.example.kevin.firebasetest4.R.id.imgRentDate;

public class AddTenantActivity extends AppCompatActivity {
    private String houseId="aaa001";

    private DatabaseReference mDatabaseCity, mDatabaseRoad, mDatabaseAdd;


    private Context context;

    ArrayAdapter<String> adapter, adapter2;
    ArrayAdapter<Integer> adaterPeriod, adapterDay;

    private ArrayList<String> alrRoadDown = new ArrayList<>();
    private ArrayList<String> alrCity = new ArrayList<>();
    private ArrayList<String> alrRoad = new ArrayList<>();
    private ArrayList<Integer> alrPeriod = new ArrayList<>();
    private ArrayList<Integer> alrDay = new ArrayList<>();

    RadioButton raLandlord, raTenant;
    ImageButton btnSignDate, imgStartDate, imgEndDate;
    EditText etTel, edtName, etID, etPhone, etRent;
    TextView tx,edtAddr, etAddrNumber, txtLandlordInfo, txSignDate, txStartDate, txEndDate;
    Spinner sp, sp2, spPayPeriod, spPayDay;//第一個下拉選單

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tenant);


        context = this;
        findViews();
        txtLandlordInfo.setText(LanlordActivity.USER_ID +"您好");

        mDatabaseCity = FirebaseDatabase.getInstance().getReference("TWzipCode5");
        mDatabaseCity.addValueEventListener(valueEventListener);

        mDatabaseRoad = FirebaseDatabase.getInstance().getReference("TWzipCode5").child("桃園市中壢區");
        mDatabaseRoad.addValueEventListener(valueEventListenerRoad);


        //程式剛啟始時載入第一個下拉選單
        adapter = new ArrayAdapter(this, R.layout.spinner_style, alrCity);
        adapter.setDropDownViewResource(R.layout.spinner_style);
        sp.setAdapter(adapter);

        //程式剛啟始時載入第一個下拉選單
        adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_style, alrRoad);
        adapter2.setDropDownViewResource(R.layout.spinner_style);
        sp2.setAdapter(adapter2);
        //=========================================
        for (int i = 1; i <= 12; i++) alrPeriod.add(i);
        adaterPeriod = new ArrayAdapter<Integer>(this, R.layout.spinner_style, alrPeriod);
        adaterPeriod.setDropDownViewResource(R.layout.spinner_style);
        spPayPeriod.setAdapter(adaterPeriod);
        //==================================================
        for (int i = 1; i <= 31; i++) alrDay.add(i);
        adapterDay = new ArrayAdapter(this, R.layout.spinner_style, alrDay);
        adapterDay.setDropDownViewResource(R.layout.spinner_style);
        spPayDay.setAdapter(adapterDay);

        setlisenter();
    }

    //  下拉類別的監看式
    private AdapterView.OnItemSelectedListener selectListener = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
//            Log.d("fire==",position+"");
//            if (position>=0) {
            int pos = sp.getSelectedItemPosition();
            switch (parent.getId()) {
                case R.id.spinnerCity:
                    Log.d("col==", "spinnerCity");
                    //讀取第一個下拉選單是選擇第幾個

                    alrRoadDown = new ArrayList<>();
                    Log.d("fire=", alrCity.get(pos));

                    mDatabaseRoad = FirebaseDatabase.getInstance().getReference("TWzipCode5").child(alrCity.get(pos));
                    mDatabaseRoad.addValueEventListener(valueEventListenerRoad2);

                    //重新產生新的Adapter，用的是二維陣列type2[pos]
                    adapter2 = new ArrayAdapter<String>(context, R.layout.spinner_style, alrRoadDown);
                    adapter2.setDropDownViewResource(R.layout.spinner_style);
                    //載入第二個下拉選單Spinner
                    sp2.setAdapter(adapter2);
                    break;
                case R.id.spinnerRoad:
                    break;
            }
            edtAddr.setText("" + sp.getSelectedItem() + sp2.getSelectedItem());
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }

    };


    private void findViews() {
        raLandlord = (RadioButton) findViewById(R.id.rnLandlord);
        raTenant = (RadioButton) findViewById(R.id.rnTenant);

        txtLandlordInfo = (TextView) findViewById(R.id.txtLandlordInfo);
        txSignDate = (TextView) findViewById(R.id.txSignDate);
        txStartDate = (TextView) findViewById(R.id.txStartDate);
        txEndDate = (TextView) findViewById(R.id.txEndDate);
        edtName = (EditText) findViewById(R.id.etName);
        etID = (EditText) findViewById(R.id.etID);
        edtAddr = (TextView) findViewById(R.id.etAddr);
        etAddrNumber = (EditText) findViewById(R.id.etAddrNumber);
        etTel = (EditText) findViewById(R.id.etTel);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etRent = (EditText) findViewById(R.id.etRent);
        btnSignDate = (ImageButton) findViewById(R.id.btnSignDate);
        imgStartDate = (ImageButton) findViewById(R.id.imgStartDate);
        imgEndDate = (ImageButton) findViewById(R.id.imgEndDate);
        spPayPeriod = (Spinner) findViewById(R.id.spPayPeriod);
        spPayDay = (Spinner) findViewById(R.id.spPayDay);
        sp2 = (Spinner) findViewById(R.id.spinnerRoad);
        sp = (Spinner) findViewById(R.id.spinnerCity);
    }



    ValueEventListener valueEventListenerRoad2 = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                alrRoadDown.add(ds.getKey());
            }

            //重新產生新的Adapter，用的是二維陣列type2[pos]
            adapter2 = new ArrayAdapter<String>(context, R.layout.spinner_style, alrRoadDown);
            adapter2.setDropDownViewResource(R.layout.spinner_style);

            //載入第二個下拉選單Spinner
            sp2.setAdapter(adapter2);

//            adapter2.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            Log.w("realdb", "loadPost:onCancelled", databaseError.toException());
            // ...
        }
    };


    ValueEventListener valueEventListenerRoad = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
//            alrRoad=new ArrayList<>();
            Log.d("FireBaseTraining", " snapshot.getValue() = " + dataSnapshot.getValue());
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                alrRoad.add(ds.getKey());
                Log.d("FireBaseTraining", " ds.getKey()=" + ds.getKey());

            }
            adapter2.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            Log.w("realdb", "loadPost:onCancelled", databaseError.toException());
            // ...
        }
    };


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
//            alrCity=new ArrayList<>();
            Log.d("FireBaseTraining", " snapshot.getValue() = " + dataSnapshot.getValue());
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                alrCity.add(ds.getKey());
            }
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            Log.w("realdb", "loadPost:onCancelled", databaseError.toException());
            // ...
        }
    };
    //================================================
    public void addTenantCancel(View target){
        finish();
    }

    //===============================================================================
    public void addTenant(View target) {
//        Log.d("addHouseData","edtName="+edtName.getText());
//        Log.d("addHouseData","etID="+etID.getText());
//        Log.d("addHouseData","edtAddr="+edtAddr.getText());
//        Log.d("addHouseData","etAddrNumber="+etAddrNumber.getText());
//        Log.d("addHouseData","etTel="+etTel.getText());
//        Log.d("addHouseData","etPhone="+etPhone.getText());
//        Log.d("addHouseData","txSignDate="+txSignDate.getText());
//        Log.d("addHouseData","txStartDate="+txStartDate.getText());
//        Log.d("addHouseData","txEndDate="+txEndDate.getText());
//        Log.d("addHouseData","etRent="+etRent.getText());
//        Log.d("addHouseData","spPayPeriod="+spPayPeriod.getSelectedItem().toString());
//        Log.d("addHouseData","spPayDay="+spPayDay.getSelectedItem().toString());
        String tempRent=etRent.getText().toString().trim();
        if (tempRent.equals("")) tempRent="0";

        Tenant tenant=new Tenant(
                LanlordActivity.USER_ID
                ,houseId
                ,edtName.getText().toString().trim()
                ,etID.getText().toString().trim()
                ,edtAddr.getText().toString().trim() + etAddrNumber.getText().toString().trim()
                ,etTel.getText().toString().trim()
                ,etPhone.getText().toString().trim()
                ,txSignDate.getText().toString().trim()
                ,txStartDate.getText().toString().trim()
                ,txEndDate.getText().toString().trim()
                ,Integer.parseInt(tempRent)
                ,Integer.parseInt(spPayPeriod.getSelectedItem().toString().trim())
                ,Integer.parseInt(spPayDay.getSelectedItem().toString().trim()));

        DatabaseReference mDatabaseAdd = FirebaseDatabase.getInstance().getReference("Tenant");
        mDatabaseAdd.push().setValue(tenant);
    }

    //====================================================================
    public void addHouseCancel(View target) {
        finish();


    }
//=================================================================
private void setlisenter() {
    sp.setOnItemSelectedListener(selectListener);
    sp2.setOnItemSelectedListener(selectListener);
    btnSignDate.setOnClickListener(dateListener);
    imgStartDate.setOnClickListener(dateListener);
    imgEndDate.setOnClickListener(dateListener);
}

    //===========================================================
    View.OnClickListener dateListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view==btnSignDate) tx=txSignDate;
            if (view==imgStartDate) tx=txStartDate;
            if (view==imgEndDate) tx=txEndDate;
            Date now = new Date();
            DatePickerDialog dpd=new DatePickerDialog(AddTenantActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    String input = i +"-"+(i1+1)+"-"+("0"+i2).substring(("0"+i2).length()-2);

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date t = null;
                    try{
                        t = formatter.parse(input);
                    }catch(ParseException e){
                    }
                    tx.setText(input);
                }
            },now.getYear()+1900,now.getMonth(),now.getDay()); //畫面出來預設值  2017,12,05
            dpd.show();
        }
    };

}

