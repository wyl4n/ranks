package com.rankup.empire.rank.feat.data.user;

import com.rankup.empire.rank.RankPlugin;
import com.rankup.empire.rank.feat.data.ranking.UserRanking;
import com.rankup.empire.rank.feat.database.MySQLProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class UserStorage {

    private final MySQLProvider service = RankPlugin.getInstance().getMySQLProvider();

    public void insert(User user) {
        try (Connection connection = service.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO `empire_rank` VALUES(?,?,?,?);")) {
                statement.setString(1, user.getName());
                statement.setInt(2, user.getRankPosition());
                statement.setDouble(3, user.getRank());
                statement.setInt(4, user.getPrestige());

                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void update(User user) {
        try (Connection connection = service.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE `empire_rank` SET rankPosition=?, rank=?, prestige=?  WHERE user_name=?;")) {
                statement.setInt(1, user.getRankPosition());
                statement.setDouble(2, user.getRank());
                statement.setInt(3, user.getPrestige());
                statement.setString(4, user.getName());

                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public User find(String id) {
        try (Connection connection = service.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `empire_rank` WHERE user_name=?;")) {
                statement.setString(1, id);
                try (ResultSet set = statement.executeQuery()) {
                    if (!set.next()) return null;

                    final String name = set.getString("user_name");
                    final int positionrank = set.getInt("rankPosition");
                    final double rank = set.getDouble("rank");
                    final int prestige = set.getInt("prestige");

                    return new User(
                            name,
                            positionrank,
                            rank,
                            prestige
                    );

                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public List<UserRanking> fetchRanking() {
        final List<UserRanking> uRanking = new LinkedList<>();

        try (Connection connection = service.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM empire_rank ORDER BY prestige;")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    final AtomicInteger position = new AtomicInteger(0);
                    while (resultSet.next()) {
                        uRanking.add(
                                UserRanking.of(
                                        resultSet.getString("user_name"),
                                        position.getAndIncrement(),
                                        resultSet.getInt("prestige")
                                )
                        );
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return uRanking;
    }
}
