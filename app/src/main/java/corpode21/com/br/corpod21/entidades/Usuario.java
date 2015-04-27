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


    public Usuario(String nome, String email,String password,float peso_inicial,float altura,float peso_meta,float biceps,
                   float cintura,float quadril, float perna, Bitmap imagem_perfil){
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
    public void setBiceps(float peso_inicial) {
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
}
