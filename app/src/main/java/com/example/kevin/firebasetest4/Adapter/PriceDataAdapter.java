package com.example.kevin.firebasetest4.Adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevin.firebasetest4.DetailActivity;
import com.example.kevin.firebasetest4.LandlordExpandable;
import com.example.kevin.firebasetest4.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//import static com.example.kevin.firebasetest4.R.id.txDeleteDescription;

/**
 * Created by kevin on 2017/12/3.
 */

public class PriceDataAdapter extends BaseAdapter {
    private LayoutInflater myInflater;
    Context mContext;
    String priceType;

    public PriceDataAdapter(Context context,String priceType) {
        myInflater = LayoutInflater.from(context);
        this.mContext=context;
        this.priceType=priceType;
    }

    @Override
    public int getCount() {
        return DetailActivity.alrAddr.size();
    }

    @Override
    public Object getItem(int i) {
        return DetailActivity.alrAddr.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    AlertDialog dialog; //讓自定Layout可有關閉功能
    View root;


    private DatabaseReference mDatabase;
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
//        mDatabase = FirebaseDatabase.getInstance().getReference(DetailActivity.FINAL_USER_ID);
        mDatabase = FirebaseDatabase.getInstance().getReference("House");

        final int position=i;

//        Log.d("fire==i=",i+"");
        convertView = myInflater.inflate(R.layout.price_detail_adapter_layout, null);
        TextView txTentNote=(TextView)convertView.findViewById(R.id.txTentNote);
        TextView txHouseAddr = (TextView) convertView.findViewById(R.id.txHouseAddr);
//        TextView txtAlarm=(TextView) convertView.findViewById(R.id.txtAlarm);
        CheckBox chkPay=(CheckBox) convertView.findViewById(R.id.checkBox);
        ImageButton imgbtnDelete=convertView.findViewById(R.id.imgbtnDelete) ;
//        Button addTenant=convertView.findViewById(R.id.btnAddTenant) ;
        txHouseAddr.setText(DetailActivity.alrAddr.get(i));

        chkPay.setChecked(DetailActivity.alrChcekPrice.get(i));
        chkPay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                DetailActivity.alrChcekPrice.set(position,isChecked);
//                DatabaseReference mDatabase;
//                mDatabase = FirebaseDatabase.getInstance().getReference(priceType);
//                mDatabase = mDatabase.child(DetailActivity.alrDelKey.get(position));
//                mDatabase.removeValue();
//                dialog.dismiss();
//
                DatabaseReference mDatabase;
                mDatabase = FirebaseDatabase.getInstance().getReference(priceType);
                mDatabase = mDatabase.child(DetailActivity.alrDelKey.get(position));
                Map<String, Object> nameMap = new HashMap();
                nameMap.put("isCheck", isChecked);
                mDatabase.updateChildren(nameMap);

            }
        });
        imgbtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Log.d("fire===",position+"");
                Log.d("fire===", "imgbtnDelete");
                Log.d("fire===" ,DetailActivity.alrDelKey.get(position));
                root = myInflater.inflate(R.layout.dialog_delete_layout, null);//找出根源樹,
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
                        mDatabase = FirebaseDatabase.getInstance().getReference(priceType);
                        mDatabase = mDatabase.child(DetailActivity.alrDelKey.get(position));
                        mDatabase.removeValue();
                        dialog.dismiss();
                    }
                });
                AlertDialog.Builder abc = new AlertDialog.Builder(mContext);
                abc.setView(root);
                dialog = abc.show();
            }
        });

        return convertView;
    }
}
