package dev.hotel.entite;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name="chambre")
public class Chambre extends BaseEntite {
	//private static final long serialVersionUID = 1L;
	
    private String numero;
	
    private Float surfaceEnM2;
	@ManyToOne
    private Hotel hotel;
	
	
	

    public Chambre() {
    }

    public Chambre(String numero, Float surfaceEnM2, Hotel hotel) {

        this.numero = numero;
        this.surfaceEnM2 = surfaceEnM2;
        this.hotel = hotel;
    }


    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Float getSurfaceEnM2() {
        return surfaceEnM2;
    }

    public void setSurfaceEnM2(Float surfaceEnM2) {
        this.surfaceEnM2 = surfaceEnM2;
    }
}
