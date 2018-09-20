package bepp.com.bepp.models;

public class medicalHistory {

    private String fecha_nacimiento;
    private String edad;
    private String estatura;
    private String peso;
    private String tipo_sangre;
    private String ultimo_diagnostico;
    private String fecha_ultima_consulta;


    public medicalHistory(String fecha_nacimiento, String edad, String estatura, String peso, String tipo_sangre, String ultimo_diagnostico, String fecha_ultima_consulta) {
        this.fecha_nacimiento = fecha_nacimiento;
        this.edad = edad;
        this.estatura = estatura;
        this.peso = peso;
        this.tipo_sangre = tipo_sangre;
        this.ultimo_diagnostico = ultimo_diagnostico;
        this.fecha_ultima_consulta = fecha_ultima_consulta;
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

    public String getUltimo_diagnostico() {
        return ultimo_diagnostico;
    }

    public void setUltimo_diagnostico(String ultimo_diagnostico) {
        this.ultimo_diagnostico = ultimo_diagnostico;
    }

    public String getFecha_ultima_consulta() {
        return fecha_ultima_consulta;
    }

    public void setFecha_ultima_consulta(String fecha_ultima_consulta) {
        this.fecha_ultima_consulta = fecha_ultima_consulta;
    }
}
