package simulation;

import controller.Configuration;
import model.UsineProduction;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;

public class FenetrePrincipale extends JFrame implements PropertyChangeListener {

	private static final long serialVersionUID = 1L;
	private static final String TITRE_FENETRE = "Laboratoire 1 : LOG121 - Simulation";
	private static final Dimension DIMENSION = new Dimension(700, 700);
	private Configuration configuration;

	public FenetrePrincipale() {
		configuration = new Configuration();
		PanneauPrincipal panneauPrincipal = new PanneauPrincipal(configuration);
		MenuFenetre menuFenetre = new MenuFenetre();
		add(panneauPrincipal);
		add(menuFenetre, BorderLayout.NORTH);
		// Faire en sorte que le X de la fenêtre ferme la fenêtre
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle(TITRE_FENETRE);
		setSize(DIMENSION);
		// Rendre la fenêtre visible
		setVisible(true);
		// Mettre la fenêtre au centre de l'écran
		setLocationRelativeTo(null);
		// Empêcher la redimension de la fenêtre
		setResizable(false);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("TEST")) {
			repaint();
			for (int index = 0; index < configuration.getUsines().size(); index++) {
				if (configuration.getUsines().get(index) instanceof UsineProduction usineProduction) {
					if (usineProduction.canProduce()) {
						usineProduction.incrementerProductionProgress();
					}
				}
			}
			System.out.println(evt.getNewValue());
		}
	}
}
