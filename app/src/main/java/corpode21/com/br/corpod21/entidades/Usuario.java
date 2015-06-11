package corpode21.com.br.corpod21.entidades;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;


public class Usuario implements Parcelable {
    private String nome;
    private String email;
    private String imagem_perfil;


    public Usuario(String nome, String email, String imagem_perfil){
        this.nome = nome;
        this.email = email;
        this.imagem_perfil = imagem_perfil;
    }

    public Usuario(Parcel parcel){
        this.nome = parcel.readString();
        this.email = parcel.readString();
        this.imagem_perfil = parcel.readString();
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

    public String getImage() {
        return imagem_perfil;
    }
    public void setImage(String image) {
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
        dest.writeString(imagem_perfil);
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
