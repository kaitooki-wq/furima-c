CREATE TABLE addresses (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name_kana VARCHAR(255) NOT NULL,
    first_name_kana VARCHAR(255) NOT NULL,
    postal_code VARCHAR(10) NOT NULL,
    prefecture_id SMALLINT NOT NULL,
    city VARCHAR(255) NOT NULL,
    block VARCHAR(255) NOT NULL,
    building VARCHAR(255),
    phone_number VARCHAR(20) NOT NULL,

    -- 外部キー制約
    CONSTRAINT fk_addresses_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
