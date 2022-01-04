public class Main {
    public static void main(String[] args) {
        Terrain t = new Terrain(20, 2);
        System.out.println(t.getPopulation());
        for (int i = 0; i<30; i++) {
            t.nextGen(t, 0.5, 0.5, 0.05);
            System.out.println(t.getPopulation());
        }
    }
}
