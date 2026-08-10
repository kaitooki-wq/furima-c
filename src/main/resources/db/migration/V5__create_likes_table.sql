CREATE TABLE likes (
    user_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,

    -- 複合主キー（重複防止）
    PRIMARY KEY (user_id, item_id),

    -- 外部キー制約
    CONSTRAINT fk_likes_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_likes_item FOREIGN KEY (item_id) REFERENCES items(id) ON DELETE CASCADE
);
