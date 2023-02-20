package com.example.gamepals.ui.create_a_group;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gamepals.Adapters.GamesAdapter;
import com.example.gamepals.GameCallback;
import com.example.gamepals.R;
import com.example.gamepals.Utils.SignalSingleton;
import com.example.gamepals.databinding.FragmentCreateAGroupBinding;
import com.example.gamepals.model.Game;
import com.example.gamepals.model.Group;
import com.example.gamepals.model.User;


public class CreateAGroupFragment extends Fragment {

    private FragmentCreateAGroupBinding binding;

    private Dialog dialog;

    private GamesAdapter gamesAdapter;

    private CreateAGroupViewModel createAGroupViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CreateAGroupViewModel createAGroupViewModel =
                new ViewModelProvider(this).get(CreateAGroupViewModel.class);
        binding = FragmentCreateAGroupBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initViews();
        initListeners();
        gamesAdapter.setFullGamesList(createAGroupViewModel.getGames());
        gamesAdapter.updateGames(createAGroupViewModel.getGames());
        return root;
    }

    private void initViews(){
        createAGroupViewModel = new CreateAGroupViewModel();
        gamesAdapter = new GamesAdapter(getContext(),this);
    }

    private void initListeners(){
        binding.createBTNCreate.setOnClickListener(view -> createGroupClicked());
        binding.createLBLGameName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize dialog
                dialog=new Dialog(getContext());

                // set custom dialog
                dialog.setContentView(R.layout.searchable_spinner);

                // set custom height and width
                dialog.getWindow().setLayout(650,800);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                EditText editText=dialog.findViewById(R.id.edit_text);
                RecyclerView listView=dialog.findViewById(R.id.select_LST_games);

                listView.setAdapter(gamesAdapter);
//                editText.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable editable) {
//                        gamesAdapter.filter(editable.toString());
//                    }
//                });
                setCallbacks();
            }
        });
    }

    private void setCallbacks(){
        gamesAdapter.setGameCallback(new GameCallback() {
            @Override
            public void favoriteClicked(Game game, int position) {
            }

            @Override
            public void itemClicked(Game game, int position) {
                binding.createLBLGameName.setText(gamesAdapter.getItem(position).getName());
                dialog.dismiss();
            }
        });
    }


    private void createGroupClicked() {
        boolean flag = true;
        if (binding.createETName.getText().toString().isEmpty()) {
            SignalSingleton.getInstance().toast("You must enter a name");
            flag = false;
        } else if (binding.createTFCapacity.getText().toString().isEmpty()) {
            SignalSingleton.getInstance().toast("You must enter capacity");
            flag = false;
        }
        if (flag) {
            createAGroup();
        }
    }

    private void createAGroup() {
        String name = binding.createETName.getText().toString();
        String gameName = binding.createLBLGameName.getText().toString();
        String description = binding.createTFDescription.getText().toString();
        String region = binding.createSPRegion.getSelectedItem().toString();
        String skill = binding.createSPSkill.getSelectedItem().toString();
        String platform = binding.createSPPlatform.getSelectedItem().toString();
        int capacity = Integer.parseInt(String.valueOf(binding.createTFCapacity.getText()));
        Group newGroup = new Group(name, capacity,gameName , description, region, skill, platform);
        User.getInstance().getGroups().put(newGroup.getId(), newGroup); // adding the group to the user's list
        CreateAGroupViewModel createAGroupViewModel = new CreateAGroupViewModel();
        createAGroupViewModel.updateGroupDB(newGroup);// updating db
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}