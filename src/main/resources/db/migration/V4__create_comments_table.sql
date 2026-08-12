CREATE TABLE comments (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    content VARCHAR(512) NOT NULL,

    -- 外部キー制約
    CONSTRAINT fk_comments_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_comments_item FOREIGN KEY (item_id) REFERENCES items(id) ON DELETE CASCADE
);
