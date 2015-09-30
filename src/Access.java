enum Access {
    Write, Read, Owner;

    public static Access fromString(String s) {
        Access result = null;
        if (s.equals("w")) {
            result = Access.Write;
            return result;
        }
        if (s.equals("r")) {
            result = Access.Read;
            return result;
        }
        if (s.equals("o")) {
            result = Access.Owner;
            return result;
        }
        return null;
    }
}
