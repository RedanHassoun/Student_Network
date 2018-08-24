package com.mohred.studentnetwork.model;

import com.mohred.studentnetwork.interfaces.ConnectionObject;

import java.util.List;

/**
 * Created by Redan on 12/19/2016.
 */

public class OrganizationsMessage implements ConnectionObject
{
    private List<Organization> orgs;

    public List<Organization> getOrgs() {
        return orgs;
    }

    public void setOrgs(List<Organization> orgs) {
        this.orgs = orgs;
    }
}
