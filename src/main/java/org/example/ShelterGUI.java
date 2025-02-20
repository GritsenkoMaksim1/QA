import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class ShelterGUI {
    private Shelter shelter = new Shelter(); // Логіка притулку
    private DefaultListModel<String> animalListModel = new DefaultListModel<>(); // Список для UI

    public ShelterGUI() {
        JFrame frame = new JFrame("Приют для животных");
        frame.setSize(600, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Панелі
        JPanel topPanel = createTopPanel(); // Панель для додавання тварин
        JScrollPane centerPanel = createCenterPanel(); // Список тварин
        JPanel bottomPanel = createBottomPanel(); // Панель для пошуку/видалення
        JPanel filePanel = createFilePanel(); // Панель для збереження/завантаження

        // Додати панелі до фрейму
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.add(filePanel, BorderLayout.EAST);

        frame.setVisible(true);
    }

    // Створення верхньої панелі (додавання)
    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        JTextField nameField = new JTextField(10);
        JTextField typeField = new JTextField(10);
        JTextField ageField = new JTextField(5);
        JButton addButton = new JButton("Добавить");

        panel.add(new JLabel("Имя:"));
        panel.add(nameField);
        panel.add(new JLabel("Тип:"));
        panel.add(typeField);
        panel.add(new JLabel("Возраст:"));
        panel.add(ageField);
        panel.add(addButton);

        addButton.addActionListener(e -> addAnimal(nameField, typeField, ageField));

        return panel;
    }

    // Створення центральної панелі (список тварин)
    private JScrollPane createCenterPanel() {
        JList<String> animalList = new JList<>(animalListModel);
        return new JScrollPane(animalList);
    }

    // Створення нижньої панелі (пошук/видалення)
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        JTextField searchField = new JTextField(10);
        JButton searchButton = new JButton("Найти");
        JButton removeButton = new JButton("Удалить");

        panel.add(new JLabel("Имя:"));
        panel.add(searchField);
        panel.add(searchButton);
        panel.add(removeButton);

        searchButton.addActionListener(e -> searchAnimal(searchField));
        removeButton.addActionListener(e -> removeAnimal(searchField));

        return panel;
    }

    // Створення панелі для збереження/завантаження
    private JPanel createFilePanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        JButton saveButton = new JButton("Сохранить");
        JButton loadButton = new JButton("Загрузить");

        panel.add(saveButton);
        panel.add(loadButton);

        saveButton.addActionListener(e -> saveToFile());
        loadButton.addActionListener(e -> loadFromFile());

        return panel;
    }

    // Функція для додавання тварини
    private void addAnimal(JTextField nameField, JTextField typeField, JTextField ageField) {
        String name = nameField.getText();
        String type = typeField.getText();
        int age;

        try {
            age = Integer.parseInt(ageField.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Возраст должен быть числом!");
            return;
        }

        shelter.addAnimal(new Animal(name, type, age));
        animalListModel.addElement("Имя: " + name + ", Тип: " + type + ", Возраст: " + age + " лет");

        nameField.setText("");
        typeField.setText("");
        ageField.setText("");
    }

    // Функція для пошуку тварини
    private void searchAnimal(JTextField searchField) {
        String name = searchField.getText();
        Animal foundAnimal = shelter.findAnimal(name);
        if (foundAnimal != null) {
            JOptionPane.showMessageDialog(null, "Найдено: " + foundAnimal);
        } else {
            JOptionPane.showMessageDialog(null, "Животное с таким именем не найдено.");
        }
    }

    // Функція для видалення тварини
    private void removeAnimal(JTextField searchField) {
        String name = searchField.getText();
        boolean removed = shelter.removeAnimal(name);

        if (removed) {
            for (int i = 0; i < animalListModel.size(); i++) {
                if (animalListModel.get(i).contains("Имя: " + name + ",")) {
                    animalListModel.remove(i);
                    break;
                }
            }
            JOptionPane.showMessageDialog(null, "Животное удалено!");
        } else {
            JOptionPane.showMessageDialog(null, "Животное с таким именем не найдено.");
        }
    }

    // Зберегти список тварин у файл
    private void saveToFile() {
        try {
            shelter.saveToFile("animals.txt");
            JOptionPane.showMessageDialog(null, "Данные сохранены в файл!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ошибка сохранения файла: " + ex.getMessage());
        }
    }

    // Завантажити список тварин із файлу
    private void loadFromFile() {
        try {
            shelter.loadFromFile("animals.txt");
            animalListModel.clear();
            for (Animal animal : shelter.getAnimals()) {
                animalListModel.addElement(animal.toString());
            }
            JOptionPane.showMessageDialog(null, "Данные загружены из файла!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ошибка загрузки файла: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ShelterGUI::new);
    }
}
