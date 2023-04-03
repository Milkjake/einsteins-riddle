import java.util.*;

public class Main {

    public static void main(String[] args) {

        long startTime = System.nanoTime();

        House fishHouse = null;

        while (fishHouse == null) {
            Random r = new Random();

            //
            // Build some houses based on clues
            //

            // The Brit lives in the red house
            House redBritHouse = new House.HouseBuilder()
                    .nationality(Nationality.British)
                    .colour(Colour.Red)
                    .build();

            // The Swede keeps dogs as pets
            House dogSwedeHouse = new House.HouseBuilder()
                    .nationality(Nationality.Swedish)
                    .pet(Pet.Dog)
                    .build();

            // The Dane drinks tea
            House teaDaneHouse = new House.HouseBuilder()
                    .nationality(Nationality.Danish)
                    .beverage(Beverage.Tea)
                    .build();

            // The German smokes Prince
            House princeGermanHouse = new House.HouseBuilder()
                    .nationality(Nationality.German)
                    .cigar(Cigar.Prince)
                    .build();

            // The Norwegian lives in the first house
            House norwegianHouse = new House.HouseBuilder()
                    .nationality(Nationality.Norwegian)
                    .build();

            List<House> houses = new LinkedList<>();

            // The Norwegian lives in the first house
            houses.add(norwegianHouse);

            // put remaining houses in temporary list
            List<House> remainingHouses = new ArrayList<>();
            remainingHouses.add(redBritHouse);
            remainingHouses.add(dogSwedeHouse);
            remainingHouses.add(teaDaneHouse);
            remainingHouses.add(princeGermanHouse);

            // add remaining houses in random locations
            while (remainingHouses.size() > 0) {
                // pick a random house
                int randomIdx = r.nextInt(remainingHouses.size());

                houses.add(remainingHouses.get(randomIdx));

                remainingHouses.remove(randomIdx);
            }

            // remaining attributes
            List<Colour> colourList = new ArrayList<>(List.of(Colour.Blue, Colour.Yellow, Colour.Green, Colour.White));
            List<Beverage> beverageList = new ArrayList<>(List.of(Beverage.Milk, Beverage.Water, Beverage.Beer, Beverage.Coffee));
            List<Cigar> cigarList = new ArrayList<>(List.of(Cigar.PallMall, Cigar.Blend, Cigar.BlueMaster, Cigar.Dunhill));
            List<Pet> petList = new ArrayList<>(List.of(Pet.Cat, Pet.Bird, Pet.Fish, Pet.Horse));


            // populate houses with remaining attributes
            while (colourList.size() > 0 || beverageList.size() > 0 ||
                    cigarList.size() > 0 || petList.size() > 0) {

                // pick a random house
                int randomHouseIdx = r.nextInt(houses.size());
                House randomHouse = houses.get(randomHouseIdx);

                if (randomHouse.getColour() == null) {
                    // pick a random colour from the remaining options
                    int randomColourIdx = r.nextInt(colourList.size());

                    Colour randomColour = colourList.get(randomColourIdx);

                    randomHouse.setColour(randomColour);

                    // remove the chosen colour from the remaining options
                    colourList.removeIf(colour -> colour.equals(randomColour));
                }

                if (randomHouse.getBeverage() == null) {
                    // pick a random beverage from the remaining options
                    int randomBeverageIdx = r.nextInt(beverageList.size());

                    Beverage randomBeverage = beverageList.get(randomBeverageIdx);

                    randomHouse.setBeverage(randomBeverage);

                    // remove the chosen beverage from the remaining options
                    beverageList.removeIf(beverage -> beverage.equals(randomBeverage));
                }

                if (randomHouse.getCigar() == null) {
                    // pick a random cigar from the remaining options
                    int randomCigarIdx = r.nextInt(cigarList.size());

                    Cigar randomCigar = cigarList.get(randomCigarIdx);

                    randomHouse.setCigar(randomCigar);

                    // remove the chosen cigar from the remaining options
                    cigarList.removeIf(cigar -> cigar.equals(randomCigar));
                }

                if (randomHouse.getPet() == null) {
                    // pick a random pet from the remaining options
                    int randomPetIdx = r.nextInt(petList.size());

                    Pet randomPet = petList.get(randomPetIdx);

                    randomHouse.setPet(randomPet);

                    // remove the chosen pet from the remaining options
                    petList.removeIf(pet -> pet.equals(randomPet));
                }

                // replace existing house with new version
                houses.remove(randomHouseIdx);
                houses.add(randomHouseIdx, randomHouse);

            }

            if (checkClues(houses)) {
                long endTime = System.nanoTime();

                long duration = ((endTime - startTime) / 1000000);  // divide by 1000000 to get milliseconds.

                System.out.printf("Fish owner was found! And it only took %d milliseconds!\n", duration);

                fishHouse = houses.stream().filter(house -> house.getPet().equals(Pet.Fish)).findFirst().get();

                System.out.println(fishHouse);
            }
        }
    }

    public static boolean checkClues(List<House> houses) {
        if (!housesAreUnique(houses)) {
            return false;
        }

        // The Brit lives in the red house
        if (houses.stream().filter(house -> house.getColour().equals(Colour.Red) &&
                house.getNationality().equals(Nationality.British)).findFirst().isEmpty()) {
            return false;
        }
        System.out.println("The Brit lives in the red house");

        // The Swede keeps dogs as pets
        if (houses.stream().filter(house -> house.getNationality().equals(Nationality.Swedish) &&
                house.getPet().equals(Pet.Dog)).findFirst().isEmpty()) {
            return false;
        }
        System.out.println("The Swede keeps dogs as pets");

        // The Dane drinks tea
        if (houses.stream().filter(house -> house.getNationality().equals(Nationality.Danish) &&
                house.getBeverage().equals(Beverage.Tea)).findFirst().isEmpty()) {
            return false;
        }
        System.out.println("The Dane drinks tea");

        // The green house is on the left of the white house
        // White house index - Green house index should be 1
        // Ex. 5 - 4 = 1
        if (houses.indexOf(houses.stream().filter(
                house -> house.getColour().equals(Colour.White)).findFirst().get()) -
                houses.indexOf(houses.stream().filter(
                        house -> house.getColour().equals(Colour.Green)).findFirst().get())
                != 1) {
            return false;
        }
        System.out.println("The green house is on the left of the white house");

        // The green house’s owner drinks coffee
        if (houses.stream().filter(house -> house.getColour().equals(Colour.Green) &&
                house.getBeverage().equals(Beverage.Coffee)).findFirst().isEmpty()) {
            return false;
        }
        System.out.println("The green house’s owner drinks coffee");

        // The person who smokes Pall Mall rears birds
        if (houses.stream().filter(house -> house.getCigar().equals(Cigar.PallMall) &&
                house.getPet().equals(Pet.Bird)).findFirst().isEmpty()) {
            return false;
        }
        System.out.println("The person who smokes Pall Mall rears birds");

        // The owner of the yellow house smokes Dunhill
        if (houses.stream().filter(house -> house.getColour().equals(Colour.Yellow) &&
                house.getCigar().equals(Cigar.Dunhill)).findFirst().isEmpty()) {
            return false;
        }
        System.out.println("The owner of the yellow house smokes Dunhill");

        // The man living in the center house drinks milk
        // Center house of 5 is index 2
        if (houses.indexOf(houses.stream().filter(
                house -> house.getBeverage().equals(Beverage.Milk)).findFirst().get()) != 2) {
            return false;
        }
        System.out.println("The man living in the center house drinks milk");

        // The Norwegian lives in the first house
        // First house should have an index of 0
        if (houses.indexOf(houses.stream().filter(
                house -> house.getNationality().equals(Nationality.Norwegian)).findFirst().get()) != 0) {
            return false;
        }
        System.out.println("The Norwegian lives in the first house");

        // The man who smokes blends lives next to the one who keeps cats
        // Blend house index - Cat house index should be 1 (absolute)
        // Ex. 5 - 4 = 1 | 4 - 5 = -1 ~> abs(-1) = 1
        if (Math.abs(houses.indexOf(houses.stream().filter(
                house -> house.getCigar().equals(Cigar.Blend)).findFirst().get()) -
                houses.indexOf(houses.stream().filter(
                        house -> house.getPet().equals(Pet.Cat)).findFirst().get()))
                != 1) {
            return false;
        }
        System.out.println("The man who smokes blends lives next to the one who keeps cats");

        // The man who keeps horses lives next to the man who smokes Dunhill
        // Horse house index - Dunhill house index should be 1 (absolute)
        // Ex. 3 - 2 = 1 | 2 - 3 = -1 ~> abs(-1) = 1
        if (Math.abs(houses.indexOf(houses.stream().filter(
                house -> house.getPet().equals(Pet.Horse)).findFirst().get()) -
                houses.indexOf(houses.stream().filter(
                        house -> house.getCigar().equals(Cigar.Dunhill)).findFirst().get()))
                != 1) {
            return false;
        }
        System.out.println("The man who keeps horses lives next to the man who smokes Dunhill");

        // The owner who smokes BlueMaster drinks beer
        if (houses.stream().filter(house -> house.getCigar().equals(Cigar.BlueMaster) &&
                house.getBeverage().equals(Beverage.Beer)).findFirst().isEmpty()) {
            return false;
        }
        System.out.println("The owner who smokes BlueMaster drinks beer");

        // The German smokes Prince
        if (houses.stream().filter(house -> house.getNationality().equals(Nationality.German) &&
                house.getCigar().equals(Cigar.Prince)).findFirst().isEmpty()) {
            return false;
        }
        System.out.println("The German smokes Prince");

        // The Norwegian lives next to the blue house
        // Norwegian house index - Blue house index should be 1 (absolute)
        // Ex. 4 - 3 = 1 | 3 - 4 = -1 ~> abs(-1) = 1
        if (Math.abs(houses.indexOf(houses.stream().filter(
                house -> house.getNationality().equals(Nationality.Norwegian)).findFirst().get()) -
                houses.indexOf(houses.stream().filter(
                        house -> house.getColour().equals(Colour.Blue)).findFirst().get()))
                != 1) {
            return false;
        }
        System.out.println("The Norwegian lives next to the blue house");

        // The man who smokes blend has a neighbor who drinks water
        // Blend house index - Water house index should be 1 (absolute)
        // Ex. 4 - 3 = 1 | 3 - 4 = -1 ~> abs(-1) = 1
        if (Math.abs(houses.indexOf(houses.stream().filter(
                house -> house.getCigar().equals(Cigar.Blend)).findFirst().get()) -
                houses.indexOf(houses.stream().filter(
                        house -> house.getBeverage().equals(Beverage.Water)).findFirst().get()))
                != 1) {
            return false;
        }
        System.out.println("The man who smokes blend has a neighbor who drinks water");

        return true;
    }

    static boolean containsColour(final List<House> houses, final Colour colour) {
        return houses.stream().anyMatch(house -> colour.equals(house.getColour()));
    }

    static boolean containsNationality(final List<House> houses, final Nationality nationality) {
        return houses.stream().anyMatch(house -> nationality.equals(house.getNationality()));
    }

    static boolean containsBeverage(final List<House> houses, final Beverage beverage) {
        return houses.stream().anyMatch(house -> beverage.equals(house.getBeverage()));
    }

    static boolean containsCigar(final List<House> houses, final Cigar cigar) {
        return houses.stream().anyMatch(house -> cigar.equals(house.getCigar()));
    }

    static boolean containsPet(final List<House> houses, final Pet pet) {
        return houses.stream().anyMatch(house -> pet.equals(house.getPet()));
    }

    static boolean housesAreUnique(final List<House> houses) {

        if (!containsColour(houses, Colour.Red) || !containsColour(houses, Colour.Blue) ||
                !containsColour(houses, Colour.Yellow) || !containsColour(houses, Colour.Green) ||
                !containsColour(houses, Colour.White)) {
            return false;
        }

        if (!containsNationality(houses, Nationality.British) || !containsNationality(houses, Nationality.German) ||
                !containsNationality(houses, Nationality.Danish) || !containsNationality(houses, Nationality.Swedish) ||
                !containsNationality(houses, Nationality.Norwegian)) {
            return false;
        }

        if (!containsBeverage(houses, Beverage.Coffee) || !containsBeverage(houses, Beverage.Beer) ||
                !containsBeverage(houses, Beverage.Tea) || !containsBeverage(houses, Beverage.Water) ||
                !containsBeverage(houses, Beverage.Milk)) {
            return false;
        }

        if (!containsCigar(houses, Cigar.Blend) || !containsCigar(houses, Cigar.BlueMaster) ||
                !containsCigar(houses, Cigar.Dunhill) || !containsCigar(houses, Cigar.PallMall) ||
                !containsCigar(houses, Cigar.Prince)) {
            return false;
        }

        if (!containsPet(houses, Pet.Cat) || !containsPet(houses, Pet.Dog) || !containsPet(houses, Pet.Bird) ||
                !containsPet(houses, Pet.Horse) || !containsPet(houses, Pet.Fish)) {
            return false;
        }

        return true;
    }
}

