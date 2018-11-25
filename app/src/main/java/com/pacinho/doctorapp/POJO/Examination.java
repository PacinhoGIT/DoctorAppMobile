package com.pacinho.doctorapp.POJO;

/**
 * Created by patryk on 2018-11-20.
 */

public class Examination {

    private String Id;
    private String Name;
    private String CustomName;
    private String Description;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCustomName() {
        return CustomName;
    }

    public void setCustomName(String customName) {
        CustomName = customName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
