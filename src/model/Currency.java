package model;

public class Currency {
    private int id;
    private String name;
    private String code;

    // Constructeurs, getters et setters
    public Currency() {}

    public Currency(int id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    //ToString
    @Override
    public String toString() {
        return "Currency" +
                "id='" + id + '\'' +
                ", currencyCode='" + id + '\'' +
                ", currencyName='" + name + '\'' +
                ", currencySymbol='" + code ;
    }
}

