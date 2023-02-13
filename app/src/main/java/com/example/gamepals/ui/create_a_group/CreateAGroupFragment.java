package com.example.gamepals.ui.create_a_group;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gamepals.databinding.FragmentCreateAGroupBinding;
import com.example.gamepals.model.Group;
import com.example.gamepals.model.User;

public class CreateAGroupFragment extends Fragment {

    private FragmentCreateAGroupBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CreateAGroupViewModel createAGroupViewModel =
                new ViewModelProvider(this).get(CreateAGroupViewModel.class);

        binding = FragmentCreateAGroupBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.createBTNCreate.setOnClickListener(view -> createGroup());
        return root;
    }



    private void createGroup(){
        String name = binding.createETName.getText().toString();
        String description = binding.createTFDescription.getEditText().toString();
        String region = binding.createSPRegion.getSelectedItem().toString();
        String skill = binding.createSPSkill.getSelectedItem().toString();
        String platform = binding.createSPPlatform.getSelectedItem().toString();
        int capacity = Integer.parseInt(binding.createTFCapacity.getText().toString());
        Group newGroup = new Group(name,capacity,description,region,skill,platform);
        User.getInstance().getGroups().put(newGroup.getId(),newGroup); // adding the group to the user's list
        CreateAGroupViewModel createAGroupViewModel = new CreateAGroupViewModel(newGroup); // updating db
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}