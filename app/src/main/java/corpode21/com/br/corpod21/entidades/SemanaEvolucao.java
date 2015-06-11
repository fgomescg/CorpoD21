package corpode21.com.br.corpod21.entidades;

import java.util.Date;

/**
 * Created by Fabio on 27/05/2015.
 */
public class SemanaEvolucao {

    private int id;
    private String ini_semana;
    private String fim_semana;
    private String peso;
    private String pesoMeta;
    private String biceps;
    private String cintura;
    private String quadril;
    private String perna;

    public SemanaEvolucao() {
        super();
    }

    public SemanaEvolucao(int id, String ini_semana, String fim_semana, String peso, String pesoMeta, String biceps,
                          String cintura, String quadril, String perna) {
        super();
        this.id = id;
        this.peso = peso;
        this.ini_semana = ini_semana;
        this.fim_semana = fim_semana;
        this.pesoMeta = pesoMeta;
        this.biceps = biceps;
        this.cintura = cintura;
        this.quadril = quadril;
        this.perna = perna;
    }

    public int getId() {
        return id;
    }
    public String getIni_semana() {
        return ini_semana;
    }
    public String getFim_semana() {
        return fim_semana;
    }
    public String getPeso() { return peso; }
    public String getPesoMeta() { return pesoMeta; }
    public String getBiceps() {
        return biceps;
    }
    public String getCintura() {
        return cintura;
    }
    public String getQuadril(){
        return quadril;
    }
    public String getPerna(){
        return perna;
    }


    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SemanaEvolucao other = (SemanaEvolucao) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Semana [id=" + id + ", peso=" + peso + ", ini_semana="
                + ini_semana + ", fim_semana=" + fim_semana + ", pesometa=" + pesoMeta +", biceps="+ biceps+
                ", cintura="+ cintura+", quadril=" + quadril +", perna="+perna+"]";
    }
}