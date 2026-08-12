CREATE TABLE items (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    seller_id BIGINT NOT NULL,
    buyer_id BIGINT,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    category_id BIGINT NOT NULL,
    condition SMALLINT NOT NULL,
    shipping_payer SMALLINT NOT NULL,
    prefecture_id SMALLINT NOT NULL,
    shipping_days SMALLINT NOT NULL,
    price INTEGER NOT NULL,
    status SMALLINT NOT NULL DEFAULT 0,

    -- 外部キー制約
    CONSTRAINT fk_items_seller FOREIGN KEY (seller_id) REFERENCES users(id),
    CONSTRAINT fk_items_buyer FOREIGN KEY (buyer_id) REFERENCES users(id)
);
