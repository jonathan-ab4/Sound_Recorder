package com.example.soundrecorder.Interfaces;

import com.example.soundrecorder.Models.RecordingItem;

public interface OnDatabaseChangedListener {

    void onNewDatabaseEntryAdded(RecordingItem recordingItem);

}
