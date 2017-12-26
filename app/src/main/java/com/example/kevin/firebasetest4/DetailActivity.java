package com.example.kevin.firebasetest4;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevin.firebasetest4.Adapter.HouseDetailAdapter;
import com.example.kevin.firebasetest4.Adapter.PriceDataAdapter;
import com.example.kevin.firebasetest4.FireBase.HouseData;
import com.example.kevin.firebasetest4.FireBase.PriceData;
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
import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends AppCompatActivity implements ListView.OnItemClickListener{

    TextView txtlandlord,txtAlarm;
    String landordID = "";
    HouseDetailAdapter myadapter;
    PriceDataAdapter myPriceadapter;

    public static String houseKey;
    public static String userKey;
    static final String FINAL_USER_TYPE="lanlord";
    public static  ArrayList<String> alrDelKey=new ArrayList<>();
    public static  ArrayList<String> alrAddr=new ArrayList<>();
    public  static ArrayList<Boolean> alrAlarm=new ArrayList<>();
    public static ArrayList<String> alrTentNote=new ArrayList<>();
    public static ArrayList<Boolean> alrPayTent=new ArrayList<>();
    public static ArrayList<Boolean> alrPayLandlord=new ArrayList<>();
    public static ArrayList<Boolean> alrChcekPrice=new ArrayList<>();

    public static ArrayList<String> alrHouseKey=new ArrayList<>();
    ArrayList<Date>alrPrePayDate=new ArrayList<Date>();
    ArrayList<Date>alrNextPayDate=new ArrayList<Date>();
//…    public HashMap<String, List<Boolean>> mTenantChkStatus = new HashMap<String, List<Boolean>>();//記錄房客是否付款
ListView listView;
    DatabaseReference reference_contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //========================================
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        houseKey = bundle.getString("HouseKey");
        userKey = bundle.getString("UserID");
        //========================================
        listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(this);

        defaultHouse();

//        myadapter = new DetailActivity.Myadapter(this);
//        myadapter = new HouseDetailAdapter(this);
//        listView.setAdapter(myadapter);

//        java.util.Date current=new java.util.Date();
//        java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String c=sdf.format(current);
    }


    //implements  ListView.OnItemClickListener===================================
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        System.out.println("rowId = " + i);
        Toast.makeText(this, "第" + i + "項", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    //=========================================================
    public void clickRent(View target){
        alrDelKey=new ArrayList<>();
        alrAddr=new ArrayList<>();
        alrChcekPrice=new ArrayList<>();
        reference_contacts = FirebaseDatabase.getInstance().getReference("Rent");
        reference_contacts.orderByChild("dateString").addValueEventListener(valueEventListenerPrice);

        myPriceadapter = new PriceDataAdapter(this,"Rent");
        listView.setAdapter(myPriceadapter);
        myPriceadapter.notifyDataSetChanged();
    }
    //=========================================================
    public void clickWater(View target){
        alrDelKey=new ArrayList<>();
        alrAddr=new ArrayList<>();
        alrChcekPrice=new ArrayList<>();
        reference_contacts = FirebaseDatabase.getInstance().getReference("Water");
        reference_contacts.orderByChild("dateString").addValueEventListener(valueEventListenerPrice);

        myPriceadapter = new PriceDataAdapter(this,"Water");
        listView.setAdapter(myPriceadapter);
        myPriceadapter.notifyDataSetChanged();
    }
    //=========================================================
    public void clickElectricity(View target){
        alrDelKey=new ArrayList<>();
        alrAddr=new ArrayList<>();
        alrChcekPrice=new ArrayList<>();
        reference_contacts = FirebaseDatabase.getInstance().getReference("Electricity");
        reference_contacts.orderByChild("dateString").addValueEventListener(valueEventListenerPrice);

        myPriceadapter = new PriceDataAdapter(this,"Electricity");
        listView.setAdapter(myPriceadapter);
        myPriceadapter.notifyDataSetChanged();
    }
    //=========================================================
    public void clickManagement(View target){
        alrDelKey=new ArrayList<>();
        alrAddr=new ArrayList<>();
        alrChcekPrice=new ArrayList<>();
        reference_contacts = FirebaseDatabase.getInstance().getReference("Management");
        reference_contacts.orderByChild("dateString").addValueEventListener(valueEventListenerPrice);

        myPriceadapter = new PriceDataAdapter(this,"Management");
        listView.setAdapter(myPriceadapter);
        myPriceadapter.notifyDataSetChanged();

    }

    //============================================================
    public void clicktenant(View target){
        myadapter = new HouseDetailAdapter(this);
        listView.setAdapter(myadapter);
        reference_contacts = FirebaseDatabase.getInstance().getReference("Tenant");
        reference_contacts.addValueEventListener(valueEventListenerTenant);

    }
    //================================================
    public void defaultHouse(){
        myadapter = new HouseDetailAdapter(this);
        listView.setAdapter(myadapter);
        Log.d("fire==defaultHouse","defaultHouse");
        reference_contacts = FirebaseDatabase.getInstance().getReference("House");
        reference_contacts.addValueEventListener(valueEventListener);
    }
    public void clickHouse(View target){
        defaultHouse();
    }

//==============================================================
private ValueEventListener valueEventListenerPrice = new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
//        Log.d("fire==alrWaterKeCha1=",alrDelKey.size()+"");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        alrDelKey=new ArrayList<>();
        alrAddr=new ArrayList<>();
        alrChcekPrice=new ArrayList<>();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            PriceData priceData = ds.getValue(PriceData.class);
            alrChcekPrice.add(priceData.getIsCheck());
//            alrAddr.add(" "+formatter.format(priceData.getDate()) + "   " + priceData.getPrice() + "\n" +ds.getKey());
            alrAddr.add(" "+formatter.format(priceData.getDate()) + "   " + priceData.getPrice());
            alrDelKey.add(ds.getKey());
        }
        myPriceadapter.notifyDataSetChanged();
//        Log.d("fire==alrWaterKeCha2=",alrDelKey.size()+"");

    }

    //======================================================================

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
};
//====================================================================

//========================================================

    private ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//                alrAlarm.clear();
            alrAlarm=new ArrayList<Boolean>();
            alrHouseKey.clear();
            alrTentNote.clear();
            alrPayTent.clear();
            alrPayLandlord.clear();
            alrAddr.clear();
            alrPrePayDate.clear();
            alrNextPayDate.clear();
            HouseData houseData =dataSnapshot.child(houseKey).getValue(HouseData.class);
            Log.d("fire==houseKey=",houseKey);
            alrAddr.add(" 使用者: " +userKey );
            alrAddr.add(" 建案名稱: " +houseData.getTitle() );
            alrAddr.add(" 房屋座落: " +houseData.getCity() + houseData.getLocation());
            alrAddr.add(" 房屋地址: " +houseData.getAddr());
            alrAddr.add(" 房東姓名: " +houseData.getLandlordID() );
            alrAddr.add(" 房客姓名: " +houseData.getTenantID() );
            alrAddr.add(" 驗證密碼: " +houseData.getIdentity());

            Log.d("fire==date==ds.getKey()",houseData.getAddr());
            myadapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    //================================================================
    private ValueEventListener valueEventListenerTenant = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//                alrAlarm.clear();
            alrAlarm=new ArrayList<Boolean>();
            alrHouseKey.clear();
            alrTentNote.clear();
            alrPayTent.clear();
            alrPayLandlord.clear();
            alrAddr.clear();
            alrPrePayDate.clear();
            alrNextPayDate.clear();
            Tenant tenant =dataSnapshot.child("-L-MLQwn3lVeq013C3Nv").getValue(Tenant.class);
            Log.d("fire==houseKey=",houseKey);
            alrAddr.add(" 使用者: " +userKey );
            alrAddr.add(" 戶籍地址: " +tenant.getAddr() );
            alrAddr.add(" 新增者: " +tenant.getCreateID() );
            alrAddr.add(" 租期終止日: " +tenant.getEndDate());
            alrAddr.add(" 房屋編號: " +tenant.getHouseID());
            alrAddr.add(" 房客編號: " +tenant.getId() );
            alrAddr.add(" 房客姓名: " +tenant.getName() );
            alrAddr.add(" 付款日期: " +tenant.getPayDay());
            alrAddr.add(" 付款週期: " +tenant.getPeriod());
            alrAddr.add(" 手機: " +tenant.getPhone());
            alrAddr.add(" 每期租金: " +tenant.getRent());
            alrAddr.add(" 簽約日期: " +tenant.getSignDate());
            alrAddr.add(" 租前開始日: " +tenant.getStartDate());
            alrAddr.add(" 市內電話: " +tenant.getTel());
            myadapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
//================================================================================
    public void delTennet(View target){
//        Log.d("fire===",position+"");
//        Log.d("fire===", "imgbtnDelete");
//        Log.d("fire===" ,DetailActivity.alrDelKey.get(position));
        LayoutInflater inflater = (LayoutInflater) this
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
//                DatabaseReference mDatabase;
//                mDatabase = FirebaseDatabase.getInstance().getReference(priceType);
//                mDatabase = mDatabase.child(DetailActivity.alrDelKey.get(position));
//                mDatabase.removeValue();
//                dialog.dismiss();
            }
        });
        AlertDialog.Builder abc = new AlertDialog.Builder(this);
        abc.setView(root);
        dialog = abc.show();

    }


    public void addTennet(View target){
        Intent intent=new Intent();
        intent.setClass(this,AddTenantActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("landordID",landordID);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    //===============================================================
    TextView txRentDate,txManDate,txWaterDate,txEleDate;
    EditText etRent,etWater,etElectricity,etManagement;
    Button confirm,cancel,confirmAdd,cancelDelete;
    AlertDialog dialog; //讓自定Layout可有關閉功能
        View root;
    public void addPrice(View target){
        StringBuilder sbBuffer=new StringBuilder();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        View root;
        TextView txDescription,txDeleteDescription;
        ImageButton imgRentDate,imgWaterDate,imgEleDate,imgManDate;
//        sbBuffer.append(etHouseAddr.getText().toString().trim());
//        sbBuffer="d";
        if (sbBuffer.length()!=0) {
//            Toast.makeText(this,"請先輸入房屋地址",Toast.LENGTH_SHORT).show();
        }
        else {
            LayoutInflater inflater = (LayoutInflater) this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            root = inflater.inflate(R.layout.dialog_tenant_addprice, null);//找出根源樹,

            etRent=root.findViewById(R.id.etRent);
            etWater=root.findViewById(R.id.etWater);
            etElectricity=root.findViewById(R.id.etElectricity);
            etManagement=root.findViewById(R.id.etManagement);

            confirmAdd =  root.findViewById(R.id.btnConfirm);
            cancelDelete=root.findViewById(R.id.btnCancel);
            txRentDate=root.findViewById(R.id.txRentDate);
            txRentDate.setText(formatter.format(new Date()));
            txManDate=root.findViewById(R.id.txManDate);
            txManDate.setText(formatter.format(new Date()));
            txWaterDate=root.findViewById(R.id.txWaterDate);
            txWaterDate.setText(formatter.format(new Date()));
            txEleDate=root.findViewById(R.id.txEleDate);
            txEleDate.setText(formatter.format(new Date()));
//===================================================================
            imgWaterDate=root.findViewById(R.id.imgWaterDate);
            imgWaterDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Date now = new Date();
                    DatePickerDialog dpd=new DatePickerDialog(DetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            String input = i +"-"+(i1+1)+"-"+("0"+i2).substring(("0"+i2).length()-2);

                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            Date t = null;
                            try{
                                t = formatter.parse(input);
                            }catch(ParseException e){
                            }
                            txWaterDate.setText(input);
                        }
                    },now.getYear()+1900,now.getMonth(),now.getDay()); //畫面出來預設值  2017,12,05
                    dpd.show();
                }
            });
// =============================================================================================
            imgEleDate=root.findViewById(R.id.imgEleDate);
            imgEleDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Date now = new Date();
                    DatePickerDialog dpd=new DatePickerDialog(DetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            String input = i +"-"+(i1+1)+"-"+("0"+i2).substring(("0"+i2).length()-2);

                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            Date t = null;
                            try{
                                t = formatter.parse(input);
                            }catch(ParseException e){
                            }
                            txEleDate.setText(input);
                        }
                    },now.getYear()+1900,now.getMonth(),now.getDay()); //畫面出來預設值  2017,12,05
                    dpd.show();
                }
            });
//================================================================================
            imgManDate=root.findViewById(R.id.imgManagement);
            imgManDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Date now = new Date();
                    DatePickerDialog dpd=new DatePickerDialog(DetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            String input = i +"-"+(i1+1)+"-"+("0"+i2).substring(("0"+i2).length()-2);

                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            Date t = null;
                            try{
                                t = formatter.parse(input);
                            }catch(ParseException e){
                            }
                            txManDate.setText(input);
                        }
                    },now.getYear()+1900,now.getMonth(),now.getDay()); //畫面出來預設值  2017,12,05
                    dpd.show();
                }
            });
//================================================================
            imgRentDate=root.findViewById(R.id.imgRentDate);
            imgRentDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Date now = new Date();
                    DatePickerDialog dpd=new DatePickerDialog(DetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            String input = i +"-"+(i1+1)+"-"+("0"+i2).substring(("0"+i2).length()-2);

                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            Date t = null;
                            try{
                                t = formatter.parse(input);
                            }catch(ParseException e){
                            }
                            txRentDate.setText(input);
                        }
                    },now.getYear()+1900,now.getMonth(),now.getDay()); //畫面出來預設值  2017,12,05
                    dpd.show();
                }
            });
//===========================================================

            confirmAdd.setOnClickListener(addPriceListener);
            cancelDelete.setOnClickListener(addPriceListener);

            AlertDialog.Builder abc = new AlertDialog.Builder(this);
            abc.setView(root);
            dialog = abc.show();
        }
    }

    private void fireBaseAdd(String input,String type,int price){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date t = null;
        try {
            t = formatter.parse(input);
        } catch (ParseException e) {
        }
        PriceData priceData = new PriceData("FINAL_LANDLORD_ID", "FINAL_HOUSE_ID", "FINAL_HOUSE_SUB_ID", "FINAL_TENANT_ID", input, t, price);
//        PriceData priceData = new PriceData(FINAL_LANDLORD_ID, FINAL_HOUSE_ID, FINAL_HOUSE_SUB_ID, FINAL_TENANT_ID, input, t, price);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(type);
        mDatabase.push().setValue(priceData);

    }

    View.OnClickListener addPriceListener= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view == confirmAdd) {
//                Log.d("fire=toString=", view.toString());
                if (etRent.getText().toString().trim().length() > 0) {
                    int price = Integer.parseInt(etRent.getText().toString().trim());
                    String input = txRentDate.getText().toString().trim();
                    String type="Rent";
                    fireBaseAdd(input,type,price);
                }

                if (etWater.getText().toString().trim().length() > 0) {
                    int price = Integer.parseInt(etWater.getText().toString().trim());
                    String input = txWaterDate.getText().toString().trim();
                    String type="Water";
                    fireBaseAdd(input,type,price);
                }
                if (etElectricity.getText().toString().trim().length() > 0) {
                    int price = Integer.parseInt(etElectricity.getText().toString().trim());
                    String input = txEleDate.getText().toString().trim();
                    String type="Electricity";
                    fireBaseAdd(input,type,price);
                }
                if (etManagement.getText().toString().trim().length() > 0) {
                    int price = Integer.parseInt(etManagement.getText().toString().trim());
                    String input = txManDate.getText().toString().trim();
                    String type="Management";
                    fireBaseAdd(input,type,price);
                }
            }
            dialog.dismiss();

        }

    };

}