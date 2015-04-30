package corpode21.com.br.corpod21.entidades;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;


public class Usuario implements Parcelable {
    private String nome;
    private String email;
    private String password;
    private float peso_inicial;
    private float altura;
    private float peso_meta;
    private float biceps;
    private float cintura;
    private float quadril;
    private float perna;
    private Bitmap imagem_perfil;
    private String h_cafe;
    private String notification_cafe;
    private String h_lanche_manha;
    private String notification_lanche_manha;
    private String h_almoco;
    private String notification_almoco;
    private String h_lanche_tarde;
    private String notification_lanche_tarde;
    private String h_jantar;
    private String notification_jantar;
    private String h_ceia;
    private String notification_ceia;


    public Usuario(String nome, String email,String password,float peso_inicial,float altura,float peso_meta,float biceps,
                   float cintura,float quadril, float perna, Bitmap imagem_perfil,
                   String h_cafe, String notification_cafe,
                   String h_lanche_manha, String notification_lanche_manha,
                   String h_almoco, String notification_almoco,
                   String h_lanche_tarde, String notification_lanche_tarde,
                   String h_jantar, String notification_jantar,
                   String h_ceia, String notification_ceia){
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.peso_inicial = peso_inicial;
        this.altura = altura;
        this.peso_meta = peso_meta;
        this.biceps = biceps;
        this.cintura = cintura;
        this.quadril = quadril;
        this.perna = perna;
        this.imagem_perfil = imagem_perfil;
        this.h_cafe = h_cafe;
        this.notification_cafe = notification_cafe;
        this.h_lanche_manha = h_lanche_manha;
        this.notification_lanche_manha = notification_lanche_manha;
        this.h_almoco = h_almoco;
        this.notification_almoco = notification_almoco;
        this.h_lanche_tarde = h_lanche_tarde;
        this.notification_lanche_tarde = notification_lanche_tarde;
        this.h_jantar = h_jantar;
        this.notification_jantar = notification_jantar;
        this.h_ceia = h_ceia;
        this.notification_ceia = notification_ceia;
    }

    public Usuario(Parcel parcel){
        this.nome = parcel.readString();
        this.email = parcel.readString();
        this.password = parcel.readString();
        this.peso_inicial = parcel.readFloat();
        this.altura = parcel.readFloat();
        this.peso_meta = parcel.readFloat();
        this.biceps = parcel.readFloat();
        this.cintura = parcel.readFloat();
        this.quadril = parcel.readFloat();
        this.perna = parcel.readFloat();
        this.imagem_perfil = (Bitmap) parcel.readValue(Bitmap.class.getClassLoader());
        this.h_cafe = parcel.readString();
        this.notification_cafe = parcel.readString();
        this.h_lanche_manha = parcel.readString();
        this.notification_lanche_manha = parcel.readString();
        this.h_almoco =parcel.readString();
        this.notification_almoco = parcel.readString();
        this.h_lanche_tarde = parcel.readString();
        this.notification_lanche_tarde = parcel.readString();
        this.h_jantar = parcel.readString();
        this.notification_jantar = parcel.readString();
        this.h_ceia = parcel.readString();
        this.notification_ceia = parcel.readString();
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String name) {
        this.email = name;
    }

    public float getPesoIni() {
        return peso_inicial;
    }
    public void setPesoIni(float peso_inicial) {
        this.peso_inicial = peso_inicial;
    }

    public float getAltura() {
        return altura;
    }
    public void setAltura(float altura) {
        this.altura = altura;
    }

    public float getPesoMeta() {
        return peso_meta;
    }
    public void setPesoMeta(float peso_meta) {
        this.peso_meta = peso_meta;
    }

    public float getBiceps() {
        return biceps;
    }
    public void setBiceps(float biceps) {
        this.biceps = biceps;
    }

    public float getCintura() {
        return cintura;
    }
    public void setCintura(float cintura) {
        this.cintura = cintura;
    }

    public float getQuadril() {
        return quadril;
    }
    public void setQuadril(float quadril) {
        this.quadril = quadril;
    }

    public float getPerna() {
        return perna;
    }
    public void setPerna(float perna) {
        this.perna = perna;
    }

    public Bitmap getImage() {
        return imagem_perfil;
    }
    public void setImage(Bitmap image) {
        this.imagem_perfil = image;
    }


    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nome);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeFloat(peso_inicial);
        dest.writeFloat(altura);
        dest.writeFloat(peso_meta);
        dest.writeFloat(biceps);
        dest.writeFloat(cintura);
        dest.writeFloat(quadril);
        dest.writeFloat(perna);
        dest.writeValue(imagem_perfil);
        dest.writeString(h_cafe);
        dest.writeString(notification_cafe);
        dest.writeString(h_lanche_manha);
        dest.writeString(notification_lanche_manha);
        dest.writeString(h_almoco);
        dest.writeString(notification_almoco);
        dest.writeString(h_lanche_tarde);
        dest.writeString(notification_lanche_tarde);
        dest.writeString(h_jantar);
        dest.writeString(notification_jantar);
        dest.writeString(h_cafe);
        dest.writeString(notification_cafe);


    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>(){

        @Override
        public Usuario createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            return new Usuario(source);
        }

        @Override
        public Usuario[] newArray(int size) {
            // TODO Auto-generated method stub
            return new Usuario[size];
        }
    };

    public String getH_cafe() {
        return h_cafe;
    }

    public void setH_cafe(String h_cafe) {
        this.h_cafe = h_cafe;
    }

    public String getNotification_cafe() {
        return notification_cafe;
    }

    public void setNotification_cafe(String notification_cafe) {
        this.notification_cafe = notification_cafe;
    }

    public String getH_lanche_manha() {
        return h_lanche_manha;
    }

    public void setH_lanche_manha(String h_lanche_manha) {
        this.h_lanche_manha = h_lanche_manha;
    }

    public String getNotification_lanche_manha() {
        return notification_lanche_manha;
    }

    public void setNotification_lanche_manha(String notification_lanche_manha) {
        this.notification_lanche_manha = notification_lanche_manha;
    }

    public String getH_almoco() {
        return h_almoco;
    }

    public void setH_almoco(String h_almoco) {
        this.h_almoco = h_almoco;
    }

    public String getNotification_almoco() {
        return notification_almoco;
    }

    public void setNotification_almoco(String notification_almoco) {
        this.notification_almoco = notification_almoco;
    }

    public String getH_lanche_tarde() {
        return h_lanche_tarde;
    }

    public void setH_lanche_tarde(String h_lanche_tarde) {
        this.h_lanche_tarde = h_lanche_tarde;
    }

    public String getNotification_lanche_tarde() {
        return notification_lanche_tarde;
    }

    public void setNotification_lanche_tarde(String notification_lanche_tarde) {
        this.notification_lanche_tarde = notification_lanche_tarde;
    }

    public String getH_jantar() {
        return h_jantar;
    }

    public void setH_jantar(String h_jantar) {
        this.h_jantar = h_jantar;
    }

    public String getNotification_jantar() {
        return notification_jantar;
    }

    public void setNotification_jantar(String notification_jantar) {
        this.notification_jantar = notification_jantar;
    }

    public String getH_ceia() {
        return h_ceia;
    }

    public void setH_ceia(String h_ceia) {
        this.h_ceia = h_ceia;
    }

    public String getNotification_ceia() {
        return notification_ceia;
    }

    public void setNotification_ceia(String notification_ceia) {
        this.notification_ceia = notification_ceia;
    }
}
