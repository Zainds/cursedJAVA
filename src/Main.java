import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;

public class Main {
    public static void pressEnterToContinue()
    {
        System.out.println("Press Enter key to continue...");
        try
        {
            System.in.read();
        }
        catch(Exception e)
        {}
    }
    public final static void clearConsole()
    {
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            //  Handle any exceptions.
        }
    }
    static String[] days = {"Понедельник", "Вторник", "Среда", "Четверг", "Пятница"};

    static class WorkshopBuilder {


        private String name;
        private String workshopName;
        private int[] detailsCreated = new int[5];

        public WorkshopBuilder(String n, String wname, int[] d) {
            name = n;
            workshopName = wname;
            for (int i = 0; i < 5; i++) {

                detailsCreated[i] = d[i];
            }
        }

        public void setName(String _name) { name = _name; };
        public void setWorkshopName(String _wName) { workshopName = _wName; };
        public void setDetailsCreated(int[] _detailsCr) { for (int i = 0; i < 5; i++) detailsCreated[i] = _detailsCr[i]; };
        public String getName() { return name; };
        public String getWorkshopName() { return workshopName; };
        public int[] getDetailsCreated() { return detailsCreated; };
        int getAllDetailsCreated() {
            int detailSum = 0;
            for (int x : detailsCreated) {
                detailSum += x;
            }
            return detailSum;
        };

        public void printBuilder() {

            System.out.print("ФИО: " + name + " \t " + "ЦЕХ: " + workshopName +
                    " \t " + "Изготовлено деталей: " + detailsCreated[0] + " "
                    + detailsCreated[1] + " " + detailsCreated[2] + " "
                    + detailsCreated[3] + " " + detailsCreated[4] + "\n");
        }
        public void printBuilderMaxDetail() {
            int day = -1, details = -999;
            for (int i = 0; i < 5; i++) {
                if (detailsCreated[i] > details) {
                    details = detailsCreated[i];
                    day = i;
                }
            }
            System.out.print("ФИО: " + name + " \t " + days[day] + " " + details + " д" + "\n");
        }

    };

    public static void updateInputFile(List<WorkshopBuilder> workers) throws FileNotFoundException {

        //ofstream fout("input.txt");
        try(FileWriter writer = new FileWriter("input.txt", false))
        {
            for (int i = 0; i < workers.size(); i++) {
                WorkshopBuilder x = workers.get(i);
                int[] dt = x.getDetailsCreated();
                String txt = x.getName() + " " + x.getWorkshopName() + " " + dt[0] + " " + dt[1]
                        + " " + dt[2] + " " + dt[3] + " " + dt[4];
                writer.write(txt);
                if (i != workers.size() - 1) writer.append("\n");
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }

    }

    public static void readWorkersFromConsole(List<WorkshopBuilder> workers) throws FileNotFoundException {

        Scanner console = new Scanner(System.in);
        Scanner console1 = new Scanner(System.in);
        Scanner console2 = new Scanner(System.in);

        int count = 0;
        String name, workshopName;
        int[] detailsCreated = new int[5];
        System.out.print("Введите количество рабочих: "); count = console.nextInt();



        for (int k = 0; k < count; k++) {

            System.out.print("Введите имя: "); name = console1.nextLine();
            //console.nextLine();
            System.out.print("Введите цех: "); workshopName = console2.nextLine();

            System.out.print("Введите количество деталей по дням(через пробел) : ");

            for (int i = 0; i < 5; i++) {
                int x;
                x = console.nextInt(); System.out.print(" ");
                detailsCreated[i] = x;
            }
            System.out.print("\n");
            WorkshopBuilder WB = new WorkshopBuilder(name, workshopName, detailsCreated);
            workers.add(WB);
        }
        updateInputFile(workers);
    }

    public static void readWorkersFromFile(List<WorkshopBuilder> workers) throws FileNotFoundException {
        File file = new File("input.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String line;
            while ((line = br.readLine()) != null) {
                String name = "";
                String wname = "";
                String mynum = "";
                int[] dArr = new int[5];
                int arrPos = 0;
                int spaceCounter = 0;

                for (int i = 0; i < line.length(); i++)
                {
                    if (line.charAt(i) == ' ') spaceCounter += 1;
                    if (spaceCounter <= 2)name += line.charAt(i);
                    if (spaceCounter > 2 && spaceCounter < 4 && line.charAt(i) != ' ')wname += line.charAt(i);
                    if (spaceCounter >= 4) {
                        if (line.charAt(i) != ' ') {
                            mynum += line.charAt(i);

                        }
                        else {
                            if (spaceCounter != 4) {

                                dArr[arrPos] = Integer.parseInt(mynum);
                                arrPos++;
                                mynum = "";
                            }
                        }
                        if (i == line.length())dArr[arrPos] = Integer.parseInt(mynum);
                    }
                }
                WorkshopBuilder WB = new WorkshopBuilder(name, wname, dArr);
                workers.add(WB);
            }
        } catch (IOException e) {
            System.out.print("Файл не найден. Создаем новый файл\n\n");
            pressEnterToContinue();
            readWorkersFromConsole(workers);
            e.printStackTrace();
        }


    }

    public static void writeWorkersFromFile(List<WorkshopBuilder> workers) {
        //ofstream fout("output.txt");
        try(FileWriter writer = new FileWriter("output.txt", false))
        {
            for (int i = 0; i < workers.size(); i++) {
                WorkshopBuilder x = workers.get(i);
                String txt = x.getName() + " " + x.getAllDetailsCreated();
                writer.write(txt);
                if (i != workers.size() - 1) writer.append("\n");
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }


    }
    public static void printAllBuilders(List<WorkshopBuilder> workers) {
        for (WorkshopBuilder x : workers) {
            x.printBuilder();
        }
    }
    //Вывести всех рабочих заданного цеха и день недели в который он собрал наибольшее кол-во деталей
    public static void printFromCurWS(List<WorkshopBuilder> workers) {
        String workshopName;
        Scanner console = new Scanner(System.in);
        System.out.print("Введите цех: "); workshopName = console.nextLine();
        for (WorkshopBuilder x : workers) {
            if (x.getWorkshopName().equals(workshopName)) {
                x.printBuilderMaxDetail();
            }
        }
    }
    public static void printAllBuildersOutputFormat(List<WorkshopBuilder> workers) {
        for (WorkshopBuilder x : workers) {
            System.out.print(x.getName() + " " + x.getAllDetailsCreated() + "\n");
        }
        writeWorkersFromFile(workers);
    }
    public void printAllBuildersFromInputFile() throws FileNotFoundException {
        List<WorkshopBuilder> localWorkers = new ArrayList<>();
        readWorkersFromFile(localWorkers);
        printAllBuilders(localWorkers);
    }

    public static void addWorker(List<WorkshopBuilder> workers) {
        String name;
        String workshopName;
        int[] detailsCreated = new int[5];
        Scanner console = new Scanner(System.in);
        System.out.print("Введите имя: "); name = console.nextLine();
        System.out.print("Введите цех: "); workshopName = console.nextLine();
        System.out.print("Введите количество деталей по дням(через пробел): ");
        for (int i = 0; i < 5; i++) {
            int x;
            x = console.nextInt(); System.out.print(" ");
            detailsCreated[i] = x;
        }
        WorkshopBuilder WB = new WorkshopBuilder(name, workshopName, detailsCreated);
        workers.add(WB);
        try(FileWriter writer = new FileWriter("input.txt", true))
        {
            String txt = "\n" + name + " " + workshopName + " " + detailsCreated[0] + " " + detailsCreated[1] + " "
                    + detailsCreated[2] + " " + detailsCreated[3] + " " + detailsCreated[4];
            writer.append(txt);

        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }

    }

    public static void deleteWorkerByFio(List<WorkshopBuilder> workers) throws FileNotFoundException {
        String name;
        Scanner console = new Scanner(System.in);
        int flag = 0;
        System.out.print("Введите ФИО для удаления работника: "); name = console.nextLine();
        for (int i = 0; i < workers.size(); i++) {

            if (workers.get(i).getName().equals(name)) {
                flag = 1;
                workers.remove(i);
                i--;
            }
        }
        if (flag == 1) {
            updateInputFile(workers);
        }
        else {
            System.out.print("Рабочий не найден\n");
            pressEnterToContinue();
        }

    }
    public static void renameWorkerWorkshopByFio(List<WorkshopBuilder> workers) throws FileNotFoundException {
        int flag = 0;
        Scanner console = new Scanner(System.in);
        String name, workshopName;
        System.out.print("Введите ФИО работника для смены цеха: "); name = console.nextLine();
        System.out.print("Введите новый цех: "); workshopName = console.nextLine();
        for (int i = 0; i < workers.size(); i++) {
            if (workers.get(i).getName().equals(name)) {
                flag = 1;
                workers.get(i).setWorkshopName(workshopName);
            }
        }
        if (flag == 1) {
            updateInputFile(workers);
        }
        else {
            System.out.print("Рабочий не найден\n");
            pressEnterToContinue();
        }
    }
    public static void showMenu(List<WorkshopBuilder> workers) throws IOException {
        boolean isScanned = false;
        while (true) {
            if (!isScanned) {
                System.out.print("\n" +
                        "1. Считать содержимое из файла\n" +
                        "2. Заполнить рабочих через консоль\n");
            }

            else {
                System.out.print("\n" +
                        "3. Выдать на экран содержимое файла\n" +
                        "4. Выдать на экран список рабочих заданного цеха\n" +
                        "5. Распечатать файл упрощенной структуры\n" +
                        "6. Добавить данные нового рабочего\n" +
                        "7. Удалить все элементы записи определённого рабочего\n" +
                        "8. Изменить цех у определённого рабочего\n" +
                        "н. Назад\n" +
                        "в. Выход\n");

            }

            Scanner scanner = new Scanner(System.in);
            char choice = scanner.next().charAt(0); //charAt() method returns the character at the specified index in a string.
            // The index of the first character is 0, the second character is 1, and so on.
            switch (choice) {
                case '1':
                    readWorkersFromFile(workers);
                    clearConsole();;
                    isScanned = true;
                    break;
                case '2':
                    clearConsole();
                    readWorkersFromConsole(workers);
                    isScanned = true;
                    break;
                case '3':
                    clearConsole();
                    if (isScanned) printAllBuilders(workers);
                    break;
                case '4':
                    clearConsole();
                    if (isScanned) printFromCurWS(workers);
                    break;
                case '5':
                    clearConsole();
                    if (isScanned) printAllBuildersOutputFormat(workers);
                    break;
                case '6':
                    if (isScanned) addWorker(workers);
                    clearConsole();
                    break;
                case '7':
                    if (isScanned) deleteWorkerByFio(workers);
                    clearConsole();
                    break;
                case '8':
                    if (isScanned) renameWorkerWorkshopByFio(workers);
                    clearConsole();
                    break;
                case 'в':
                    System.exit(0);
                    break;
                case 'н':
                    clearConsole();
                    isScanned = false;
                    workers.clear();
                    break;
                default:
                    System.out.println( "Недопустимое значение!\n" );
                    break;
            }
        }

    }
    public static void main(String[] args) throws IOException {

        List<WorkshopBuilder> workers = new ArrayList<>(); ;

        showMenu(workers);
    }
}