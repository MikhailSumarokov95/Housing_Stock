package ru.sumarokov.housing_stock.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;
import ru.sumarokov.housing_stock.entity.House;

public class HouseDto {

    @Range(min = 0, max = 9223372036854775807L, message = "Поле \"id\" должно находится в пределах от 0 до 9223372036854775807")
    private final Long id;
    @NotEmpty(message = "Поле \"Адрес\" должно быть заполнено")
    @Size(min = 0, max = 64, message = "Поле \"Адрес\" должно быть длиной от 0 до 64 символов")
    private final String address;
    @Range(min = 0, max = 9223372036854775807L, message = "Поле \"id владельца\" должно находится в пределах от 0 до 9223372036854775807")
    private final Long ownerId;

    public HouseDto(Long id,
                    String address,
                    Long ownerId) {
        this.id = id;
        this.address = address;
        this.ownerId = ownerId;
    }

    public Long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public House toEntity() {
        return new House(id, address, ownerId);
    }

    public static HouseDto toDto(House entity) {
        return new HouseDto(entity.getId(),
                entity.getAddress(),
                entity.getOwnerId());
    }
}
