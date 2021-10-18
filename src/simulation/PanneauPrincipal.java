package simulation;

import controller.Configuration;
import controller.XMLReader;
import model.*;
import model.Component;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

public class PanneauPrincipal extends JPanel {
	@Serial
	private static final long serialVersionUID = 1L;
	public static final Point VITESSE = new Point(1,1);
	private final Configuration configuration;

	public PanneauPrincipal(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		if (!XMLReader.FILE_PATH.isEmpty()) {
			drawChemins(g);
			drawUsines(g);
			drawComponents(g);
		}
	}

	private void drawUsines(Graphics g) {
		for (Usine usine : this.configuration.getUsines()) {
			String iconPath = usine.getIcone().getPath();

			// Dessiner Icones
			showIcon(g, iconPath, usine.getPosition());
		}
	}

	private void drawChemins(Graphics g) {
		for (Usine usine : this.configuration.getUsines()) {
			int x = usine.getPosition().x;
			int y = usine.getPosition().y;

			for (Chemin chemin : usine.getChemins()) {
				Usine destination = null;

				for (Usine usineDestination : this.configuration.getUsines()) {
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

	private void drawComponents(Graphics g) {
		// Pour toutes les usines
		for (Usine usine : this.configuration.getUsines()) {
			// Si c'est une usine de production
			if (usine instanceof UsineProduction usineProduction) {
				// Produire les composants
				if (usineProduction.canProduce()) {
					if (usineProduction.getProductionProgress() > usineProduction.getIntervalleProduction()) {
						usineProduction.addComponent(usineProduction.produce());
						usineProduction.resetProductionProgress();
					}
				}

				// Afficher composants
				for (Component component : usineProduction.getComponents()) {
					String iconPath = "src/ressources/" + TypeComposant.getType(component.getType()) + ".png";
					// Dessiner Icones
					showIcon(g, iconPath, component.getPosition());
				}

				// Déplacer les composants
				usineProduction.moveComponents(this.configuration, VITESSE);
			}
		}
	}

	private void showIcon(Graphics g, String iconPath, Point position) {
		ImageIcon imageIcon = new ImageIcon(iconPath);
		Image image = imageIcon.getImage();
		Image resizedImage = image.getScaledInstance(Usine.WIDTH, Usine.HEIGTH, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(resizedImage);

		icon.paintIcon(this, g, position.x - Usine.WIDTH/2, position.y - Usine.HEIGTH/2);
	}
}