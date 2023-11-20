package bowling;

import java.util.HashMap;
import java.util.Map;

public class PartieMultiJoueurs implements IPartieMultiJoueurs{
	private Map<String, PartieMonoJoueur> partieJoueur;
	private String[] nomsJoueurs;
	private int nbJoueur;
	private int tourJoueurNum;


	@Override
	public String demarreNouvellePartie(String[] nomsDesJoueurs) throws IllegalArgumentException {
		if (nomsDesJoueurs.length == 0) throw new IllegalArgumentException("Il faut au minimum 1 joueur");
		partieJoueur = new HashMap<>();
		nbJoueur = nomsDesJoueurs.length;
		tourJoueurNum = 0;
		this.nomsJoueurs = nomsDesJoueurs;
		for (String nom: nomsDesJoueurs) {
			partieJoueur.put(nom, new PartieMonoJoueur());
		}
		return tourJoueur();
	}

	@Override
	public String enregistreLancer(int nombreDeQuillesAbattues) throws IllegalStateException {
		if (estTerminer()) throw  new IllegalStateException("La partie est terminée");
		PartieMonoJoueur partieJoueur = this.partieJoueur.get(nomsJoueurs[tourJoueurNum]);
		partieJoueur.enregistreLancer(nombreDeQuillesAbattues);
		if (partieJoueur.numeroProchainLancer() == 1 || partieJoueur.estTerminee()) tourJoueurNum = (tourJoueurNum +1)%nbJoueur;
		if (estTerminer()) return "Partie terminée";
		return tourJoueur();
	}

	@Override
	public int scorePour(String nomDuJoueur) throws IllegalArgumentException {
		PartieMonoJoueur partieJoueur = this.partieJoueur.get(nomDuJoueur);
		if (partieJoueur == null) throw new IllegalArgumentException("Joueur inexistant");
		return partieJoueur.score();
	}

	private String tourJoueur(){
		return "Prochain tir: joueur " + nomsJoueurs[tourJoueurNum] + ", tour n° " + partieJoueur.get(nomsJoueurs[tourJoueurNum]).numeroTourCourant() + ", boule n° " + partieJoueur.get(nomsJoueurs[tourJoueurNum]).numeroProchainLancer();
	}

	private boolean estTerminer(){
		return partieJoueur.get(nomsJoueurs[nbJoueur-1]).estTerminee();
	}
}
