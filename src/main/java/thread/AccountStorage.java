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
        boolean add;
        if (!accounts.containsKey(account.id())) {
            accounts.put(account.id(), new Account(account.id(), account.amount()));
            add = true;
        } else {
            add = false;
            System.out.println("This Account already exist");
        }
        return add;
    }

    public synchronized boolean update(Account account) {
        boolean upd;
        if (accounts.containsKey(account.id())) {
            accounts.put(account.id(), account);
            upd = true;
        } else {
            upd = false;
        }
        return upd;
    }

    public synchronized void delete(int id) {
        if (accounts.containsKey(id)) {
            accounts.remove(id);
        } else {
            System.out.println("This account doesn't exist");
        }
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
        if (accFrom.isPresent() && accTo.isPresent()) {
            Account accFr = accFrom.get();
            Account accT = accTo.get();
            if (accFrom.get().amount() < amount) {
                System.out.println("The amount of money is not enough for transfer");
                upd = false;
            } else {
                Account updAccFrom = accFr.withAmount(accFr.amount() - amount);
                Account updAccTo = accT.withAmount(accT.amount() + amount);
                accounts.put(fromId, updAccFrom);
                accounts.put(toId, updAccTo);
                upd = true;
            }
        } else {
            System.out.println("1 or both accounts do not exist");
            upd = false;
        }
        return upd;
    }
}
