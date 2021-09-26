package simulation;

import controller.Configuration;
import model.Chemin;
import model.Usine;

import java.awt.*;

import javax.swing.*;

public class PanneauPrincipal extends JPanel {

	private static final long serialVersionUID = 1L;
	
	// Variables temporaires de la demonstration:
	private Point position = new Point(0,0);
	private Point vitesse = new Point(1,1);
	private int taille = 32;

	public static final int USINE_WIDTH = 32;
	public static final int USINE_HEIGTH = 30;

	@Override
	public void paint(Graphics g) {
		Configuration configuration = new Configuration("src/ressources/configuration.xml");
		super.paint(g);
		// On ajoute à la position le delta x et y de la vitesse
		/*
		position.translate(vitesse.x, vitesse.y);
		g.fillRect(position.x, position.y, taille, taille);
		*/

		// Dessiner chemins
		for (Usine usine : configuration.getUsines()) {
			int x = usine.getPosition().x;
			int y = usine.getPosition().y;

			for (Chemin chemin : usine.getChemins()) {
				Usine destination = null;

				for (Usine usineDestination : configuration.getUsines()) {
					if (usineDestination.getId() == chemin.getDestination()) {
						destination = usineDestination;
					}
				}

				int xDestination = destination.getPosition().x;
				int yDestination = destination.getPosition().y;

				g.drawLine(x, y, xDestination, yDestination);
			}
		}

		// Dessiner usines
		for (Usine usine : configuration.getUsines()) {
			String iconPath = usine.getIcone().getPath();
			int x = usine.getPosition().x;
			int y = usine.getPosition().y;

			// Rectangles temp
			//g.fillRect(x - USINE_WIDTH/2, y - USINE_HEIGTH/2, USINE_WIDTH, USINE_HEIGTH);

			// Dessiner Icones
			ImageIcon imageIcon = new ImageIcon(iconPath);
			Image image = imageIcon.getImage();
			Image resizedImage = image.getScaledInstance(USINE_WIDTH, USINE_HEIGTH, Image.SCALE_SMOOTH);
			ImageIcon icon = new ImageIcon(resizedImage);

			icon.paintIcon(this, g, x - USINE_WIDTH/2, y - USINE_HEIGTH/2);
		}
	}
}