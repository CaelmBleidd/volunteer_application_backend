package ru.ifmo.volunteer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import ru.ifmo.volunteer.model.Achievement;
import ru.ifmo.volunteer.repository.AchievementRepository;

@Service
public class AchievementService {
    private final AchievementRepository achievementRepository;

    public AchievementService(final AchievementRepository achievementRepository) {
        this.achievementRepository = achievementRepository;
    }

    public List<Achievement> getAllByUserId(final long userId) {
        return achievementRepository.getAllByUserId(userId);
    }

    public void awardUser(final long userId, final long achievementId) {
        final long date = System.currentTimeMillis();
        achievementRepository.awardUser(userId, achievementId, date);
    }

    public void takeAway(final long userId, final long achievementId) {
        achievementRepository.takeAway(userId, achievementId);
    }
}
