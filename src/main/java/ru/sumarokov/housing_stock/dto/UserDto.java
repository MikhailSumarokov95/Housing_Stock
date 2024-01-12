package ru.sumarokov.housing_stock.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;
import ru.sumarokov.housing_stock.entity.User;

public class UserDto {

    @Range(min = 0, max = 9223372036854775807L, message = "Поле \"id\" должно находится в пределах от 0 до 9223372036854775807")
    private final Long id;
    @NotEmpty(message = "Поле \"Имя\" должно быть заполнено")
    @Size(min = 0, max = 64, message = "Поле \"Имя\" должно быть длиной от 0 до 64 символов")
    private final String name;
    @Min(value = 0, message = "Поле \"Возраст\" не может быть отрицательной велииной")
    @Range(min = 0, max = 2147483647, message = "Поле \"возраст\" должно находится в пределах от 0 до 2147483647")
    private final Integer age;
    @Range(min = 0, max = 9223372036854775807L, message = "Поле \"id\" должно находится в пределах от 0 до 9223372036854775807")
    private final Long houseId;

    public UserDto(Long id, String name, Integer age, Long houseId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.houseId = houseId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Long getHouseId() {
        return houseId;
    }

    public User toEntity() {
        return new User(id, name, age, houseId);
    }

    public static UserDto toDto(User entity) {
        return new UserDto(entity.getId(),
                entity.getName(),
                entity.getAge(),
                entity.getHouseId());
    }
}
