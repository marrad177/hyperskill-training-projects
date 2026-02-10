// Include import statements
public class Main {

    enum Fonts {
        BOLD, LARGE, MEDIUM, SMALL, ITALIC, NORMAL
    }   

    EnumSet<Fonts> enumSet;

    public static void main(String[] args) {

        Main object = new Main();

        Scanner sc = new Scanner(System.in);
        String string = sc.nextLine().trim();

        // Change Code below this line
        switch (string) {
            case "Main-Heading":
                break;
            case "Quote":
                    break;
            case "Paragraph":
                    break;
            case "Sub-Heading":
                    break;
            case "Quote|BOLD":
                    break;
            default : System.out.println("ERROR");
                    break;
        }
        // Change the code above this line
        System.out.println(object.enumSet);
    }
}