package net.majorkernelpanic.streaming;

public class DefaultSession {
    private static final DefaultSession ourInstance = new DefaultSession();

    public static DefaultSession getInstance()
    {
        return ourInstance;
    }

    private DefaultSession()
    {
    }
}
