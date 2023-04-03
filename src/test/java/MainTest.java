import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {

    @Test
    void checkClues_shouldReturnTrue() {
        // arrange
        List<House> testHouses = new LinkedList<>();
        testHouses.add(new House(Nationality.Norwegian, Colour.Yellow, Beverage.Water, Cigar.Dunhill, Pet.Cat));
        testHouses.add(new House(Nationality.Danish, Colour.Blue, Beverage.Tea, Cigar.Blend, Pet.Horse));
        testHouses.add(new House(Nationality.British, Colour.Red, Beverage.Milk, Cigar.PallMall, Pet.Bird));
        testHouses.add(new House(Nationality.German, Colour.Green, Beverage.Coffee, Cigar.Prince, Pet.Fish));
        testHouses.add(new House(Nationality.Swedish, Colour.White, Beverage.Beer, Cigar.BlueMaster, Pet.Dog));

        // act
        boolean result = Main.checkClues(testHouses);

        // assert
        assertTrue(result);
    }

}
