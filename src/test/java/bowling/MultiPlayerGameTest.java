package bowling;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MultiPlayerGameTest {

	private PartieMultiJoueurs partie;
	private String[] Joueurs;

	@BeforeEach
	public void setUp(){
		partie = new PartieMultiJoueurs();
		Joueurs = new String[]{"Didier Boule", "Alain Lucci"};
	}

	@Test
	void testDemarrerPartie(){
		assertEquals(partie.demarreNouvellePartie(Joueurs), "Prochain tir: joueur Didier Boule, tour n° 1, boule n° 1", "Ce n'est pas le bon tour/joueur/lancer");
	}

	@Test
	void testDemarrerPartieSansJoueur(){
		assertThrows(IllegalArgumentException.class, () -> {
			partie.demarreNouvellePartie(new String[]{});
		}, "Une partie ne peux pas commencer sans joueur");
	}

	@Test
	void testEnregistrementLancer(){
		partie.demarreNouvellePartie(Joueurs);
		assertEquals(partie.enregistreLancer(9), "Prochain tir: joueur Didier Boule, tour n° 1, boule n° 2", "Ce n'est pas le bon tour/joueur/lancer");
		assertEquals(partie.enregistreLancer(1), "Prochain tir: joueur Alain Lucci, tour n° 1, boule n° 1", "Ce n'est pas le bon tour/joueur/lancer");
		assertEquals(partie.enregistreLancer(10), "Prochain tir: joueur Didier Boule, tour n° 2, boule n° 1", "Ce n'est pas le bon tour/joueur/lancer");
		for (int i = 0; i < 21; i++) {
			partie.enregistreLancer(10);
		}
		assertEquals(partie.enregistreLancer(10), "Partie terminée", "La partie doit être términée");
		assertThrows(IllegalStateException.class, () -> {partie.enregistreLancer(1);}, "On ne doit pas pouvoir lancer àprès la fin de la partie");
	}

	@Test
	void testScore(){
		partie.demarreNouvellePartie(Joueurs);
		partie.enregistreLancer(3);
		assertEquals(partie.scorePour("Didier Boule"), 3, "Le score n'est pas bon pour ce joueur");
		assertThrows(IllegalArgumentException.class, () -> {partie.scorePour("Alain Gucci");}, "Ce joueur n'existe pas");
	}
}