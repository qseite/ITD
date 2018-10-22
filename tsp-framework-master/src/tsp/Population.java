package tsp;

import java.util.ArrayList;

public class Population {
	
	ArrayList<Individu> population;
	int nombreIndividus;
	Instance g_instance;
	
	public Population(int nbIndividu, Instance g_instance) {
		this.population = new ArrayList<Individu>();
		this.nombreIndividus=nbIndividu;
		this.g_instance=g_instance;
		ArrayList<Integer> temp=new ArrayList<Integer>();
		for (int i=0;i<g_instance.getNbCities();i++) {
			temp.add(i);
		}
		Individu temp2=new Individu(g_instance,temp);
		this.population.add(temp2);
		for (int i=1;i<nbIndividu;i++) {
			temp2.mutation();
			this.population.add(new Individu(g_instance,temp2.getOrdreVisite()));
		}
	}
	
    public Population(ArrayList<Individu> population) {
    	this.population=population;
    }
	
	
	public ArrayList<Individu> getPopulation(){
		return this.population;
	}
	
	public Instance getInstance() {
		return this.g_instance;
	}
	
	public int getIndexBest() throws Exception {
		double valeur=this.population.get(0).getValeur();
		int index=0;
		for (int i=1;i<this.population.size();i++) {
			double inter = this.population.get(i).getValeur();
			if (inter<valeur){
				valeur=inter;
				index=i;
			}	
		}
		return index;	
	}
	
	//retourne le meilleur individu
	public Individu getBest() throws Exception {
		return this.population.get(this.getIndexBest());
	}
	
	public Individu crossover1(Individu ind1, Individu ind2) {
		ArrayList<Integer> ordre = new ArrayList<Integer>();
		int taille = ind1.getOrdreVisite().size();
		int alea = (int)(Math.random()*taille-2);
		int index=0;
		while (index<alea) {
			ordre.add(ind1.getOrdreVisite().get(index));
			index++;
		}
		int i=index;
		int tailleIntermediaire=ordre.size();
		while(index<taille) {
			int compteur=0;
			boolean present = false;
			while (compteur<tailleIntermediaire) {
				if(ind2.getOrdreVisite().get(i)==ordre.get(compteur)) {
					present=true;
				}compteur++;
			}if (present==false) {
				ordre.add(ind2.getOrdreVisite().get(i));
			}
			if (i<taille && i>0) {
				i=i+1;
			}else {
				i=0;
			}index++;
		}

		Individu res = new Individu(this.g_instance,ordre);
		return res;
	}
	
	public Individu crossover2(Individu i1, Individu i2) {
		int index1 = (int)(Math.random()*this.getInstance().getNbCities()-1);
		int index2 = index1 + (int)(Math.random()*(this.getInstance().getNbCities()-index1-1));
		ArrayList<Integer> child = new ArrayList<Integer>();
		for (int i=0;i<index1;i++) {
			child.add(i1.getOrdreVisite().get(i));
		}
		for (int i=index1;i<index2;i++) {
			if (!child.contains(i2.getOrdreVisite().get(i))) {
				child.add(i2.getOrdreVisite().get(i));
			} else {
				int k=0; 
				while (child.contains(i2.getOrdreVisite().get(k)) && k<i2.getOrdreVisite().size()) {
					k++;
				}
				child.add(i2.getOrdreVisite().get(k));
			}
		}
		for (int i=index2;i<this.getInstance().getNbCities();i++) {
			if (!child.contains(i1.getOrdreVisite().get(i))) {
				child.add(i1.getOrdreVisite().get(i));
			} else {
				int j=index1; 
				while (child.contains(i1.getOrdreVisite().get(j)) && j<i2.getOrdreVisite().size()) {
					j++;
				}
				child.add(i1.getOrdreVisite().get(j));
			}
		}
		return new Individu(this.getInstance(),child);
	}
	
	
	// insere l'individu en argument et enl�ve et retourne l'individu retir� (celui qui a la plus grande distance)
	public Individu insertion(Individu aInserer) throws Exception {
		double valeur=this.population.get(0).getValeur();
		int index=0;
		for (int i=1;i<this.population.size();i++) {
			double inter = this.population.get(i).getValeur();
			if (inter>valeur){
				index=i;
				valeur=inter;
			}
	    }Individu res = this.population.get(index);
		this.population.remove(index);
		this.population.add(aInserer);
		return res;
     }
	
	public ArrayList<Individu> selection() throws Exception {
		
		int index = this.getIndexBest();
		Individu ind1 = this.population.get(index);
		//Population copie = new Population(this.getPopulation());
		//copie.getPopulation().remove(index);
		int alea = (int)(Math.random()*this.getPopulation().size()-1);
	    System.out.print(alea+"/");
		Individu ind2=this.getPopulation().get(alea);
		
		ArrayList<Individu> res = new ArrayList<Individu>();
		res.add(ind1);
		res.add(ind2);
		
		return res;
		
	}
}
