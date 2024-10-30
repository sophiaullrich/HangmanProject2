package hangmanproject;

public class Name {
    private String name; // stores the user's name

    // constructor no longer prompts for the name using Scanner
    public Name(String name) {
        this.name = name.trim(); // store the entered name after trimming whitespace
    }

    // getter method to retrieve the user's name
    public String getName() {
        return name;
    }

    // setter method to update the user's name if needed
    public void setName(String name) {
        this.name = name;
    }
}
