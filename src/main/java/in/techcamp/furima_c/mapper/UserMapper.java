package in.techcamp.furima_c.mapper;

import in.techcamp.furima_c.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    // メアドを使ってユーザーを検索
    @Select("SELECT * FROM users WHERE email = #{email}")
    UserEntity findByEmail(String email);

    //メアドが既存のものなのか調べる
    @Select("SELECT EXISTS(SELECT 1 FROM users WHERE email = #{email})")
    boolean existsByEmail(String email);

    // アカウントの保存
    @Insert("INSERT INTO users (nickname, email, password, last_name, first_name, last_name_kana, first_name_kana, birthday) " +
            "VALUES (#{nickname}, #{email}, #{password}, #{lastName}, #{firstName}, #{lastNameKana}, #{firstNameKana}, #{birthday})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(UserEntity user);
}
