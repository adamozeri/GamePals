package com.example.gamepals.Activities.ui.my_groups;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gamepals.Models.User;
import com.example.gamepals.Activities.ui.chat.ChatActivity;
import com.example.gamepals.Callbacks.GroupCallback;
import com.example.gamepals.Adapters.GroupAdapter;
import com.example.gamepals.Utils.Constants;
import com.example.gamepals.databinding.FragmentMyGroupsBinding;
import com.example.gamepals.Models.Group;

import java.util.HashMap;

public class MyGroupsFragment extends Fragment {

    private FragmentMyGroupsBinding binding;

    private GroupAdapter groupAdapter;

    private MyGroupsViewModel myGroupsViewModel;

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
        initListeners();
        return root;
    }



    private void initViews() {
        myGroupsViewModel = new MyGroupsViewModel();
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
            public void leaveClicked(Group group, int position) {
                group.removeUser(User.getInstance().getUid());
                User.getInstance().getGroups().remove(group.getId());
                groupAdapter.removeGroup(group.getId());
                groupAdapter.notifyItemRemoved(position);
                groupAdapter.notifyItemChanged(position);
                if(group.getUsersID().isEmpty()){

                    myGroupsViewModel.removeGroupFromDB(group.getId());
                }
                else{
                    myGroupsViewModel.updateGroupDB(group);
                }
                myGroupsViewModel.updateUserDB();
            }
        });
    }

    private void initListeners() {
        binding.myGroupsETSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                groupAdapter.filter(false,editable.toString());
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

    @Override
    public void onPause() {
        super.onPause();
        binding.myGroupsETSearch.setText(null);
    }
}