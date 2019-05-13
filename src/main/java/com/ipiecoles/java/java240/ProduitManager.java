package com.ipiecoles.java.java240;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProduitManager {

    private List<Produit> produits = new ArrayList<>();
    private BitcoinService bitcoinService ;
    private WebPageManager webPageManager ;

    /**
     * Méthode qui demande les caractéristiques d'un nouveau produit
     * à l'utilisateur et qui l'ajoute au catalogue
     */
    public void ajouterProduit(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrez l'intitulé du produit");
        String intitule = scanner.nextLine();
        if(produits.stream().
                map(Produit::getIntitule).
                anyMatch(s -> s.equals(intitule))){
            System.out.println("Ce produit existe déjà dans le catalogue !");
            return;
        }
        System.out.println("Entrez le prix du produit");
        Double prixEuro = scanner.nextDouble();

        produits.add(new Produit(intitule, prixEuro));
    }

    /**
     * Méthode qui affiche tous les produits du catalogue
     */
    public void afficherTousLesProduits(){
        produits.forEach(System.out::println);
    }

    /**
     * M�thode qui affiche les d�tails du produit du num�ro passé en paramètre
     * et notamment le prix en bitcoin
     * @param index
     * @throws IOException
     */
    public void afficherDetailProduit(Integer index) throws IOException {
        
        System.out.println(produits.get(index).toString() + ", " + bitcoinService.getBitcoinPrice(produits.get(index).getPrixEuro()) + " BTC");
    }

    /**
     * Méthode qui initialise le catalogue à partir d'un fichier distant.
     * @throws IOException
     */
    public void initialiserCatalogue() throws IOException {
        
        String catalogue = webPageManager.getPageContentsFromCacheIfExists("https://pjvilloud.github.io/ipi-java-240-cours/catalogue.txt");
        int nbProduits = 0;
        for(String line : catalogue.split("\n")){
            String[] elements = line.split(";");
            produits.add(new Produit(elements[0], Double.parseDouble(elements[1])));
            nbProduits++;
        }
        System.out.println("Ajout de " + nbProduits + " produits !");
    }

	public BitcoinService getBitcoinService() {
		return bitcoinService;
	}

	public void setBitcoinService(BitcoinService bitcoinService) {
		this.bitcoinService = bitcoinService;
	}

	public WebPageManager getWebPageManager() {
		return webPageManager;
	}

	public void setWebPageManager(WebPageManager webPageManager) {
		this.webPageManager = webPageManager;
	}

}
