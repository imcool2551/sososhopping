package com.sososhopping.domain.store.repository;

import com.sososhopping.entity.store.StoreType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class StoreSearchRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public StoreSearchRepository(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public Map<Long, Double> getNearStoreIdsByCategory(double lat, double lng, double radius,
                                                       StoreType storeType, int offset, int limit) {

        String sql = " " +
                "  SELECT store.store_id AS store_id, " +
                "         p.radius AS radius, " +
                "         p.distance_unit " +
                "                 * DEGREES(ACOS(LEAST(1.0, COS(RADIANS(p.latpoint)) " +
                "                 * COS(RADIANS(store.lat)) " +
                "                 * COS(RADIANS(p.longpoint - store.lng)) " +
                "                 + SIN(RADIANS(p.latpoint)) " +
                "                 * SIN(RADIANS(store.lat))))) AS distance " +
                "  FROM store " +
                "  JOIN ( " +
                "        SELECT  :lat  AS latpoint, :lng AS longpoint, " +
                "                :radius  AS radius, 111.045 AS distance_unit " +
                "  ) AS p  " +
                "  WHERE store.lat " +
                "         BETWEEN p.latpoint - (p.radius / p.distance_unit) " +
                "         AND p.latpoint + (p.radius / p.distance_unit) " +
                "     AND store.lng " +
                "         BETWEEN p.longpoint - (p.radius / (p.distance_unit * COS(RADIANS(p.latpoint)))) " +
                "         AND p.longpoint + (p.radius / (p.distance_unit * COS(RADIANS(p.latpoint)))) " +
                "     AND store.store_type LIKE :storeType " +
                "     AND store.is_open = 1" +
                "     AND store.store_status LIKE \"ACTIVE\"" +
                " HAVING distance <= radius " +
                " ORDER BY distance " +
                " LIMIT :limit OFFSET :offset";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("lat", lat)
                .addValue("lng", lng)
                .addValue("radius", radius)
                .addValue("storeType", storeType.toString())
                .addValue("limit", limit + 1)
                .addValue("offset", offset);
        List<Map<Long, Double>> list = jdbcTemplate.query(sql, parameters, new StoreMapper());

        return list.stream()
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<Long, Double> getNearStoreIdsByStoreName(double lat, double lng, double radius,
                                                       String storeName, int offset, int limit) {

        String sql = " " +
                "  SELECT store.store_id AS store_id, " +
                "         p.radius AS radius, " +
                "         p.distance_unit " +
                "                 * DEGREES(ACOS(LEAST(1.0, COS(RADIANS(p.latpoint)) " +
                "                 * COS(RADIANS(store.lat)) " +
                "                 * COS(RADIANS(p.longpoint - store.lng)) " +
                "                 + SIN(RADIANS(p.latpoint)) " +
                "                 * SIN(RADIANS(store.lat))))) AS distance " +
                "  FROM store " +
                "  JOIN ( " +
                "        SELECT  :lat  AS latpoint, :lng AS longpoint, " +
                "                :radius  AS radius, 111.045 AS distance_unit " +
                "  ) AS p  " +
                "  WHERE store.lat " +
                "         BETWEEN p.latpoint - (p.radius / p.distance_unit) " +
                "         AND p.latpoint + (p.radius / p.distance_unit) " +
                "     AND store.lng " +
                "         BETWEEN p.longpoint - (p.radius / (p.distance_unit * COS(RADIANS(p.latpoint)))) " +
                "         AND p.longpoint + (p.radius / (p.distance_unit * COS(RADIANS(p.latpoint)))) " +
                "     AND store.name LIKE :storeName " +
                "     AND store.is_open = 1" +
                "     AND store.store_status LIKE \"ACTIVE\"" +
                " HAVING distance <= radius " +
                " ORDER BY distance " +
                " LIMIT :limit OFFSET :offset";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("lat", lat)
                .addValue("lng", lng)
                .addValue("radius", radius)
                .addValue("storeName", "%" + storeName + "%")
                .addValue("limit", limit + 1)
                .addValue("offset", offset);
        List<Map<Long, Double>> list = jdbcTemplate.query(sql, parameters, new StoreMapper());

        return list.stream()
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<Long, Double> getNearStoreIdsByItemName(double lat, double lng, double radius,
                                                       String itemName, int offset, int limit) {

        String sql = "  " +
                " SELECT DISTINCT(store.store_id) AS store_id, distance " +
                "  FROM ( " +
                "   SELECT store.*, " +
                "      p.radius, " +
                "      p.distance_unit " +
                "           * DEGREES(ACOS(LEAST(1.0, COS(RADIANS(p.latpoint)) " +
                "           * COS(RADIANS(store.lat)) " +
                "           * COS(RADIANS(p.longpoint - store.lng)) " +
                "           + SIN(RADIANS(p.latpoint)) " +
                "           * SIN(RADIANS(store.lat))))) AS distance " +
                "    FROM store " +
                "    INNER JOIN (    " +
                "      SELECT  :lat  AS latpoint,  :lng AS longpoint, " +
                "          :radius AS radius,      111.045 AS distance_unit " +
                "    ) AS p " +
                "    WHERE store.lat " +
                "     BETWEEN p.latpoint  - (p.radius / p.distance_unit) " +
                "       AND p.latpoint  + (p.radius / p.distance_unit) " +
                "    AND store.lng " +
                "     BETWEEN p.longpoint - (p.radius / (p.distance_unit * COS(RADIANS(p.latpoint)))) " +
                "       AND p.longpoint + (p.radius / (p.distance_unit * COS(RADIANS(p.latpoint)))) " +
                "    AND store.store_status LIKE \"ACTIVE\"  " +
                "    AND store.is_open = 1 " +
                "   ) AS store " +
                " INNER JOIN item i " +
                "   ON i.store_id = store.store_id AND i.name LIKE :itemName " +
                " WHERE distance <= radius " +
                " ORDER BY distance " +
                " LIMIT :limit OFFSET :offset";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("lat", lat)
                .addValue("lng", lng)
                .addValue("radius", radius)
                .addValue("itemName", "%" + itemName + "%")
                .addValue("limit", limit + 1)
                .addValue("offset", offset);

        List<Map<Long, Double>> list = jdbcTemplate.query(sql, parameters, new StoreMapper());
        return list.stream()
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    static class StoreMapper implements RowMapper<Map<Long, Double>> {
        @Override
        public Map<Long, Double> mapRow(ResultSet rs, int rowNum) throws SQLException {
            Map<Long, Double> idToDistance = new HashMap<>();
            idToDistance.put(rs.getLong("store_id"), rs.getDouble("distance"));
            return idToDistance;
        }
    }
}


