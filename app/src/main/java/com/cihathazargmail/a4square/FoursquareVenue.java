package com.cihathazargmail.a4square;

public class FoursquareVenue {
    private String name;
    private String city;

    private String category;

    public FoursquareVenue() {
        this.name = "";
        this.city = "";
        this.setCategory("");
    }

    public String getCity() {
        if (city.length() > 0) {
            return city;
        }
        return city;
    }

    public void setCity(String city) {
        if (city != null) {
            this.city = city.replaceAll("\\(", "").replaceAll("\\)", "");                           //Listeye verilerin doldurulup gosterilmesi icin gerekli veri tanimlamalari yapiliyor bu sinifta
            ;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
