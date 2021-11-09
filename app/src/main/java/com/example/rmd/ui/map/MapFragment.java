package com.example.rmd.ui.map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.rmd.R;
import com.example.rmd.add;
import com.example.rmd.add_timetable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jarvislau.destureviewbinder.GestureViewBinder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MapFragment extends Fragment {

    List<String>
            FB=new ArrayList<>(),
            CB=new ArrayList<>(),
            SA=new ArrayList<>(),
            SB=new ArrayList<>(),
            SC=new ArrayList<>(),
            SD=new ArrayList<>(),
            PB=new ArrayList<>(),
            MA=new ArrayList<>(),
            MB=new ArrayList<>(),
            EB=new ArrayList<>(),
            EE=new ArrayList<>(),
            IR=new ArrayList<>(),
            IA=new ArrayList<>(),
            HS=new ArrayList<>(),
            IBSS=new ArrayList<>(),
            ES=new ArrayList<>(),
            DB=new ArrayList<>(),
            AS=new ArrayList<>();
    public static Fragment mFragment;

    public View  onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_map, container, false);

        ViewGroup group = root.findViewById(R.id.map_group);
        View target = root.findViewById(R.id.map_target);
        GestureViewBinder.bind(root.getContext(), group, target);

        TextView fb = root.findViewById(R.id.map_fb);
        TextView cb = root.findViewById(R.id.map_cb);
        TextView sa = root.findViewById(R.id.map_sa);
        TextView sb = root.findViewById(R.id.map_sb);
        TextView sc = root.findViewById(R.id.map_sc);
        TextView sd = root.findViewById(R.id.map_sd);
        TextView pb = root.findViewById(R.id.map_pb);
        TextView ma = root.findViewById(R.id.map_ma);
        TextView mb = root.findViewById(R.id.map_mb);
        TextView eb = root.findViewById(R.id.map_eb);
        TextView ee = root.findViewById(R.id.map_ee);
        TextView ir = root.findViewById(R.id.map_ir);
        TextView ia = root.findViewById(R.id.map_ia);
        TextView hs = root.findViewById(R.id.map_hs);
        TextView ibss = root.findViewById(R.id.map_ibss);
        TextView es = root.findViewById(R.id.map_es);
        TextView db = root.findViewById(R.id.map_db);
        TextView as = root.findViewById(R.id.map_as);

        //get current week
        Date date=new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Chongqing"));
        String currWeek = dateFormat.format(date);

        SharedPreferences tt = this.getActivity().getSharedPreferences("timetable", Activity.MODE_PRIVATE);
        String cd_key = "tt_00001";
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
        while (i>0) {
            if (tt.contains(cd_key)) {
                String data = tt.getString(cd_key, "");

                if (data.split("_")[0].equals("FB") && data.split("_")[1].equals(currWeek)) { FB.add(data); setRedDot(fb); }
                if (data.split("_")[0].equals("CB") && data.split("_")[1].equals(currWeek)) { CB.add(data); setRedDot(cb); }
                if (data.split("_")[0].equals("SA") && data.split("_")[1].equals(currWeek)) { SA.add(data); setRedDot(sa); }
                if (data.split("_")[0].equals("SB") && data.split("_")[1].equals(currWeek)) { SB.add(data); setRedDot(sb); }
                if (data.split("_")[0].equals("SC") && data.split("_")[1].equals(currWeek)) { SC.add(data); setRedDot(sc); }
                if (data.split("_")[0].equals("SD") && data.split("_")[1].equals(currWeek)) { SD.add(data); setRedDot(sd); }
                if (data.split("_")[0].equals("PB") && data.split("_")[1].equals(currWeek)) { PB.add(data); setRedDot(pb); }
                if (data.split("_")[0].equals("MA") && data.split("_")[1].equals(currWeek)) { MA.add(data); setRedDot(ma); }
                if (data.split("_")[0].equals("MB") && data.split("_")[1].equals(currWeek)) { MB.add(data); setRedDot(mb); }
                if (data.split("_")[0].equals("EB") && data.split("_")[1].equals(currWeek)) { EB.add(data); setRedDot(eb); }
                if (data.split("_")[0].equals("EE") && data.split("_")[1].equals(currWeek)) { EE.add(data); setRedDot(ee); }
                if (data.split("_")[0].equals("IR") && data.split("_")[1].equals(currWeek)) { IR.add(data); setRedDot(ir); }
                if (data.split("_")[0].equals("IA") && data.split("_")[1].equals(currWeek)) { IA.add(data); setRedDot(ia); }
                if (data.split("_")[0].equals("HS") && data.split("_")[1].equals(currWeek)) { HS.add(data); setRedDot(hs); }
                if (data.split("_")[0].equals("IBSS") && data.split("_")[1].equals(currWeek)) { IBSS.add(data); setRedDot(ibss); }
                if (data.split("_")[0].equals("ES") && data.split("_")[1].equals(currWeek)) { ES.add(data); setRedDot(es); }
                if (data.split("_")[0].equals("DB") && data.split("_")[1].equals(currWeek)) { DB.add(data); setRedDot(db); }
                if (data.split("_")[0].equals("AS") && data.split("_")[1].equals(currWeek)) { AS.add(data); setRedDot(as); }

                String[] num = cd_key.split("tt_");
                cd_key = add.getCode(cd_key, num[1]);
                i--;
            }
        }

        FloatingActionButton button1 = root.findViewById(R.id.floatingActionButton2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), add_timetable.class);
                startActivity(intent);
            }
        });

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailTT_dialog(FB);
            }
        });
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailTT_dialog(CB);
            }
        });
        sa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailTT_dialog(SA);
            }
        });
        sb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailTT_dialog(SB);
            }
        });
        sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailTT_dialog(SC);
            }
        });
        sd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailTT_dialog(SD);
            }
        });
        pb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailTT_dialog(PB);
            }
        });
        ma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailTT_dialog(MA);
            }
        });
        mb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailTT_dialog(MB);
            }
        });
        eb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailTT_dialog(EB);
            }
        });
        ee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailTT_dialog(EE);
            }
        });
        ir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailTT_dialog(IR);
            }
        });
        ia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailTT_dialog(IA);
            }
        });
        hs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailTT_dialog(HS);
            }
        });
        ibss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailTT_dialog(IBSS);
            }
        });
        es.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailTT_dialog(ES);
            }
        });
        db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailTT_dialog(DB);
            }
        });
        as.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailTT_dialog(AS);
            }
        });

        mFragment=this;
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        ImageView imageView = getView().findViewById(R.id.map_imageView);
        switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
            case Configuration.UI_MODE_NIGHT_YES:
                imageView.setImageResource(R.drawable.darkmap);
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                imageView.setImageResource(R.drawable.lightmap);
                break;
        }
    }

    public void detailTT_dialog (List<String> data) {
        AlertDialog.Builder cf_dialog = new AlertDialog.Builder(this.getActivity());
        cf_dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        if (data.size()!=0) {
            String[] message = new String[data.size()];
            for (int i=0; i<data.size(); i++) {
                message[i] = data.get(i).split("_")[4] +" in "+ data.get(i).split("_")[5] +" at "+ data.get(i).split("_")[2] +":"+ data.get(i).split("_")[3];
            }
            cf_dialog.setItems(message, null);
            cf_dialog.setTitle(data.get(0).split("_")[0] + " Building");
        } else {
            cf_dialog.setMessage("No courses in this building today !");
        }
        cf_dialog.show();
    }

    public void setRedDot(TextView building) {
        Drawable drawable = this.getActivity().getDrawable(R.drawable.red_bubble);
        drawable.setBounds(0,0,16,16);
        building.setCompoundDrawables(null,null,drawable,null);
    }

}
