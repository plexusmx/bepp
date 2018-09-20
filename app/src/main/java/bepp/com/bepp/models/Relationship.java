package bepp.com.bepp.models;

public class Relationship {
    private String id_parentesco;
    private String nombre;

    public Relationship(String id_parentesco, String nombre) {
        this.id_parentesco = id_parentesco;
        this.nombre = nombre;
    }

    public String getId_parentesco() {
        return id_parentesco;
    }

    public void setId_parentesco(String id_parentesco) {
        this.id_parentesco = id_parentesco;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}



