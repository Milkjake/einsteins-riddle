import java.util.Objects;

public class House {

    Nationality nationality;
    Colour colour;
    Beverage beverage;
    Cigar cigar;
    Pet pet;

    public House(Nationality nationality, Colour colour, Beverage beverage, Cigar cigar, Pet pet) {
        this.nationality = nationality;
        this.colour = colour;
        this.beverage = beverage;
        this.cigar = cigar;
        this.pet = pet;
    }

    public House(HouseBuilder builder) {
        this.nationality = builder.nationality;
        this.colour = builder.colour;
        this.beverage = builder.beverage;
        this.cigar = builder.cigar;
        this.pet = builder.pet;
    }

    public Nationality getNationality() {
        return nationality;
    }

    public void setNationality(Nationality nationality) {
        this.nationality = nationality;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public Beverage getBeverage() {
        return beverage;
    }

    public void setBeverage(Beverage beverage) {
        this.beverage = beverage;
    }

    public Cigar getCigar() {
        return cigar;
    }

    public void setCigar(Cigar cigar) {
        this.cigar = cigar;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    @Override
    public String toString() {
        return "House{" +
                "nationality=" + nationality +
                ", colour=" + colour +
                ", beverage=" + beverage +
                ", cigar=" + cigar +
                ", pet=" + pet +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        House house = (House) o;
        return nationality == house.nationality && colour == house.colour && beverage == house.beverage && cigar == house.cigar && pet == house.pet;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nationality, colour, beverage, cigar, pet);
    }


    public static class HouseBuilder {
        Nationality nationality;
        Colour colour;
        Beverage beverage;
        Cigar cigar;
        Pet pet;

        public HouseBuilder nationality(Nationality nationality) {
            this.nationality = nationality;
            return this;
        }

        public HouseBuilder colour(Colour colour) {
            this.colour = colour;
            return this;
        }

        public HouseBuilder beverage(Beverage beverage) {
            this.beverage = beverage;
            return this;
        }

        public HouseBuilder cigar(Cigar cigar) {
            this.cigar = cigar;
            return this;
        }

        public HouseBuilder pet(Pet pet) {
            this.pet = pet;
            return this;
        }

        public House build() {
            return new House(this);
        }
    }
}
