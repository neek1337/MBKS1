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
                Table.getTable().createSubject(currentUser, subjName, password);
                System.out.println("Создан субъект " + subjName);
                continue;
            }


            if (s.equals("login")) {
                String login = scanner.next();
                String password = scanner.next();
                try {
                    MySubject user = Table.getTable().findSubject(login);
                    if (user.getPassword().equals(password)) {
                        currentUser = user;
                        System.out.println("Добро пожаловать, " + login);
                    } else {
                        System.out.println("Ошибка авторизации.");
                    }
                } catch (IllegalAccessException e) {
                    System.out.println(e.getMessage());
                    ;
                }
                continue;
            }


            if (s.equals("createObject")) {
                String name = scanner.next();
                if (currentUser != null) {
                    Table.getTable().createObject(currentUser, name);
                    System.out.println("Cоздан объект " + name);
                } else {
                    System.out.println("Необходимо авторизоваться.");
                }
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
                if (currentUser != null) {
                    try {
                        Table.getTable().enter(currentUser, subjName, objName, access);
                    } catch (IllegalAccessException e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    System.out.println("Необходимо авторизоваться.");
                }
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
                if (currentUser != null) {
                    try {
                        Table.getTable().delete(currentUser, subjName, objName, access);
                    } catch (IllegalAccessException e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    System.out.println("Необходимо авторизоваться.");
                }
                continue;
            }


            if (s.equals("destroyObject")) {
                String objName = scanner.next();
                if (currentUser != null) {
                    Table.getTable().destroyObject(currentUser, objName);
                } else {
                    System.out.println("Необходимо авторизоваться.");
                }
                continue;
            }

            if (s.equals("destroySubject")) {
                String subjName = scanner.next();
                if (currentUser != null) {
                    Table.getTable().destroySubject(currentUser, subjName);
                } else {
                    System.out.println("Необходимо авторизоваться.");
                }
                continue;
            }
            scanner.nextLine();
            System.out.println("Введена неизвестная команда.");
        }


    }
}
