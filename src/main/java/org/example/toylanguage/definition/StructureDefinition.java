package org.example.toylanguage.definition;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class StructureDefinition implements Definition {
    @EqualsAndHashCode.Include
    private final String name;
    private final List<String> arguments;
}

