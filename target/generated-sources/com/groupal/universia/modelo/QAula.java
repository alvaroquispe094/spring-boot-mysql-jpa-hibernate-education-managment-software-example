package com.groupal.universia.modelo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAula is a Querydsl query type for Aula
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAula extends EntityPathBase<Aula> {

    private static final long serialVersionUID = -1467986454L;

    public static final QAula aula = new QAula("aula");

    public final BooleanPath activo = createBoolean("activo");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath nombre = createString("nombre");

    public QAula(String variable) {
        super(Aula.class, forVariable(variable));
    }

    public QAula(Path<? extends Aula> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAula(PathMetadata metadata) {
        super(Aula.class, metadata);
    }

}

