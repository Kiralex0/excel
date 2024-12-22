package com.example.excel.service;

import com.example.excel.model.UserState;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserStateService {

    private final Map<Long, UserState> userStates = new HashMap<>();
    private final Map<Long, Long> userPointIds = new HashMap<>();

    public UserState getUserState(Long userId) {
        return userStates.getOrDefault(userId, UserState.DEFAULT);
    }

    public void setUserState(Long userId, UserState state) {
        userStates.put(userId, state);
    }

    public void clearUserState(Long userId) {
        userStates.remove(userId);
    }
    public Long getPointId(Long userId) {
        return userPointIds.get(userId);
    }

    public void setPointId(Long userId, Long pointId) {
        userPointIds.put(userId, pointId);
    }
}
