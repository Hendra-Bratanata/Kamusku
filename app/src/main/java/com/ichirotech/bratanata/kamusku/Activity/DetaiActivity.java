package com.ichirotech.bratanata.kamusku.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ichirotech.bratanata.kamusku.POJO_Parcelable.KamusModel;
import com.ichirotech.bratanata.kamusku.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetaiActivity extends AppCompatActivity {
    @BindView(R.id.tv_abjadDetail)
    TextView tvAbjadDetail;
    @BindView(R.id.tv_descdetail)
    TextView tvDescDetail;
    KamusModel model;

    public static String EXTRA_NOTE = "extra_note";
    public static String EXTRA_POSITION = "extra_position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detai);
        ButterKnife.bind(this);
    model = getIntent().getParcelableExtra(EXTRA_NOTE);

    tvAbjadDetail.setText(model.getAbjad());
    tvDescDetail.setText(model.getDesc());


    }
}
