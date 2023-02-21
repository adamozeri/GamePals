package com.example.gamepals.Activities.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gamepals.Callbacks.GroupCallback;
import com.example.gamepals.Adapters.GroupAdapter;
import com.example.gamepals.Activities.MainActivity;
import com.example.gamepals.Utils.SignalSingleton;
import com.example.gamepals.databinding.FragmentHomeBinding;
import com.example.gamepals.Models.Group;
import com.example.gamepals.Models.User;

import java.util.HashMap;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private GroupAdapter groupAdapter;
    private HomeViewModel homeViewModel;
    private Observer<HashMap<String, Group>> observer = new Observer<HashMap<String, Group>>() {

        @Override
        public void onChanged(HashMap<String, Group> groups) {
            groupAdapter.updateGroups(groups);
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initViews();
        setCallbacks();
        initListeners();
        return root;
    }

    private void initListeners() {
        binding.homeETSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                groupAdapter.filterName(editable.toString(),binding.homeCBFavGames.isChecked());
            }
        });

        binding.homeLogout.setOnClickListener(view -> ((MainActivity)getContext()).signOut());

        binding.homeCBFavGames.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    groupAdapter.filterFav();
                }
                else {
                    groupAdapter.setFilteredGroup(new HashMap<>(groupAdapter.getAllGroupList()));
                    if(binding.homeETSearch.getText().toString() != null)
                        groupAdapter.filterName(binding.homeETSearch.getText().toString(),binding.homeCBFavGames.isChecked());
                }
            }
        });
    }




    private void initViews() {
        binding.homeTVHello.setText("Hello, " + User.getInstance().getName());
        homeViewModel = new HomeViewModel();
        homeViewModel.getMGroups().observe(getViewLifecycleOwner(), observer);

        groupAdapter = new GroupAdapter(this);
        binding.homeLSTGroups.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.homeLSTGroups.setAdapter(groupAdapter);
    }

    private void setCallbacks() {
        groupAdapter.setGroupCallback(new GroupCallback() {
            @Override
            public void joinClicked(Group group, int position) {
                if(group.getCapacity() <= group.getNumOfUsers()){
                    SignalSingleton.getInstance().toast("The group is full");
                }
                else{
                    User.getInstance().getGroups().put(group.getId(), group);
                    group.addUser(User.getInstance().getUid());
                    groupAdapter.removeGroup(group.getId());
                    groupAdapter.notifyItemRemoved(position);
                    homeViewModel.updateJoinedGroupDB(group);
                }
            }

            @Override
            public void itemClicked(Group group, int position) {
            }

            @Override
            public void leaveClicked(Group item, int position) {

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.homeETSearch.setText(null);
    }
}