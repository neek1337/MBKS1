import java.util.HashMap;

public class Table {

    private static Table table = new Table();

    private HashMap<MySubject, HashMap<MyObject, Access>> smap;
    private HashMap<MyObject, HashMap<MySubject, Access>> omap;

    public static Table getTable() {
        return table;
    }

    public Table() {
        this.smap = new HashMap<MySubject, HashMap<MyObject, Access>>();
        this.omap = new HashMap<MyObject, HashMap<MySubject, Access>>();
    }

    public Access checkAccess(MySubject subject, MyObject object) throws IllegalAccessException {
        HashMap<MyObject, Access> subj = smap.get(subject);
        if (subj != null) {
            Access result = subj.get(object);
            if (result == null) throw new IllegalAccessException("Ошибка доступа.");
            return result;
        } else throw new IllegalAccessException("Данный субъект не зарегестрирован в системе.");
    }

    public MySubject findSubject(String name) throws IllegalAccessException {
        for (MySubject mySubject : smap.keySet()) {
            if (mySubject.getName().equals(name)) return mySubject;
        }
        throw new IllegalAccessException("Данный субъект не зарегестрирован в системе.");
    }

    public MyObject findObject(String name) throws IllegalAccessException {
        for (MyObject myObject : omap.keySet()) {
            if (myObject.getName().equals(name)) return myObject;
        }
        throw new IllegalAccessException("Данный объект не зарегестрирован в системе.");
    }

    public boolean enter(MySubject owner, String subjectName, String objectName, Access access) throws IllegalAccessException {
        try {
            MyObject object = findObject(objectName);

            MySubject subject = findSubject(subjectName);
            if (checkAccess(owner, object) == Access.Owner) {
                HashMap<MyObject, Access> subj = smap.get(subject);
                subj.put(object, access);
                omap.get(object).put(subject, access);
                return true;
            } else throw new IllegalAccessException("Ошибка доступа.");
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
            ;
        }
        return false;
    }

    public boolean delete(MySubject owner, String subjectName, String objectName, Access access) throws IllegalAccessException {
        try {
            MySubject subject = findSubject(subjectName);
            MyObject object = findObject(objectName);
            if (checkAccess(owner, object) == Access.Owner) {
                HashMap<MyObject, Access> subj = smap.get(subject);
                subj.remove(object, access);
                omap.get(object).remove(subject, access);
            } else throw new IllegalAccessException("Ошибка доступа.");

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            ;
        }
        return false;
    }

    public boolean createObject(MySubject owner, String objectName) {
        try {
            MyObject object = new MyObject(objectName);
            HashMap<MyObject, Access> subj = smap.get(owner);
            if (subj != null) {
                if (!omap.containsKey(object)) {
                    subj.put(object, Access.Owner);
                    omap.put(object, new HashMap<MySubject, Access>());
                    omap.get(object).put(owner, Access.Owner);
                } else throw new IllegalAccessException("Данный объект уже зарегестрирован в системе.");

            } else
                throw new IllegalAccessException("Данный субъект не зарегестрирован в системе.");
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
            ;
        }
        return false;
    }

    public boolean createSubject(MySubject owner, String subjectName, String password) {
        try {
            MySubject subject = new MySubject(subjectName, password);

            if (!smap.containsKey(subject)) {
                smap.put(subject, new HashMap<MyObject, Access>());
                smap.get(subject).put(subject, Access.Owner);
                if (owner != null) smap.get(owner).put(subject, Access.Owner);
                omap.put(subject, new HashMap<MySubject, Access>());
                omap.get(subject).put(subject, Access.Owner);
                if (owner != null) omap.get(owner).put(subject, Access.Owner);
                return true;
            } else
                throw new IllegalAccessException("Данный субъект уже зарегестрирован в системе.");
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
            ;
        }
        return false;
    }

    public boolean destroyObject(MySubject owner, String objectName) {
        try {
            MyObject object = findObject(objectName);
            if (checkAccess(owner, object) == Access.Owner) {
                HashMap<MySubject, Access> subj = omap.get(object);
                for (MySubject s : subj.keySet()) {
                    HashMap<MyObject, Access> obj = smap.get(s);
                    obj.remove(object);
                }
                omap.remove(object);
                return true;
            } else throw new IllegalAccessException("Недостаточно прав для удаления объекта.");
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
            ;
        }
        return false;
    }

    public boolean destroySubject(MySubject owner, String subjectName) {
        try {
            MySubject subject = findSubject(subjectName);
            if (checkAccess(owner, subject) == Access.Owner) {
                if (smap.get(owner).get(subject) != null) smap.get(owner).remove(subject);
                smap.remove(subject);
                omap.remove(subject);
                return true;
            } else throw new IllegalAccessException("Недостаточно прав для удаления субъекта.");
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
            ;
        }
        return false;
    }


}


