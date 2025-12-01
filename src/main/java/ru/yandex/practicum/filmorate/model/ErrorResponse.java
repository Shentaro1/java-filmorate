package ru.yandex.practicum.filmorate.model;

import lombok.*;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {
    private final String error;
    private final String description;

}
