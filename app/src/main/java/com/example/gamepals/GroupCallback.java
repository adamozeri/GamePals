package com.example.gamepals;

import com.example.gamepals.model.Group;

public interface GroupCallback {
    void joinClicked(Group group, int position);
    void itemClicked(Group group, int position);
    void leaveClicked(Group group, int position);
}
