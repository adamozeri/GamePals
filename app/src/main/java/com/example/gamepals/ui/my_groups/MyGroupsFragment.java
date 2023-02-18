package com.example.gamepals.ui.my_groups;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gamepals.GroupRecyclerAdapter;
import com.example.gamepals.databinding.FragmentMyGroupsBinding;
import com.example.gamepals.model.Group;

import java.util.HashMap;

public class MyGroupsFragment extends Fragment {

    private FragmentMyGroupsBinding binding;

    private GroupRecyclerAdapter groupAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MyGroupsViewModel myGroupsViewModel =
                new ViewModelProvider(this).get(MyGroupsViewModel.class);

        binding = FragmentMyGroupsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initViews();

        return root;
    }

    private void initViews() {
        MyGroupsViewModel myGroupsViewModel = new MyGroupsViewModel();
        myGroupsViewModel.getGroups().observe(getViewLifecycleOwner(),observer);

        groupAdapter = new GroupRecyclerAdapter(getContext());
        binding.myGroupsGroups.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.myGroupsGroups.setAdapter(groupAdapter);
    }


    Observer<HashMap<String, Group>> observer = new Observer<HashMap<String,Group>>(){

        @Override
        public void onChanged(HashMap<String,Group> groups) {
            groupAdapter.updateGroups(groups);
        }
    };
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}