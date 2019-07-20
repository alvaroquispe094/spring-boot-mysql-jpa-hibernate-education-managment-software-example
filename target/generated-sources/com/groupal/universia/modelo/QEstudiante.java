package com.groupal.universia.modelo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEstudiante is a Querydsl query type for Estudiante
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QEstudiante extends EntityPathBase<Estudiante> {

    private static final long serialVersionUID = -1660835245L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEstudiante estudiante = new QEstudiante("estudiante");

    public final BooleanPath activo = createBoolean("activo");

    public final StringPath apellido = createString("apellido");

    public final QCarrera carrera;

    public final StringPath direccion = createString("direccion");

    public final NumberPath<Integer> documento = createNumber("documento", Integer.class);

    public final DateTimePath<java.util.Date> fecha = createDateTime("fecha", java.util.Date.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath mail = createString("mail");

    public final StringPath nombre = createString("nombre");

    public final StringPath password = createString("password");

    public final StringPath telefono = createString("telefono");

    public final StringPath username = createString("username");

    public QEstudiante(String variable) {
        this(Estudiante.class, forVariable(variable), INITS);
    }

    public QEstudiante(Path<? extends Estudiante> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEstudiante(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEstudiante(PathMetadata metadata, PathInits inits) {
        this(Estudiante.class, metadata, inits);
    }

    public QEstudiante(Class<? extends Estudiante> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.carrera = inits.isInitialized("carrera") ? new QCarrera(forProperty("carrera")) : null;
    }

}

