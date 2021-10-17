package Patrons.Observateur;

public interface Observable {
    void ajouterObservateur(Observateur observateur);
    void retirerObservateur(Observateur observateur);
    void notifierObservateurs();
}
