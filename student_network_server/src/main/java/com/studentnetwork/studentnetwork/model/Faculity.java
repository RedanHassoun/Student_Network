package com.studentnetwork.studentnetwork.model;

public class Faculity
{
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrgID() {
		return orgID;
	}
	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}
	private String name;
	private String orgID;

	public String toString(){
		return "{ id: "+this.id+", name: "+this.name+"}";
	}
}
