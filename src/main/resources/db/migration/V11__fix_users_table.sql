-- 1. 既存の外部キー制約を削除
ALTER TABLE items DROP CONSTRAINT IF EXISTS fk_items_seller;
ALTER TABLE items DROP CONSTRAINT IF EXISTS fk_items_buyer;

-- 2. カラムの削除と追加をまとめて実行
ALTER TABLE items 
    DROP COLUMN seller_id,
    DROP COLUMN buyer_id,
    DROP COLUMN status,
    ADD COLUMN user_id BIGINT;

-- 3. 新しく追加した user_id に外部キー制約を付与（推奨）
ALTER TABLE items 
    ADD CONSTRAINT fk_items_user FOREIGN KEY (user_id) REFERENCES users(id);