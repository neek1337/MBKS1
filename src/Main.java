import java.util.Scanner;

public class Main {
    public static MySubject currentUser;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String s = scanner.next();
            if (s.equals("createSubject")) {
                String subjName = scanner.next();
                String password = scanner.next();
                Table.getTable().createSubject(subjName, password);
                System.out.println("Создан субъект " + subjName);
                continue;
            }


            if (s.equals("createObject")) {

                String userName = scanner.next();
                String objname = scanner.next();
                Table.getTable().createObject(Table.getTable().findSubject(userName), objname);
                System.out.println("Cоздан объект " + objname);

                continue;
            }


            if (s.equals("enter")) {
                String subjName = scanner.next();
                String objName = scanner.next();
                String ac = scanner.next();
                Access access = Access.fromString(ac);
                if (access == null) {
                    System.out.println("Недопустимое значение права доступа.");
                    continue;
                }

                Table.getTable().enter(subjName, objName, access);

                continue;
            }

            if (s.equals("createSubject1")) {
                String subjName = scanner.next();
                String password = scanner.next();
                boolean isSecret = scanner.nextBoolean();
                Table.getTable().createSubject1(subjName,isSecret);
                System.out.println("Создан субъект " + subjName);
                continue;
            }


            if (s.equals("createObject1")) {

                String userName = scanner.next();
                String objname = scanner.next();
                boolean isSecret = scanner.nextBoolean();
                Table.getTable().createObject1(Table.getTable().findSubject(userName), objname, isSecret);
                System.out.println("Cоздан объект " + objname);

                continue;
            }






            if (s.equals("delete")) {
                String subjName = scanner.next();
                String objName = scanner.next();
                String ac = scanner.next();
                Access access = Access.fromString(ac);
                if (access == null) {
                    System.out.println("Недопустимое значение права доступа.");
                    continue;
                }

                Table.getTable().delete(subjName, objName, access);

                continue;
            }


            if (s.equals("destroyObject")) {
                String objName = scanner.next();

                Table.getTable().destroyObject(currentUser, objName);

                continue;
            }

            if (s.equals("destroySubject")) {
                String subjName = scanner.next();
                Table.getTable().destroySubject(currentUser, subjName);

                continue;
            }

            if (s.equals("print")) {
                Table.getTable().print();
                continue;
            }

            if (s.equals("createFile")) {
                String subjectName = scanner.next();
                String filename = scanner.next();
                String foldername = scanner.next();
                Table.getTable().createFile(subjectName, filename, foldername);
                System.out.println("Создан файл " + filename + " в паке " + foldername);
                continue;
            }
            if (s.equals("createFile1")) {
                String subjectName = scanner.next();
                String filename = scanner.next();
                String foldername = scanner.next();
                Table.getTable().createFile1(subjectName, filename, foldername);
                System.out.println("Создан файл " + filename + " в паке " + foldername);
                continue;
            }

            if (s.equals("createFolder")) {
                String subjName = scanner.next();
                String folderName = scanner.next();
                Table.getTable().createFolder(subjName, folderName);
                System.out.println("Создана папка " + folderName);
                continue;
            }
            if (s.equals("createFolder1")) {
                String subjName = scanner.next();
                String folderName = scanner.next();
                boolean isSecret = scanner.nextBoolean();
                Table.getTable().createFolder1(subjName, folderName, isSecret);
                System.out.println("Создана папка " + folderName);
                continue;
            }

            if (s.equals("executeFile")) {
                String subjName = scanner.next();
                String objName = scanner.next();
                Table.getTable().executeFile(subjName, objName);
                continue;
            }
            if (s.equals("executeFile1")) {
                String subjName = scanner.next();
                String objName = scanner.next();
                Table.getTable().executeFile1(subjName, objName);
                continue;
            }
            if (s.equals("printInfo")) {
                String name = scanner.next();
                System.out.println(Table.getTable().findObject(name).getName());
                continue;
            }

            scanner.nextLine();
            System.out.println("Введена неизвестная команда.");
        }


    }
}
