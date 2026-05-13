package br.com.sasr.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Agendamento {
    private Long id;
    private Sala sala;
    private Colaborador colaborador;
    private String titulo;
    private LocalDate data;
    private LocalTime horaInicio;
    private LocalTime horaFim;

    public Agendamento() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {

        this.id = id;
    }
    public Sala getSala() {

        return sala;
    }
    public void setSala(Sala sala) {

        this.sala = sala;
    }
    public Colaborador getColaborador() {

        return colaborador;
    }
    public void setColaborador(Colaborador colaborador) {

        this.colaborador = colaborador;
    }
    public String getTitulo() {

        return titulo;
    }
    public void setTitulo(String titulo) {

        this.titulo = titulo;
    }
    public LocalDate getData() {

        return data;
    }
    public void setData(LocalDate data) {

        this.data = data;
    }
    public LocalTime getHoraInicio() {

        return horaInicio;
    }
    public void setHoraInicio(LocalTime horaInicio) {

        this.horaInicio = horaInicio;
    }
    public LocalTime getHoraFim() {

        return horaFim;
    }
    public void setHoraFim(LocalTime horaFim) {

        this.horaFim = horaFim;
    }
}