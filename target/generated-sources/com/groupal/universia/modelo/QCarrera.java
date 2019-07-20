package com.groupal.universia.modelo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCarrera is a Querydsl query type for Carrera
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCarrera extends EntityPathBase<Carrera> {

    private static final long serialVersionUID = -218870635L;

    public static final QCarrera carrera = new QCarrera("carrera");

    public final BooleanPath activo = createBoolean("activo");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath nombre = createString("nombre");

    public QCarrera(String variable) {
        super(Carrera.class, forVariable(variable));
    }

    public QCarrera(Path<? extends Carrera> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCarrera(PathMetadata metadata) {
        super(Carrera.class, metadata);
    }

}

