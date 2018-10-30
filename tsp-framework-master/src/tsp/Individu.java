package tsp;

import java.util.ArrayList;

public class Individu {
	
	private Instance g_instance;
	private ArrayList<Integer> ordreVisite;
	
	
	public Individu(Instance g_instance, ArrayList<Integer> ordreVisite) {
		this.g_instance=g_instance;
		this.ordreVisite=ordreVisite;
	}
	
	public ArrayList<Integer> getOrdreVisite(){
		return this.ordreVisite;
	}
	
	public Instance getInstance() {
		return this.g_instance;
	}

	public double getValeur() throws Exception {
		double res=0;
		int index=0;
		while(index<this.ordreVisite.size()-1) {
			res+=this.g_instance.getDistances(this.ordreVisite.get(index),this.ordreVisite.get(index+1));
			index++;
		}res+=this.g_instance.getDistances(this.ordreVisite.get(this.ordreVisite.size()-1),this.ordreVisite.get(0));
		return res;
	}

	
	public void mutation() {
		int nombreVille=this.getOrdreVisite().size();
		int index1=(int)(Math.random()*nombreVille-1);
		int index2=index1+(int)(Math.random()*(nombreVille-index1-1));
		int index3=index2+(int)(Math.random()*(nombreVille-index2-1));
		int index4=index3+(int)(Math.random()*(nombreVille-index3-1));
		ArrayList<Integer> mutation = new ArrayList<Integer>();
		for (int i=0;i<index1;i++) {
			mutation.add(this.getOrdreVisite().get(i));
		}
		for (int i=index1;i<index2;i++) {
			mutation.add(this.getOrdreVisite().get(i));
		}
		for (int i=index2;i<index3;i++) {
			mutation.add(this.getOrdreVisite().get(i));
		}
		for (int i=index3;i<index4;i++) {
			mutation.add(this.getOrdreVisite().get(i));
		}
		for (int i=index4;i<nombreVille;i++) {
		    mutation.add(this.getOrdreVisite().get(i));
		}
		
		this.ordreVisite=mutation;
	}
}
