import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Shelter {
    private List<Animal> animals;

    public Shelter() {
        animals = new ArrayList<>();
    }

    // Додати тварину
    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    // Отримати список усіх тварин
    public List<Animal> getAnimals() {
        return animals;
    }

    // Видалити тварину за іменем
    public boolean removeAnimal(String name) {
        return animals.removeIf(animal -> animal.getName().equalsIgnoreCase(name));
    }

    // Знайти тварину за іменем
    public Animal findAnimal(String name) {
        for (Animal animal : animals) {
            if (animal.getName().equalsIgnoreCase(name)) {
                return animal;
            }
        }
        return null;
    }

    // Зберегти тварин у файл
    public void saveToFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Animal animal : animals) {
                writer.write(animal.getName() + "," + animal.getType() + "," + animal.getAge());
                writer.newLine();
            }
        }
    }

    // Завантажити тварин із файлу
    public void loadFromFile(String filename) throws IOException {
        animals.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    String type = parts[1];
                    int age = Integer.parseInt(parts[2]);
                    animals.add(new Animal(name, type, age));
                }
            }
        }
    }
}
