package com.sososhopping.repository.store;

import com.sososhopping.entity.owner.StoreType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class JdbcStoreRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final EntityManager em;

    public JdbcStoreRepository(DataSource dataSource, EntityManager em) {
        this.em = em;
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public Map<Long, Double> getNearStoreIdsByCategory(
            Double lat,
            Double lng,
            Double radius,
            StoreType storeType,
            Integer offset
    ) {
        String sql = "SELECT store.store_id AS store_id,\n" +
                "        p.radius AS radius,\n" +
                "        p.distance_unit\n" +
                "                 * DEGREES(ACOS(LEAST(1.0, COS(RADIANS(p.latpoint))\n" +
                "                 * COS(RADIANS(store.lat))\n" +
                "                 * COS(RADIANS(p.longpoint - store.lng))\n" +
                "                 + SIN(RADIANS(p.latpoint))\n" +
                "                 * SIN(RADIANS(store.lat))))) AS distance\n" +
                "  FROM store\n" +
                "  JOIN (   /* these are the query parameters */\n" +
                "        SELECT  :lat  AS latpoint, :lng AS longpoint,\n" +
                "                :radius  AS radius,    111.045 AS distance_unit\n" +
                "  ) AS p \n" +
                "  WHERE store.lat\n" +
                "         BETWEEN p.latpoint  - (p.radius / p.distance_unit)\n" +
                "         AND p.latpoint  + (p.radius / p.distance_unit)\n" +
                "     AND store.lng\n" +
                "         BETWEEN p.longpoint - (p.radius / (p.distance_unit * COS(RADIANS(p.latpoint))))\n" +
                "         AND p.longpoint + (p.radius / (p.distance_unit * COS(RADIANS(p.latpoint))))\n" +
                "     AND store.store_type LIKE :storeType\n" +
                "     AND store.business_status = 1" +
                "    AND store.store_status LIKE \"ACTIVE\"\n" +
                " HAVING distance <= radius\n" +
                " ORDER BY distance\n" +
                " LIMIT :limit OFFSET :offset";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("lat", lat)
                .addValue("lng", lng)
                .addValue("radius", radius)
                .addValue("storeType", storeType.toString())
                .addValue("limit", 10 + 1)
                .addValue("offset", offset);
        List<Map<Long, Double>> list = jdbcTemplate.query(sql, parameters, new StoreMapper());

        return list.stream()
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<Long, Double> getLocalCurrencyStores(
            Double lat,
            Double lng,
            Double radius,
            Integer offset
    ) {
        String sql = "SELECT store.store_id AS store_id,\n" +
                "        p.radius AS radius,\n" +
                "        p.distance_unit\n" +
                "                 * DEGREES(ACOS(LEAST(1.0, COS(RADIANS(p.latpoint))\n" +
                "                 * COS(RADIANS(store.lat))\n" +
                "                 * COS(RADIANS(p.longpoint - store.lng))\n" +
                "                 + SIN(RADIANS(p.latpoint))\n" +
                "                 * SIN(RADIANS(store.lat))))) AS distance\n" +
                "  FROM store\n" +
                "  JOIN (   /* these are the query parameters */\n" +
                "        SELECT  :lat  AS latpoint, :lng AS longpoint,\n" +
                "                :radius  AS radius,    111.045 AS distance_unit\n" +
                "  ) AS p \n" +
                "  WHERE store.lat\n" +
                "         BETWEEN p.latpoint  - (p.radius / p.distance_unit)\n" +
                "         AND p.latpoint  + (p.radius / p.distance_unit)\n" +
                "     AND store.lng\n" +
                "         BETWEEN p.longpoint - (p.radius / (p.distance_unit * COS(RADIANS(p.latpoint))))\n" +
                "         AND p.longpoint + (p.radius / (p.distance_unit * COS(RADIANS(p.latpoint))))\n" +
                "     AND store.local_currency_status = 1 \n" +
                "     AND store.business_status = 1 \n" +
                "     AND store.store_status LIKE \"ACTIVE\"\n" +
                " HAVING distance <= radius\n" +
                " ORDER BY distance\n" +
                " LIMIT :limit OFFSET :offset";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("lat", lat)
                .addValue("lng", lng)
                .addValue("radius", radius)
                .addValue("limit", 10 + 1)
                .addValue("offset", offset);
        List<Map<Long, Double>> list = jdbcTemplate.query(sql, parameters, new StoreMapper());

        return list.stream()
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<Long, Double> getNearStoreIdsByStoreName(
            Double lat,
            Double lng,
            Double radius,
            String storeName,
            Integer offset
    ) {
        String sql = "SELECT  store.store_id AS store_id,\n" +
                "        p.radius AS radius,\n" +
                "        p.distance_unit\n" +
                "                 * DEGREES(ACOS(LEAST(1.0, COS(RADIANS(p.latpoint))\n" +
                "                 * COS(RADIANS(store.lat))\n" +
                "                 * COS(RADIANS(p.longpoint - store.lng))\n" +
                "                 + SIN(RADIANS(p.latpoint))\n" +
                "                 * SIN(RADIANS(store.lat))))) AS distance\n" +
                "  FROM store\n" +
                "  JOIN (   /* these are the query parameters */\n" +
                "        SELECT  :lat  AS latpoint, :lng AS longpoint,\n" +
                "                :radius  AS radius,    111.045 AS distance_unit\n" +
                "  ) AS p \n" +
                "  WHERE store.lat\n" +
                "         BETWEEN p.latpoint  - (p.radius / p.distance_unit)\n" +
                "         AND p.latpoint  + (p.radius / p.distance_unit)\n" +
                "     AND store.lng\n" +
                "         BETWEEN p.longpoint - (p.radius / (p.distance_unit * COS(RADIANS(p.latpoint))))\n" +
                "         AND p.longpoint + (p.radius / (p.distance_unit * COS(RADIANS(p.latpoint))))\n" +
                "     AND store.name LIKE :storeName\n" +
                "    AND store.store_status LIKE \"ACTIVE\" \n" +
                "     AND store.business_status = 1\n" +
                " HAVING distance <= radius\n" +
                " ORDER BY distance\n" +
                " LIMIT :offset, :limit";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("lat", lat)
                .addValue("lng", lng)
                .addValue("radius", radius)
                .addValue("storeName", "%" + storeName + "%")
                .addValue("limit", 10 + 1)
                .addValue("offset", offset);
        List<Map<Long, Double>> list = jdbcTemplate.query(sql, parameters, new StoreMapper());

        return list.stream()
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<Long, Double> getNearStoreIdsByItemName(
            Double lat,
            Double lng,
            Double radius,
            String itemName,
            Integer offset
    ) {
        String sql = "  SELECT DISTINCT(store.store_id) AS store_id, distance\n" +
                "  FROM (\n" +
                "   SELECT store.*,\n" +
                "      p.radius,\n" +
                "      p.distance_unit\n" +
                "           * DEGREES(ACOS(LEAST(1.0, COS(RADIANS(p.latpoint))\n" +
                "           * COS(RADIANS(store.lat))\n" +
                "           * COS(RADIANS(p.longpoint - store.lng))\n" +
                "           + SIN(RADIANS(p.latpoint))\n" +
                "           * SIN(RADIANS(store.lat))))) AS distance\n" +
                "    FROM store\n" +
                "    INNER JOIN (   /* these are the query parameters */\n" +
                "      SELECT  :lat  AS latpoint,  :lng AS longpoint,\n" +
                "          :radius AS radius,      111.045 AS distance_unit\n" +
                "    ) AS p\n" +
                "    WHERE store.lat\n" +
                "     BETWEEN p.latpoint  - (p.radius / p.distance_unit)\n" +
                "       AND p.latpoint  + (p.radius / p.distance_unit)\n" +
                "    AND store.lng\n" +
                "     BETWEEN p.longpoint - (p.radius / (p.distance_unit * COS(RADIANS(p.latpoint))))\n" +
                "       AND p.longpoint + (p.radius / (p.distance_unit * COS(RADIANS(p.latpoint))))\n" +
                "    AND store.store_status LIKE \"ACTIVE\" \n" +
                "    AND store.business_status = 1\n" +
                "   ) AS store\n" +
                " INNER JOIN item i\n" +
                "   ON i.store_id = store.store_id AND i.name LIKE :itemName\n" +
                " WHERE distance <= radius\n" +
                " ORDER BY distance\n" +
                " LIMIT :limit OFFSET :offset";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("lat", lat)
                .addValue("lng", lng)
                .addValue("radius", radius)
                .addValue("itemName", "%" + itemName + "%")
                .addValue("limit", 10 + 1)
                .addValue("offset", offset);

        List<Map<Long, Double>> list = jdbcTemplate.query(sql, parameters, new StoreMapper());
        return list.stream()
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    static class StoreMapper implements RowMapper {
        @Override
        public Map<Long, Double> mapRow(ResultSet rs, int rowNum) throws SQLException {
            LinkedHashMap<Long, Double> mapRet = new LinkedHashMap<>();
            Long storeId = rs.getLong("store_id");
            Double distance = rs.getDouble("distance");
            mapRet.put(storeId, distance);
            return mapRet;
        }
    }
}

