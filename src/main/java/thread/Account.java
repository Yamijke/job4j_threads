package thread;

public record Account(int id, int amount) {
    public Account withAmount(int newAmount) {
        return new Account(this.id, newAmount);
    }
}
