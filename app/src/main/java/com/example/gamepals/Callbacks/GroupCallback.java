package com.example.gamepals.Callbacks;

import com.example.gamepals.Models.Group;

public interface GroupCallback {
    void joinClicked(Group group, int position);
    void itemClicked(Group group, int position);
    void leaveClicked(Group group, int position);
}
