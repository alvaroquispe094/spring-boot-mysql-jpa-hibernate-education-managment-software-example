package com.groupal.universia.modelo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCarreraMateria is a Querydsl query type for CarreraMateria
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCarreraMateria extends EntityPathBase<CarreraMateria> {

    private static final long serialVersionUID = 2001043856L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCarreraMateria carreraMateria = new QCarreraMateria("carreraMateria");

    public final BooleanPath activo = createBoolean("activo");

    public final QCarrera carrera;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QMateria materia;

    public QCarreraMateria(String variable) {
        this(CarreraMateria.class, forVariable(variable), INITS);
    }

    public QCarreraMateria(Path<? extends CarreraMateria> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCarreraMateria(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCarreraMateria(PathMetadata metadata, PathInits inits) {
        this(CarreraMateria.class, metadata, inits);
    }

    public QCarreraMateria(Class<? extends CarreraMateria> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.carrera = inits.isInitialized("carrera") ? new QCarrera(forProperty("carrera")) : null;
        this.materia = inits.isInitialized("materia") ? new QMateria(forProperty("materia")) : null;
    }

}

