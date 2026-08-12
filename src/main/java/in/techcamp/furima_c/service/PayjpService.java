package in.techcamp.furima_c.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class PayjpService {

    // アプリケーションの設定ファイル（application.propertiesなど）から、PAY.JPの「秘密鍵（パスワードのようなもの）」を読み込んで secretKey に代入します
    @Value("${payjp.secret-key}")
    private String secretKey;

    // 実際の決済処理を行うメソッド（機能）です。金額（amount）とカード情報の暗号（token）を受け取ります
    public void charge(int amount, String token) {
        
        // 外部のAPI（今回はPAY.JP）と通信するための道具を準備します 今RestTemplateは少し古いRestClientとかwebClietを使用
        RestTemplate restTemplate = new RestTemplate();

        // PAY.JPに「私は正しい利用者です」と証明するための「Basic認証」のパスワードを作成します
        // PAY.JPのルールに従い、「秘密鍵:（コロン）」という文字列を作ります
        String auth = secretKey + ":";
        // その文字列をBase64というルールで暗号化（エンコード）します
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));

        // 通信の「ヘッダー（手紙の封筒の表面のようなもの）」を準備します
        HttpHeaders headers = new HttpHeaders();
        // 封筒に「認証情報」を貼り付けます
        headers.set("Authorization", "Basic " + encodedAuth);
        // 封筒に「中身のデータ形式はフォーム送信（URLエンコード）です」と書き込みます
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 通信の「ボディ（手紙の便箋＝中身）」を準備します
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        // 中身に「金額」「カードのトークン」「通貨（日本円＝jpy）」を書き込みます
        body.add("amount", String.valueOf(amount));
        body.add("card", token);
        body.add("currency", "jpy");

        // ヘッダー（封筒）とボディ（便箋）を合体させて、一つの「リクエスト（手紙）」を完成させます
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        // エラーが起きるかもしれない処理を try { } で囲みます
        try {
            // PAY.JPのURLに向けて、POST（送信）を行います
            restTemplate.postForEntity(
                    "https://api.pay.jp/v1/charges", // 送り先のURL
                    request,                         // 送るデータ（手紙）
                    String.class        // 返ってくる返事の形式（文字列）
            );
        } catch (Exception e) {
            // もし通信失敗などのエラー（例外）が起きたら、ここでキャッチして強制終了させ、エラーメッセージを出します
            throw new RuntimeException("決済処理に失敗しました: " + e.getMessage(), e);
        }
    }
}