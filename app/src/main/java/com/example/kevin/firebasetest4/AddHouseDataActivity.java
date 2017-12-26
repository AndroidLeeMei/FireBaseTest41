package com.example.kevin.firebasetest4;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevin.firebasetest4.FireBase.HouseData;
import com.example.kevin.firebasetest4.FireBase.PriceData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

    public class AddHouseDataActivity extends AppCompatActivity {

        private DatabaseReference mDatabaseCity,mDatabaseRoad,mDatabaseAdd;

        private Spinner sp;//第一個下拉選單
        private Spinner sp2;//第二個下拉選單
        private Context context;

        ArrayAdapter<String> adapter ;
        ArrayAdapter<String> adapter2;

        private ArrayList<String> alrRoadDown=new ArrayList<>();
        private ArrayList<String> alrCity=new ArrayList<>();
        private ArrayList<String> alrRoad=new ArrayList<>();

        RadioButton raLandlord,raTenant;
        EditText editAddrNum,edtTel,edtTitle;
        TextView edtAddr;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_house_data);

            context = this;
            findViews();

            mDatabaseCity = FirebaseDatabase.getInstance().getReference("TWzipCode5");
            mDatabaseCity.addValueEventListener(valueEventListener);

            mDatabaseRoad = FirebaseDatabase.getInstance().getReference("TWzipCode5").child("桃園市中壢區");
            mDatabaseRoad.addValueEventListener(valueEventListenerRoad);





            //程式剛啟始時載入第一個下拉選單
            adapter = new ArrayAdapter<String>(this,R.layout.spinner_style, alrCity);
            adapter.setDropDownViewResource(R.layout.spinner_style);
            sp = (Spinner) findViewById(R.id.spinnerCity);
    //        adapter.setDropDownViewResource(R.layout.spinner_style);
            sp.setAdapter(adapter);


            //程式剛啟始時載入第一個下拉選單
            adapter2 = new ArrayAdapter<String>(this,R.layout.spinner_style, alrRoad);
            adapter2.setDropDownViewResource(R.layout.spinner_style);
    //        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp2 = (Spinner) findViewById(R.id.spinnerRoad);
            sp2.setAdapter(adapter2);

            setlisenter();
        }

    //  下拉類別的監看式
        private AdapterView.OnItemSelectedListener selectListener = new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id){
    //            Log.d("fire==",position+"");
    //            if (position>=0) {
                    int pos = sp.getSelectedItemPosition();
                    switch (parent.getId()) {
                        case R.id.spinnerCity:
                            Log.d("col==", "spinnerCity");
                            //讀取第一個下拉選單是選擇第幾個

                            alrRoadDown = new ArrayList<>();
                            Log.d("fire=",alrCity.get(pos));

                            mDatabaseRoad = FirebaseDatabase.getInstance().getReference("TWzipCode5").child(alrCity.get(pos));
                            mDatabaseRoad.addValueEventListener(valueEventListenerRoad2);

                            //重新產生新的Adapter，用的是二維陣列type2[pos]
                            adapter2 = new ArrayAdapter<String>(context,R.layout.spinner_style, alrRoadDown);
                            adapter2.setDropDownViewResource(R.layout.spinner_style);
                            //載入第二個下拉選單Spinner
                            sp2.setAdapter(adapter2);
                            break;
                        case R.id.spinnerRoad:
                            break;
                    }
                    edtAddr.setText("" + sp.getSelectedItem() + sp2.getSelectedItem());
    //            }

            }

            public void onNothingSelected(AdapterView<?> arg0){

            }

        };


        private void findViews(){
            raLandlord=(RadioButton)findViewById(R.id.rnLandlord);
            raTenant=(RadioButton)findViewById(R.id.rnTenant);

            edtTitle=(EditText)findViewById(R.id.edtTitle);
            edtAddr=(TextView)findViewById(R.id.edtAddr);
            editAddrNum=(EditText)findViewById(R.id.editAddrNum);
            edtTel=(EditText)findViewById(R.id.edtTitle);

        }

        private void setlisenter() {
            sp.setOnItemSelectedListener(selectListener);
            sp2.setOnItemSelectedListener(selectListener);
        }

        ValueEventListener valueEventListenerRoad2 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        alrRoadDown.add(ds.getKey());
                }

                //重新產生新的Adapter，用的是二維陣列type2[pos]
                adapter2 = new ArrayAdapter<String>(context,R.layout.spinner_style,alrRoadDown);
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

    //===============================================================================
        public void addHouseData(View target){
            if (raLandlord.isChecked()|| raTenant.isChecked()) {
                String strlandord = "", strtentant = "";
                String houseCreate = (raLandlord.isChecked() ? "landlord" : "tenant");
                if (raLandlord.isChecked()) strlandord =LanlordActivity.USER_ID;
                if (raTenant.isChecked()) strtentant = LanlordActivity.USER_ID;
                //        Log.d("addHouseData","raTenant="+raLandlord.isChecked());
                //        Log.d("addHouseData","raTenant="+raTenant.isChecked());
                //        Log.d("addHouseData","edtTitle="+edtTitle.getText());
                //        Log.d("addHouseData","edtaddr="+edtAddr.getText());
                //        Log.d("addHouseData","sp="+sp.getSelectedItem().toString());
                //        Log.d("addHouseData","sp2="+sp2.getSelectedItem().toString());
                //        Log.d("addHouseData","houseCreate="+houseCreate);

                //        mDatabaseAdd = FirebaseDatabase.getInstance().getReference("House");
                HouseData houseData = new HouseData(
                        LanlordActivity.USER_ID
                        , strlandord
                        , strtentant
                        , houseCreate
                        , sp.getSelectedItem().toString()
                        , sp2.getSelectedItem().toString()
                        , editAddrNum.getText().toString()
                        , edtTitle.getText().toString());
                DatabaseReference mDatabaseAdd = FirebaseDatabase.getInstance().getReference("House");
                mDatabaseAdd.push().setValue(houseData);
                finish();
            }else
                Toast.makeText(AddHouseDataActivity.this,"請選擇身分類別",Toast.LENGTH_SHORT).show();
        }

        //====================================================================
        public void addHouseCancel(View target){
            finish();
        }

        public void addSubHouse(View target){


        }
    }
