package com.ichirotech.bratanata.kamusku.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ichirotech.bratanata.kamusku.POJO_Parcelable.KamusModel;
import com.ichirotech.bratanata.kamusku.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KamusIndoAdapter extends RecyclerView.Adapter<KamusIndoAdapter.IndoHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<KamusModel> list = new ArrayList<>();

    public KamusIndoAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public IndoHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kamus_row,parent,false);

        return new IndoHolder(view);
    }

    public void addItem(ArrayList<KamusModel> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull IndoHolder holder, int position) {
        holder.tvAbjad.setText(list.get(position).getAbjad());
        holder.tvDesc.setText(list.get(position).getDesc());


    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class IndoHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_abjad)
        TextView tvAbjad;
    @BindView(R.id.tv_desc)
        TextView tvDesc;
        public IndoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
