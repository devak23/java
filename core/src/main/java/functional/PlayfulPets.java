package functional;

import functional.model.Pet;
import functional.spec.PetMatcher;
import util.DataLoader;

import java.util.List;
import java.util.stream.Collectors;

public class PlayfulPets {

    public static void main(String[] args) {
        List<Pet> pets = DataLoader.loadList("pets.json", Pet.class);
        Pet.pets.clear();
        Pet.pets.addAll(pets);

        matchPet("Poodles", byAnimalAndBreed, new Pet("Molly", "Dog", "Poodle", null, 0.0, null));
        matchPet("Pets less than $800", byPrice, new Pet(null, null, null, null, 800.0, null));
    }

    public static void matchPet(String criteria, PetMatcher matcher, Pet pet) {
        System.out.println(STR."Matching \{criteria}");
        System.out.println(STR."First: \{matcher.first(pet)}");
        System.out.println("All matches");
        List<Pet> pets = matcher.match(pet);
        pets.forEach(System.out::println);
    }

    static PetMatcher byAnimalAndBreed = pet -> Pet.pets.stream()
            .filter(p -> p.animal().equalsIgnoreCase(pet.animal()) && p.breed().equalsIgnoreCase(pet.breed()))
            .collect(Collectors.toList());

    static PetMatcher byPrice =  pet -> Pet.pets.stream().filter(p -> p.price() <=pet.price()).toList();
}
