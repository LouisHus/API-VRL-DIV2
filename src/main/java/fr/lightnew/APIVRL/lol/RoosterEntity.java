package fr.lightnew;

public class RoosterEntity {

    public final String photo;
    private final String name;
    private final String pseudo;

    public RoosterEntity(String logo, String name, String pseudo) {
        this.photo = logo;
        this.name = name;
        this.pseudo = pseudo;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

    public String getPseudo() {
        return pseudo;
    }

    @Override
    public String toString() {
        return "RoosterEntity{" +
                "logo='" + photo + '\'' +
                ", name='" + name + '\'' +
                ", pseudo='" + pseudo + '\'' +
                '}';
    }
}
