package ru.sumarokov.housing_stock.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;
import ru.sumarokov.housing_stock.entity.User;

public class UserRegistrationInfoDto {

    @NotEmpty(message = "Поле \"Имя\" должно быть заполнено")
    @Size(min = 0, max = 64, message = "Поле \"Имя\" должно быть длиной от 0 до 64 символов")
    private final String name;
    @Min(value = 0, message = "Поле \"Возраст\" не может быть отрицательной велииной")
    @Range(min = 0, max = 2147483647, message = "Поле \"возраст\" должно находится в пределах от 0 до 2147483647")
    private final Integer age;
    @NotEmpty(message = "Поле \"Пароль\" должно быть заполнено")
    @Size(min = 0, max = 64, message = "Поле \"Пароль\" должно быть длиной от 0 до 64 символов")
    private final String password;

    public UserRegistrationInfoDto(String name, Integer age, String password) {
        this.name = name;
        this.age = age;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getPassword() {
        return password;
    }

    public User toEntity() {
        return new User(name, age, password);
    }
}
