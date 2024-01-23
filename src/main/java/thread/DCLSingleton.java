package thread;

public final class DCLSingleton {

    private static volatile DCLSingleton instance;

    public static DCLSingleton getInstance() {
        DCLSingleton singleton = instance;
        if (instance == null) {
            synchronized (DCLSingleton.class) {
                singleton = instance;
                if (singleton == null) {
                    instance = singleton = new DCLSingleton();
                }
            }
        }
        return singleton;
    }

    private DCLSingleton() {
    }
}
