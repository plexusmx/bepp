package bepp.com.bepp.models;

public class Familiar {

    private String nombre;
    private String paterno;
    private String materno;
    private String sexo;
    private String fecha_nacimiento;
    private String tel_movil;
    private Integer id_familiar;
    private String correo;
    private String parentesco;


    public Familiar(String nombre, String paterno, String materno, String sexo, String fecha_nacimiento, String tel_movil, String correo, String parentesco) {
        this.nombre = nombre;
        this.paterno = paterno;
        this.materno = materno;
        this.sexo = sexo;
        this.fecha_nacimiento = fecha_nacimiento;
        this.tel_movil = tel_movil;
        this.correo = correo;
        this.parentesco = parentesco;
    }


    public Familiar(Integer id_familiar) {
        this.id_familiar = id_familiar;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getTel_movil() {
        return tel_movil;
    }

    public void setTel_movil(String tel_movil) {
        this.tel_movil = tel_movil;
    }

    public Integer getId_familiar() {
        return id_familiar;
    }

    public void setId_familiar(Integer id_familiar) {
        this.id_familiar = id_familiar;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }
}
