package com.example.kevin.firebasetest4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mDatabase = FirebaseDatabase.getInstance().getReference();

//        mDatabase = FirebaseDatabase.getInstance().getReference("Management");
////                        mDatabase = mDatabase.child(TenantActivity.listPriceKey.get(mGroupPosition));
//        mDatabase = mDatabase.child("-L-AoJRx0PRgDNbPPedX");
//        mDatabase.removeValue();

//        mDatabase.addValueEventListener(valueEventListener);
        mDatabase.addChildEventListener(childEventListener);
        //ListenerForSingleValueEvent( originalListener) 解除監聽

        //add方法1
//        mDatabase = mDatabase.child("0");
//        User user = new User("3Amy", 3224);
//        mDatabase.setValue(user);

        //add方法1
//        mDatabase = mDatabase.child("users").child("0");
//        User user = new User("2Amy", 224);
//        mDatabase.setValue(user);
        //add方法2,用 push( ) 新增一個擁有唯一ID的節點資料
//        mDatabase = mDatabase.child("users");
//        User user = new User("Addy", 20);
//        mDatabase.push().setValue(user);

        //update,只更新姓名,  setValue:沒有指定資料的節點也會被覆蓋掉
//        mDatabase = mDatabase.child("users").child("3");
//        Map<String, Object> nameMap = new HashMap<String, Object>();
//        nameMap.put("name", "陳大明");
//        mDatabase.updateChildren(nameMap);

        //setValue:沒有指定資料的節點也會被覆蓋掉
//        mDatabase = mDatabase.child("addr").child("1");
//        Map<String, Object> nameMap = new HashMap<String, Object>();
//        nameMap.put("name", "Chi Kuo4");
//        mDatabase.setValue(nameMap);


        //用 removeValue( ) 刪除資料,或是setValue(null);updateChildren(null);
//        mDatabase = mDatabase.child("users").child("-KzcwTXekOYB1ltZbLY4");
//        mDatabase.removeValue();


    }

//  ====  一次回傳所有子節點資料…=================================
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d("FireBaseTraining", " snapshot.getValue() = " + dataSnapshot.getValue());
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                User user = ds.getValue(User.class);
                Log.d("FireBaseTraining", "name = " + user.getName() + " , Age = " + user.getAge());
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            Log.w("realdb", "loadPost:onCancelled", databaseError.toException());
            // ...
        }
    };
//    =================================================================================?…
    //此方法只會回傳有發生資料變更的單一子節點資料，而非
    ChildEventListener childEventListener=new ChildEventListener(){

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            User user = dataSnapshot.getValue(User.class);
            Log.d("FireBaseTraining", "Added : name = " + user.getName() + " , Age = " + user.getAge());
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            User user = dataSnapshot.getValue(User.class);
            Log.d("FireBaseTraining", "Changed : name = " + user.getName() + " , Age = " + user.getAge());
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            User user = dataSnapshot.getValue(User.class);
            Log.d("FireBaseTraining", "Removed : name = " + user.getName() + " , Age = " + user.getAge());
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            User user = dataSnapshot.getValue(User.class);
            Log.d("FireBaseTraining", "Moved : name = " + user.getName() + " , Age = " + user.getAge());
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };



}
