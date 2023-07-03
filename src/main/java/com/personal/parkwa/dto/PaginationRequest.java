package com.personal.parkwa.dto;

import com.personal.parkwa.entity.Server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PaginationRequest {
    private int page = 0;
    private int size = 10;
    private Server server;
    private String keyword;
    private String searchBy;
}
