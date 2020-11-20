package com.example.soundrecorder.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soundrecorder.Adapters.FileViewerAdapter;
import com.example.soundrecorder.Database.DBHelper;
import com.example.soundrecorder.Models.RecordingItem;
import com.example.soundrecorder.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FileViewerFragment extends Fragment {


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    DBHelper dbHelper;

    ArrayList<RecordingItem>arrayListAudios;

    private FileViewerAdapter fileViewerAdapter;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_file_viewer,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper= new DBHelper(getContext());
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        llm.setReverseLayout(true);
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);

        arrayListAudios = dbHelper.getAllAudios();

        if(arrayListAudios==null)
        {
            Toast.makeText(getContext(),"No audio files",Toast.LENGTH_LONG).show();

        }
        else
        {
             fileViewerAdapter = new FileViewerAdapter(getActivity(),arrayListAudios,llm);
             recyclerView.setAdapter(fileViewerAdapter);

        }
    }
}
