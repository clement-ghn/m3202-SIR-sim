import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Terrain {

    ArrayList<Individu> population = new ArrayList<>();
    int infectes;

    public Terrain(int taille, int infectes) {
        for (int i = 0; i < taille; i++) {
            population.add(new Individu());
            population.get(i).setDistance(Math.random());
        }
        for (int j = 0; j < taille; j++) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, taille);
            population.get(j).setPlusProche(population.get(randomNum));
        }
        for (int k = 0; k < infectes; k++) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, taille);
            population.get(randomNum).setStatut("I");
        }
    }

    public ArrayList<Individu> getPopulation() {
        return population;
    }

    public void setPopulation(ArrayList<Individu> population) {
        this.population = population;
    }

    public void nextGen(Terrain t, double contagion, double seuilProx, double probRetablissement) {
        ArrayList<Individu> newPopulation = new ArrayList<Individu>();
        newPopulation = t.getPopulation();
        int popSize = t.getPopulation().size();
        for (int i = 0; i<popSize; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, newPopulation.size());
            Individu e = newPopulation.get(i);
            if (e.getStatut() == "S" && e.getDistance() < seuilProx && e.getPlusProche().getStatut() == "I") {
                if (Math.random() < contagion) {
                    e.setStatut("I");
                }
            }
            if (e.getStatut() == "I") {
                if (Math.random() < probRetablissement) {
                    e.setStatut("R");
                }
            }
            e.setDistance(Math.random());
            e.setPlusProche(newPopulation.get(randomNum));
            newPopulation.set(i, e);
        }
        t.setPopulation(newPopulation);
    }
}
