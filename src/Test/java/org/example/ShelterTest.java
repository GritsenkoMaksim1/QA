import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

class ShelterTest {
    private Shelter shelter;
    private Animal animal1, animal2;

    @BeforeEach
    void setUp() {
        shelter = new Shelter();
        animal1 = new Animal("Buddy", "Dog", 3);
        animal2 = new Animal("Whiskers", "Cat", 2);
        shelter.addAnimal(animal1);
        shelter.addAnimal(animal2);
    }

    @Test
    void testAddAnimal() {
        Animal newAnimal = new Animal("Charlie", "Parrot", 1);
        shelter.addAnimal(newAnimal);
        assertEquals(3, shelter.getAnimals().size());
        assertTrue(shelter.getAnimals().contains(newAnimal));
    }

    @Test
    void testRemoveAnimal() {
        assertTrue(shelter.removeAnimal("Buddy"));
        assertEquals(1, shelter.getAnimals().size());
        assertFalse(shelter.getAnimals().contains(animal1));
    }

    @Test
    void testFindAnimal() {
        Animal found = shelter.findAnimal("Whiskers");
        assertNotNull(found);
        assertEquals("Cat", found.getType());
    }

    @Test
    void testSaveAndLoadFromFile() throws IOException {
        String filename = "test_animals.txt";
        shelter.saveToFile(filename);

        Shelter newShelter = new Shelter();
        newShelter.loadFromFile(filename);
        List<Animal> loadedAnimals = newShelter.getAnimals();

        assertEquals(2, loadedAnimals.size());
        assertEquals("Buddy", loadedAnimals.get(0).getName());
        assertEquals("Whiskers", loadedAnimals.get(1).getName());

        new File(filename).delete(); // Очистка файлу після тесту
    }

    @Test
    void testRemoveNonExistentAnimal() {
        assertFalse(shelter.removeAnimal("Ghost"));
        assertEquals(2, shelter.getAnimals().size());
    }
}
