package com.mohred.studentnetwork.model;

import com.mohred.studentnetwork.interfaces.ConnectionObject;

import java.util.List;

/**
 * Created by Redan on 1/14/2017.
 */

public class CourseMaterialList implements ConnectionObject
{
    private List<CourseMaterial> materialList;

    public List<CourseMaterial> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(List<CourseMaterial> materialList) {
        this.materialList = materialList;
    }

}
