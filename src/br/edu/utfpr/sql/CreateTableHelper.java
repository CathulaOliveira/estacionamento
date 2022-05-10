package br.edu.utfpr.sql;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateTableHelper {
    private final String createTable;
    private final String dropTable;
}
