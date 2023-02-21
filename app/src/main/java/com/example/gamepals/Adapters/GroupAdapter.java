package com.example.gamepals.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gamepals.Callbacks.GroupCallback;
import com.example.gamepals.R;
import com.example.gamepals.Models.Group;
import com.example.gamepals.Models.User;
import com.example.gamepals.Activities.ui.my_groups.MyGroupsFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.HashMap;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

    private Fragment fragment;
    private HashMap<String, Group> filteredGroup;
    private HashMap<String, Group> allGroupList;



    private GroupCallback groupCallback;


    public GroupAdapter(Fragment fragment) {
        this.filteredGroup = new HashMap<>();
        this.allGroupList = new HashMap<>();
        this.fragment = fragment;
    }

    public GroupAdapter setGroupCallback(GroupCallback groupCallback) {
        this.groupCallback = groupCallback;
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
        holder.group_TV_capacity.setText(group.getNumOfUsers() + "/" + group.getCapacity());
        holder.group_TV_region.setText(group.getRegion());
        holder.group_TV_skill.setText(group.getSkill());
        holder.group_TV_platform.setText(group.getPlatform());
        holder.group_TV_game.setText(group.getGame().getName());
        Glide.
                with(fragment.getContext()).
                load(group.getGame().getImage()).
                into(holder.group_IMG_game);
    }

    private Group getItem(int position) {
        ArrayList<Group> groupsValues = new ArrayList<>(filteredGroup.values());
        return groupsValues.get(position);
    }

    @Override
    public int getItemCount() {
        return filteredGroup == null ? 0 : filteredGroup.size();
    }

    public void updateGroups(HashMap<String, Group> groups) {
        this.filteredGroup = groups;
        this.allGroupList = groups;
        notifyDataSetChanged();
    }



    public void removeGroup(String groupID) {
        filteredGroup.remove(groupID);
        if(allGroupList.containsKey(groupID))
            allGroupList.remove(groupID);
        notifyDataSetChanged();
    }

    public void setFilteredGroup(HashMap<String, Group> filteredGroup) {
        this.filteredGroup = filteredGroup;
    }

    public HashMap<String, Group> getAllGroupList() {
        return allGroupList;
    }

    private void filterList(HashMap<String, Group> filterList) {
        this.filteredGroup = filterList;
        notifyDataSetChanged();
    }


    public void filterName(String text, boolean isChecked) {
        HashMap<String, Group> filteredList = new HashMap<>();
        if (!isChecked) {
            for (Group group : allGroupList.values()) {
                if (group.getName().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.put(group.getId(), group);
                }
            }
        } else {
            for (Group group : filteredGroup.values()) {
                if (group.getName().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.put(group.getId(), group);
                }
            }
        }
        filterList(filteredList);
    }

    public void filterFav() {
        HashMap<String, Group> filteredList = new HashMap<>();
        for (Group group : allGroupList.values()) {
            if (User.getInstance().checkFavGame(group.getGame().getName())) {
                filteredList.put(group.getId(), group);
            }
        }
        filterList(filteredList);
    }


    public class GroupViewHolder extends RecyclerView.ViewHolder {
        private MaterialTextView group_TV_groupName;
        private MaterialTextView group_TV_game;
        private MaterialTextView group_TV_groupDescription;
        private MaterialTextView group_TV_capacity;
        private MaterialTextView group_TV_region;
        private MaterialTextView group_TV_skill;
        private MaterialTextView group_TV_platform;
        private MaterialButton group_BTN_join;
        private MaterialButton group_BTN_leave;
        private ShapeableImageView group_IMG_game;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            initViews();
            itemView.setOnClickListener(view -> groupCallback.itemClicked(getItem(getAdapterPosition()), getAdapterPosition()));
            group_BTN_join.setOnClickListener(view -> groupCallback.joinClicked(getItem(getAdapterPosition()), getAdapterPosition()));
            group_BTN_leave.setOnClickListener(view -> groupCallback.leaveClicked(getItem(getAdapterPosition()), getAdapterPosition()));
            if (fragment instanceof MyGroupsFragment) {
                group_BTN_join.setVisibility(View.INVISIBLE);
                group_BTN_leave.setVisibility(View.VISIBLE);
            } else {
                group_BTN_join.setVisibility(View.VISIBLE);
                group_BTN_leave.setVisibility(View.INVISIBLE);
            }
        }

        private void initViews() {
            group_TV_groupName = itemView.findViewById(R.id.group_TV_groupName);
            group_TV_game = itemView.findViewById(R.id.group_TV_game);
            group_TV_groupDescription = itemView.findViewById(R.id.group_TV_groupDescription);
            group_TV_capacity = itemView.findViewById(R.id.group_TV_capacity);
            group_TV_region = itemView.findViewById(R.id.group_TV_region);
            group_TV_skill = itemView.findViewById(R.id.group_TV_skill);
            group_TV_platform = itemView.findViewById(R.id.group_TV_platform);
            group_BTN_join = itemView.findViewById(R.id.group_BTN_join);
            group_BTN_leave = itemView.findViewById(R.id.group_BTN_leave);
            group_IMG_game = itemView.findViewById(R.id.group_IMG_game);
        }
    }
}
