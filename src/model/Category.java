package model;

import java.util.Objects;

public class Category {
    private int id;
    private String name;
    private String type;

    //Constructor
    public Category(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    //Getter

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    //Setter

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    //ToString()

    @Override
    public String toString() {
        return "Category:" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'';
    }

    //Equals and Hashcode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category category)) return false;
        return getId() == category.getId() && Objects.equals(getName(), category.getName()) && Objects.equals(getType(), category.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getType());
    }
}
