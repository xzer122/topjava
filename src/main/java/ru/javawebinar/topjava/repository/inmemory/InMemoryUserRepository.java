package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractBaseEntity;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

    private final AtomicInteger counter = new AtomicInteger();
    private final Map<Integer, User> repository = new ConcurrentHashMap<>();

    {
        save(new User(1, "Rick Sanchez", "rick@hmail.com", "mortyasshole123", Role.ADMIN));
        save(new User(2, "Morty Smith", "coolmorty@gmail.com", "password", Role.USER));
    }


    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            return user;
        }
        return repository.computeIfPresent(user.getId(), (id, oldUser) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        List<User> userList = new ArrayList<>(repository.values());
        userList.sort(Comparator.comparing(AbstractNamedEntity::getName));
        Map<String, List<User>> groupedUsersByNames = userList.stream().collect(Collectors.groupingBy(AbstractNamedEntity::getName));
        for (Map.Entry<String, List<User>> entry : groupedUsersByNames.entrySet()) {
            List<User> sortedUsersById = entry.getValue();
            sortedUsersById.sort(Comparator.comparing(AbstractBaseEntity::getId));
            int firstIndex = getFirstIndex(userList, sortedUsersById);
            int lastIndex = getLastIndex(userList, sortedUsersById);
            setUsersFormTo(userList, firstIndex, lastIndex, sortedUsersById);
        }
        return userList;
    }

    private int getFirstIndex(List<User> mainList, List<User> listWithParameters) {
        int index = mainList.size();
        for (User user : listWithParameters) {
            int indexOfUser = mainList.indexOf(user);
            index = Math.min(index, indexOfUser);
        }
        return index;
    }

    private int getLastIndex(List<User> mainList, List<User> parameters) {
        int index = -1;
        for (User user : parameters) {
            int indexOfUser = mainList.lastIndexOf(user);
            index = Math.max(index, indexOfUser);
        }
        return index;
    }

    private void setUsersFormTo(List<User> users, int indexFrom, int indexTo, List<User> listFrom) {
        int index = indexFrom;
        while (index < indexTo) {
            users.set(index, listFrom.get(index - indexFrom));
            index++;
        }
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        User user = null;
        for (User currentUser : repository.values())
            if (currentUser.getEmail().equals(email)) {
                user = currentUser;
            }
        return user;
    }
}
