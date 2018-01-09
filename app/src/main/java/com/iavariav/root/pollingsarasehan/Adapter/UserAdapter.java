package com.iavariav.root.pollingsarasehan.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.iavariav.root.pollingsarasehan.Model.UserModel;
import com.iavariav.root.pollingsarasehan.R;

import java.util.ArrayList;

/**
 * Created by root on 1/8/18.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> implements Filterable{

    private Context context;
    private ArrayList<UserModel> UserModels;
    private ArrayList<UserModel> searchResult;
    private String string;

    public UserAdapter(ArrayList<UserModel> getResult, Context context) {
        this.context = context;
        this.UserModels = getResult;
    }

    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_mahasiswa, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(UserAdapter.MyViewHolder holder, int position) {
        UserModel userModel  = UserModels.get(position);
        holder.nama.setText(userModel.getNama());
        holder.npm.setText(userModel.getNpm());
    }

    @Override
    public int getItemCount() {
        return UserModels.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                final FilterResults filterResults = new FilterResults();
                final ArrayList<UserModel> result = new ArrayList<>();

                if (searchResult == null)
                    searchResult = UserModels;
                if (charSequence != null){
                    if (UserModels !=null & searchResult.size()>0){
                        for (final UserModel s : searchResult){
                            if (s.getNama().toLowerCase().contains(charSequence.toString()))result.add(s);
//                            if (s.getNama().equalsIgnoreCase(s.getNama()))result.add(s);
                        }
                    }
                    filterResults.values = result;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                UserModels = (ArrayList<UserModel>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView nama, npm;
        public MyViewHolder(View itemView) {
            super(itemView);

            nama = (TextView) itemView.findViewById(R.id.tvNamaMahasiswa);
            npm = (TextView) itemView.findViewById(R.id.tvNPMMahasiswa);



        }
    }
}
