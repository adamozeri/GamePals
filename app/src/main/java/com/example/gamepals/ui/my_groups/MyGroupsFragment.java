package com.example.gamepals.ui.my_groups;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gamepals.ui.chat.ChatActivity;
import com.example.gamepals.GroupCallback;
import com.example.gamepals.Adapters.GroupAdapter;
import com.example.gamepals.Utils.Constants;
import com.example.gamepals.databinding.FragmentMyGroupsBinding;
import com.example.gamepals.model.Group;

import java.util.HashMap;

public class MyGroupsFragment extends Fragment {

    private FragmentMyGroupsBinding binding;

    private GroupAdapter groupAdapter;

    private Observer<HashMap<String, Group>> observer = new Observer<HashMap<String, Group>>() {
        @Override
        public void onChanged(HashMap<String, Group> groups) {
            groupAdapter.updateGroups(groups);
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MyGroupsViewModel myGroupsViewModel =
                new ViewModelProvider(this).get(MyGroupsViewModel.class);

        binding = FragmentMyGroupsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initViews();
        setCallbacks();
        return root;
    }

    private void initViews() {
        MyGroupsViewModel myGroupsViewModel = new MyGroupsViewModel();
        myGroupsViewModel.getGroups().observe(getViewLifecycleOwner(), observer);

        groupAdapter = new GroupAdapter(this);
        binding.myGroupsGroups.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.myGroupsGroups.setAdapter(groupAdapter);
    }

    private void setCallbacks() {
        groupAdapter.setGroupCallback(new GroupCallback() {
            @Override
            public void joinClicked(Group group, int position) {
            }

            @Override
            public void itemClicked(Group group, int position) {
                loadChatActivity(group);
            }

            @Override
            public void leaveClicked(Group item, int position) {

            }
        });
    }

    private void loadChatActivity(Group group) {
        Intent intent = new Intent(getContext(), ChatActivity.class);
        intent.putExtra(Constants.KEY_CHAT, group.getChatMessages());
        intent.putExtra(Constants.KEY_GROUP_NAME, group.getName());
        intent.putExtra(Constants.KEY_GROUP_ID,group.getId());
        startActivity(intent);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}