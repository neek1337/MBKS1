import java.util.ArrayList;
import java.util.HashMap;

public class Table {

    private static ArrayList<MyObject> secrets = new ArrayList<MyObject>();
    private static Table table = new Table();

    private HashMap<MySubject, HashMap<MyObject, ArrayList<Access>>> subjectHashMap;
    private HashMap<MyObject, HashMap<MySubject, ArrayList<Access>>> objectHashMap;

    public static Table getTable() {
        return table;
    }

    public void print() {
        for (MySubject subject : subjectHashMap.keySet()) {
            System.out.println(subject + ":");
            for (MyObject object : subjectHashMap.get(subject).keySet()) {
                System.out.println("    " + object + "  " + subjectHashMap.get(subject).get(object));
            }
            System.out.println("-------------------------------");
        }
    }

    public Table() {
        this.subjectHashMap = new HashMap<MySubject, HashMap<MyObject, ArrayList<Access>>>();
        this.objectHashMap = new HashMap<MyObject, HashMap<MySubject, ArrayList<Access>>>();
    }

    public boolean checkAccess(MySubject subject, MyObject object, Access access) {
        boolean result = false;
        HashMap<MyObject, ArrayList<Access>> subj = subjectHashMap.get(subject);

        for (MyObject object1 : subj.keySet()) {
            if (object1.equals(object) && subj.get(object).contains(access)) {
                result = true;
            }
        }

        return result;

    }

    public MySubject findSubject(String name) {
        for (MySubject mySubject : subjectHashMap.keySet()) {
            if (mySubject.getName().equals(name)) return mySubject;
        }
        return null;
    }

    public MyObject findObject(String name) {
        for (MyObject myObject : objectHashMap.keySet()) {
            if (myObject.getName().equals(name)) return myObject;
        }
        return null;
    }

    public void enter(String subjectName, String objectName, Access access) {
        MyObject object = findObject(objectName);
        MySubject subject = findSubject(subjectName);
        HashMap<MyObject, ArrayList<Access>> subj = subjectHashMap.get(subject);
        if (subj.get(object) == null) {
            subj.put(object, new ArrayList<Access>());
        }
        subj.get(object).add(access);
        if (objectHashMap.get(object).get(subject) == null) {
            objectHashMap.get(object).put(subject, new ArrayList<Access>());
        }
        objectHashMap.get(object).get(subject).add(access);
    }

    public boolean delete(String subjectName, String objectName, Access access) {

        MySubject subject = findSubject(subjectName);
        MyObject object = findObject(objectName);

        HashMap<MyObject, ArrayList<Access>> subj = subjectHashMap.get(subject);
        subj.get(object).remove(access);
        objectHashMap.get(object).get(subject).remove(access);
        return false;
    }

    public boolean createObject(MySubject owner, String objectName) {


        MyObject object = new MyObject(objectName);

        objectHashMap.put(object, new HashMap<MySubject, ArrayList<Access>>());

        return false;
    }

    public boolean createSubject(String subjectName, String password) {
        MySubject subject = new MySubject(subjectName, password);

        subjectHashMap.put(subject, new HashMap<MyObject, ArrayList<Access>>());

        objectHashMap.put(subject, new HashMap<MySubject, ArrayList<Access>>());

        return false;
    }

    public boolean createSubject(String subjectName) {
        MySubject subject = new MySubject(subjectName);

        subjectHashMap.put(subject, new HashMap<MyObject, ArrayList<Access>>());

        objectHashMap.put(subject, new HashMap<MySubject, ArrayList<Access>>());

        return false;
    }


    public boolean destroyObject(MySubject owner, String objectName) {

        MyObject object = findObject(objectName);
        HashMap<MySubject, ArrayList<Access>> subj = objectHashMap.get(object);
        for (MySubject s : subj.keySet()) {
            HashMap<MyObject, ArrayList<Access>> obj = subjectHashMap.get(s);
            obj.remove(object);
        }
        objectHashMap.remove(object);
        return true;

    }

    public boolean destroySubject(MySubject owner, String subjectName) {

        MySubject subject = findSubject(subjectName);
        if (subjectHashMap.get(owner).get(subject) != null) subjectHashMap.get(owner).remove(subject);
        subjectHashMap.remove(subject);
        objectHashMap.remove(subject);
        return true;

    }

    public void createFile(String subjName, String fileName, String folderName) {
        MySubject subject = findSubject(subjName);
        MyObject folder = findObject(folderName);
        if (checkAccess(subject, folder, Access.fromString("w"))) {
            createObject(subject, fileName);
            enter(subjName, fileName, Access.fromString("o"));
            enter(subjName, fileName, Access.fromString("r"));
            enter(subjName, fileName, Access.fromString("w"));
            enter(subjName, fileName, Access.fromString("e"));
        } else System.out.println("Недостаточно прав");
        HashMap<MySubject, ArrayList<Access>> subjMap = objectHashMap.get(folder);
        for (MySubject mySubject : subjMap.keySet()) {
            for (Access access : subjMap.get(mySubject)) {
                if (!subject.equals(mySubject)) {
                    enter(mySubject.getName(), fileName, access);
                }
            }

        }
    }

    public void createFolder(String subjName, String folderName) {
        MySubject subject = findSubject(subjName);
        MyObject folder = findObject(folderName);
        createObject(subject, folderName);
        enter(subjName, folderName, Access.fromString("o"));
        enter(subjName, folderName, Access.fromString("r"));
        enter(subjName, folderName, Access.fromString("w"));
        enter(subjName, folderName, Access.fromString("e"));
    }

    public void executeFile(String subjName, String objName) {
        MySubject subject = findSubject(subjName);
        MyObject object = findObject(objName);
        if (checkAccess(subject, object, Access.fromString("r")) &&
                checkAccess(subject, object, Access.fromString("w")) &&
                checkAccess(subject, object, Access.fromString("e"))) {
            createSubject("subjT");
            HashMap<MyObject, ArrayList<Access>> s = subjectHashMap.get(subject);
            for (MyObject myObject : s.keySet()) {
                for (Access access : s.get(myObject)) {
                    enter("subjT", myObject.name, access);
                }
            }
        }
    }
}


