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
        // Ініціалізація об'єкта Shelter та додавання двох тварин перед кожним тестом
        shelter = new Shelter();
        animal1 = new Animal("Buddy", "Dog", 3);
        animal2 = new Animal("Whiskers", "Cat", 2);
        shelter.addAnimal(animal1);
        shelter.addAnimal(animal2);
    }

    @Test
    void testAddAnimal() {
        // Тестуємо додавання нової тварини до притулку
        Animal newAnimal = new Animal("Charlie", "Parrot", 1);
        shelter.addAnimal(newAnimal);

        // Перевіряємо, що кількість тварин збільшилась до 3
        assertEquals(3, shelter.getAnimals().size());

        // Перевіряємо, що нова тварина присутня в списку тварин
        assertTrue(shelter.getAnimals().contains(newAnimal));
    }

    @Test
    void testRemoveAnimal() {
        // Тестуємо видалення тварини за ім'ям
        assertTrue(shelter.removeAnimal("Buddy"));

        // Перевіряємо, що кількість тварин зменшилась до 1
        assertEquals(1, shelter.getAnimals().size());

        // Перевіряємо, що тварина Buddy більше не є в списку
        assertFalse(shelter.getAnimals().contains(animal1));
    }

    @Test
    void testFindAnimal() {
        // Тестуємо пошук тварини за ім'ям
        Animal found = shelter.findAnimal("Whiskers");

        // Перевіряємо, що знайдена тварина не є null
        assertNotNull(found);

        // Перевіряємо, що тип знайденої тварини є "Cat"
        assertEquals("Cat", found.getType());
    }

    @Test
    void testSaveAndLoadFromFile() throws IOException {
        // Тестуємо збереження тварин у файл та їх завантаження з файлу
        String filename = "test_animals.txt";
        shelter.saveToFile(filename); // Зберігаємо поточний стан притулку у файл

        Shelter newShelter = new Shelter();
        newShelter.loadFromFile(filename); // Завантажуємо тварин з файлу
        List<Animal> loadedAnimals = newShelter.getAnimals();

        // Перевіряємо, що кількість завантажених тварин дорівнює 2
        assertEquals(2, loadedAnimals.size());
        // Перевіряємо, що імена завантажених тварин відповідають очікуваним
        assertEquals("Buddy", loadedAnimals.get(0).getName());
        assertEquals("Whiskers", loadedAnimals.get(1).getName());

        // Очищаємо файл після тесту
        new File(filename).delete();
    }

    @Test
    void testRemoveNonExistentAnimal() {
        // Тестуємо спробу видалення тварини, якої не існує
        assertFalse(shelter.removeAnimal("Ghost"));

        // Перевіряємо, що кількість тварин залишилась незмінною
        assertEquals(2, shelter.getAnimals().size());
    }
}
