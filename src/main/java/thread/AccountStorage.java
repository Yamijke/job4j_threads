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
        return !accounts.containsKey(account.id())
                && accounts.putIfAbsent(account.id(), new Account(account.id(), account.amount())) == null;
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
        boolean upd;
        if (accFrom.isPresent() && accTo.isPresent() && accFrom.get().amount() >= amount) {
            int accFromTotal = accFrom.get().amount() - amount;
            int accToTotal = accTo.get().amount() + amount;
            accounts.put(fromId, new Account(fromId, accFromTotal));
            accounts.put(toId, new Account(toId, accToTotal));
            upd = true;
        } else {
            System.out.println("1 or both accounts do not exist");
            upd = false;
        }
        return upd;
    }
}
