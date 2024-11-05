import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class Flyplass {

    private int MaksKo = 10;                            // makskø

    private int tidssteg;
    private float fl_pr, fa_pr;                         // fly landet average, fly ankomst average per tidsenhet

    public Flyplass(String navn){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Velkommen til " + navn + " Flyplass");
        System.out.print("Antall tidsteg?: ");
        tidssteg = scanner.nextInt();                                                           // Parameter verdier fra bruker
        System.out.print("Forventet antall avganger pr tidsenhet? (< 1.0): ");
        fa_pr = scanner.nextFloat();
        System.out.print("Forventet antall landinger pr tidsenhet? (1 - ankomst pr tidsenhet>=): ");
        fl_pr = scanner.nextFloat();

        if(fl_pr+fa_pr > 1.0){
            System.out.println("\nAnkomst + landing kan ikke overskride 1.0");                  // Om fly landet + fly ankomst er større enn 1
            System.exit(0);                                                               // Programmet avsluttes
        }
    }
    public class rullebane{
        private int ferdig;
        public rullebane(){ferdig = 0;}                                     // Rullebane (ikke brukt)
        public void setferdig(int tid){ferdig = tid;}
        public boolean ledig(int tid){return tid >= ferdig;}
    }
    public void simuler(){

         int flyLandet = 0, flyTattav = 0;
         int flyAvvist = 0;
         int flyNummer = 0;
         int totLVenteTid = 0, totAVenteTid = 0, totFlyBehandlet = 0, totFly = 0, totBetTid = 0, totLedig = 0;          // Setter alle verdiene som skal brukes til 0


        Queue<Fly> FlyKoLande = new LinkedList<>();                              // Tom kø for Fly som skal lande og ta av
        Queue<Fly> flyKoTaAv = new LinkedList<>();

        rullebane[] enRullebane = new rullebane[1];                                 // Rullebane array som har plass til 1

        Random R = new Random();                                                    // Random objekt

        int landeRandom= getPoissonRandom(fl_pr);
        int taAvRandom = getPoissonRandom(fa_pr);


        for (int tid = 0; tid < tidssteg; tid++)                // Starter tidssteg
        {


            double trekning = R.nextDouble();                       // Får et random tall mellom 0 og 1
                                                                                                                                                                   //System.out.println("R = " + trekning);
                                                                                                                                                                          //  if (0<trekning && trekning < 1) {
                System.out.println(tid + ":");
                                                                                                                                                                  //System.out.println("Trekning: " + trekning + ", fl: " + fl_pr + " og " + "fa: " + fa_pr);
                                                                                                                                                                     // int antFly = getPoissonRandom(R.nextDouble());
                if(trekning<fl_pr) {                          // Ser om trekning er større en low og om low = fly rate for ta av                                    //Math.abs(fl_pr-trekning)> Math.abs(fa_pr-trekning

                    for (int i = 0; i < landeRandom ; i++) {          // Da får vi inn mellom 1 og 4 fly som skal lande

                        if (FlyKoLande.size() == MaksKo) {                         // Om køen er lik 10, så blir fly avvist
                            flyAvvist++;
                            System.out.println("Dra til en annen flyplass");
                        } else {
                            FlyKoLande.add(new Fly(tid, flyNummer));                        // Fly som skal landet blir plassert bakerst i køen, og får tildelt tid den kom inn og nummer
                            System.out.println("Fly " + flyNummer + " klar for landing");
                            flyNummer++;
                            totFly++;
                        }
                    }
                }
                else {
                    for (int i = 0; i < taAvRandom; i++) {            // Om trekning ikke er mindre enn low og at low er fly som skal landet
                                                                                                                                                                                    //System.out.println(getPoissonRandom(fa_pr));
                        if (flyKoTaAv.size() == MaksKo) {
                            flyAvvist++;                                            // Samme som over skjer
                        } else {
                            flyKoTaAv.add(new Fly(tid, flyNummer));
                            System.out.println("Fly " + flyNummer + " klar for avgang");
                            flyNummer++;
                            totFly++;
                        }

                   }
                }
                if (!FlyKoLande.isEmpty()) {                                        // Om landingskøen ikke er tom
                    int betTid = (tid - FlyKoLande.peek().getFlyankommet());        // Får betjeningstid for flyet som skal behandles(toppen av køen), tidssteg vi er i minus tid de kom i køen
                    assert FlyKoLande.peek() != null;
                    System.out.println("Fly " + FlyKoLande.peek().getFlyNummer() + " landet, ventetid " + betTid + " enheter");

                    FlyKoLande.remove();                                            // Fjerner fra køen

                    flyLandet++;
                    totLVenteTid+=betTid;                                           // inkrementerer verdier
                    totBetTid += betTid;
                    totFlyBehandlet++;
                }
                else if (!flyKoTaAv.isEmpty()) {                                // Om køen der fly skal ta av ikke er tom, og at det ikke ble behandlet noen fly som skulle lande(else if)
                    int betTid = (tid - flyKoTaAv.peek().getFlyankommet());
                    assert flyKoTaAv.peek() != null;
                    System.out.println("Fly " + flyKoTaAv.peek().getFlyNummer() + " tatt av, ventetid " + (betTid) + " enheter");

                    flyKoTaAv.remove();                             // Fjerner fra kø

                    flyTattav++;
                    totAVenteTid+=betTid;                           // Inkrementerer verdier
                    totBetTid += betTid;
                    totFlyBehandlet++;
                }
           // }
            else{
                System.out.println("Flyplassen er tom");        // Om både flykøen som skal ta av og lande er tom
                totLedig++;
            }
        }

        // Simulering ferdig, skriv ut litt statistikk
        System.out.println("----------------------------------------");
        System.out.println("Simulering ferdig etter " + tidssteg + " tidsenheter");

        System.out.println("Samlet betjeningstid: " + totBetTid);
        System.out.println("Antall fly behandlet: " + totFlyBehandlet);


        System.out.println("\nAntall fly landet: " + flyLandet);
        System.out.println("Antall fly tatt av: " + flyTattav);
        System.out.println("Antall fly avvist: " + flyAvvist);
        System.out.println("Antall fly i avgangskø: " + flyKoTaAv.size());
        System.out.println("Antall fly i landingskø: " + FlyKoLande.size());

        if (totFlyBehandlet > 0)
        {
            System.out.println("\nGjennomsnittlig ledig tid: " +
                    (float) totLedig / (float) totFly) ;
            System.out.println("Gjennomsnittlig Total ventetid: " +
                    (float) (totLVenteTid+totAVenteTid) / (float) totFly);

        }
        if(totAVenteTid == 0){
            System.out.println("Fly fikk ikke dratt");
        }
        else {
            System.out.println("Gjennomsnittlig ventetid Avgang: " +
                    (float) totAVenteTid / (float) tidssteg);
        }
        if(totLVenteTid == 0){
            System.out.println("Fly fikk ikke landet");
        }
        else {
            System.out.println("Gjennomsnittlig ventetid Landing: " +
                    (float) totLVenteTid / (float) tidssteg);
        }


    }
    private static int getPoissonRandom(double mean)
    {
        Random r = new Random();
        double L = Math.exp(-mean);
        int k = 0;
        double p = 1.0;
        do
        {
            p = p * r.nextDouble();
            k++;
        } while (p > L);
        return k - 1;
    }
}
