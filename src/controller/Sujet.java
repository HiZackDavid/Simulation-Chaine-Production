package controller;

public interface Sujet {
    void ajouterObservateur(Observateur observateur);
    void retirerObservateur(Observateur observateur);
    void notifierObservateurs();
}
