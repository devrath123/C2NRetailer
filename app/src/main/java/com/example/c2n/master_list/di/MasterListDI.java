package com.example.c2n.master_list.di;

public class MasterListDI {
    private static MasterListComponent masterListComponent;

    public static MasterListComponent getMasterListComponent() {
        if (masterListComponent == null) {
            masterListComponent = DaggerMasterListComponent.builder().build();
        }
        return masterListComponent;
    }
}
