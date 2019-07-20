package com.example.c2n.master_list.di;

import com.example.c2n.master_list.presenter.MasterListActivity;

import dagger.Component;

@Component(modules = MasterListModule.class)
public interface MasterListComponent {

    void inject(MasterListActivity masterListActivity);
}
