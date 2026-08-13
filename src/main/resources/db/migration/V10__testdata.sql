-- 1. users テーブルのテストデータ
-- 3人のユーザー（山田太郎、鈴木花子、佐藤次郎）を作成
INSERT INTO users (nickname, email, password, last_name, first_name, last_name_kana, first_name_kana, birthday) 
VALUES
('たろう', 'taro@example.com', 'hashed_password_1', '山田', '太郎', 'ヤマダ', 'タロウ', '1990-01-01'),
('はなこ', 'hanako@example.com', 'hashed_password_2', '鈴木', '花子', 'スズキ', 'ハナコ', '1992-05-15'),
('じろう', 'jiro@example.com', 'hashed_password_3', '佐藤', '次郎', 'サトウ', 'ジロウ', '1988-11-20');


-- 2. items テーブルのテストデータ
-- id=1: 出品中（未購入）の商品
-- id=2: ユーザー1(山田)がユーザー2(鈴木)から購入した商品
-- id=3: ユーザー3(佐藤)がユーザー1(山田)から購入した商品
INSERT INTO items (seller_id, buyer_id, name, description, category_id, condition, shipping_payer, prefecture_id, shipping_days, price, status, image) 
VALUES
(1, NULL, 'ノートPC', '中古のノートパソコンです。動作確認済み。', 1, 3, 1, 13, 2, 45000, 0, 'sample_pc.jpg'),
(2, 1, '限定スニーカー', '新品未着用のスニーカーです。', 2, 1, 2, 27, 1, 15000, 1, 'sample_sneaker.jpg'),
(1, 3, 'ビジネス書セット', '人気ビジネス書5冊セット。書き込みなし。', 3, 2, 1, 13, 3, 3000, 1, 'sample_book.jpg');

-- 3. buy テーブルのテストデータ
-- ※ itemsの取引状況に合わせて、誰がどの商品を買ったかの履歴を作成
-- id=1: ユーザー1(山田) が 商品2(スニーカー) を購入
-- id=2: ユーザー3(佐藤) が 商品3(ビジネス書) を購入
INSERT INTO buy (user_id, item_id) 
VALUES
(1, 2),
(3, 3);


-- 4. address テーブルのテストデータ
-- ※ 購入履歴（buy_id）に紐づく配送先住所
INSERT INTO address (buy_id, post_number, prefecture, city, block, building, phone) 
VALUES
(1, '154-0015', 13, '世田谷区', '桜新町1-1-1', 'サクラマンション201', '090-1234-5678'),
(2, '540-0002', 27, '大阪市', '中央区大阪城1-1', NULL, '080-8765-4321');