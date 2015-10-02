public class MySubject extends MyObject {
    private String password;

    public MySubject(String name, String password) {
        super(name);
        this.password = password;
    }

    public MySubject(String name) {
        super(name);
        this.password = "";
    }

    public String getPassword() {
        return password;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        MySubject mySubject = (MySubject) o;

        if (!password.equals(mySubject.password)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }
}
