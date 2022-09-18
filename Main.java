package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import static com.company.Main.Colors.*;

public class Main {

    public static void main(String[] args) {

        ArrayList<Pokemon> cardDeck = fetchThemAll();
        ArrayList<String> pCardTypes = new ArrayList<>(
                Arrays.asList("WATER", "ELECTRIC", "GRASS", "FIRE"));
        System.out.println(WhiteB + BLACK + "Welcome to Pokemon...or My-Mon ;)" + RESET);
        String pType1 = getUserPokeType(pCardTypes, cardDeck);
        Pokemon p1 = getRandomCardOfType(pType1);
        showCard(p1);
        String pType2 = getUserPokeType(pCardTypes, cardDeck);
        Pokemon p2 = getRandomCardOfType(pType2);
        showCard(p2);
        enterToPlay();
        PokeResults result = playUntilFeint(p1, p2);
        showCompletedResults(result);

    }

    private static void enterToPlay() {
        System.out.println("\n\n" + GreenB + BLACK + "--Press enter to BATTLE!--" + RESET + "");
        try {
            System.in.read();
        } catch (Exception ignored) {
        }
    }
    private static void nextRound() {
        System.out.println("\n\n" + RedB + BLACK + "--Press enter to ATTACK!--" + RESET + "");
        try {
            System.in.read();
        } catch (Exception ignored) {
        }
    }

    private static void showCompletedResults(PokeResults result) {
        System.out.printf("""

                        ---%s FINAL STATS---      ---%s FINAL STATS---
                        \tHP remaining:%s \t\t\t\tHP remaining:%s
                        \tDamage dealt:%s\t\t\t\tDamage dealt:%s
                        \tTotal Attacks:%s \t\t\t\tTotal Attacks:%s
                        \tTotal Defends:%S \t\t\t\tTotal Defends:%S
                        \tAlive? : %s \t\t\t\t\tAlive? : %s
                                                
                        """,
                result.pokemon.name,
                result.opponent.name,
                (int)result.pokemon.life,(int) result.opponent.life,
                result.opponent.HP, result.pokemon.HP - (int) result.pokemon.life,
                result.pokemon.attacks, result.opponent.attacks,
                result.pokemon.defends, result.opponent.defends,
                result.pokemon.isAlive(), result.opponent.isAlive()
        );
    }

    private static PokeResults playUntilFeint(Pokemon p1, Pokemon p2) {

        int battleCount = 1;
        PokeResults p_1 = new PokeResults(p1, p2);
        PokeResults p_2 = new PokeResults(p2, p1);
        PokeResults winner;

        while (true) {

            if (p1.isAlive()) {
                System.out.printf("\n" + WhiteB + BLACK + "----------------] ATTACK #%s [----------------" + RESET + "\n", battleCount);
                p1.defend(p2);

                battleCount++;
                p1.attacks++;
                p2.defends++;
            }
            nextRound();

            if (!p1.isAlive() && !p2.isAlive()) {
                continue;
            } else if (!p1.isAlive()) {
                System.out.printf("\n%s FEINTED\n", p1.name);
                winner = p_2;
                return winner;
            }

            if (p2.isAlive()) {
                System.out.printf("\n" + WhiteB + BLACK + "----------------] ATTACK #%s [----------------" + RESET + "\n", battleCount);
                p2.defend(p1);
                battleCount++;
                p2.attacks++;
                p1.defends++;
            }
            nextRound();

            if (!p1.isAlive() && !p2.isAlive()) {
                continue;
            } else if (!p2.isAlive()) {
                System.out.printf("\n%s FEINTED\n", p2.name);
                winner = p_1;
                return winner;
            }
        }

    }

    private static void showCard(Pokemon p1) {
        System.out.printf("""
                Your Pokemon is %s
                Stats - Type:%s HP:%s AP:%s
                Special Ability - %s""", p1.name, p1.pType, p1.HP, p1.AP, p1.getSpecialAbility());
    }

    private static Pokemon getRandomCardOfType(String pType1) {

        ArrayList<Pokemon> pokemon = fetchThemAll();
        ArrayList<Pokemon> cardType = new ArrayList<>();
        Random r = new Random();
        int pickCard = r.nextInt(2);

        for (Pokemon poke : pokemon) {
            if (poke.getpType().equalsIgnoreCase(pType1)) {
                cardType.add(poke);
            } else {
                cardType.remove(poke);
            }
        }
        return cardType.get(pickCard);
    }

    private static String getUserPokeType(ArrayList<String> pCardTypes, ArrayList<Pokemon> cardDeck) {

        Scanner s = new Scanner(System.in);
        String userSelection;
        System.out.println(WHITE + "\nSelect a Poke-Type " + RESET + pCardTypes.stream().toList());
        userSelection = s.next();

        if (pCardTypes.contains(userSelection.toUpperCase())) {
            return userSelection;
        } else {
            getUserPokeType(pCardTypes, cardDeck);
        }

        return userSelection;
    }

    private static ArrayList<Pokemon> fetchThemAll() {

        ArrayList<Pokemon> pokemons = new ArrayList<>();
        pokemons.add(new WaterPoke("Wartole", "Water", 150, 10, .1));
        pokemons.add(new WaterPoke("Blastoise", "Water", 200, 20, .05));
        pokemons.add(new FirePoke("Charizard", "Fire", 200, 15, .2));
        pokemons.add(new FirePoke("Flarean", "Fire", 200, 20, .05));
        pokemons.add(new GrassPoke("BayLeaf", "Grass", 225, 15, .1));
        pokemons.add(new GrassPoke("Tangela", "Grass", 250, 15, .2));
        pokemons.add(new Electric("Pikachu", "Electric", 150, 10, .4));
        pokemons.add(new Electric("Voltroib", "Electric", 175, 10, .5));
        return pokemons;

    }

    static class Pokemon {
        private final String name;
        private final String pType;
        private final int HP;
        private final double AP;
        protected final double DR;

        protected double life;
        protected String specialAbility;
        private int attacks;
        private int defends;


        public Pokemon(String name, String pType, int HP, int AP, double DR) {
            this.name = name;
            this.pType = pType;
            this.HP = HP;
            this.AP = AP;
            this.life = HP;
            this.DR = DR;
        }

        public String getName() {
            return name;
        }

        public String getpType() {
            return pType;
        }

        public double getAP() {
            return AP;
        }

        public void defend(Pokemon poke) {

        }

        public boolean isAlive() {
            return life > 0;
        }

        public double getRand() {
            return Math.random();
        }

        public String getSpecialAbility() {
            return specialAbility;
        }

        @Override
        public String toString() {
            return String.format("\nName:%s type:%s HP:%s AP:%s Life:%s",
                    name, pType, HP, AP, life);
        }
    }

    static class GrassPoke extends Pokemon {

        public GrassPoke(String name, String pType, int HP, int AP, double DR) {
            super(GREEN + name + RESET, pType, HP, AP, DR);
        }

        @Override
        public void defend(Pokemon poke) {
            double hit = poke.AP;
            if (poke.pType.equalsIgnoreCase("Fire") && getRand() <= 0.25) {
                hit = ((poke).getAP() * 2.5);
                System.out.printf("""

                        ---%s DEFENDS against %s

                        \t---%s is HIT with %s 2.5X EXTRA DAMAGE!---
                        """, getName(), poke.name, getName(), hit);
            } else if (getRand() <= DR) {
                System.out.printf("\n---%s DODGED %s attack from %s!---\n", getName(), hit, poke.name);
                System.out.printf("\n---%s has %s HP left---\n", getName(), life);
                return;
            } else {
                System.out.printf("""

                        ---%s DEFENDS against %s

                        \t---%s is HIT with %s DAMAGE!---
                        """, getName(), poke.name, getName(), hit);
            }
            life = life - hit;
            if (life < 0) {
                life = 0;
            }
            System.out.printf("\n\t---%s has %s HP left---\n", getName(), life);
        }

        @Override
        public String getSpecialAbility() {
            specialAbility = "Has 5% chance of doing 1.4x damage to Electric types";
            return GreenB + BLACK + specialAbility + RESET;
        }

    }

    static class FirePoke extends Pokemon {

        public FirePoke(String name, String pType, int HP, int AP, double DR) {
            super(RED + name + RESET, pType, HP, AP, DR);
        }

        @Override
        public void defend(Pokemon poke) {
            double hit = poke.AP;
            if (poke.pType.equalsIgnoreCase("Water") && getRand() <= 0.15) {
                hit = ((poke).getAP() * 2);
                System.out.printf("""

                        ---%s DEFENDS against %s

                        \t---%s is HIT with %s 2X EXTRA DAMAGE!---
                        """, getName(), poke.name, getName(), hit);
            } else if (getRand() <= DR) {
                System.out.printf("\n---%s DODGED %s attack from %s!---\n", getName(), hit, poke.name);
                System.out.printf("\n---%s has %s HP left---\n", getName(), life);
                return;
            } else {
                System.out.printf("""

                        ---%s DEFENDS against %s

                        \t---%s is HIT with %s DAMAGE!---
                        """, getName(), poke.name, getName(), hit);
            }
            life = life - hit;
            if (life < 0) {
                life = 0;
            }
            System.out.printf("\n\t---%s has %s HP left---\n", getName(), life);
        }

        @Override
        public String getSpecialAbility() {
            specialAbility = "Has a 25% chance of doing 2.5x damage towards grass types";
            return RedB + BLACK + specialAbility + RESET;
        }

    }

    static class Electric extends Pokemon {

        public Electric(String name, String pType, int HP, int AP, double DR) {
            super(YELLOW + name + RESET, pType, HP, AP, DR);
        }

        @Override
        public void defend(Pokemon poke) {
            double hit = poke.AP;
            if (poke.pType.equalsIgnoreCase("Grass") && getRand() <= 0.05) {
                hit = ((poke).getAP() * 1.5);
                System.out.printf("""

                        ---%s DEFENDS against %s

                        \t---%s is HIT with %s 1.5X EXTRA DAMAGE!---
                        """, getName(), poke.name, getName(), hit);
            } else if (getRand() <= DR) {
                System.out.printf("\n---%s DODGED %s attack from %s!---\n", getName(), hit, poke.name);
                System.out.printf("\n---%s has %s HP left---\n", getName(), life);
                return;
            } else {
                System.out.printf("""

                        ---%s DEFENDS against %s

                        \t---%s is HIT with %s DAMAGE!---
                        """, getName(), poke.name, getName(), hit);
            }
            life = life - hit;
            if (life < 0) {
                life = 0;
            }
            System.out.printf("\n\t---%s has %s HP left---\n", getName(), life);
        }

        @Override
        public String getSpecialAbility() {
            specialAbility = "Has 30% chance of doing 1.4x damage to Water types";
            return YellowB + BLACK + specialAbility + RESET;
        }
    }

    static class WaterPoke extends Pokemon {

        public WaterPoke(String name, String pType, int HP, int AP, double DR) {
            super(BLUE + name + RESET, pType, HP, AP, DR);
        }

        @Override
        public void defend(Pokemon poke) {
            double hit = poke.AP;
            if (poke.pType.equalsIgnoreCase("Electric") && getRand() <= .3) {
                hit = ((poke).getAP() * 1.4);
                System.out.printf("""

                        ---%s DEFENDS against %s

                        \t---%s is HIT with %s 1.4X EXTRA DAMAGE!---
                        """, getName(), poke.name, getName(), hit);
            } else if (getRand() <= DR) {
                System.out.printf("\n---%s DODGED %s attack from %s!---\n", getName(), hit, poke.name);
                System.out.printf("\n---%s has %s HP left---\n", getName(), life);
                return;
            } else {
                System.out.printf("""

                        ---%s DEFENDS against %s

                        \t---%s is HIT with %s DAMAGE!---
                        """, getName(), poke.name, getName(), hit);
            }
            life = life - hit;
            if (life < 0) {
                life = 0;
            }
            System.out.printf("\n\t---%s has %s HP left---\n", getName(), life);
        }

        @Override
        public String getSpecialAbility() {
            specialAbility = "Has 15% chance of doing 2x damage to Fire types";
            return Blueb + BLACK + specialAbility + RESET;
        }


    }

    static class PokeResults {

        private final Pokemon pokemon;
        private final Pokemon opponent;

        public PokeResults(Pokemon pokemon, Pokemon opponent) {
            this.pokemon = pokemon;
            this.opponent = opponent;
        }

    }

    static class Colors {

        public static final String RESET = "\u001B[0m";
        public static final String RED = "\u001B[31m";
        public static final String GREEN = "\u001B[32m";
        public static final String YELLOW = "\u001B[33m";
        public static final String BLUE = "\u001B[34m";
        public static final String WHITE = "\u001B[37m";
        public static final String RedB = ("\033[0;101m");
        public static final String GreenB = ("\033[0;102m");
        public static final String YellowB = ("\033[0;103m");
        public static final String Blueb = ("\033[0;104m");
        public static final String BLACK = "\033[1;30m";
        public static final String WhiteB = "\033[0;107m";

    }

}