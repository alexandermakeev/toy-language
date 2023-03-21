package org.example.toylanguage.context.definition;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.toylanguage.statement.ClassStatement;

import java.util.Set;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ClassDefinition implements Definition {
    @EqualsAndHashCode.Include
    private final ClassDetails classDetails;
    private final Set<ClassDetails> inheritedClasses;
    private final ClassStatement statement;
    private final DefinitionScope definitionScope;
}

