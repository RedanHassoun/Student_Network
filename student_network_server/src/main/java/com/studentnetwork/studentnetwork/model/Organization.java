package com.studentnetwork.studentnetwork.model;


public class Organization 
{
	private String id;
    private String name;
    private String type;
    private double longitude;
    private double latitude;
    public static final String TYPE_COLLEGE = "college";
    public static final String TYPE_UNIVERSITY = "university";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toString(){
        return "{ id : "+this.id+", name : "+this.name+", type: "+
                this.type+",longitude:"+this.longitude+", latitude:"+
                this.latitude+"}";
    }
}
