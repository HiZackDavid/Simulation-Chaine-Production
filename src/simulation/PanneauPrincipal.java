package simulation;

import controller.Configuration;
import controller.XMLReader;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

public class PanneauPrincipal extends JPanel {

	@Serial
	private static final long serialVersionUID = 1L;
	public static final int USINE_WIDTH = 32;
	public static final int USINE_HEIGTH = 30;
	public static final Point VITESSE = new Point(1,1);
	private final Configuration configuration;

	public PanneauPrincipal() {
		configuration = new Configuration();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		if (!XMLReader.FILE_PATH.isEmpty()) {
			drawChemins(g, configuration);
			drawUsines(g, configuration);
			drawComponents(g, configuration);
		}
	}

	private void drawUsines(Graphics g, Configuration configuration) {
		for (Usine usine : configuration.getUsines()) {
			String iconPath = usine.getIcone().getPath();
			int x = usine.getPosition().x;
			int y = usine.getPosition().y;

			// Dessiner Icones
			showIcon(g, iconPath, usine.getPosition(), USINE_WIDTH, USINE_HEIGTH);
		}
	}

	private void drawChemins(Graphics g, Configuration configuration) {
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

				if (destination != null) {
					int xDestination = destination.getPosition().x;
					int yDestination = destination.getPosition().y;

					g.drawLine(x, y, xDestination, yDestination);
				}
			}
		}
	}

	private void drawComponents(Graphics g, Configuration configuration) {
		// Pour toutes les usines
		for (Usine usine : configuration.getUsines()) {
			// Si c'est une usine de production
			if (usine instanceof UsineProduction usineProduction) {
				// On ajoute les composants dans le chemin
				for (Chemin chemin : usine.getChemins()) {
					if (chemin.getComposants().size() < 1) {
						chemin.addComposant(usineProduction.produce());
					}
				}

				for (Chemin chemin : usine.getChemins()) {
					// Afficher composants
					for (Composant composant : chemin.getComposants()) {
						String iconPath = "src/ressources/" + TypeComposant.getType(composant.getTypeComposant()) + ".png";
						// Dessiner Icones
						showIcon(g, iconPath, composant.getPosition(), USINE_WIDTH, USINE_HEIGTH);
					}

					chemin.moveComposants(configuration, VITESSE);
				}
			}
		}
	}

	private void showIcon(Graphics g, String iconPath, Point position, int width, int height) {
		ImageIcon imageIcon = new ImageIcon(iconPath);
		Image image = imageIcon.getImage();
		Image resizedImage = image.getScaledInstance(USINE_WIDTH, USINE_HEIGTH, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(resizedImage);

		icon.paintIcon(this, g, position.x - USINE_WIDTH/2, position.y - USINE_HEIGTH/2);
	}
}