package com.example.gamepals;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gamepals.model.Group;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.HashMap;

public class GroupRecyclerAdapter extends RecyclerView.Adapter<GroupRecyclerAdapter.GroupViewHolder> {

    private Context context;
    private HashMap<String, Group> groups;
    private GroupCallback groupCallback;


    public GroupRecyclerAdapter(Context context) {
        this.context = context;
        this.groups = new HashMap<>();
    }

    public GroupRecyclerAdapter setGroupCallback(GroupCallback joinCallback) {
        this.groupCallback = joinCallback;
        return this;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_item, parent, false);
        GroupViewHolder groupViewHolder = new GroupViewHolder(view);
        return groupViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        Group group = getItem(position);
        holder.group_TV_groupName.setText(group.getName());
        holder.group_TV_groupDescription.setText(group.getDescription());
        holder.group_TV_capacity.setText(group.getNumOfUsers()+"/" + group.getCapacity());
        holder.group_TV_region.setText(group.getRegion());
        holder.group_TV_skill.setText(group.getSkill());
        holder.group_TV_platform.setText(group.getPlatform());
    }

    private Group getItem(int position) {
        ArrayList<Group> groupsValues = new ArrayList<>(groups.values());
        return groupsValues.get(position);
    }

    @Override
    public int getItemCount() {
        return groups == null ? 0 : groups.size();
    }

    public void updateGroups(HashMap<String, Group> groups) {
        this.groups = groups;
        notifyDataSetChanged();
    }


    public class GroupViewHolder extends RecyclerView.ViewHolder {
        private MaterialTextView group_TV_groupName;
        private MaterialTextView group_TV_groupDescription;
        private MaterialTextView group_TV_capacity;
        private MaterialTextView group_TV_region;
        private MaterialTextView group_TV_skill;
        private MaterialTextView group_TV_platform;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            group_TV_groupName = itemView.findViewById(R.id.group_TV_groupName);
            group_TV_groupDescription = itemView.findViewById(R.id.group_TV_groupDescription);
            group_TV_capacity = itemView.findViewById(R.id.group_TV_capacity);
            group_TV_region = itemView.findViewById(R.id.group_TV_region);
            group_TV_skill = itemView.findViewById(R.id.group_TV_skill);
            group_TV_platform = itemView.findViewById(R.id.group_TV_platform);
        }
    }
}
