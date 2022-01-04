public class Individu {

    double distance;
    Individu plusProche;
    String statut;

    public Individu() {
        this.statut = "S";
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public double getDistance() {
        return distance;
    }

    public Individu getPlusProche() {
        return plusProche;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setPlusProche(Individu plusProche) {
        this.plusProche = plusProche;
    }

    @Override
    public String toString() {
        return statut;
    }
}
