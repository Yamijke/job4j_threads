package thread;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), new Account(account.id(), account.amount())) == null;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), accounts.get(account.id()), account);
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        if (accounts.containsKey(id)) {
            return Optional.of(accounts.get(id));
        } else {
            System.out.println("This account doesn't exist");
            return Optional.empty();
        }
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        Optional<Account> accFrom = getById(fromId);
        Optional<Account> accTo = getById(toId);
        if (!accFrom.isPresent() || !accTo.isPresent()) {
            System.out.println("One of the accounts does not exist");
            return false;
        }
        Account accFr = accFrom.get();
        Account accT = accTo.get();
        if (accFr.amount() < amount) {
            System.out.println("Not enough funds for the transfer");
            return false;
        }
        Account newAccFrom = new Account(accFr.id(), accFr.amount() - amount);
        Account newAccTo = new Account(accT.id(), accT.amount() + amount);
        boolean updateFrom = update(newAccFrom);
        boolean updateTo = update(newAccTo);
        return updateFrom && updateTo;
    }
}
