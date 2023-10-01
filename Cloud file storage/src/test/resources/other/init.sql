CREATE SCHEMA IF NOT EXISTS <schema name>;

DROP ALIAS IF EXISTS <schema name>.custom_to_number;
CREATE ALIAS <schema name>.custom_to_number AS $$
Long customToNumber(String str) {
    return Long.valueOf(str);
}
$$;
