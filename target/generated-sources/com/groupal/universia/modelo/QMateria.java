package com.groupal.universia.modelo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QMateria is a Querydsl query type for Materia
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMateria extends EntityPathBase<Materia> {

    private static final long serialVersionUID = 67703556L;

    public static final QMateria materia = new QMateria("materia");

    public final BooleanPath activo = createBoolean("activo");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath nombre = createString("nombre");

    public QMateria(String variable) {
        super(Materia.class, forVariable(variable));
    }

    public QMateria(Path<? extends Materia> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMateria(PathMetadata metadata) {
        super(Materia.class, metadata);
    }

}

