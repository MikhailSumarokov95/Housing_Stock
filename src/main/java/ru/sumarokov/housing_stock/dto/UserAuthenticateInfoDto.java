package ru.sumarokov.housing_stock.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import ru.sumarokov.housing_stock.entity.User;

public class UserAuthenticateInfoDto {

    @NotEmpty(message = "Поле \"Имя\" должно быть заполнено")
    @Size(min = 0, max = 64, message = "Поле \"Имя\" должно быть длиной от 0 до 64 символов")
    private String name;
    @NotEmpty(message = "Поле \"Пароль\" должно быть заполнено")
    @Size(min = 0, max = 64, message = "Поле \"Пароль\" должно быть длиной от 0 до 64 символов")
    private String password;

    public User toEntity() {
        return new User(name, password);
    }
}
