package com.example.scouser.minirepositoryviewer;

/**
 * Created by Scouser on 9/3/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class GitHubAdapter extends RecyclerView.Adapter<GitHubAdapter.MyViewHolder> {

    private List<GitHub> gitList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, full_name, owner;

        public MyViewHolder(View view) {
            super(view);
            owner = (TextView) view.findViewById(R.id.owner);
            full_name = (TextView) view.findViewById(R.id.full_name);
            name = (TextView) view.findViewById(R.id.name);
        }
    }


    public GitHubAdapter(List<GitHub> gitList) {
        this.gitList = gitList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.github_list_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        GitHub gitHub = gitList.get(position);
        holder.full_name.setText(gitHub.getFull_name());
        holder.name.setText(gitHub.getName());
        holder.owner.setText(gitHub.getOwner());
    }

    @Override
    public int getItemCount() {
        return gitList.size();
    }
}
