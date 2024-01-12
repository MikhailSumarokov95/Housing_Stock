package ru.sumarokov.housing_stock.dto;

public class UserDto {

    private final Long id;
    private final String name;
    private final Integer age;
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
}
