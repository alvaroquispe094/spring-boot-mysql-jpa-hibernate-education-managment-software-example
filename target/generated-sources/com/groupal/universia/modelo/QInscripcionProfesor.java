package com.groupal.universia.modelo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInscripcionProfesor is a Querydsl query type for InscripcionProfesor
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QInscripcionProfesor extends EntityPathBase<InscripcionProfesor> {

    private static final long serialVersionUID = -779434190L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInscripcionProfesor inscripcionProfesor = new QInscripcionProfesor("inscripcionProfesor");

    public final BooleanPath activo = createBoolean("activo");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QInscripcion inscripcion;

    public final QProfesor profesor;

    public QInscripcionProfesor(String variable) {
        this(InscripcionProfesor.class, forVariable(variable), INITS);
    }

    public QInscripcionProfesor(Path<? extends InscripcionProfesor> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInscripcionProfesor(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInscripcionProfesor(PathMetadata metadata, PathInits inits) {
        this(InscripcionProfesor.class, metadata, inits);
    }

    public QInscripcionProfesor(Class<? extends InscripcionProfesor> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.inscripcion = inits.isInitialized("inscripcion") ? new QInscripcion(forProperty("inscripcion"), inits.get("inscripcion")) : null;
        this.profesor = inits.isInitialized("profesor") ? new QProfesor(forProperty("profesor")) : null;
    }

}

