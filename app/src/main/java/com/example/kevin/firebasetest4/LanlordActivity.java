package com.example.kevin.firebasetest4;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kevin.firebasetest4.FireBase.HouseData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LanlordActivity extends AppCompatActivity implements ListView.OnItemClickListener {

    TextView txtlandlord, txtAlarm;
    String landordID = "";
    Myadapter myadapter;
    static String USER_ID = "J3455800020";
    static String USER_TYPE = "lanlord";
    ArrayList<String> alrAddr = new ArrayList<>();
    ArrayList<Boolean> alrAlarm = new ArrayList<>();
    ArrayList<String> alrTentNote = new ArrayList<>();

    ArrayList<String> alrHouseKey = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lanlord);

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(this);

        txtlandlord = (TextView) findViewById(R.id.txtlandlord);
        txtlandlord.setText(USER_ID + "歡迎使用本系統!");

        myadapter = new Myadapter(this);
        listView.setAdapter(myadapter);


        DatabaseReference reference_contacts = FirebaseDatabase.getInstance().getReference("House");
        reference_contacts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                alrHouseKey = new ArrayList<>();
                alrTentNote=new ArrayList<String>();
                alrAddr=new ArrayList<String>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    HouseData house = ds.getValue(HouseData.class);
                    if (house.getCreateUser().equals(USER_ID)
//                            || house.getLandlordID().equals(FINAL_USER_ID)
//                            || house.getTenantID().equals(FINAL_USER_ID)
 ) {
                        alrAddr.add("建案: "+house.getTitle());
                        alrTentNote.add("地址: " + house.getCity()+house.getLocation()+"\t"+house.getAddr());
                        alrHouseKey.add(ds.getKey());
                    }
                }
                myadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addHouse(View target) {
        Intent intent = new Intent();
        intent.setClass(this, AddHouseDataActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("landordID", landordID);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    //implements  ListView.OnItemClickListener===================================
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//        System.out.println("rowId = " + i);
//        Toast.makeText(this, "第" + i + "項", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.setClass(this, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("UserID", USER_ID);
        bundle.putString("HouseKey", alrHouseKey.get(i));
//        Log.d("fire==on=HouseKey=",alrHouseKey.get(i));
//        Log.d("fire==on=UserID=",FINAL_USER_ID);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
//implements  ListView.OnItemClickListener===================================//implements  ListView.OnItemClickListener===================================


    //==================OPTION MENU======================
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("設定");
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent it = new Intent(LanlordActivity.this, AddLanlordData.class);
        it.putExtra("LANLORD_ID",USER_ID);
        it.putExtra("USER_TYPE",USER_TYPE);
        startActivity(it);
        return super.onOptionsItemSelected(item);
    }


    //=================ADAPTER======================================
    class Myadapter extends BaseAdapter {
        private LayoutInflater myInflater;
        Context mContext;

        public Myadapter(Context context) {
            myInflater = LayoutInflater.from(context);
            this.mContext = context;
        }

        @Override
        public int getCount() {
            return alrAddr.size();
        }

        @Override
        public Object getItem(int i) {
            return alrAddr.size();
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        AlertDialog dialog; //讓自定Layout可有關閉功能
        View root;
        EditText et_message;
        Button confirmMessage, cancelMessage;

        private DatabaseReference mDatabase;

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
//            mDatabase = FirebaseDatabase.getInstance().getReference(FINAL_USER_ID);

            final int position = i;

//            Log.d("fire==i=", i + "");
            convertView = myInflater.inflate(R.layout.my_simple_item, null);
            TextView txTentNote = (TextView) convertView.findViewById(R.id.txTentNote);
            TextView txHouseAddr = (TextView) convertView.findViewById(R.id.txHouseAddr);
            ImageButton btnDel = convertView.findViewById(R.id.btnDel);
//            Button addTenant=convertView.findViewById(R.id.btnAddTenant) ;
//            Button delhouse=convertView.findViewById(R.id.btnDelhouse) ;
            txHouseAddr.setText(alrAddr.get(i));
            txTentNote.setText(alrTentNote.get(i));

            Log.d("fire==", "" + i);


            btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("fire===position", position + "");
                    Log.d("fire===alrHouseKey", alrHouseKey.get(position));
                    LayoutInflater inflater = (LayoutInflater) mContext
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    root = inflater.inflate(R.layout.dialog_delete_layout, null);//找出根源樹,
                    TextView txDeleteDescription = root.findViewById(R.id.txDeleteDescription);
                    Button confirmDelete = root.findViewById(R.id.btn_Deleteconfirm);
                    Button cancelDelete = root.findViewById(R.id.btn_Deletecancel);
                    txDeleteDescription.setText("請問是否要將資料刪除");
                    cancelDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    confirmDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DatabaseReference mDatabase;
                            mDatabase = FirebaseDatabase.getInstance().getReference("House");
                            mDatabase = mDatabase.child(alrHouseKey.get(position));
                            mDatabase.removeValue();
//                            myadapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    });
                    AlertDialog.Builder abc = new AlertDialog.Builder(mContext);
                    abc.setView(root);
                    dialog = abc.show();

                }
            });
//            addTenant.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent=new Intent();
//                    intent.setClass(LanlordActivity.this,AddTenantActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("landordID",landordID);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//
//                }
//            });
//            imgBtnTalk.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    LayoutInflater inflater = (LayoutInflater) mContext
//                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                    root = inflater.inflate(R.layout.dialog_message_layout, null);//找出根源樹,
//                    et_message=root.findViewById(R.id.et_message);
//                    confirmMessage = root.findViewById(R.id.btm_confirmMessage);
//                    cancelMessage=root.findViewById(R.id.btn_cancelMessage);
//
//                    cancelMessage.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            dialog.dismiss();
//                        }
//                    });
//                    confirmMessage.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                            mDatabase = mDatabase.child(alrHouseKey.get(position));
//                            Map<String, Object> nameMap = new HashMap<String, Object>();
//                            nameMap.put("landNote", et_message.getText().toString().trim());
//                            mDatabase.updateChildren(nameMap);
//                            dialog.dismiss();
//
//                        }
//                    });
//                    AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
//                    ab.setView(root);
//                    dialog = ab.show();
//                }
//            });

//            chkPayLandlord.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//
////                        Log.d("fire==打勾==…", b + "," + position);
//                    mDatabase = mDatabase.child(alrHouseKey.get(position));
//                    Map<String, Object> nameMap = new HashMap<String, Object>();
//                    nameMap.put("payLandord",b);
//                    mDatabase.updateChildren(nameMap);
//                    if (b) {
//                        nameMap.put("prePayDate", new Date());
//                        mDatabase.updateChildren(nameMap);
//                    }
//
//
//
//                }
//            });


            return convertView;
        }
    }




}
