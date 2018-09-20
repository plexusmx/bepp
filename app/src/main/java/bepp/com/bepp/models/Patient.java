package bepp.com.bepp.models;

/**
 * Created by admin on 20/09/17.
 */

public class Patient {


    private String usuario;
    private String nombre;
    private String apellidop;
    private String apellidom;
    private String sexo;
    private String tel_movil;
    private String fotografia;
    private Integer id_usuario;
    private String password;
    private String correo;
    private  String fecha_nacimiento;
    private String edad;
    private String estatura;
    private String peso;
    private String tipo_sangre;

    private  String nuevo_password;
    private  String actual_password;


    public Patient(String usuario, String nombre, String apellidop, String apellidom,String sexo, String tel_movil, String password, String fotografia, String correo, Integer id_usuario) {

        this.usuario = usuario;
        this.nombre = nombre;
        this.apellidop = apellidop;
        this.apellidom = apellidom;
        this.sexo = sexo;
        this.tel_movil = tel_movil;
        this.password = password;
        this.fotografia = fotografia;
        this.correo = correo;
        this.id_usuario = id_usuario;

    }

    public Patient(String fecha_nacimiento, String edad, String estatura, String peso, String tipo_sangre) {
        this.fecha_nacimiento = fecha_nacimiento;
        this.edad = edad;
        this.estatura = estatura;
        this.peso = peso;
        this.tipo_sangre = tipo_sangre;
    }


    public Patient(String nuevo_password, String actual_password) {
        this.nuevo_password = nuevo_password;
        this.actual_password = actual_password;
    }

    public String getActual_password() {
        return actual_password;
    }

    public void setActual_password(String actual_password) {
        this.actual_password = actual_password;
    }

    public String getNuevo_password() {
        return nuevo_password;
    }

    public void setNuevo_password(String nuevo_password) {
        this.nuevo_password = nuevo_password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }



    public String getFotografia() {
        return fotografia;
    }

    public void setFotografia(String fotografia) {
        this.fotografia = fotografia;
    }




    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidop() {
        return apellidop;
    }

    public void setApellidop(String apellidop) {
        this.apellidop = apellidop;
    }

    public String getApellidom() {
        return apellidom;
    }

    public void setApellidom(String apellidom) {
        this.apellidom = apellidom;
    }

    public String getTel_movil() {
        return tel_movil;
    }

    public void setTel_movil(String tel_movil) {
        this.tel_movil = tel_movil;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getEstatura() {
        return estatura;
    }

    public void setEstatura(String estatura) {
        this.estatura = estatura;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getTipo_sangre() {
        return tipo_sangre;
    }

    public void setTipo_sangre(String tipo_sangre) {
        this.tipo_sangre = tipo_sangre;
    }

}
