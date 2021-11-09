package com.example.rmd.ui.list;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.rmd.Msg;
import com.example.rmd.MsgAdapter;
import com.example.rmd.MsgUtil;
import com.example.rmd.R;
import com.example.rmd.add;
import com.example.rmd.add_timetable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListFragment extends Fragment {

    String cd_key;
    Map<String, String> delete = new HashMap<String, String>();
    private ListView listView;
    private List<Msg> msgList;
    private MsgAdapter adapter;
    private SharedPreferences tt;
    public static Activity mactivity;
    public static Context mContext;
    public static Fragment mFragment;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.list_fragment, container, false);

        tt = this.getActivity().getSharedPreferences("timetable", Activity.MODE_PRIVATE);

        //get the size of deadlines data
        cd_key = "tt_00001";
        String cd_test = cd_key;
        int i = 0;
        if (!tt.contains("tt_00001") && tt.contains("tt_00002")) {
            cd_test = "tt_00002";
            i = 1;
        }
        while (tt.contains(cd_test)) {
            String[] num = cd_test.split("tt_");
            cd_test = add.getCode(cd_test, num[1]);
            if (tt.contains(add.getCode(cd_test, cd_test.split("tt_")[1]))) {
                i = i+2;
                cd_test = add.getCode(cd_test, cd_test.split("tt_")[1]);
            } else {i++;}
        }

        //load CardViews adapter and ListView
        listView = (ListView)root.findViewById(R.id.tt_listview);
        msgList = MsgUtil.TTgetMsgList(tt, i);
        adapter = new MsgAdapter(msgList, getContext(),"timetable");
        listView.setAdapter(adapter);

        while (i > 0) {
            if (tt.contains(cd_key)) {
                String data = tt.getString(cd_key, "");
                delete.put(data.split("_")[0], cd_key);
            }
            String[] num = cd_key.split("tt_");
            cd_key = add.getCode(cd_key, num[1]);
            i--;
        }

        //set listener for floating buttons
        FloatingActionButton button1 = root.findViewById(R.id.floatingActionButton3);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), add_timetable.class);
                startActivityForResult(intent, 1);
            }
        });

        FloatingActionButton button2 = root.findViewById(R.id.floatingActionButton4);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAll_dialog("Delete all activities?");
            }
        });

        //assign current context, activity and fragment
        mContext=root.getContext();
        mactivity=this.getActivity();
        mFragment=this;
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //clear all deadlines data
    public void clearAll_data() {
        SharedPreferences tt = this.getActivity().getSharedPreferences("timetable", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = tt.edit();
        tt.edit().clear().commit();
        if (!tt.contains("key")) {
            editor.putString("key", "cd_00001");
            editor.commit();
        }
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }

    //clear the selected deadline data
    public void clearThis_data(String delete_key) {
        SharedPreferences tt = ListFragment.mContext.getSharedPreferences("timetable", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = tt.edit();
        editor.remove(delete_key);
        editor.commit();
        ListFragment.mFragment.getFragmentManager().beginTransaction().detach(ListFragment.mFragment).attach(ListFragment.mFragment).commit();
    }

    //pop up the confirmation dialog
    public void deleteAll_dialog (String message) {
        AlertDialog.Builder cf_dialog = new AlertDialog.Builder(this.getActivity());
        cf_dialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                clearAll_data();
            }
        });
        cf_dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        cf_dialog.setMessage(message);
        cf_dialog.setTitle("Warning");
        cf_dialog.show();
    }

}