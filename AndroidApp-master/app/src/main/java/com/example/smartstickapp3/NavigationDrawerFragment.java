package com.example.smartstickapp3;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NavigationDrawerFragment extends Fragment {

    private ActionBarDrawerToggle mDraweToggle;
    private DrawerLayout mDrawerLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_navigation_drawer,container,false);
        setUpRecyclerView(v);
        return v;
    }

    private void setUpRecyclerView(View v) {
        RecyclerView recyclerView = v.findViewById(R.id.drawerList);
        MyAdaptor adaptor = new MyAdaptor(getActivity(),NavigationDrawerItem.getData());

        recyclerView.setAdapter(adaptor);
        //layoutumuzu tanımladık.
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    public void setUpNavigationDrawer(DrawerLayout drawerLayout,Toolbar toolbar){
        mDrawerLayout = drawerLayout;
        mDraweToggle = new ActionBarDrawerToggle(getActivity(),mDrawerLayout, toolbar,R.string.drawer_open,R.string.drawer_open);

        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                mDraweToggle.onDrawerSlide(drawerView,slideOffset);
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                //DRAWER LAYOUT AÇIK OLDUĞUNDA
                //Toast.makeText(getActivity(), "Açıldı", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

                //Toast.makeText(getActivity(), "Kapandı.", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDraweToggle.syncState();
            }
        });
    }
}
