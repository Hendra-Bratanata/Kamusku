package com.ichirotech.bratanata.kamusku;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.widget.ProgressBar;
import android.widget.TextView;

import com.ichirotech.bratanata.kamusku.Activity.MainActivity;
import com.ichirotech.bratanata.kamusku.AppPreference.appPreference;
import com.ichirotech.bratanata.kamusku.Helper.KamusEngHelper;
import com.ichirotech.bratanata.kamusku.Helper.KamusIndoHelper;
import com.ichirotech.bratanata.kamusku.POJO_Parcelable.KamusModel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadingScreen extends AppCompatActivity {
@BindView(R.id.progresbar_screen)
    ProgressBar progressBar;
@BindView(R.id.tv_LoadingScreen)
    TextView tvLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        ButterKnife.bind(this);
        tvLoading.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
       new LoadData().execute();


    }
    private class LoadData extends AsyncTask<Void,Integer,Void>{
        KamusIndoHelper indoHelper;
        KamusEngHelper engHelper;
        com.ichirotech.bratanata.kamusku.AppPreference.appPreference appPreference;
        double progres;
        double progres2;
        double maxProgres =100;


        @Override
        protected void onPreExecute() {

            engHelper = new KamusEngHelper(LoadingScreen.this);
            indoHelper = new KamusIndoHelper(LoadingScreen.this);
            appPreference =new appPreference(LoadingScreen.this);
            progressBar.setVisibility(View.VISIBLE);
            tvLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Boolean firstRun = appPreference.getFirstRun();


            if (firstRun) {
                ArrayList<KamusModel> kamusModelsIndo = preLoadRaw("indo");

//  Insert Table_indo
                indoHelper.open();
                progres = 30;
                publishProgress((int) progres);
                double maxprogressInsert = 60.0;
                double progressDiff = (maxprogressInsert - progres) / kamusModelsIndo.size();
                indoHelper.beginTransaction();
                try {
                    for (KamusModel model : kamusModelsIndo) {
                        indoHelper.insertTransaction(model);
                        progres += progressDiff;
                        publishProgress((int) progres);
                    }
                    indoHelper.setTransactionSuccess();
                } catch (Exception e) {
                    e.printStackTrace();

                }
                indoHelper.endTransaction();
                indoHelper.close();

//  insert table_eng
                ArrayList<KamusModel> kamusModelsEng = preLoadRaw("eng");
                engHelper.open();
                Log.d("insert","open eng");
                progres2 = 60;
                publishProgress((int) progres2);
                double maxprogresinsert2 = 80.0;
                double progresdif2 = (maxprogresinsert2 - progres2)/kamusModelsEng.size();
                engHelper.beginTransaction();
                Log.d("insert","begin eng");
                try {
                    for (KamusModel model : kamusModelsEng){
                        engHelper.insertTransaction(model);
                        progres2 += progresdif2;
                        publishProgress((int) progres2);
                    }
                    engHelper.setTransactionSuccess();
                    Log.d("insert","eng success");
                }catch (Exception e){
                    e.printStackTrace();
                }
                engHelper.endTransaction();
                Log.d("insert","eng end");
                engHelper.close();

                appPreference.setFirstRun(false);
                publishProgress((int) maxProgres);

            }else {
                try {
                    synchronized (this) {
                        this.wait(2000);

                        publishProgress(50);

                        this.wait(2000);
                        publishProgress((int) maxProgres);
                    }
                } catch (Exception e) {
                }
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent intent = new Intent(LoadingScreen.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private ArrayList<KamusModel> preLoadRaw(String type) {
        ArrayList<KamusModel> kamusModels = new ArrayList<>();

            String line = null;
            BufferedReader reader;
            int count = 0;
        if (type.equalsIgnoreCase("indo")) {
            Log.d("insert","Load Indo");
            try {

                Resources resources = getResources();
                InputStream dataRaw = resources.openRawResource(R.raw.indonesia_english);
                reader = new BufferedReader(new InputStreamReader(dataRaw));
                do {
                    line = reader.readLine();
                    String[] spliter = line.split("\\t");

                    KamusModel kamusModel = new KamusModel(spliter[0], spliter[1]);

                    kamusModels.add(kamusModel);
                    count++;
                } while (line != null);

            } catch (Exception e) {
                e.printStackTrace();
            }


        }else if(type.equalsIgnoreCase("eng")){
            Log.d("insert","Load Eng");
            try {
                Resources resources = getResources();
                InputStream dataRawEng = resources.openRawResource(R.raw.english_indonesia);
                reader = new BufferedReader(new InputStreamReader(dataRawEng));
                do {
                    line = reader.readLine();
                    String[] spliter = line.split("\\t");

                    KamusModel kamusModel = new KamusModel(spliter[0], spliter[1]);

                    kamusModels.add(kamusModel);
                    count++;
                } while (line != null);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return kamusModels;
    }

}
